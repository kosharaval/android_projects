package com.example.pocketbook.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketbook.R
import com.example.pocketbook.SocketIOModel
import com.example.pocketbook.databinding.FragmentHomeBinding
import com.example.pocketbook.entities.DailyTransactions
import com.example.pocketbook.entities.Transaction
import com.example.pocketbook.entities.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.net.URISyntaxException

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var socketIOModel: SocketIOModel

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    var mSocket: Socket? = null
    private val newTransactionType = Types.newParameterizedType(List::class.java, DailyTransactions::class.java)
    private val userType = Types.newParameterizedType(List::class.java, User::class.java)
    var dailyTransactionArrayList = mutableListOf<Transaction>()

    var budget:Double =0.0
    var income:Double =0.0
    var expense:Double =0.0
    var saving:Double =0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        socketIOModel = ViewModelProvider(this).get(SocketIOModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textViewHomeResult = root.findViewById<TextView>(R.id.textViewHomeResult)
        val buttonGetTransactionData = root.findViewById<Button>(R.id.buttonGetTransactionData)
        val txtMonthBudget = root.findViewById<TextView>(R.id.txtMonthBudget)
        val txtMonthIncome = root.findViewById<TextView>(R.id.txtMonthIncome)
        val txtMonthSpend = root.findViewById<TextView>(R.id.txtMonthSpend)
        val txtSaving = root.findViewById<TextView>(R.id.txtSaving)

        var uri = socketIOModel.hostString

//        socketIOModel.hostString.observe(viewLifecycleOwner,{
//            uri = it
//        })

        try {
            mSocket = IO.socket(uri)

        } catch (e: URISyntaxException) {
            Log.d("URI error", e.message.toString())
        }

        try {
            mSocket?.connect()
            textViewHomeResult.text = "connected to " + uri + " " + mSocket?.connected()
        } catch (e: Exception) {
            textViewHomeResult.text = " Failed to connect. " + e.message
        }

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            textViewHomeResult.text = "sending"
            mSocket?.emit("messages", "hi")
        });

        binding.btnAdd.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_nav_home_to_newTransactionFragment)
        }

        val recyclerViewTransaction = root.findViewById<RecyclerView>(R.id.recyclerViewTransaction)
        buttonGetTransactionData.setOnClickListener {
            mSocket?.on("datasent", getTransactionData)
            mSocket?.on("userData",getUserData)
            mSocket?.emit("getTransactionData","kosha@gmail.com")
            mSocket?.emit("getUserData","kosha@gmail.com","pass")

            recyclerViewAdapter = RecyclerViewAdapter(dailyTransactionArrayList)
            val layoutManager = LinearLayoutManager(context)
            recyclerViewTransaction.layoutManager = layoutManager
            recyclerViewTransaction.adapter = recyclerViewAdapter
        }

        return root
    }

    var getTransactionData = Emitter.Listener {
        val data = it[0] as String
        Log.d("data received", data.toString())

        // Using data class Employee2
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter : JsonAdapter<List<DailyTransactions>> = moshi.adapter(newTransactionType)

        val dataList = adapter.fromJson(data)

        for (e in dataList ?: emptyList() ) {
            Log.i(this.toString(), "${e.description} - ${e.amount}")
            val dailyTransactionList = Transaction(e.category_name,e.category_type,e.amount,e.description,e.date,e.userEmail)
            dailyTransactionArrayList.add(dailyTransactionList)
            textViewHomeResult.setText("TransactionData")

            if(e.category_type == "Income")
            {
                income += e.amount.toString().toDouble()
                txtMonthIncome.text = income.toString()
            }
            if(e.category_type == "Expense")
            {
                expense += e.amount.toString().toDouble()
                txtMonthSpend.text = income.toString()
            }

        }
    }

    var getUserData = Emitter.Listener {
        val data = it[0] as String
        Log.d("data received", data.toString())

        // Using data class Employee2
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter : JsonAdapter<List<User>> = moshi.adapter(userType)

        val dataList = adapter.fromJson(data)

        for (e in dataList ?: emptyList() ) {
            Log.i(this.toString(), "${e.fname} - ${e.lname}")

            budget = e.budget.toString().toDouble()
            txtMonthBudget.text = budget.toString()

            requireActivity().runOnUiThread {
                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                saving = ((budget + income) - expense)
                txtSaving.text = saving.toString()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}

