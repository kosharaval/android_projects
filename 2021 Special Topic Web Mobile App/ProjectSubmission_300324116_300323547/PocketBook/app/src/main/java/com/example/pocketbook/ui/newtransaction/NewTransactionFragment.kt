package com.example.pocketbook.ui.newtransaction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.pocketbook.R
import com.example.pocketbook.SocketIOModel
import com.example.pocketbook.databinding.FragmentNewTransactionBinding
import com.example.pocketbook.entities.Category
import com.example.pocketbook.entities.DailyTransactions
import com.squareup.moshi.Types
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_new_transaction.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.net.URISyntaxException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NewTransactionFragment : Fragment(),AdapterView.OnItemSelectedListener {

    private lateinit var binding : FragmentNewTransactionBinding
    private lateinit var viewModel: NewTransactionViewModel
    private lateinit var socketIOModel: SocketIOModel

    var mSocket: Socket? = null
    //private val newTransactionType = Types.newParameterizedType(List::class.java, DailyTransactions::class.java)

    val categoryNameList: Array<String> = arrayOf("Housing Rent", "Transportation", "Food", "Freelance","Salary","Utilities","MSP")
    val categoryTypeList: Array<String> = arrayOf("Income", "Expense")

    var category_type: String = ""
    var category_name: String = ""
    var amount: Double = 0.0
    var description: String = ""
    var userEmail: String = ""

    val currentDate = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = currentDate.format(formatter)


    companion object {
        fun newInstance() = NewTransactionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        binding = FragmentNewTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textViewResults = root.findViewById<TextView>(R.id.textViewResults)
        val editTextAmount = root.findViewById<EditText>(R.id.editTextAmount)
        val editTextDescription = root.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = root.findViewById<Button>(R.id.buttonSubmit)

        //Getting the Socket URI from the Main Activity using SaveInstanceState
        var uri = socketIOModel.hostString

        //socketIOModel.hostString.observe(viewLifecycleOwner,{
        //    uri = it
        //})

        try {
            mSocket = IO.socket(uri)

        } catch (e: URISyntaxException) {
            Log.d("URI error", e.message.toString())
        }

        try {
            mSocket?.connect()
            textViewResults.text = "connected to " + uri + " " + mSocket?.connected()
        } catch (e: Exception) {
            textViewResults.text = " Failed to connect. " + e.message
        }

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            textViewResults.text = "sending"
            mSocket?.emit("messages", "hi")
        });

        val spinnerCategoryType = root.findViewById<Spinner>(R.id.spinnerCategoryType)
        val categoryTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryTypeList)
        categoryTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoryType.setAdapter(categoryTypeAdapter)

        val spinnerCategoryName = root.findViewById<Spinner>(R.id.spinnerCategoryName)
        val categoryNameAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNameList)
        categoryNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoryName.setAdapter(categoryNameAdapter)

        buttonSubmit.setOnClickListener {
            amount = editTextAmount.text.toString().toDouble()
            description = editTextDescription.text.toString()
            userEmail = "kosha@gmail.com"
            category_name = spinnerCategoryName.selectedItem.toString()
            category_type = spinnerCategoryType.selectedItem.toString()
            var dateString = date.toString()
            mSocket?.emit("addTransaction",category_name,category_type,amount,description,dateString,userEmail)
        }
        mSocket?.on("addedNewTransaction",onTrnasactionAdded)

        return root
    }

    var onTrnasactionAdded = Emitter.Listener {
        val message = it[0] as String
        textViewResults.text = message
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewTransactionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when(parent?.getId()){
            R.id.spinnerCategoryName -> category_name = parent.getItemAtPosition(position).toString()
            R.id.spinnerCategoryType -> category_type = parent.getItemAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}

