package com.note.shop.helper

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.note.shop.model.ItemsModel

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: ItemsModel)

    @Query("SELECT * FROM items_table")
    suspend fun getCartItems(): List<ItemsModel>

    @Delete
    suspend fun deleteCartItem(item: ItemsModel)

    @Update
    suspend fun updateCartItem(item: ItemsModel)
}
