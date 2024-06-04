package com.sina.efood.data.repository

import com.sina.efood.core.errors.DataError
import com.sina.efood.core.local.entities.RecipesEntity
import com.sina.efood.core.remote.connectivity.NetworkListener
import com.sina.efood.core.remote.dto.RecipeDto
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.data.AppResult
import com.sina.efood.data.local.LocalDataSource
import com.sina.efood.data.openLocalResponse
import com.sina.efood.data.remote.RemoteDataSource
import com.sina.efood.domain.repository.IRecipesRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class RecipesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val networkListener: NetworkListener,
) : IRecipesRepository {

    override suspend fun getAndStoreRecipes(queries: Map<String, String>): Flow<AppResult<RecipesDto, DataError>> =
        flow<AppResult<RecipesDto, DataError>> {
            if (networkListener.checkNetworkAvailability().value) {
                when (val networkAppResult: AppResult<RecipesDto, DataError.Network> =
                    remoteDataSource.getRecipes(queries)) {
                    is AppResult.Success -> {
                        localDataSource.saveRecipes(RecipesEntity(networkAppResult.data))
                        emit(AppResult.Success(networkAppResult.data))
                    }

                    is AppResult.Error -> emit(AppResult.Error(networkAppResult.error))
                }
            } else {
                when (val openLocalResponse: AppResult<List<RecipesEntity>, DataError.Local> =
                    localDataSource.readRecipes.openLocalResponse()) {
                    is AppResult.Success -> {
                        if (openLocalResponse.data.isEmpty()) emit(AppResult.Error(DataError.Network.NO_INTERNET))
                        else emit(AppResult.Success(openLocalResponse.data.first().recipesDto))
                    }

                    is AppResult.Error -> emit(AppResult.Error(openLocalResponse.error))
                }
            }
        }.flowOn(dispatcher)


    override suspend fun getRecipesById(recipeId: Int) : Flow<AppResult<RecipeDto, DataError>> = flow<AppResult<RecipeDto, DataError>> {
        if (networkListener.checkNetworkAvailability().value) {
            when(val networkResult = remoteDataSource.getRecipeById(recipeId)){
                is AppResult.Error -> emit(AppResult.Error(networkResult.error))
                is AppResult.Success -> {
                    emit(AppResult.Success(networkResult.data))
                }
            }
        }
    }
}
