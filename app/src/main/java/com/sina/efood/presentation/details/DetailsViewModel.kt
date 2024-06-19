package com.sina.efood.presentation.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sina.efood.core.base.BaseViewModel
import com.sina.efood.core.errors.asUiText
import com.sina.efood.core.remote.dto.IngredientDto
import com.sina.efood.core.remote.dto.RecipeDto
import com.sina.efood.core.ui.UiText
import com.sina.efood.data.AppResult
import com.sina.efood.domain.usecase.GetIngredientByIdUseCase
import com.sina.efood.domain.usecase.GetRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val getIngredientByIdUseCase: GetIngredientByIdUseCase
) : BaseViewModel() {
    private val TAG = "DetailsViewModel"

    //    private var recipeId: Int? = null
    private val overviewEventsChannel = Channel<OverviewEvents>()
    val overviewEvents = overviewEventsChannel.receiveAsFlow()

    //    private var recipeId: Int? = null
    private val ingredientEventsChannel = Channel<IngredientEvents>()
    val ingredientEvents = ingredientEventsChannel.receiveAsFlow()


    val recipeId: StateFlow<Int?> = savedStateHandle.getStateFlow<Int?>("recipeId", null)

    init {
        // You can still log here to check if the value is immediately available
        Log.e(TAG, "Recipe ID in ViewModel init: ${recipeId.value}")
    }

    fun getRecipe() = viewModelScope.launch(Dispatchers.IO) {
        recipeId.first()?.let {
            Log.e(TAG, "getRecipe: $it", )
            getRecipeUseCase.invoke(it).collect { result ->
                when (result) {
                    is AppResult.Error -> overviewEventsChannel.send(OverviewEvents.Error(result.error.asUiText()))
                    is AppResult.Success -> overviewEventsChannel.send(OverviewEvents.RecipeLoaded(result.data))
                }
            }
        }
    }


    fun getIngredient(id: Int) = viewModelScope.launch(Dispatchers.IO){
        recipeId.first()?.let {
            getIngredientByIdUseCase.invoke(it).collect{result->
                when (result) {
                    is AppResult.Error -> ingredientEventsChannel.send(IngredientEvents.Error(result.error.asUiText()))
                    is AppResult.Success -> ingredientEventsChannel.send(IngredientEvents.IngredientLoaded(result.data))
                }
            }
        }
    }

    sealed interface OverviewEvents {
        data class Error(val error: UiText) : OverviewEvents
        data class RecipeLoaded(val recipeDto: RecipeDto) : OverviewEvents
    }

    sealed interface IngredientEvents {
        data class Error(val error: UiText) : IngredientEvents
        data class IngredientLoaded(val ingredientDto: IngredientDto) : IngredientEvents

    }
}
