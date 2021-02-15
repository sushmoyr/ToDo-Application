package com.sushmoyr.todoapplication.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushmoyr.todoapplication.R
import com.sushmoyr.todoapplication.data.viewmodel.ToDoViewModel
import com.sushmoyr.todoapplication.fragments.ListAdapter
import com.sushmoyr.todoapplication.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val adapter: ListAdapter by lazy {
        ListAdapter()
    }
    private val todoViewModel:ToDoViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        todoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data->
            sharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        view.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyView(it)
        })

        return view
    }

    private fun showEmptyView(emptyDatabase: Boolean) {
        if(emptyDatabase){
            noDataImage.visibility = View.VISIBLE
            noDataText.visibility = View.VISIBLE
        }else{
            noDataImage.visibility = View.INVISIBLE
            noDataText.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_delete_all){
            confirmDelete()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            todoViewModel.deleteAll()
            Toast.makeText(requireContext(), "All Data Deleted", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure want to delete EVERYTHING?")
        builder.show()
    }

}