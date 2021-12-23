package com.t.simpletodoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        listOfTasks.add("Do assignment")
        listOfTasks.add("Work on the report")
        listOfTasks.add("Sleep")

        loadItems()

        // Look up recyclerView in layout
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // set the button and input field, do that the user can enter a task and add it to the list.
        findViewById<Button>(R.id.button).setOnClickListener {
            val userInputTask = inputTextField.text.toString()

            listOfTasks.add(userInputTask)
            adapter.notifyItemInserted(listOfTasks.size - 1)

            inputTextField.setText("")
            saveItems()
        }
    }

    // Data persistence by reading and writing from a file.
    fun getDataFile(): File {
        return File(filesDir, "data.txt")
    }

    // Load the item by reading every line in the data
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}