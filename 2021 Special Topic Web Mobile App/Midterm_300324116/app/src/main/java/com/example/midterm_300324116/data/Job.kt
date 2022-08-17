package com.example.midterm_300324116.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.midterm_300324116.NEW_JOB_ID
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var jobId: Int,
    var agency: String,
    var postingType: String,
    var noPostions: String,
    var businessTitle: String,
    var civilServiceTitle: String,
    var jobCategory: String,
    var fullTimePartTime: String,
    var salaryRangeFrom: Int,
    var salaryRangeTo: Int,
    var location: String,
    var division: String,
    var jobDescription: String,
    var postingDate: String
) : Parcelable {
    constructor() : this(NEW_JOB_ID,
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        0,
        "",
        "",
        "",
        "")
}
