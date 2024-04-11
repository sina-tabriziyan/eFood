package com.sina.efood.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.efood.base.BaseFragment
import com.sina.efood.databinding.FragmentRecipesBinding
import com.sina.efood.fragments.recipes.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : BaseFragment<FragmentRecipesBinding, RecipesViewModel>(
    FragmentRecipesBinding::inflate, RecipesViewModel::class
) {
    private lateinit var adapter: RecipesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipesAdapter()

        binding.rvRecipes.adapter = adapter
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getRecipes("", "")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect {
                    when (it) {
                        is RecipesViewModel.RecipesEvents.Error -> {
                            Log.e("TAG", "onViewCreated: ${it.error.asString(requireContext())}")
                        }

                        is RecipesViewModel.RecipesEvents.RecipesLoaded -> {
                            adapter.submitList(it.recipes.results)
                            Log.e("TAG", "onViewCreated: ${it.recipes}")
                        }
                    }
                }
            }
        }
    }


}