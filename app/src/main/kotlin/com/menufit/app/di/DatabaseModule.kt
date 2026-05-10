package com.menufit.app.di

import android.content.Context
import androidx.room.Room
import com.menufit.app.data.database.MenufitDatabase
import com.menufit.app.data.database.dao.RecipeDao
import com.menufit.app.data.database.dao.CollectionDao
import com.menufit.app.data.database.dao.IngredientDao
import com.menufit.app.data.database.dao.StepDao
import com.menufit.app.data.database.dao.TechniqueDao
import com.menufit.app.data.database.dao.RecipeTechniqueDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMenufitDatabase(
        @ApplicationContext context: Context
    ): MenufitDatabase {
        return Room.databaseBuilder(
            context,
            MenufitDatabase::class.java,
            "menufit_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(database: MenufitDatabase): RecipeDao {
        return database.recipeDao()
    }

    @Singleton
    @Provides
    fun provideCollectionDao(database: MenufitDatabase): CollectionDao {
        return database.collectionDao()
    }

    @Singleton
    @Provides
    fun provideIngredientDao(database: MenufitDatabase): IngredientDao {
        return database.ingredientDao()
    }

    @Singleton
    @Provides
    fun provideStepDao(database: MenufitDatabase): StepDao {
        return database.stepDao()
    }

    @Singleton
    @Provides
    fun provideTechniqueDao(database: MenufitDatabase): TechniqueDao {
        return database.techniqueDao()
    }

    @Singleton
    @Provides
    fun provideRecipeTechniqueDao(database: MenufitDatabase): RecipeTechniqueDao {
        return database.recipeTechniqueDao()
    }
}
