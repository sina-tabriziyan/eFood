package com.sina.efood.core.remote.dto


import com.google.gson.annotations.SerializedName

data class IngredientDto(
    @SerializedName("ingredients")
    val ingredients: List<Ingredient?>?
) {
    data class Ingredient(
        @SerializedName("amount")
        val amount: Amount?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("name")
        val name: String?
    ) {
        data class Amount(
            @SerializedName("metric")
            val metric: Metric?,
            @SerializedName("us")
            val us: Us?
        ) {
            data class Metric(
                @SerializedName("unit")
                val unit: String?,
                @SerializedName("value")
                val value: Double?
            )

            data class Us(
                @SerializedName("unit")
                val unit: String?,
                @SerializedName("value")
                val value: Double?
            )
        }
    }
}