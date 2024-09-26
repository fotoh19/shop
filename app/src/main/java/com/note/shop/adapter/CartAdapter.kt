package com.note.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.note.shop.helper.ManagmentCart
import com.note.shop.model.ItemsModel
import com.note.shop.databinding.ViewholderCartBinding
import com.note.shop.helper.ChangeNumberItemsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CartAdapter(
    private val listItemSelected: ArrayList<ItemsModel>,
    context: Context,
    private val coroutineScope: CoroutineScope,
    private var changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    private val managementCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding =
            ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun getItemCount(): Int = listItemSelected.size

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = listItemSelected[position]

        holder.binding.titleTxt.text = item.title
        holder.binding.feeEacheTime.text = "$${item.showRecommended}"
        holder.binding.totalEacheitem.text = "$${Math.round(item.numberInCart * item.price)}"
        holder.binding.numberitemTxt.text = item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .into(holder.binding.pic)

        holder.binding.pluscartBtn.setOnClickListener {
            coroutineScope.launch {
                managementCart.plusItem(listItemSelected, position,changeNumberItemsListener)
                notifyDataSetChanged()
                changeNumberItemsListener.onChanged()
            }
        }

        holder.binding.minuscartBtn.setOnClickListener {
            coroutineScope.launch {
                managementCart.minusItem(listItemSelected,position,changeNumberItemsListener)
                notifyDataSetChanged()
                changeNumberItemsListener.onChanged()
            }
        }
    }
}
