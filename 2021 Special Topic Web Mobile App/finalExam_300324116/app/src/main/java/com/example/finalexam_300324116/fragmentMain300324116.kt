package com.example.finalexam_300324116

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.example.finalexam_300324116.databinding.FragmentMain300324116FragmentBinding
import com.squareup.moshi.Types
import io.socket.client.Socket

class fragmentMain300324116 : Fragment() {

    private lateinit var binding: FragmentMain300324116FragmentBinding

    companion object {
        fun newInstance() = fragmentMain300324116()
    }

    private lateinit var viewModel: FragmentMain300324116ViewModel

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    var mSocket: Socket? = null
    private val listingsType = Types.newParameterizedType(List::class.java, ListingsAndReview::class.java)
    var listingsAndReviewArrayList = mutableListOf<ListingsAndReview>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMain300324116FragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonSearchMain = root.findViewById<Button>(R.id.buttonSearchMain)
        val buttonViewBooking = root.findViewById<Button>(R.id.buttonViewBooking)
        val buttonUserRegistration = root.findViewById<Button>(R.id.buttonUserRegistration)

        buttonSearchMain.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_fragmentMain300324116_to_fragmentSearchAirbnb300324116)
        }

        buttonViewBooking.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_fragmentMain300324116_to_fragmentViewBookings3003241162)
        }

        buttonUserRegistration.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_fragmentMain300324116_to_fragmentRegistration300324116)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentMain300324116ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}