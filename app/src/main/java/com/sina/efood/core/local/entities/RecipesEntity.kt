package com.sina.efood.core.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.core.utils.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var recipesDto: RecipesDto
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}