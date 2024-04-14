package com.sina.efood.presentation.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sina.efood.R
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.databinding.FragmentRecipesBinding
import com.sina.efood.presentation.fragments.recipes.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

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

        binding.fabRecipesDialog.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipeDialog)
        }
    }
}

