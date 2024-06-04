package com.sina.efood.domain.repository

import com.sina.efood.core.errors.DataError
import com.sina.efood.core.remote.dto.RecipeDto
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.data.AppResult
import kotlinx.coroutines.flow.Flow

interface IRecipesRepository {
    suspend fun getAndStoreRecipes(queries: Map<String, String>): Flow<AppResult<RecipesDto, DataError>>
    suspend fun getRecipesById(recipeId: Int): Flow<AppResult<RecipeDto, DataError>>
}