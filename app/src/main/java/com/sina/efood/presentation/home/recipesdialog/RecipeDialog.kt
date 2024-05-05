package com.sina.efood.presentation.home.recipesdialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sina.efood.R
import com.sina.efood.databinding.RecipesBottomSheetBinding
import com.sina.efood.presentation.home.recipesdialog.MealType.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class RecipeDialog : BottomSheetDialogFragment() {
    private lateinit var binding: RecipesBottomSheetBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RecipesBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipesTypes = listOf(MAIN_COURSE, BREAD, MARINADE, SIDE_DISH)
        val dietTypes = listOf(DietType.Gluten_Free, DietType.Ketogenic, DietType.Paleo)
        with(binding) {
            ChipBuilder<MealType>(mealTypeChipGroup, {
                it.name
            }, {
                Log.e("TAG", "onViewCreated: $it")
            }).buildChips(recipesTypes)
            ChipBuilder<DietType>(dietTypeChipGroup, {
                it.name
            }, {
                Log.e("TAG", "onViewCreated: $it")
            }).buildChips(dietTypes)
        }
    }

}

enum class MealType {
    MAIN_COURSE, BREAD, MARINADE, SIDE_DISH, BREAKFAST, DESSERT, FINGERFOOD, SOUP;

    companion object {
        fun fromString(value: String): MealType? {
            return values().find { it.name.equals(value, ignoreCase = true) }
        }
    }
}

enum class DietType {
    Gluten_Free, Ketogenic, Vegetarian, Pescetarian, Paleo, Primal, Whole30;

    companion object {
        fun fromString(value: String): DietType? {
            return values().find { it.name.equals(value, ignoreCase = true) }
        }
    }
}

class ChipBuilder<T>(
    private val chipGroup: ChipGroup,
    private val itemToString: (T) -> String,
    private val itemChecked: (String) -> Unit
) {
    fun buildChips(items: List<T>) {
        val inflater = LayoutInflater.from(chipGroup.context)
        items.forEach { item ->
            val chip = inflater.inflate(R.layout.custom_chip, chipGroup, false) as Chip
            chip.text = itemToString(item).lowercase(Locale.ROOT).replace('_', ' ')
            chip.isCheckable = true
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) itemChecked.invoke(item.toString())
            }
            chipGroup.addView(chip)
        }
    }
}

