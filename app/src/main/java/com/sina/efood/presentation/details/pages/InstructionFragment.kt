package com.sina.efood.presentation.details.pages

import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentInstructionBinding
import com.sina.efood.presentation.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class InstructionFragment : BaseFragment<FragmentInstructionBinding, DetailsViewModel>(
    FragmentInstructionBinding::inflate,
    DetailsViewModel::class,
) {
}