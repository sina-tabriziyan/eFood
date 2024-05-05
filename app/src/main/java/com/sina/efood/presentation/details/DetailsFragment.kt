package com.sina.efood.presentation.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentDetailsBinding
import com.sina.efood.presentation.MainActivity


class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>(
    FragmentDetailsBinding::inflate,
    DetailsViewModel::class
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

class DetailsFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewFragment()
            1 -> IngredientFragment()
            2 -> InstructionFragment()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}
