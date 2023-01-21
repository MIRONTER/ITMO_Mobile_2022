package com.example.itmo_2022

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.itmo_2022.lab1.Lab1Activity
import com.example.itmo_2022.lab2.ui.Lab2Activity
import com.example.itmo_2022.lab3.Lab3Activity
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private val prefName = "PREFS_FILE"
    private val prefKey = "PREF_SCORES"
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showBurgerButton()

        val settings = getSharedPreferences(prefName, MODE_PRIVATE)
        var scores = settings.getInt(prefKey, 0)
        val scoresView = findViewById<TextView>(R.id.txt_scores)
        scoresView.text = scores.toString()

        fun edScores(int: Int) {
            val prefEditor: SharedPreferences.Editor = settings.edit()
            prefEditor.putInt(prefKey, int)
            prefEditor.apply()
        }

        val btnUp = findViewById<Button>(R.id.btn_up)
        btnUp.setOnClickListener {
            if (scores < 100) {
                scores++
                scoresView.text = scores.toString()
                edScores(scores)
            }
        }

        val btnDown = findViewById<Button>(R.id.btn_down)
        btnDown.setOnClickListener {
            if (scores > 0) {
                scores--
                scoresView.text = scores.toString()
                edScores(scores)
            }
        }

        val lab1Intent = Intent(this, Lab1Activity::class.java)
        val btnLab1 = findViewById<Button>(R.id.btn_lab1)
        btnLab1.setOnClickListener {
            startActivity(lab1Intent)
        }

        val lab2Intent = Intent(this, Lab2Activity::class.java)
        val btnLab2 = findViewById<Button>(R.id.btn_lab2)
        btnLab2.setOnClickListener {
            startActivity(lab2Intent)
        }

        val lab3Intent = Intent(this, Lab3Activity::class.java)
        val btnLab3 = findViewById<Button>(R.id.btn_lab3)
        btnLab3.setOnClickListener {
            startActivity(lab3Intent)
        }

        val nav = findViewById<NavigationView>(R.id.nav_view)
        nav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_1 -> startActivity(lab1Intent)
                R.id.nav_2 -> startActivity(lab2Intent)
                R.id.nav_3 -> startActivity(lab3Intent)
            }
            true
        }
    }

    private fun showBurgerButton()
    {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(
            this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}