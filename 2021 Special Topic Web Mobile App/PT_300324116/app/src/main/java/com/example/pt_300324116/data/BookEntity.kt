package com.example.pt_300324116.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pt_300324116.NEW_BOOK_ID
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var author: String,
    var rating: String,
    var price: String,
    var year: String,
    var genre: String,
): Parcelable {
    constructor() : this(NEW_BOOK_ID,"","","","","","")
    constructor(title: String, author: String,rating: String, price: String, year: String, genre: String): this(
        NEW_BOOK_ID, title,author,rating,price,year,genre)
}

