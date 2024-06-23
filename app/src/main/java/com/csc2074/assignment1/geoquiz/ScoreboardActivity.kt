package com.csc2074.assignment1.geoquiz

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreboardActivity : AppCompatActivity() {
    private lateinit var lvScoreboard: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var btnMainMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        lvScoreboard = findViewById(R.id.lv_scoreboard)
        btnMainMenu = findViewById(R.id.btn_main_menu)

        val scoreboardEntries = Scoreboard.getScoreboard(this)
        val entries = scoreboardEntries?.map { "${it[0]} - Score: ${it[1]}, Cheats: ${it[2]}" } ?: emptyList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, entries)
        lvScoreboard.adapter = adapter

        btnMainMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}