package com.menufit.app.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.menufit.app.data.database.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipeById(recipeId: Long): Flow<RecipeEntity?>

    @Query("SELECT * FROM recipes ORDER BY title ASC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("""
        SELECT * FROM recipes
        WHERE title LIKE '%' || :query || '%'
           OR description LIKE '%' || :query || '%'
        ORDER BY title ASC
    """)
    fun searchRecipes(query: String): Flow<List<RecipeEntity>>

    @Query("""
        SELECT * FROM recipes
        WHERE difficulty = :difficulty
        ORDER BY title ASC
    """)
    fun getRecipesByDifficulty(difficulty: String): Flow<List<RecipeEntity>>

    @Query("""
        SELECT * FROM recipes
        WHERE durationMinutes < :maxDuration
        ORDER BY durationMinutes ASC
    """)
    fun getRecipesByDuration(maxDuration: Int): Flow<List<RecipeEntity>>
}
