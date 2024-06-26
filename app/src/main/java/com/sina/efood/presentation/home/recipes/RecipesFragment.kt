package com.sina.efood.presentation.home.recipes

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.efood.R
import com.sina.efood.core.base.BaseFragment
import com.sina.efood.core.utils.onQueryTextSubmit
import com.sina.efood.databinding.FragmentRecipesBinding
import com.sina.efood.presentation.home.recipes.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : BaseFragment<FragmentRecipesBinding, RecipesViewModel>(
    FragmentRecipesBinding::inflate, RecipesViewModel::class
) {
    private val TAG = "RecipesFragment"
    private lateinit var adapter: RecipesAdapter
    private lateinit var menuHost: MenuHost


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipesAdapter { id ->
            viewModel.onRecipeItemClicked(id)
        }
        menuHost = requireActivity()

        binding.rvRecipes.adapter = adapter
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getRecipes()
        observeEvents()


        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_recipes, menu)
                val searchAction = menu.findItem(R.id.action_search)
                val searchView = searchAction.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.onQueryTextSubmit {
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

    private fun observeEvents() {
        launchWhen({
            viewModel.events.collect {
                when (it) {
                    is RecipesViewModel.RecipesEvents.Error -> {
                        Log.e(TAG, "onViewCreated Error: ${it.error.asString(requireContext())}")
                    }

                    is RecipesViewModel.RecipesEvents.RecipesLoaded -> {
                        adapter.submitList(it.recipes.results)
                    }

                    is RecipesViewModel.RecipesEvents.NavigateToDetailsFragment -> {
                        findNavController().navigate(
                            RecipesFragmentDirections.actionRecipesFragmentToDetailsFragment(it.foodId)
                        )
                    }
                }
            }
        }, Lifecycle.State.RESUMED)
    }
}

