package com.menufit.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.menufit.app.data.database.entity.IngredientEntity
import com.menufit.app.data.database.entity.RecipeTechniqueEntity
import com.menufit.app.data.database.entity.StepEntity
import com.menufit.app.data.database.entity.TechniqueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Insert
    suspend fun insertIngredient(ingredient: IngredientEntity)

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId ORDER BY sortOrder ASC")
    fun getIngredientsForRecipe(recipeId: Long): Flow<List<IngredientEntity>>
}

@Dao
interface StepDao {
    @Insert
    suspend fun insertStep(step: StepEntity)

    @Query("SELECT * FROM steps WHERE recipeId = :recipeId ORDER BY sortOrder ASC")
    fun getStepsForRecipe(recipeId: Long): Flow<List<StepEntity>>
}

@Dao
interface TechniqueDao {
    @Insert
    suspend fun insertTechnique(technique: TechniqueEntity)

    @Query("SELECT * FROM techniques ORDER BY name ASC")
    fun getAllTechniques(): Flow<List<TechniqueEntity>>

    @Query("SELECT * FROM techniques WHERE id IN (SELECT techniqueId FROM recipe_techniques WHERE recipeId = :recipeId)")
    fun getTechniquesForRecipe(recipeId: Long): Flow<List<TechniqueEntity>>
}

@Dao
interface RecipeTechniqueDao {
    @Insert
    suspend fun insertRecipeTechnique(relation: RecipeTechniqueEntity)
}
