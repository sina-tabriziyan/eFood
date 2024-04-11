package com.sina.efood.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sina.efood.data.models.FoodResponse
import com.sina.efood.utils.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipeEntity(
    var foodResponse: FoodResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}