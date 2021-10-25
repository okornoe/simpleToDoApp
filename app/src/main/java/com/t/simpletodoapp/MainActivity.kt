package com.t.simpletodoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val listOfTasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        listOfTasks.add("Do assignment")
        listOfTasks.add("Work on the report")
        listOfTasks.add("Sleep")

        // Look up recyclerView in layout
        val recyclerView =  findViewById<RecyclerView>(R.id.recyclerView)

        // create adapter passing in the sample user data
        val adapter = TaskItemAdapter(listOfTasks)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // set the button and input field, do that the user can enter a task and add it to the list.
        findViewById<Button>(R.id.button).setOnClickListener {
            val userInputTask =  inputTextField.text.toString()

            listOfTasks.add(userInputTask)
            adapter.notifyItemInserted(listOfTasks.size - 1)

            inputTextField.setText("")
        }
    }
}