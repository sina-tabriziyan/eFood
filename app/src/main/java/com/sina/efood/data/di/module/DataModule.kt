package com.sina.efood.data.di.module

import com.sina.efood.core.di.annotation.IODispatcher
import com.sina.efood.core.local.RecipesDao
import com.sina.efood.core.remote.FoodService
import com.sina.efood.core.remote.connectivity.NetworkListener
import com.sina.efood.data.local.LocalDataSource
import com.sina.efood.data.remote.RemoteDataSource
import com.sina.efood.data.repository.RecipesRepository
import com.sina.efood.domain.repository.IRecipesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideLocalDataSource(recipesDao: RecipesDao): LocalDataSource =
        LocalDataSource(recipesDao)

    @Provides
    fun provideRemoteDataSource(foodService: FoodService): RemoteDataSource =
        RemoteDataSource(foodService)


    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        networkListener: NetworkListener
    ): IRecipesRepository = RecipesRepository(remoteDataSource, localDataSource, ioDispatcher, networkListener)


}