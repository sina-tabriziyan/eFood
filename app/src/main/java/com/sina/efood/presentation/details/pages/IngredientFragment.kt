package com.sina.efood.presentation.details.pages

import android.os.Bundle
import android.view.View
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentIngredientBinding
import com.sina.efood.presentation.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngredientFragment : BaseFragment<FragmentIngredientBinding, DetailsViewModel>(
    FragmentIngredientBinding::inflate,
    DetailsViewModel::class
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}