package com.sina.efood.data

import com.sina.efood.data.local.LocalDataSource
import com.sina.efood.data.models.FoodResponse
import com.sina.efood.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getRecipes(queries: Map<String, String>): Flow<AppResult<FoodResponse, DataError.Network>> {
        return remoteDataSource.getRecipes(queries).toFlow()
            .catch { _ -> emit(AppResult.Error(DataError.Network.UNKNOWN)) }
            .flatMapConcat { remoteResult ->
                flowOf(remoteResult)
//                if (remoteResult is AppResult.Success) flowOf(remoteResult)
//                else localDataSource.getSavedRecipes()
            }.flowOn(dispatcher)
    }
}


class GetRecipesUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(queries: Map<String, String>): Flow<AppResult<FoodResponse, DataError.Network>> {
        return repository.getRecipes(queries)
    }
}
