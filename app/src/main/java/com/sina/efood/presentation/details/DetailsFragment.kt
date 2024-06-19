package com.sina.efood.presentation.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentDetailsBinding
import com.sina.efood.presentation.details.pages.IngredientFragment
import com.sina.efood.presentation.details.pages.InstructionFragment
import com.sina.efood.presentation.details.pages.OverviewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>(
    FragmentDetailsBinding::inflate,
    DetailsViewModel::class,
) {
    private val TAG = "DetailsFragment"
    private val titles = listOf("Overview", "Ingredients", "Instructions")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = DetailsFragmentAdapter(childFragmentManager, lifecycle)
        binding.detailsViewPager.adapter = fragmentAdapter
        TabLayoutMediator(binding.tabLayout, binding.detailsViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        arguments?.getInt("recipeId").also {
            Log.e(TAG, "onViewCreated: $it")
        }

    }

    inner class DetailsFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> OverviewFragment().apply {
                    arguments = Bundle().apply {
                        putInt("recipeId", viewModel.recipeId.value ?: -1)
                    }
                }

                1 -> IngredientFragment().apply {
                    arguments = Bundle().apply {
                        putInt("recipeId", viewModel.recipeId.value ?: -1)
                    }
                }
                2 -> InstructionFragment().apply {
                    arguments = Bundle().apply {
                        putInt("recipeId", viewModel.recipeId.value ?: -1)
                    }
                }
                else -> throw IllegalStateException("Invalid position: $position")
            }
        }
    }

}

