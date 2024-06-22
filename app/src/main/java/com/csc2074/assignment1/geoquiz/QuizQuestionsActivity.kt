package com.csc2074.assignment1.geoquiz

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private var mCurrentPosition: Int = 1
private var mQuestionsList: ArrayList<Question>? = null
private var mSelectedOptionPosition: Int = 0
private var mCorrectAnswers: Int = 0


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var tvQuestion: TextView
    private lateinit var ivImage: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)

        mQuestionsList = Constants.getQuestions()

        setQuestion()

        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {
        val question = mQuestionsList!![mCurrentPosition-1]

        defaultOptionView()

        if(mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit.text = "Finish"
        } else {
            btnSubmit.text = "Submit"
        }

        progressBar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition" + "/" + progressBar.max

        tvQuestion.text = question!!.question
        ivImage.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(0, tvOptionTwo)
        options.add(0, tvOptionThree)
        options.add(0, tvOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#999999"))
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
            option.setTypeface(Typeface.DEFAULT)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#000000"))
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
        tv.setTypeface(Typeface.DEFAULT_BOLD)
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when(answer) {
            1 ->{
                tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 ->{
                tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 ->{
                tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 ->{
                tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(tvOptionOne, 1)
            }
            R.id.tv_option_two-> {
                selectedOptionView(tvOptionTwo, 2)
            }
            R.id.tv_option_three-> {
                selectedOptionView(tvOptionThree, 3)
            }
            R.id.tv_option_four-> {
                selectedOptionView(tvOptionFour, 4)
            }
            R.id.btn_submit-> {
                if(mSelectedOptionPosition == 0) {
                    mCurrentPosition ++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size ->{
                            setQuestion()
                        }else ->{
                            Toast.makeText(this, "You completed the quiz", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition -1)
                    if(question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionsList!!.size) {
                        btnSubmit.text = "Finish"
                    } else {
                        btnSubmit.text = "Next Question"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }
}