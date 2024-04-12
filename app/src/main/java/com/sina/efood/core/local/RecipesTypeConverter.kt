package com.sina.efood.core.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sina.efood.core.remote.dto.RecipesDto

class RecipesTypeConverter {

    private var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipes: RecipesDto): String = gson.toJson(foodRecipes)


    @TypeConverter
    fun stringToFoodRecipe(data: String): RecipesDto =
        gson.fromJson(data, object : TypeToken<RecipesDto>() {}.type)


}
