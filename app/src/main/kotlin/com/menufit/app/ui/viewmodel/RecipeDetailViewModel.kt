package com.menufit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.menufit.app.data.repository.RecipeRepository
import com.menufit.app.domain.model.Ingredient
import com.menufit.app.domain.model.Recipe
import com.menufit.app.domain.model.RecipeDetail
import com.menufit.app.domain.model.Step
import com.menufit.app.domain.model.Technique
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _recipeDetail = MutableStateFlow<RecipeDetail?>(null)
    val recipeDetail: StateFlow<RecipeDetail?> = _recipeDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _currentServings = MutableStateFlow(0)
    val currentServings: StateFlow<Int> = _currentServings.asStateFlow()

    fun loadRecipe(recipeId: Long) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                recipeRepository.getRecipeById(recipeId)
                    .combine(recipeRepository.getIngredientsForRecipe(recipeId)) { recipe, ingredients ->
                        Pair(recipe, ingredients)
                    }
                    .combine(recipeRepository.getStepsForRecipe(recipeId)) { (recipe, ingredients), steps ->
                        Triple(recipe, ingredients, steps)
                    }
                    .combine(recipeRepository.getTechniquesForRecipe(recipeId)) { (recipe, ingredients, steps), techniques ->
                        Quadruple(recipe, ingredients, steps, techniques)
                    }
                    .collect { (recipe, ingredients, steps, techniques) ->
                        if (recipe != null) {
                            val detail = RecipeDetail(
                                recipe = recipe.toRecipe(),
                                ingredients = ingredients.map { it.toIngredient() },
                                steps = steps.map { it.toStep() },
                                techniques = techniques.map { it.toTechnique() }
                            )
                            _recipeDetail.value = detail
                            _currentServings.value = recipe.servingsBase
                            _isLoading.value = false
                        }
                    }
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement de la recette: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun updateServings(servings: Int) {
        _currentServings.value = servings
    }

    fun getScaledIngredients(): List<Ingredient> {
        val detail = _recipeDetail.value ?: return emptyList()
        val factor = _currentServings.value.toDouble() / detail.recipe.servingsBase.toDouble()
        return detail.ingredients.map { ingredient ->
            ingredient.copy(
                quantity = (ingredient.quantity * factor).let { q ->
                    "%.2f".format(q).toDouble()
                }
            )
        }
    }

    private fun com.menufit.app.data.database.entity.RecipeEntity.toRecipe() = Recipe(
        id = this.id,
        title = this.title,
        description = this.description,
        difficulty = this.difficulty,
        durationMinutes = this.durationMinutes,
        servingsBase = this.servingsBase,
        photoPath = this.photoPath,
        sourceUrl = this.sourceUrl
    )

    private fun com.menufit.app.data.database.entity.IngredientEntity.toIngredient() = Ingredient(
        id = this.id,
        name = this.name,
        quantity = this.quantity,
        unit = this.unit,
        sortOrder = this.sortOrder
    )

    private fun com.menufit.app.data.database.entity.StepEntity.toStep() = Step(
        id = this.id,
        instruction = this.instruction,
        durationSeconds = this.durationSeconds,
        sortOrder = this.sortOrder
    )

    private fun com.menufit.app.data.database.entity.TechniqueEntity.toTechnique() = Technique(
        id = this.id,
        name = this.name,
        description = this.description,
        videoPath = this.videoPath
    )
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
