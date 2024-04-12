package com.sina.efood.core.di.module

import android.app.Application
import androidx.room.Room
import com.sina.efood.core.di.annotation.DatabaseName
import com.sina.efood.core.local.RecipesDao
import com.sina.efood.core.local.RecipesDatabase
import com.sina.efood.core.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    @DatabaseName
    fun provideDatabaseName(): String = DATABASE_NAME

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
        @DatabaseName dataBase: String
    ): RecipesDatabase =
        Room.databaseBuilder(application, RecipesDatabase::class.java, dataBase)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(dataBase: RecipesDatabase): RecipesDao = dataBase.recipeDao()


}