package com.sina.efood.presentation.home.recipes.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sina.efood.R
import com.sina.efood.core.remote.dto.RecipesDto
import com.sina.efood.databinding.ItemRecipeBinding

class RecipesAdapter(private val onItemCLicked: (Int) -> Unit) :
    ListAdapter<RecipesDto.FoodResult, RecipesAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<RecipesDto.FoodResult?>() {
        override fun areItemsTheSame(
            oldItem: RecipesDto.FoodResult,
            newItem: RecipesDto.FoodResult
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: RecipesDto.FoodResult,
            newItem: RecipesDto.FoodResult
        ): Boolean = oldItem == newItem
    }) {
    inner class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: RecipesDto.FoodResult

        init {
            itemView.setOnClickListener { onItemCLicked.invoke(item.id) }
        }

        fun bind(foodResult: RecipesDto.FoodResult) {
            item = foodResult

            with(binding) {
                tvTitle.text = item.title
                tvDescription.text = item.summary
                tvHeart.text = item.aggregateLikes.toString()
                tvClock.text = item.readyInMinutes.toString()
                tvVegeterian.changeTextColorByVeganStatus(item.vegan)
                imgRecipes.load(item.image) {
                    crossfade(600)
                    error(R.drawable.ic_error)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}

fun TextView.changeTextColorByVeganStatus(isVegan: Boolean) {
    val color = ContextCompat.getColor(
        context,
        if (isVegan) R.color.green else R.color.mediumGrey
    )
    setTextColor(color)
    TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(color))
}