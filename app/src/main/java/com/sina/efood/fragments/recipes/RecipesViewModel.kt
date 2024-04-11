package com.sina.efood.fragments.recipes

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.efood.R
import com.sina.efood.data.AppResult
import com.sina.efood.data.DataError
import com.sina.efood.data.GetRecipesUseCase
import com.sina.efood.data.models.FoodResponse
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
        data class RecipesLoaded(val recipes: FoodResponse) : RecipesEvents
    }

}

fun Map<String, String>.queriesGetRecipes(
    type: String,
    diet: String,
): Map<String, String> = this.toMutableMap().also {
    it["type"] = type
    it["diet"] = diet
    it["number"] = "50"
    it["addRecipeInformation"] = "true"
    it["fillIngredients"] = "true"
}

sealed class UiText {
    data class DynamicString(val values: String) : UiText()

    class StaticString(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> values
            is StaticString -> context.getString(id, *args)
        }
    }
}

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StaticString(R.string.this_is_full)
        DataError.Network.REQUEST_TIMEOUT -> UiText.StaticString(R.string.request_timeout)
        DataError.Network.TOO_MANY_REQUEST -> UiText.StaticString(R.string.too_many_requests)
        DataError.Network.NO_INTERNET -> UiText.StaticString(R.string.not_network)
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StaticString(R.string.payload_too_large)
        DataError.Network.SERVER_ERROR -> UiText.StaticString(R.string.server_error)
        DataError.Network.SERIALIZATION -> UiText.StaticString(R.string.serialization_error)
        DataError.Network.UNKNOWN -> UiText.StaticString(R.string.unknown_error)
    }
}
