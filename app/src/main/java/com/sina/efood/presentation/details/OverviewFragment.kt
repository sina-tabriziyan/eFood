package com.sina.efood.presentation.details

import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentOverviewBinding


class OverviewFragment : BaseFragment<FragmentOverviewBinding, DetailsViewModel>(
    FragmentOverviewBinding::inflate,
    DetailsViewModel::class
) {
}