package com.sina.efood.presentation.details.pages

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentOverviewBinding
import com.sina.efood.presentation.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : BaseFragment<FragmentOverviewBinding, DetailsViewModel>(
    FragmentOverviewBinding::inflate,
    DetailsViewModel::class,
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated: OverviewFragment", )
        viewModel.getRecipe()
        observeEvents()

    }


    private fun observeEvents() {
        launchWhen({
            viewModel.events.collect {
                when (it) {
                    is DetailsViewModel.DetailsEvents.Error -> Log.e(
                        "TAG",
                        "observeEvents: ${it.error}",
                    )

                    is DetailsViewModel.DetailsEvents.RecipeLoaded -> {
                        Log.e("TAG", "observeEvents: ${it.recipeDto}")
                    }
                }
            }
        }, Lifecycle.State.RESUMED)
    }
}