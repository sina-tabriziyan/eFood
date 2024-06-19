package com.sina.efood.presentation.details.pages

import android.os.Bundle
import android.util.Log
import android.view.View
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentInstructionBinding
import com.sina.efood.presentation.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstructionFragment : BaseFragment<FragmentInstructionBinding, DetailsViewModel>(
    FragmentInstructionBinding::inflate,
    DetailsViewModel::class,
) {

    private val TAG = "InstructionFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.recipeId.also {
            Log.e(TAG, "onViewCreated: ${it.value}")
        }
    }
}