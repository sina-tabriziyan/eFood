package com.sina.efood.data.repository

import com.sina.efood.core.errors.DataError
import com.sina.efood.data.local.LocalDataSource
import com.sina.efood.core.local.entities.RecipesEntity
import com.sina.efood.data.AppResult
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.data.openLocalResponse
import com.sina.efood.data.remote.RemoteDataSource
import com.sina.efood.domain.repository.IRecipesRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class RecipesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) : IRecipesRepository {

    override suspend fun getAndStoreRecipes(queries: Map<String, String>): Flow<AppResult<RecipesDto, DataError>> =
        flow {
            when (val openLocalResponse: AppResult<List<RecipesEntity>, DataError.Local> =
                localDataSource.readRecipes.openLocalResponse()) {
                is AppResult.Success -> if (openLocalResponse.data.isEmpty()) {
                    when (val networkAppResult: AppResult<RecipesDto, DataError.Network> =
                        remoteDataSource.getRecipes(queries)) {
                        is AppResult.Success -> {
                            localDataSource.saveRecipes(RecipesEntity(networkAppResult.data))
                            emit(AppResult.Success(networkAppResult.data))
                        }

                        is AppResult.Error -> emit(AppResult.Error(networkAppResult.error))
                    }
                } else emit(AppResult.Success(openLocalResponse.data.first().recipesDto))

                is AppResult.Error -> emit(AppResult.Error(openLocalResponse.error))
            }
        }
}

