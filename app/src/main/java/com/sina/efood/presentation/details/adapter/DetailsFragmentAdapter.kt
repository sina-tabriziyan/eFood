package com.sina.efood.presentation.details.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sina.efood.presentation.details.pages.IngredientFragment
import com.sina.efood.presentation.details.pages.InstructionFragment
import com.sina.efood.presentation.details.pages.OverviewFragment

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
