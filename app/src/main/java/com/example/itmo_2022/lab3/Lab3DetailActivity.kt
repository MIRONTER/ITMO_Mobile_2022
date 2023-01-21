package com.example.itmo_2022.lab3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.itmo_2022.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Lab3DetailActivity : AppCompatActivity(), CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab3_detail)

        val lab3DetailTitle = findViewById<TextView>(R.id.lab3_title)
        val lab3DetailDescription = findViewById<TextView>(R.id.lab3_description)
        val lab3DetailImage = findViewById<ImageView>(R.id.lab3_image)
        val editTextTitle = findViewById<EditText>(R.id.lab3_detail_editText_title)
        val editTextYear = findViewById<EditText>(R.id.lab3_detail_editText_year)
        val editTextDescription = findViewById<EditText>(R.id.lab3_detail_editText_description)
        val editTextImageURL = findViewById<EditText>(R.id.lab3_detail_editText_imageURL)
        val buttonUpdate = findViewById<Button>(R.id.btn_lab3_update)

        val id = Integer.parseInt(intent.getSerializableExtra("id").toString())
        var title = intent.getSerializableExtra("title").toString()
        var year = intent.getSerializableExtra("year").toString()
        var description = intent.getSerializableExtra("description").toString()
        var imageURL = intent.getSerializableExtra("image").toString()

        fun fill() {
            lab3DetailTitle.text = "$title ($year)"
            lab3DetailDescription.text = description
            Picasso.get().load(imageURL).into(lab3DetailImage)
        }

        fill()
        editTextTitle.setText(title)
        editTextYear.setText(year)
        editTextDescription.setText(description)
        editTextImageURL.setText(imageURL)

        buttonUpdate.setOnClickListener {
            if (editTextTitle.text.isNotEmpty() && editTextYear.text.isNotEmpty() && editTextDescription.text.isNotEmpty() && editTextImageURL.text.isNotEmpty()) {
                launch()
                {
                    val response = requests.updateGame(
                        id,
                        editTextTitle.text.toString(),
                        Integer.parseInt(editTextYear.text.toString()),
                        editTextDescription.text.toString(),
                        editTextImageURL.text.toString()
                    )
                    Toast.makeText(
                        applicationContext,
                        response.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            title = editTextTitle.text.toString()
            year = editTextYear.text.toString()
            description = editTextDescription.text.toString()
            imageURL = editTextImageURL.text.toString()
            fill()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

