package com.example.pocketbook

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketbook.databinding.ActivityNavigationDrawerBinding
import com.example.pocketbook.entities.Category
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.content_navigation_drawer.*
import org.json.JSONObject
import java.io.InputStream
import java.net.URISyntaxException

class NavigationDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationDrawerBinding

    var mSocket: Socket? = null
    var userName: String? = "kosha";
    lateinit var roomName: String;
    private val myType = Types.newParameterizedType(List::class.java, Category::class.java)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigationDrawer.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile, R.id.nav_reports
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        //Connecting to the Server:
        var string: String?
        try {
            val inputStream: InputStream = assets.open("source.txt")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            string = String(buffer)
            textViewHost.setText(string)
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }


        try {
            mSocket = IO.socket(textViewHost.text.toString())

        } catch (e: URISyntaxException) {
            Log.d("URI error", e.message.toString())
        }


        try {
            mSocket?.connect()
            textViewResult.text = "connected to " + textViewHost.text.toString() + " " + mSocket?.connected()
        } catch (e: Exception) {
            textViewResult.text = " Failed to connect. " + e.message
        }

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            textViewResult.text = "sending"
            mSocket?.emit("messages", "hi")
        });

        //listening and opportunity for the other to talk
        //next 2 lines are listening part
        mSocket?.on("notification", onNewUser) // To know if the new user entered the room.
        mSocket?.on("datasent", onDataRequest)
        mSocket?.on("userAdded", onUserLogin)


        // ===============
        buttonGetCategory.setOnClickListener {
            mSocket?.emit("getTheData",userName?.toString())
        }


    }

    var onUserLogin = Emitter.Listener {
        val data = it[0] as String
        Log.d("data received", data.toString())

        // Using data class Employee2
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter : JsonAdapter<List<Category>> = moshi.adapter(myType)

        val dataList = adapter.fromJson(data)
        textViewResult.text = ""

        for (e in dataList ?: emptyList() ) {
            Log.i(this.toString(), "${e.category_name} - ${e.category_type}")
            textViewResult.append("${e.category_name} - ${e.category_type} \n")
        }
    }

    var onDataRequest = Emitter.Listener {
        val data = it[0] as String
        Log.d("data received", data.toString())

        // Using data class Employee2
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter : JsonAdapter<List<Category>> = moshi.adapter(myType)

        val dataList = adapter.fromJson(data)
        textViewResult.text = ""

        for (e in dataList ?: emptyList() ) {
            Log.i(this.toString(), "${e.category_name} - ${e.category_type}")
            textViewResult.append("${e.category_name} - ${e.category_type} \n")
        }
    }

    var onNewUser = Emitter.Listener {
        val message = it[0] as String
        textViewResult.text = message
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}