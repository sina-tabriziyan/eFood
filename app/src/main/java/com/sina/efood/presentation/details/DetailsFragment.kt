package com.sina.efood.presentation.details

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentDetailsBinding
import com.sina.efood.presentation.details.adapter.DetailsFragmentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>(
    FragmentDetailsBinding::inflate,
    DetailsViewModel::class,
) {
    private val titles = listOf("Overview", "Ingredients", "Instructions")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = DetailsFragmentAdapter(childFragmentManager, lifecycle)
        binding.detailsViewPager.adapter = fragmentAdapter
        TabLayoutMediator(binding.tabLayout, binding.detailsViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}

