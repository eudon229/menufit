package com.menufit.app.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val difficulty: String, // "Facile", "Intermédiaire", "Avancé"
    val durationMinutes: Int,
    val servingsBase: Int,
    val photoPath: String?,
    val sourceUrl: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recipeId: Long,
    val name: String,
    val quantity: Double,
    val unit: String, // "g", "ml", "pincée", etc.
    val sortOrder: Int
)

@Entity(tableName = "steps")
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recipeId: Long,
    val instruction: String,
    val durationSeconds: Int,
    val sortOrder: Int
)

@Entity(tableName = "techniques")
data class TechniqueEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val videoPath: String?
)

@Entity(
    tableName = "recipe_techniques",
    primaryKeys = ["recipeId", "techniqueId"]
)
data class RecipeTechniqueEntity(
    val recipeId: Long,
    val techniqueId: Long
)

@Entity(tableName = "collections")
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val color: String, // hex color
    val createdAt: LocalDateTime = LocalDateTime.now()
)

@Entity(
    tableName = "collection_recipes",
    primaryKeys = ["collectionId", "recipeId"]
)
data class CollectionRecipeEntity(
    val collectionId: Long,
    val recipeId: Long,
    val sortOrder: Int = 0
)

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recipeId: Long,
    val openedAt: LocalDateTime,
    val cookedAt: LocalDateTime? = null
)
