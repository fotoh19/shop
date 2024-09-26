package com.note.shop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "items_table")
data class ItemsModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String = "",
    var description: String = "",
    var picUrl: ArrayList<String> = ArrayList(),
    var model: ArrayList<String> = ArrayList(),
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var numberInCart: Int = 0,
    var showRecommended: Boolean = false,
    var categoryId: Int = 0
) : Parcelable, Serializable
