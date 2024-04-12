package com.sina.efood.presentation.fragments.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.efood.core.errors.asUiText
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.core.remote.queries.queriesGetRecipes
import com.sina.efood.core.ui.UiText
import com.sina.efood.data.AppResult
import com.sina.efood.domain.usecase.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase
) : ViewModel() {
    private val eventChannel = Channel<RecipesEvents>()
    val events = eventChannel.receiveAsFlow()


    fun getRecipes(type: String, diet: String) {
        viewModelScope.launch {
            getRecipesUseCase.invoke(
                queries = mapOf<String, String>().queriesGetRecipes(
                    type, diet
                )
            ).collect {
                when (it) {
                    is AppResult.Error -> eventChannel.send(RecipesEvents.Error(it.error.asUiText()))
                    is AppResult.Success -> eventChannel.send(RecipesEvents.RecipesLoaded(it.data))
                }
            }
        }
    }

    sealed interface RecipesEvents {
        data class Error(val error: UiText) : RecipesEvents
        data class RecipesLoaded(val recipes: RecipesDto) : RecipesEvents
    }

}