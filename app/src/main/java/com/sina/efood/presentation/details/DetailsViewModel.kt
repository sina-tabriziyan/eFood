package com.sina.efood.presentation.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sina.efood.core.base.BaseViewModel
import com.sina.efood.core.errors.asUiText
import com.sina.efood.core.remote.dto.RecipeDto
import com.sina.efood.core.ui.UiText
import com.sina.efood.data.AppResult
import com.sina.efood.domain.usecase.GetRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val TAG = "DetailsViewModel"
    private var recipeId: Int? = null
    private val eventChannel = Channel<DetailsEvents>()
    val events = eventChannel.receiveAsFlow()


    init {
        savedStateHandle.get<Int>("recipeId")?.let {
            recipeId = it
        }
    }

    fun getRecipe() = viewModelScope.launch(Dispatchers.IO) {
        recipeId?.let {
            Log.e(TAG, "getRecipe: $it", )
            getRecipeUseCase.invoke(it).collect { result ->
                when (result) {
                    is AppResult.Error -> eventChannel.send(DetailsEvents.Error(result.error.asUiText()))
                    is AppResult.Success -> eventChannel.send(DetailsEvents.RecipeLoaded(result.data))
                }
            }
        }
    }

    sealed interface DetailsEvents {
        data class Error(val error: UiText) : DetailsEvents
        data class RecipeLoaded(val recipeDto: RecipeDto) : DetailsEvents
    }

}
