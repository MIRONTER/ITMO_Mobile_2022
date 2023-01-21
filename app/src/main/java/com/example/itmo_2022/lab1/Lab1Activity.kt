package com.example.itmo_2022.lab1

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import com.example.itmo_2022.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class Lab1Activity : AppCompatActivity() {

    private val tag = "LAB1"
    private val dataLabel = "DATA_LABEL"
    private val dataColor = "DATA_COLOR"
    private val dataList = "DATA_LIST"

    private var label = ""
    private var changeColor = false
    private var showList = true

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab1)

        label = getString(R.string.lab1_label)
        savedInstanceState?.let {
            label = it.getString(dataLabel).toString()
            changeColor = it.getBoolean(dataColor)
            showList = it.getBoolean(dataList)
        }

        val lab1EditText = findViewById<EditText>(R.id.lab1EditText)
        val lab1Label = findViewById<TextView>(R.id.lab1Label)
        val lab1List = findViewById<ListView>(R.id.lab1List)
        val lab1Coordinator = findViewById<CoordinatorLayout>(R.id.lab1Coordinator)

        lab1Label.text = label
        if (changeColor) {
            lab1Label.setTextColor(Color.GREEN)
        }
        val lab1BtnHideList = findViewById<Button>(R.id.lab1BtnHideList)
        if(!showList)
        {
            lab1List.visibility = View.INVISIBLE
            lab1BtnHideList.text = getString(R.string.show_list)
        }

        val pics = arrayOf(
            R.drawable.ratchet,
            R.drawable.jak,
            R.drawable.sly,
        )
        val icons = arrayOf(
            R.drawable.ratchet_svg,
            R.drawable.jak_svg,
            R.drawable.sly_svg,
        )
        val descriptions = resources.getStringArray(R.array.games_description)

        lab1List.setOnItemClickListener { adapterView, _, i, _ ->
            val lab1DetailIntent = Intent(this, Lab1DetailActivity::class.java)
            lab1DetailIntent.putExtra("title", adapterView.getItemAtPosition(i).toString())
            lab1DetailIntent.putExtra("pics", pics[i])
            lab1DetailIntent.putExtra("icon", icons[i])
            lab1DetailIntent.putExtra("description", descriptions[i])
            startActivity(lab1DetailIntent)
        }

        val colorDrawable = ColorDrawable(Color.GREEN)
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        val lab1BtnToast = findViewById<Button>(R.id.lab1BtnToast)
        lab1BtnToast.setOnClickListener {
            val str = "Toast " + getString(R.string.works)
            Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
            Log.d(tag, str)
        }

        lab1BtnHideList.setOnClickListener {
            showList = if (lab1List.isVisible) {
                lab1List.visibility = View.INVISIBLE
                lab1BtnHideList.text = getString(R.string.show_list)
                Log.d(tag, "The list is hidden")
                false
            } else {
                lab1List.visibility = View.VISIBLE
                lab1BtnHideList.text = getString(R.string.hide_list)
                Log.d(tag, "The list is revealed")
                true
            }
        }

        val lab1SwitchChangeColor = findViewById<Switch>(R.id.lab1SwitchChangeColor)
        lab1SwitchChangeColor.setOnClickListener {
            changeColor = if (lab1SwitchChangeColor.isChecked) {
                lab1Label.setTextColor(Color.GREEN)
                true
            } else {
                lab1Label.setTextColor(Color.GRAY)
                false
            }
        }

        val lab1Fab = findViewById<FloatingActionButton>(R.id.lab1Fab)
        lab1Fab.setOnClickListener {
            label = lab1EditText.text.toString()
            lab1Label.text = lab1EditText.text
            lab1EditText.text.clear()
            Snackbar.make(lab1Coordinator, getString(R.string.text_changed), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(dataLabel, label)
        outState.putBoolean(dataColor, changeColor)
        outState.putBoolean(dataList, showList)
        super.onSaveInstanceState(outState)
    }
}