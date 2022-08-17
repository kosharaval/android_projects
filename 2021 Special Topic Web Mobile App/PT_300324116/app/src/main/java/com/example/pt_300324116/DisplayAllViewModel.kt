package com.example.pt_300324116

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynavcompdemo1.db.AppDatabase
import com.example.pt_300324116.data.BookEntity
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisplayAllViewModel(app: Application) : AndroidViewModel(app) {
    // TODO: Implement the ViewModel

    var bookEntityListJSON : MutableList<Book> = mutableListOf()
    lateinit var bookEntityList: MutableList<BookEntity>

    private val database = AppDatabase.getInstance(app)
    val bookListDB = database?.bookDao()?.getAll()

    private val myType = Types.newParameterizedType(List::class.java, BookEntity::class.java)
    private val FILENAME = "bestsellers.json"

    fun addBookData(){
        viewModelScope.launch { withContext(Dispatchers.IO){

//                var bookEntityList : MutableList<BookEntity> = mutableListOf()
//                val text = FileHelper.getDataFromAssets(this@DisplayAllViewModel,FILENAME)
//                val moshi: Moshi = Moshi.Builder()
//                    .add(KotlinJsonAdapterFactory())
//                    .build()
//                val adapter : JsonAdapter<List<BookEntity>> = moshi.adapter(myType)
//                val bookList = adapter.fromJson(text)
//
                for(b in bookEntityListJSON?: emptyList()){
                    var bookEntity: BookEntity = BookEntity()
                    bookEntity.title = b.title
                    bookEntity.author = b.author
                    bookEntity.rating = b.rating
                    bookEntity.price = b.price
                    bookEntity.year = b.year
                    bookEntity.genre = b.genre

                    bookEntityList.add(bookEntity)
                }


            database?.bookDao()?.insertAll(bookEntityList)
            }
        }
    }
}