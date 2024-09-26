package com.note.shop.helper

import android.content.Context
import com.note.shop.model.ItemsModel

class ManagmentCart(val context: Context) {

    private val cartDao: CartDao by lazy {
        CartDatabase.getDatabase(context).cartDao()
    }

    suspend fun insertItem(item: ItemsModel) {
        val listFood = getListCart()
        val existAlready = listFood.any { it.title == item.title }

        if (existAlready) {
            val index = listFood.indexOfFirst { it.title == item.title }
            listFood[index].numberInCart = item.numberInCart
            cartDao.updateCartItem(listFood[index])
        } else {
            cartDao.insertCartItem(item)
        }
    }

    suspend fun getListCart(): List<ItemsModel> {
        return cartDao.getCartItems()
    }

    suspend fun minusItem(
        listFood: List<ItemsModel>,
        position: Int,
        listener: ChangeNumberItemsListener
    ) {
        if (listFood[position].numberInCart == 1) {
            cartDao.deleteCartItem(listFood[position])
        } else {
            listFood[position].numberInCart--
            cartDao.updateCartItem(listFood[position])
        }
        listener.onChanged()
    }

    suspend fun plusItem(
        listFood: List<ItemsModel>,
        position: Int,
        listener: ChangeNumberItemsListener
    ) {
        listFood[position].numberInCart++
        cartDao.updateCartItem(listFood[position])
        listener.onChanged()
    }

    suspend fun getTotalFee(): Double {
        val listFood = getListCart()
        var fee = 0.0
        for (item in listFood) {
            fee += item.price * item.numberInCart
        }
        return fee
    }
}
