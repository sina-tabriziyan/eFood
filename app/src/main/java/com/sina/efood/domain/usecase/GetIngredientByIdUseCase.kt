package com.sina.efood.domain.usecase

import com.sina.efood.domain.repository.IRecipesRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetIngredientByIdUseCase @Inject constructor(
    private val repository: IRecipesRepository,
) {
    suspend fun invoke(id: Int) = flow {
        emitAll(repository.getIngredientById(id))
    }
}