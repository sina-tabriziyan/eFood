package com.sina.efood.presentation.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sina.efood.R
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.core.utils.onQueryTextSubmit
import com.sina.efood.databinding.FragmentRecipesBinding
import com.sina.efood.presentation.fragments.recipes.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class RecipesFragment : BaseFragment<FragmentRecipesBinding, RecipesViewModel>(
    FragmentRecipesBinding::inflate, RecipesViewModel::class
) {
    private lateinit var adapter: RecipesAdapter
    private lateinit var menuHost: MenuHost


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipesAdapter()
        menuHost = requireActivity()

        binding.rvRecipes.adapter = adapter
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getRecipes()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect {
                    when (it) {
                        is RecipesViewModel.RecipesEvents.Error -> {
                            Log.e("TAG", "onViewCreated Error: ${it.error.asString(requireContext())}")
                        }

                        is RecipesViewModel.RecipesEvents.RecipesLoaded -> {
                            adapter.submitList(it.recipes.results)
                            Log.e("TAG", "onViewCreated: ${it.recipes}")
                        }
                    }
                }
            }
        }


        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_recipes, menu)
                val searchAction = menu.findItem(R.id.action_search)
                val searchView = searchAction.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.onQueryTextSubmit {
                    Log.e("TAG", "onCreateMenu: $it", )
                    viewModel.setSearchQuery(it)

                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.fabRecipesDialog.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipeDialog)
        }
    }
}

