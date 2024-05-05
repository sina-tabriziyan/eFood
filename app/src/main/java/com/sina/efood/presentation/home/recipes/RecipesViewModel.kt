package com.sina.efood.presentation.home.recipes

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.efood.core.errors.asUiText
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.core.ui.UiText
import com.sina.efood.data.AppResult
import com.sina.efood.domain.usecase.GetRecipesUseCase
import com.sina.efood.domain.usecase.SaveDietUseCase
import com.sina.efood.domain.usecase.SaveMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val saveDietUseCase: SaveDietUseCase,
    private val saveMealUseCase: SaveMealUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    private val eventChannel = Channel<RecipesEvents>()
    val events = eventChannel.receiveAsFlow()

    private val _searchQuery = MutableStateFlow(state.get<String>(SEARCH_QUERY_KEY) ?: "")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        state[SEARCH_QUERY_KEY] = query
        getRecipes()
    }

    companion object {
        private const val SEARCH_QUERY_KEY = "search_query"
    }

    init {
        getRecipes()
    }

    fun getRecipes() = viewModelScope.launch {
        Log.e("TAG", "getRecipes: ${searchQuery.first()}")
        getRecipesUseCase.invoke(searchQuery.first()).collect {
            when (it) {
                is AppResult.Error -> eventChannel.send(RecipesEvents.Error(it.error.asUiText()))

                is AppResult.Success -> eventChannel.send(RecipesEvents.RecipesLoaded(it.data))
            }
        }
    }


    fun dietTypeChanged(dietType: String) {
        viewModelScope.launch { saveDietUseCase.invoke(dietType) }
    }

    fun mealTypeChanged(mealType: String) {
        viewModelScope.launch { saveMealUseCase.invoke(mealType) }
    }

    fun onRecipeItemClicked(foodId: Int) {
        viewModelScope.launch { eventChannel.send(RecipesEvents.NavigateToDetailsFragment(foodId)) }
    }

    sealed interface RecipesEvents {
        data class Error(val error: UiText) : RecipesEvents
        data class RecipesLoaded(val recipes: RecipesDto) : RecipesEvents
        data class NavigateToDetailsFragment(val foodId:Int) : RecipesEvents
    }

}