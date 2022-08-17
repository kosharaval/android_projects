package com.example.pocketbook.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pocketbook.R
import com.example.pocketbook.SocketIOModel
import com.example.pocketbook.databinding.FragmentProfileBinding
import com.example.pocketbook.entities.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_new_transaction.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.net.URISyntaxException
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private lateinit var socketIOModel: SocketIOModel

    var mSocket: Socket? = null
    private val userType = Types.newParameterizedType(List::class.java, User::class.java)

    var user = User()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        socketIOModel = ViewModelProvider(this).get(SocketIOModel::class.java)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textViewProfileResult = root.findViewById<TextView>(R.id.textViewProfileResult)
        val textViewName = root.findViewById<TextView>(R.id.textViewName)
        val textViewEmail = root.findViewById<TextView>(R.id.textViewEmail)
        val textViewPhone = root.findViewById<TextView>(R.id.textViewPhone)
        val titleBudget = root.findViewById<TextView>(R.id.titleBudget)
        val textViewBudget = root.findViewById<TextView>(R.id.textViewBudget)
        val buttonGetUserData = root.findViewById<Button>(R.id.buttonGetUserData)

        var uri = socketIOModel.hostString

//        socketIOModel.hostString.observe(viewLifecycleOwner,{
//            uri = it
//        })

        textViewName.text = user.fname + user.lname
        textViewEmail.text = user.email
        textViewPhone.text = user.phone
        textViewBudget.text = user.budget.toString()

        try {
            mSocket = IO.socket(uri)

        } catch (e: URISyntaxException) {
            Log.d("URI error", e.message.toString())
        }

        try {
            mSocket?.connect()
            textViewProfileResult.text = "connected to " + uri + " " + mSocket?.connected()
        } catch (e: Exception) {
            textViewProfileResult.text = " Failed to connect. " + e.message
        }

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            textViewProfileResult.text = "sending"
            mSocket?.emit("messages", "hi")
        });

        buttonGetUserData.setOnClickListener {
            mSocket?.emit("getUserData","kosha@gmail.com","pass")
        }

        mSocket?.on("userData", getUserData)

        titleBudget.setOnClickListener(View.OnClickListener {
            addBudget(textViewBudget)

        })

        mSocket?.emit("userUpdate",onUserUpdated)

        
        return root
    }

    var onUserUpdated = Emitter.Listener {
        val message = it[0] as String
        textViewProfileResult.text = message
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

            var fname: String = e.fname
            var lname: String = e.lname
            var email: String = e.email
            var phone: String = e.phone

            textViewProfileResult.setText("User Data Loaded")
            textViewName.setText(fname +" " + lname)
            textViewEmail.setText(email)
            textViewPhone.setText(phone)
        }
    }

    fun addBudget(textViewBudget: TextView) {

        val builder = AlertDialog.Builder(context)
        val budgetLayout: View = layoutInflater.inflate(R.layout.dialog_add_budget, null)
        builder.setView(budgetLayout)

        builder.setPositiveButton("SAVE") { dialog, which ->
            val editTextAmount = budgetLayout.findViewById<EditText>(R.id.editTextAmount)
            if (editTextAmount.text.toString() != "") {

                textViewBudget.text = "$" + editTextAmount.text.toString()
                var budget = editTextAmount.text.toString().toDouble()
                mSocket?.emit("addBudget",textViewEmail.text,budget)
                Toast.makeText(
                    context,
                    "Budget of $" + editTextAmount.text.toString() + " added",
                    Toast.LENGTH_SHORT
                ).show()

            } else Toast.makeText(context, "Budget discarded, cannot be empty", Toast.LENGTH_SHORT)
                .show()
        }
            .setNegativeButton(
                "CANCEL"
            ) { dialog, which -> dialog.dismiss() }
        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }
    
    
    override fun onDestroyView() {
        super.onDestroyView()
    }
}