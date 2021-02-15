package com.sushmoyr.todoapplication.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sushmoyr.todoapplication.R
import com.sushmoyr.todoapplication.data.model.Priority
import com.sushmoyr.todoapplication.data.model.ToDoData
import com.sushmoyr.todoapplication.fragments.list.ListFragmentDirections
import kotlinx.android.synthetic.main.custom_row_layout.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.title_text.text = dataList[position].title
        holder.itemView.description_text.text = dataList[position].description
        holder.itemView.row_background.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }
        val priority = dataList[position].priority
        when (dataList[position].priority){
            Priority.HIGH -> holder.itemView.indicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.red)
            )
            Priority.MEDIUM -> holder.itemView.indicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.yellow)
            )
            Priority.LOW -> holder.itemView.indicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.green)
            )
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(todoData : List<ToDoData>){
        this.dataList = todoData
        notifyDataSetChanged()
    }
}