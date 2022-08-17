package com.example.mynavcompdemo1

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.mynavcompdemo1.databinding.AddNoteFragmentBinding

class AddNoteFragment : Fragment() {

    companion object {
        fun newInstance() = AddNoteFragment()
    }

    private lateinit var viewModel: AddNoteViewModel
    private lateinit var bindingAddNoteFragment : AddNoteFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        bindingAddNoteFragment = AddNoteFragmentBinding.inflate(inflater,container,false)
        val bundle = Bundle()

        bindingAddNoteFragment.buttonSave.setOnClickListener {

            var newNoteText = bindingAddNoteFragment.editTextNewNote.text
            bundle.putString("NewNoteText",newNoteText.toString())

            NavHostFragment.findNavController(this).navigate(R.id.action_addNoteFragment_to_mainFragment,bundle)
            bindingAddNoteFragment.editTextNewNote.setText("")
        }
        return bindingAddNoteFragment.root
    }

}