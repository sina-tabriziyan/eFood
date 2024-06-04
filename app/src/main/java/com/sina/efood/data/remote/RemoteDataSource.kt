package com.sina.efood.data.remote

import com.sina.efood.data.AppResult
import com.sina.efood.core.errors.DataError
import com.sina.efood.core.remote.FoodService
import com.sina.efood.core.remote.dto.RecipeDto
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.data.openResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodService: FoodService
) {
    suspend fun getRecipes(queries: Map<String, String>): AppResult<RecipesDto, DataError.Network> =
        foodService.getRecipes(queries).openResponse()

    suspend fun searchRecipes(searchQueries: Map<String, String>): AppResult<RecipesDto, DataError.Network> =
        foodService.searchRecipes(searchQueries).openResponse()

    suspend fun getRecipeById(recipeId: Int): AppResult<RecipeDto, DataError.Network> =
        foodService.getRecipeById(recipeId).openResponse()

}