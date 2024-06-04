package com.sina.efood.domain.usecase

import com.sina.efood.core.errors.DataError
import com.sina.efood.core.remote.dto.RecipeDto
import com.sina.efood.data.AppResult
import com.sina.efood.domain.repository.IRecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(
    private val repository: IRecipesRepository
) {
    suspend operator fun invoke(recipeId: Int): Flow<AppResult<RecipeDto, DataError>> = flow {
        emitAll(repository.getRecipesById(recipeId))
    }
}