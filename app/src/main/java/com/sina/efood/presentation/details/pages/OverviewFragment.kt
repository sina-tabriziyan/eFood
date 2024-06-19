package com.sina.efood.presentation.details.pages

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.Lifecycle
import coil.load
import com.sina.efood.R
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.core.remote.dto.RecipeDto
import com.sina.efood.databinding.FragmentOverviewBinding
import com.sina.efood.presentation.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : BaseFragment<FragmentOverviewBinding, DetailsViewModel>(
    FragmentOverviewBinding::inflate,
    DetailsViewModel::class,
) {
    private val TAG = "OverviewFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.recipeId.also {
            Log.e(TAG, "onViewCreated: ${it.value}")
        }
        viewModel.getRecipe()
        observeEvents()

    }


    private fun observeEvents() {
        launchWhen({
            viewModel.overviewEvents.collect {
                when (it) {
                    is DetailsViewModel.OverviewEvents.Error -> Log.e(TAG, "observeEvents: ${it.error}")
                    is DetailsViewModel.OverviewEvents.RecipeLoaded -> {
                        loadRecipes(it.recipeDto)
                        Log.e(TAG, "observeEvents: ${it.recipeDto}")
                    }
                }
            }
        }, Lifecycle.State.RESUMED)
    }

    private fun loadRecipes(recipeDto: RecipeDto) = with(binding) {
        val greenColor = ContextCompat.getColor(root.context, R.color.green)
        imgOverview.load(recipeDto.image)
        tvTitle.text = recipeDto.title
        tvSummary.text = recipeDto.summary
        tvTime.text = recipeDto.readyInMinutes.toString()
        tvLikes.text = recipeDto.aggregateLikes.toString()
        listOf(
            tvCheckVegetarian to recipeDto.vegetarian,
            tvCheckVegan to recipeDto.vegan,
            tvCheckGlutenFree to recipeDto.glutenFree,
            tvCheckHealthy to recipeDto.veryHealthy,
            tvDairyFree to recipeDto.dairyFree,
            tvCheap to recipeDto.cheap,
        ).onEach { (textView, value) ->
            if (value == true) {
                textView.apply {
                    setTextColor(greenColor)
                    TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(greenColor))
                }
            }
        }

    }
}