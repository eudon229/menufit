package com.menufit.app.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.menufit.app.data.database.entity.CollectionEntity
import com.menufit.app.data.database.entity.CollectionRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Insert
    suspend fun insertCollection(collection: CollectionEntity): Long

    @Update
    suspend fun updateCollection(collection: CollectionEntity)

    @Delete
    suspend fun deleteCollection(collection: CollectionEntity)

    @Query("SELECT * FROM collections ORDER BY createdAt DESC")
    fun getAllCollections(): Flow<List<CollectionEntity>>

    @Query("SELECT * FROM collections WHERE id = :collectionId")
    fun getCollectionById(collectionId: Long): Flow<CollectionEntity?>

    @Insert
    suspend fun addRecipeToCollection(relation: CollectionRecipeEntity)

    @Delete
    suspend fun removeRecipeFromCollection(relation: CollectionRecipeEntity)

    @Query("""
        SELECT recipe_id FROM collection_recipes
        WHERE collection_id = :collectionId
        ORDER BY sortOrder ASC
    """)
    fun getRecipesInCollection(collectionId: Long): Flow<List<Long>>
}
