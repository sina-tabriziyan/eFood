package com.sina.efood.core.remote.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class RecipesDto(
    @SerializedName("results")
    val results: List<FoodResult>,
) {
    @Parcelize
    data class FoodResult(
        @SerializedName("aggregateLikes")
        val aggregateLikes: Int,
        @SerializedName("cheap")
        val cheap: Boolean,
        @SerializedName("dairyFree")
        val dairyFree: Boolean,
        @SerializedName("extendedIngredients")
        val extendedIngredients: @RawValue List<ExtendedIngredient>,
        @SerializedName("glutenFree")
        val glutenFree: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int,
        @SerializedName("sourceName")
        val sourceName: String?,
        @SerializedName("sourceUrl")
        val sourceUrl: String,
        @SerializedName("summary")
        val summary: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("vegan")
        val vegan: Boolean,
        @SerializedName("vegetarian")
        val vegetarian: Boolean,
        @SerializedName("veryHealthy")
        val veryHealthy: Boolean,
    ) : Parcelable {

        @Suppress("DEPRECATED_ANNOTATION")
        @Parcelize
        data class ExtendedIngredient(
            @SerializedName("amount")
            val amount: Double,
            @SerializedName("consistency")
            val consistency: String,
            @SerializedName("image")
            val image: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("original")
            val original: String,
            @SerializedName("unit")
            val unit: String
        ) : Parcelable
    }
}