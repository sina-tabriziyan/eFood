package com.sina.efood.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sina.efood.data.models.FoodResponse

class RecipesTypeConverter {

    private var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipes: FoodResponse): String = gson.toJson(foodRecipes)


    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodResponse =
        gson.fromJson(data, object : TypeToken<FoodResponse>() {}.type)


    @TypeConverter
    fun foodResultToString(result: FoodResponse): String = gson.toJson(result)


    @TypeConverter
    fun stringToFoodResult(data: String): FoodResponse =
        gson.fromJson(data, object : TypeToken<FoodResponse>() {}.type)

}
