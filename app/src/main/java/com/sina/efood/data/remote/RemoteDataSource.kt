package com.sina.efood.data.remote

import com.sina.efood.data.AppResult
import com.sina.efood.data.DataError
import com.sina.efood.data.models.FoodResponse
import com.sina.efood.data.openResponse
import javax.inject.Inject

typealias RootError = com.sina.efood.data.Error

class RemoteDataSource @Inject constructor(
    private val foodService: FoodService
) {

    suspend fun getRecipes(queries: Map<String, String>): AppResult<FoodResponse, DataError.Network> =
        foodService.getRecipes(queries).openResponse()

    suspend fun searchRecipes(searchQueries: Map<String, String>) =
        foodService.searchRecipes(searchQueries).openResponse()

}




