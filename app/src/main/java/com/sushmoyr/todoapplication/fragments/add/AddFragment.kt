package com.sushmoyr.todoapplication.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sushmoyr.todoapplication.R
import com.sushmoyr.todoapplication.data.model.Priority
import com.sushmoyr.todoapplication.data.model.ToDoData
import com.sushmoyr.todoapplication.data.viewmodel.ToDoViewModel
import com.sushmoyr.todoapplication.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private val todoViewModel : ToDoViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by viewModels()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        view.priorities_spinner.onItemSelectedListener = sharedViewModel.listener

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                saveDataToDB()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveDataToDB() {
        val mTitle = title_et.text.toString()
        val mSpinner = priorities_spinner.selectedItem.toString()
        val mDesc = description_et.text.toString()

        val validation = sharedViewModel.verifyData(mTitle, mDesc)
        if(validation){
            val toDoData = ToDoData(0, mTitle, sharedViewModel.parsePriority(mSpinner) ,mDesc)
            todoViewModel.insertData(toDoData)
            Toast.makeText(requireContext(), "Data added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }


    }



}