package com.menufit.app.domain.model

data class Recipe(
    val id: Long,
    val title: String,
    val description: String,
    val difficulty: String,
    val durationMinutes: Int,
    val servingsBase: Int,
    val photoPath: String?,
    val sourceUrl: String?
)

data class Ingredient(
    val id: Long,
    val name: String,
    val quantity: Double,
    val unit: String,
    val sortOrder: Int
)

data class Step(
    val id: Long,
    val instruction: String,
    val durationSeconds: Int,
    val sortOrder: Int
)

data class Technique(
    val id: Long,
    val name: String,
    val description: String,
    val videoPath: String?
)

data class RecipeDetail(
    val recipe: Recipe,
    val ingredients: List<Ingredient>,
    val steps: List<Step>,
    val techniques: List<Technique>
)
