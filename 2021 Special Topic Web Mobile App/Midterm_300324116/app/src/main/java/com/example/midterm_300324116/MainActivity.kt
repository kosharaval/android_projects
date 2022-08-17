package com.example.midterm_300324116

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.midterm_300324116.data.Job
import com.example.midterm_300324116.data.JobDatabase
import com.example.readonlinejson.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : AppCompatActivity() {

    //private val database = JobDatabase.getInstance(app)
    private val myType = Types.newParameterizedType(List::class.java, Job::class.java)
    private val FILENAME = "nyc_jobs_me.json"

    var jobList: MutableList<Job> = mutableListOf<Job>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val text = FileHelper.getDataFromAssets(this,FILENAME)
//        val moshi: Moshi = Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//        val adapter : JsonAdapter<List<Job>> = moshi.adapter(myType)
//        val jobPostingList = adapter.fromJson(text)
//
//        for (j in jobPostingList?: emptyList()){
//            var job: Job = Job()
//            job.jobId = j.jobId
//            job.agency = j.agency
//            job.postingType = j.postingType
//            job.noPostions = j.noPostions
//            job.businessTitle = j.businessTitle
//            job.civilServiceTitle = j.civilServiceTitle
//            job.jobCategory = j.jobCategory
//            job.fullTimePartTime = j.fullTimePartTime
//            job.salaryRangeFrom = j.salaryRangeFrom
//            job.salaryRangeTo = j.salaryRangeTo
//            job.location = j.location
//            job.division = j.division
//            job.jobDescription = j.jobDescription
//            job.postingDate = j.postingDate
//
//            jobList.add(job)
//        }
//        database?.jobDao()?.insertAll(jobList)
    }

}