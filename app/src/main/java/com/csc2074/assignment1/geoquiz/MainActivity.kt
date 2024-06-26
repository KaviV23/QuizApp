package com.csc2074.assignment1.geoquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.btn_start)
        val etName: AppCompatEditText = findViewById(R.id.et_name)
        val btnScoreboard: Button = findViewById(R.id.btn_scoreboard)

        btnStart.setOnClickListener {
            if(etName.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter name before continuing", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, etName.text.toString())
                startActivity(intent)
            }
        }
        
        btnScoreboard.setOnClickListener {
            if (Scoreboard.getScoreboard(this).isNullOrEmpty()) {
                Toast.makeText(this, "Scoreboard is Empty", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ScoreboardActivity::class.java)
                startActivity(intent)
            }
        }
    }
}