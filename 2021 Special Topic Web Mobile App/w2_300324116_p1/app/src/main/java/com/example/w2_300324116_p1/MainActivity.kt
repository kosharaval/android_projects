package com.example.w2_300324116_p1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextTitle: EditText = findViewById(R.id.editTextTitle)
        val editTextArtist: EditText = findViewById(R.id.editTextArtist)
        val editTextPlayTime: EditText = findViewById(R.id.editTextPlayTime)

        val buttonAdd: Button =  findViewById(R.id.buttonAdd)
        val buttonSort: Button =  findViewById(R.id.buttonSort)
        val radioGroupSortBy:RadioGroup = findViewById(R.id.radioGroupSortBy)

        val textViewRecordList:TextView = findViewById(R.id.textViewRecordList)

        var recordingList = ArrayList<Recording>()

        buttonAdd.setOnClickListener{

            var title:String = editTextTitle.text.toString()
            var artist:String = editTextArtist.text.toString()
            var playTime:Int = editTextPlayTime.text.toString().toInt()

            var record:Recording = Recording(title,artist,playTime)
            recordingList.add(record)

            editTextTitle.setText("")
            editTextArtist.setText("")
            editTextPlayTime.setText("")

            //textViewRecordList.setText(refreshTextView(recordingList))
        }

        buttonSort.setOnClickListener{

            var selectedRadioButtonId: Int = radioGroupSortBy.checkedRadioButtonId
            var sortedDisplyText: String = ""

            if(recordingList.count() < 5){

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alert")
                builder.setMessage("Please enter aleast 5 Recorings before Sorting them")
                builder.setPositiveButton("OK"){dialogInterface, which->
                    "Clicked OK"
                }
                val alertDialog:AlertDialog = builder.create()
                alertDialog.show()
            }else{

                when(selectedRadioButtonId){
                    R.id.radioButtonTitle -> {
                        var sortedByTitle = recordingList.sortedBy{it.title}
                        sortedDisplyText += "Sorted By Title \n"
                        for(t in sortedByTitle) {
                            sortedDisplyText += t.title + " | " + t.artist + " | " + t.playTime + "\n"
                        }
                    }
                    R.id.radioButtonArtist-> {
                        var sortedByTitle = recordingList.sortedBy{it.artist}
                        sortedDisplyText += "Sorted By Artist \n"
                        for(t in sortedByTitle) {
                            sortedDisplyText += t.title + " | " + t.artist + " | " + t.playTime + "\n"
                        }
                    }
                    R.id.radioButtonPlayTime -> {
                        var sortedByTitle = recordingList.sortedBy{it.playTime}
                        sortedDisplyText += "Sorted By Play Time \n"
                        for(t in sortedByTitle) {
                            sortedDisplyText += t.title + " | " + t.artist + " | " + t.playTime + "\n"
                        }
                    }
                    else -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Alert")
                        builder.setMessage("Please select a property by which you want to Sort")
                        builder.setPositiveButton("OK"){dialogInterface, which->
                            "Clicked OK"
                        }
                        val alertDialog:AlertDialog = builder.create()
                        alertDialog.show()
                    }
                }
                textViewRecordList.setText(sortedDisplyText)
            }

        }
    }


    private fun refreshTextView(recordingList: ArrayList<Recording>): String {

        var displayText:String = ""
        for (r in recordingList){
            displayText += r.title + " | " + r.artist + " | " + r.playTime + "\n"
        }
        return displayText
    }
}