package com.sushmoyr.todoapplication.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sushmoyr.todoapplication.R
import com.sushmoyr.todoapplication.data.model.Priority
import com.sushmoyr.todoapplication.data.model.ToDoData
import com.sushmoyr.todoapplication.data.viewmodel.ToDoViewModel
import com.sushmoyr.todoapplication.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)

        view.current_title_et.setText(args.currentItem.title)
        view.current_description_et.setText(args.currentItem.description)
        view.current_priorities_spinner.setSelection(parsePriority(args.currentItem.priority))
        view.current_priorities_spinner.onItemSelectedListener = sharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_save -> {
                updateDataInDb()
            }
            R.id.menu_delete -> {
                deleteItem()
            }
        }


        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            toDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(), "Item Deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete?")
        builder.setMessage("Are you sure want to '${args.currentItem.title}'?")
        builder.show()
    }

    private fun updateDataInDb() {
        val mTitle = current_title_et.text.toString()
        val mSpinner = current_priorities_spinner.selectedItem.toString()
        val mDesc = current_description_et.text.toString()

        val validation = sharedViewModel.verifyData(mTitle, mDesc)

        if(validation){
            val toDoData = ToDoData(args.currentItem.id,
                mTitle,
                sharedViewModel.parsePriority(mSpinner),
                mDesc)

            toDoViewModel.updateData(toDoData)
            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(), "Please Fill All", Toast.LENGTH_SHORT).show()
        }
    }


    private fun parsePriority(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

}