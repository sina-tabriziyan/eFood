package com.sina.efood.presentation.details.pages

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentIngredientBinding
import com.sina.efood.presentation.details.DetailsViewModel
import com.sina.efood.presentation.details.adapter.IngredientsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngredientFragment : BaseFragment<FragmentIngredientBinding, DetailsViewModel>(
    FragmentIngredientBinding::inflate,
    DetailsViewModel::class
) {
    private val TAG = "IngredientFragment"
    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientsAdapter = IngredientsAdapter()
        viewModel.recipeId.also {
            Log.e(TAG, "onViewCreated: ${it.value}")
            it.value?.let { it1 -> viewModel.getIngredient(it1) }

        }

        launchWhen({
            viewModel.ingredientEvents.collect {
                when (it) {
                    is DetailsViewModel.IngredientEvents.Error -> Log.e(TAG, "onViewCreated: ${it.error}", )
                    is DetailsViewModel.IngredientEvents.IngredientLoaded -> with(binding) {
                        rvIngredients.adapter = ingredientsAdapter
                        rvIngredients.layoutManager = LinearLayoutManager(root.context)
                        ingredientsAdapter.submitList(it.ingredientDto.ingredients)
                    }
                }
            }
        }, Lifecycle.State.CREATED)

    }
}