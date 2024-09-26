package com.note.shop.adapter


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.note.shop.model.ItemsModel
import com.note.shop.R
import com.note.shop.databinding.ViewholderRecommendationBinding

class RecommendedAdapter :
    ListAdapter<ItemsModel, RecommendedAdapter.Viewholder>(DiffCallback()) {

    class Viewholder(val binding: ViewholderRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<ItemsModel>() {
        override fun areItemsTheSame(oldItem: ItemsModel, newItem: ItemsModel): Boolean {

            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemsModel, newItem: ItemsModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val binding = ViewholderRecommendationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Viewholder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            titleTxt.text = item.title
            priceTxt.text = "$${item.price}"
            ratingTxt.text = item.rating.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .into(pic)

            root.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("object", item)
                }

                val navController = it.findNavController()
                navController.navigate(R.id.action_homeFragment_to_detailFragment2, bundle)
            }
        }
    }
}

