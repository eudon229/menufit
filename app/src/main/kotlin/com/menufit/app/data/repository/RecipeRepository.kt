package com.menufit.app.data.repository

import com.menufit.app.data.database.dao.RecipeDao
import com.menufit.app.data.database.dao.IngredientDao
import com.menufit.app.data.database.dao.StepDao
import com.menufit.app.data.database.dao.TechniqueDao
import com.menufit.app.data.database.dao.RecipeTechniqueDao
import com.menufit.app.data.database.entity.RecipeEntity
import com.menufit.app.data.database.entity.IngredientEntity
import com.menufit.app.data.database.entity.StepEntity
import com.menufit.app.data.database.entity.TechniqueEntity
import com.menufit.app.data.seeddata.SeedDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    private val stepDao: StepDao,
    private val techniqueDao: TechniqueDao,
    private val recipeTechniqueDao: RecipeTechniqueDao
) {
    private var isSeeded = false

    suspend fun initializeSeedData() {
        if (isSeeded) return

        try {
            // Check if data already exists
            val existing = recipeDao.getAllRecipes().first()
            if (existing.isNotEmpty()) {
                isSeeded = true
                return
            }
        } catch (e: Exception) {
            // Continue with seeding
        }

        // Insert seed data
        SeedDataProvider.getRecipes().forEach { recipe ->
            recipeDao.insertRecipe(recipe)
        }

        SeedDataProvider.getIngredients().forEach { ingredient ->
            ingredientDao.insertIngredient(ingredient)
        }

        SeedDataProvider.getSteps().forEach { step ->
            stepDao.insertStep(step)
        }

        SeedDataProvider.getTechniques().forEach { technique ->
            techniqueDao.insertTechnique(technique)
        }

        SeedDataProvider.getRecipeTechniques().forEach { relation ->
            recipeTechniqueDao.insertRecipeTechnique(relation)
        }

        isSeeded = true
    }

    fun getAllRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getAllRecipes()
    }

    fun searchRecipes(query: String): Flow<List<RecipeEntity>> {
        return if (query.isBlank()) {
            getAllRecipes()
        } else {
            recipeDao.searchRecipes(query)
        }
    }

    fun getRecipeById(recipeId: Long): Flow<RecipeEntity?> {
        return recipeDao.getRecipeById(recipeId)
    }

    fun getIngredientsForRecipe(recipeId: Long): Flow<List<IngredientEntity>> {
        return ingredientDao.getIngredientsForRecipe(recipeId)
    }

    fun getStepsForRecipe(recipeId: Long): Flow<List<StepEntity>> {
        return stepDao.getStepsForRecipe(recipeId)
    }

    fun getTechniquesForRecipe(recipeId: Long): Flow<List<TechniqueEntity>> {
        return techniqueDao.getTechniquesForRecipe(recipeId)
    }

    fun getRecipesByDifficulty(difficulty: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByDifficulty(difficulty)
    }

    fun getRecipesByDuration(maxDuration: Int): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByDuration(maxDuration)
    }
}
