package com.sina.efood.domain.usecase

import com.sina.efood.core.errors.DataError
import com.sina.efood.core.local.prefs.PrefManager
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.core.remote.queries.queriesGetRecipes
import com.sina.efood.data.AppResult
import com.sina.efood.domain.repository.IRecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: IRecipesRepository,
    private val prefManager: PrefManager
) {
    suspend operator fun invoke(searchQuery: String): Flow<AppResult<RecipesDto, DataError>> = flow {
        val dietAndMealType = prefManager.readSearch.first()
        emitAll(
            repository.getAndStoreRecipes(
                mapOf<String, String>().queriesGetRecipes(
                    searchQuery,"",""
//                    dietAndMealType.mealType.name,
//                    dietAndMealType.dietType.name
                )
            )
        )
    }
}

class SaveMealUseCase @Inject constructor(
    private val prefManager: PrefManager
) {
    suspend operator fun invoke(mealType: String) {
        prefManager.saveMealType(mealType)
    }
}

class SaveDietUseCase @Inject constructor(
    private val prefManager: PrefManager
) {
    suspend operator fun invoke(dietType: String) {
        prefManager.saveDietType(dietType)
    }
}

