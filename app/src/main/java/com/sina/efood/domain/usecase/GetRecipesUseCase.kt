package com.sina.efood.domain.usecase

import com.sina.efood.core.errors.DataError
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.data.AppResult
import com.sina.efood.domain.repository.IRecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: IRecipesRepository,
) {
    suspend operator fun invoke(queries: Map<String, String>): Flow<AppResult<RecipesDto, DataError>> = repository.getAndStoreRecipes(queries)
}
