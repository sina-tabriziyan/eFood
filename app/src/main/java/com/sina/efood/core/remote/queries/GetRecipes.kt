package com.sina.efood.core.remote.queries

fun Map<String, String>.queriesGetRecipes(
    type: String,
    diet: String,
): Map<String, String> = this.toMutableMap().also {
    it["type"] = type
    it["diet"] = diet
    it["number"] = "50"
    it["addRecipeInformation"] = "true"
    it["fillIngredients"] = "true"
}