package com.example.pt_300324116.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // it performs an update if it exists
    fun insertBook(note: BookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(notes: MutableList<BookEntity>)

    @Query("SELECT * FROM books ORDER BY id ASC")
    fun getAll(): LiveData<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id") // :id refers to the parameter
    fun getNoteById(id: Int): BookEntity?

    @Query("SELECT COUNT(*) from books")
    fun getCount(): Int

    @Delete
    fun deleteBooks(selectedNotes: List<BookEntity>): Int

    @Query("DELETE FROM books")
    fun deleteAll():Int

    @Delete
    fun deleteBook(note: BookEntity)
}