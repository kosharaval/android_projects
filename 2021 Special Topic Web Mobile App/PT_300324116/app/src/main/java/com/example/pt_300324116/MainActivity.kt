package com.example.pt_300324116

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.moshidemo2.utilities.FileHelper
import com.example.mynavcompdemo1.db.AppDatabase
import com.example.pt_300324116.data.BookEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : AppCompatActivity() {

    //private val database = AppDatabase.getInstance()
    //val bookList = database?.bookDao()?.getAll()

    private val myType = Types.newParameterizedType(List::class.java, BookEntity::class.java)
    private val FILENAME = "bestsellers.json"

    var bookEntityList : MutableList<Book> = mutableListOf()

    private val viewModel: DisplayAllViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.bookEntityListJSON = readJsonFile()

        //database?.bookDao()?.insertAll(bookEntityList)
    }

    fun readJsonFile(): MutableList<Book> {

        val text = FileHelper.getDataFromAssets(this,FILENAME)
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter : JsonAdapter<List<Book>> = moshi.adapter(myType)
        val bookList = adapter.fromJson(text)

        for(b in bookList?: emptyList()){
            var bookItem: Book = Book()
            bookItem.title = b.title
            bookItem.author = b.author
            bookItem.rating = b.rating
            bookItem.price = b.price
            bookItem.year = b.year
            bookItem.genre = b.genre

            bookEntityList.add(bookItem)
        }

        return bookEntityList
    }
}