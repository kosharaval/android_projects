package com.example.midterm_300324116.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(notes: List<Job>)

    @Query("SELECT * FROM jobs ORDER BY id ASC")
    fun getAll(): LiveData<List<Job>>
}