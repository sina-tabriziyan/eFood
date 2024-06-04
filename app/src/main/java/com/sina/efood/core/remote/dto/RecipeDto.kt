package com.sina.efood.core.remote.dto


import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int?,
    @SerializedName("analyzedInstructions")
    val analyzedInstructions: List<Any?>?,
    @SerializedName("cheap")
    val cheap: Boolean?,
    @SerializedName("cookingMinutes")
    val cookingMinutes: Any?,
    @SerializedName("creditsText")
    val creditsText: String?,
    @SerializedName("cuisines")
    val cuisines: List<Any?>?,
    @SerializedName("dairyFree")
    val dairyFree: Boolean?,
    @SerializedName("diets")
    val diets: List<Any?>?,
    @SerializedName("dishTypes")
    val dishTypes: List<String?>?,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient?>?,
    @SerializedName("gaps")
    val gaps: String?,
    @SerializedName("glutenFree")
    val glutenFree: Boolean?,
    @SerializedName("healthScore")
    val healthScore: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("imageType")
    val imageType: String?,
    @SerializedName("instructions")
    val instructions: String?,
    @SerializedName("license")
    val license: String?,
    @SerializedName("lowFodmap")
    val lowFodmap: Boolean?,
    @SerializedName("occasions")
    val occasions: List<Any?>?,
    @SerializedName("originalId")
    val originalId: Any?,
    @SerializedName("preparationMinutes")
    val preparationMinutes: Any?,
    @SerializedName("pricePerServing")
    val pricePerServing: Double?,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int?,
    @SerializedName("servings")
    val servings: Int?,
    @SerializedName("sourceName")
    val sourceName: String?,
    @SerializedName("sourceUrl")
    val sourceUrl: String?,
    @SerializedName("spoonacularScore")
    val spoonacularScore: Double?,
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("sustainable")
    val sustainable: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vegan")
    val vegan: Boolean?,
    @SerializedName("vegetarian")
    val vegetarian: Boolean?,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean?,
    @SerializedName("veryPopular")
    val veryPopular: Boolean?,
    @SerializedName("weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int?
) {
    data class ExtendedIngredient(
        @SerializedName("aisle")
        val aisle: String?,
        @SerializedName("amount")
        val amount: Double?,
        @SerializedName("consistency")
        val consistency: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("measures")
        val measures: Measures?,
        @SerializedName("meta")
        val meta: List<String?>?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("nameClean")
        val nameClean: String?,
        @SerializedName("original")
        val original: String?,
        @SerializedName("originalName")
        val originalName: String?,
        @SerializedName("unit")
        val unit: String?
    ) {
        data class Measures(
            @SerializedName("metric")
            val metric: Metric?,
            @SerializedName("us")
            val us: Us?
        ) {
            data class Metric(
                @SerializedName("amount")
                val amount: Double?,
                @SerializedName("unitLong")
                val unitLong: String?,
                @SerializedName("unitShort")
                val unitShort: String?
            )

            data class Us(
                @SerializedName("amount")
                val amount: Double?,
                @SerializedName("unitLong")
                val unitLong: String?,
                @SerializedName("unitShort")
                val unitShort: String?
            )
        }
    }
}