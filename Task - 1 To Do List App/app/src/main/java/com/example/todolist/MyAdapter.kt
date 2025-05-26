package com.example.todolist

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val tasklist: ArrayList<Tasks>, val context: Activity) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskTitle)
        val taskTypes: TextView = itemView.findViewById(R.id.taskType)
        val checkBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.eachitem, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = tasklist[position]
        holder.taskName.text = currentItem.task
        holder.taskTypes.text = currentItem.taskType

        // Prevent previous listener from triggering
        holder.checkBox.setOnCheckedChangeListener(null)

        // Set checkbox to unchecked initially or based on data
        holder.checkBox.isChecked = currentItem.isCompleted

        // Set click listener for checkbox
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(context, "Task completed successfully!", Toast.LENGTH_SHORT).show()
                currentItem.isCompleted = true
            } else {
                currentItem.isCompleted = false
            }
        }
    }

    override fun getItemCount(): Int {
        return tasklist.size
    }
}
