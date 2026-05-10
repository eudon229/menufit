package com.menufit.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.menufit.app.data.database.dao.RecipeDao
import com.menufit.app.data.database.dao.CollectionDao
import com.menufit.app.data.database.entity.RecipeEntity
import com.menufit.app.data.database.entity.IngredientEntity
import com.menufit.app.data.database.entity.StepEntity
import com.menufit.app.data.database.entity.TechniqueEntity
import com.menufit.app.data.database.entity.CollectionEntity
import com.menufit.app.data.database.entity.RecipeTechniqueEntity
import com.menufit.app.data.database.entity.CollectionRecipeEntity
import com.menufit.app.data.database.entity.HistoryEntity

@Database(
    entities = [
        RecipeEntity::class,
        IngredientEntity::class,
        StepEntity::class,
        TechniqueEntity::class,
        RecipeTechniqueEntity::class,
        CollectionEntity::class,
        CollectionRecipeEntity::class,
        HistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MenufitDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun collectionDao(): CollectionDao

    companion object {
        @Volatile
        private var INSTANCE: MenufitDatabase? = null

        fun getDatabase(context: Context): MenufitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MenufitDatabase::class.java,
                    "menufit_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
