package com.sina.efood.presentation.details

import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentIngredientBinding
import com.sina.efood.databinding.FragmentInstructionBinding

class IngredientFragment : BaseFragment<FragmentIngredientBinding, DetailsViewModel>(
    FragmentIngredientBinding::inflate,
    DetailsViewModel::class
) {
}