package com.sina.efood.presentation.details.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.gson.annotations.SerializedName
import com.sina.efood.R
import com.sina.efood.core.errors.DataError
import com.sina.efood.core.remote.dto.IngredientDto
import com.sina.efood.core.utils.BASE_IMAGE_URL
import com.sina.efood.databinding.ItemIngredientBinding
import kotlinx.parcelize.Parcelize
import java.util.Locale

class IngredientsAdapter :
    ListAdapter<IngredientDto.Ingredient, IngredientsAdapter.ViewHolder>(object : DiffUtil.ItemCallback<IngredientDto.Ingredient?>() {
        override fun areItemsTheSame(oldItem: IngredientDto.Ingredient, newItem: IngredientDto.Ingredient): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: IngredientDto.Ingredient, newItem: IngredientDto.Ingredient): Boolean = oldItem == newItem
    }) {
    inner class ViewHolder(val binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IngredientDto.Ingredient) = with(binding) {
            imgIngredient.load(BASE_IMAGE_URL + item.image) {
                crossfade(600)
                error(R.drawable.ic_error)
            }
            tvIngredientName.text = item.name?.capitalize(Locale.ROOT)
            tvIngredientAmount.text = item.amount.toString()
            tvIngredientUnit.text = item.amount?.us?.unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))


}


@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class ExtendedIngredient(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("consistency")
    val consistency: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("unit")
    val unit: String
) : Parcelable