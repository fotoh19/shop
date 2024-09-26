package com.note.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.note.shop.R
import com.note.shop.databinding.ViewholderModelBinding

@Suppress("DEPRECATION")
class SelectedModelAdapter(private val items: MutableList<String>) :
    RecyclerView.Adapter<SelectedModelAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    inner class Viewholder(val binding: ViewholderModelBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectedModelAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderModelBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: SelectedModelAdapter.Viewholder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.modelTxt.text = items[position]

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        if (selectedPosition == position) {
            holder.binding.modelLayout.setBackgroundResource(R.drawable.green_bg_selected)
            holder.binding.modelTxt.setTextColor(context.resources.getColor(R.color.green))
        } else {
            holder.binding.modelLayout.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.modelTxt.setTextColor(context.resources.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int = items.size
}