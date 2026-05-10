package com.menufit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.menufit.app.data.repository.RecipeRepository
import com.menufit.app.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                recipeRepository.initializeSeedData()
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement des recettes"
                _isLoading.value = false
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getRecipes(): Flow<List<Recipe>> {
        return if (_searchQuery.value.isBlank()) {
            recipeRepository.getAllRecipes().mapToRecipeList()
        } else {
            recipeRepository.searchRecipes(_searchQuery.value).mapToRecipeList()
        }
    }

    private fun Flow<List<com.menufit.app.data.database.entity.RecipeEntity>>.mapToRecipeList(): Flow<List<Recipe>> {
        return kotlinx.coroutines.flow.map { recipes ->
            recipes.map { it.toRecipe() }
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
}
