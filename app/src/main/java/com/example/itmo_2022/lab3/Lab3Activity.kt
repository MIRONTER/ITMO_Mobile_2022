package com.example.itmo_2022.lab3

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.itmo_2022.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Lab3Activity : AppCompatActivity(), CoroutineScope {

    private val gamesIDs: MutableList<Int> = mutableListOf()
    private var position: Int = 0
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab3)

        val buttonAdd = findViewById<Button>(R.id.lab3_btn_add)
        val buttonDelete = findViewById<Button>(R.id.lab3_btn_delete)
        val editTextTitle = findViewById<EditText>(R.id.lab3_editText_title)
        val editTextYear = findViewById<EditText>(R.id.lab3_editText_year)
        val editTextDescription = findViewById<EditText>(R.id.lab3_editText_description)
        val editTextImageURL = findViewById<EditText>(R.id.lab3_editText_imageURL)
        val list = findViewById<ListView>(R.id.lab3_list)
        val fab = findViewById<FloatingActionButton>(R.id.lab3_fab)

        updateGamesList()

        val lab3DetailIntent = Intent(this, Lab3DetailActivity::class.java)
        list.setOnItemClickListener { _, _, i, _ ->
            launch()
            {
                val response = requests.getGame(gamesIDs[i])
                if (response != null) {
                    lab3DetailIntent.putExtra("id", response.id)
                    lab3DetailIntent.putExtra("title", response.title)
                    lab3DetailIntent.putExtra("year", response.year)
                    lab3DetailIntent.putExtra("image", response.imageURL)
                    lab3DetailIntent.putExtra("description", response.description)
                    startActivity(lab3DetailIntent)
                }
            }
        }

        list.setOnItemLongClickListener { adapterView, _, i, _ ->
            position = gamesIDs[i]
            val txt =
                resources.getString(R.string.check) + adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
            true
        }

        buttonAdd.setOnClickListener {
            if (editTextTitle.text.isNotEmpty() && editTextYear.text.isNotEmpty() && editTextDescription.text.isNotEmpty() && editTextImageURL.text.isNotEmpty()) {
                launch()
                {
                    val response = requests.createGame(
                        editTextTitle.text.toString(),
                        Integer.parseInt(editTextYear.text.toString()),
                        editTextDescription.text.toString(),
                        editTextImageURL.text.toString()
                    )
                    updateGamesList()
                    Toast.makeText(
                        applicationContext,
                        response.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    editTextTitle.text.clear()
                    editTextYear.text.clear()
                    editTextDescription.text.clear()
                    editTextImageURL.text.clear()
                }
            }
        }

        buttonDelete.setOnClickListener {
            if (position != 0) {
                launch()
                {
                    val response = requests.deleteGame(position)
                    Toast.makeText(
                        applicationContext,
                        response.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    updateGamesList()
                }
            }
        }

        fab.setOnClickListener { updateGamesList() }

    }

    private fun updateGamesList() {
        val list = findViewById<ListView>(R.id.lab3_list)
        val gamesList: MutableList<String> = mutableListOf()
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            gamesList
        )
        gamesIDs.clear()
        launch()
        {
            val response = requests.getAllGames()
            for (game in response) {
                gamesList += game.title
                gamesIDs += game.id
            }
            list.adapter = arrayAdapter
            arrayAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}