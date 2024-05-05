package com.sina.efood.core.local.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sina.efood.core.local.prefs.PrefManager.PreferencesKey.diet
import com.sina.efood.core.local.prefs.PrefManager.PreferencesKey.meal
import com.sina.efood.presentation.home.recipesdialog.DietType
import com.sina.efood.presentation.home.recipesdialog.MealType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PrefManager @Inject constructor(private val appDataStore: DataStore<Preferences>) {
    private object PreferencesKey {
        val meal = stringPreferencesKey(PREFERENCES_MEAL)
        val diet = stringPreferencesKey(PREFERENCES_DIET)
    }
    suspend fun saveMealType(mealType: String) = appDataStore.edit { preferences ->
        preferences[meal] = mealType
    }

    suspend fun saveDietType(dietType: String) = appDataStore.edit { preferences ->
        preferences[diet] = dietType
    }


    val readMealType: Flow<String> = appDataStore.data.catch { exception ->
        if (exception is IOException) emit(emptyPreferences()) else throw exception
    }.map { preferences -> preferences[meal] ?: MealType.BREAD.name }

    val readDietType: Flow<String> = appDataStore.data.catch { exception ->
        if (exception is IOException) emit(emptyPreferences()) else throw exception
    }.map { preferences -> preferences[diet] ?: DietType.Paleo.name }


    val readSearch = appDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else throw exception
    }.map { preferences ->
        DietAndMealType(
            DietType.valueOf(preferences[diet] ?: DietType.Paleo.name),
            MealType.valueOf(preferences[meal] ?: MealType.BREAD.name)
        )
    }
}

const val PREFERENCES_NAME = "store_pref"
const val PREFERENCES_MEAL = "mealKey"
const val PREFERENCES_DIET = "dietKey"

data class DietAndMealType(val dietType: DietType, val mealType: MealType)