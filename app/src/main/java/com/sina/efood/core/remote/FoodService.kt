package com.sina.efood.core.remote

import com.sina.efood.core.remote.dto.IngredientDto
import com.sina.efood.core.remote.dto.RecipeDto
import com.sina.efood.core.remote.dto.RecipesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface FoodService {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<RecipesDto>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<RecipesDto>

    @GET("recipes/{id}/information")
    suspend fun getRecipeById(
        @Path("id") id: Int,
    ): Response<RecipeDto>

    @GET("recipes/{id}/ingredientWidget.json")
    suspend fun getIngredientById(
        @Path("id") id: Int
    ):Response<IngredientDto>
}