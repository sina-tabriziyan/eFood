package com.sina.efood.data.local

import com.sina.efood.core.local.RecipesDao
import com.sina.efood.core.local.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {

    val readRecipes: Flow<List<RecipesEntity>> = recipesDao.readRecipes()
    suspend fun saveRecipes(recipesEntity: RecipesEntity) = recipesDao.insertRecipes(recipesEntity)

}
