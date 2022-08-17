package com.example.pocketbook.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pocketbook.R
import com.example.pocketbook.databinding.FragmentReportsBinding
import java.util.ArrayList


class ReportsFragment : Fragment() {

    private lateinit var reportsViewModel: ReportsViewModel
    private var _binding: FragmentReportsBinding? = null

    var reportMonths: Spinner? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reportsViewModel =
            ViewModelProvider(this).get(ReportsViewModel::class.java)

        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        reportMonths = root.findViewById<Spinner>(R.id.spMonth)
        val months: MutableList<String> = ArrayList()
        months.add("Bar Chart (Monthly Transactions)")
        months.add("Pie Chart (Monthly Category Wise")
        val adapterIncome = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, months)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}