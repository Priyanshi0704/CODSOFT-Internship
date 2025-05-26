package com.example.todolist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskList: ArrayList<Tasks>
    private lateinit var adapter: MyAdapter
    private lateinit var addBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing views
        recyclerView = findViewById(R.id.recyclerView)
        addBtn = findViewById(R.id.addBtn)

        recyclerView.layoutManager = LinearLayoutManager(this)
        taskList = ArrayList()

        // Sample data
        val task = arrayOf(
            "Do 2 Questions of DSA",
            "Attend the meeting at 3:00 PM",
            "Go to market",
            "Do Assignment of DBMS",
            "Dance Practice",
            "Drink 3L of water"
        )
        val taskType = arrayOf("Study", "Work", "Home", "Study", "Dance", "Water")

        for (i in task.indices) {
            taskList.add(Tasks(task[i], taskType[i]))
        }

        adapter = MyAdapter(taskList, this)
        recyclerView.adapter = adapter

        // Add button click listener
        addBtn.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialogue, null)

        val taskInput = dialogView.findViewById<TextInputEditText>(R.id.AddTaskTT)
        val taskTypeInput = dialogView.findViewById<TextInputEditText>(R.id.AddTaskTypeTT)
        val addButton = dialogView.findViewById<Button>(R.id.add_Button)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        addButton.setOnClickListener {
            val taskText = taskInput.text.toString().trim()
            val typeText = taskTypeInput.text.toString().trim()

            if (taskText.isEmpty() || typeText.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show()
            } else {
                taskList.add(Tasks(taskText, typeText))
                adapter.notifyItemInserted(taskList.size - 1)
                alertDialog.dismiss()
            }
        }

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}
