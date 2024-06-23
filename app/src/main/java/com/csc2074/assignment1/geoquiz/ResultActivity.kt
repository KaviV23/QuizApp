package com.csc2074.assignment1.geoquiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvCheats: TextView
    private lateinit var btnFinish: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvName = findViewById(R.id.tv_name)
        tvScore = findViewById(R.id.tv_score)
        tvCheats = findViewById(R.id.tv_cheats_used)
        btnFinish = findViewById(R.id.btn_finish)

        val username = intent.getStringExtra(Constants.USER_NAME)
        tvName.text = username
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val cheatsUsed = intent.getIntExtra(Constants.CHEATS_USED, 0)

        tvScore.text = "Scored $correctAnswers out of $totalQuestions"
        tvCheats.text = "$cheatsUsed Cheats Used"

        val scoreboardEntry: Array<String> = arrayOf("$username", "$correctAnswers/$totalQuestions", cheatsUsed.toString())

        Scoreboard.addEntry(this, scoreboardEntry)

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}