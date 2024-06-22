package com.csc2074.assignment1.geoquiz

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.PersistableBundle
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
private var mUserName: String? = null
private var mRemainingCheats: Int = 3

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var tvQuestion: TextView
    private lateinit var ivImage: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var tvBack: TextView
    private lateinit var tvCheat: TextView
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        tvBack = findViewById(R.id.tv_back)
        tvCheat = findViewById(R.id.tv_cheat)
        btnSubmit = findViewById(R.id.btn_submit)

        mQuestionsList = Constants.getQuestions()

        setQuestion()

        tvBack.setOnClickListener(this)
        tvCheat.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {
        val question = mQuestionsList!![mCurrentPosition-1]

        defaultOptionView()

        if(question.selectedAnswer != 0) {
            tvCheat.setOnClickListener(null)
            if(question!!.correctAnswer != question.selectedAnswer) {
                answerView(question.selectedAnswer, R.drawable.wrong_option_border_bg)
            }
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
            tvOptionOne.setOnClickListener(null)
            tvOptionTwo.setOnClickListener(null)
            tvOptionThree.setOnClickListener(null)
            tvOptionFour.setOnClickListener(null)
        } else {
            tvCheat.setOnClickListener(this)
            tvOptionOne.setOnClickListener(this)
            tvOptionTwo.setOnClickListener(this)
            tvOptionThree.setOnClickListener(this)
            tvOptionFour.setOnClickListener(this)
        }

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

    private fun cheat() {
        val question = mQuestionsList?.get(mCurrentPosition -1)
        if(mRemainingCheats != 0) {
            mCorrectAnswers++
            mRemainingCheats--
            question!!.selectedAnswer = question.correctAnswer
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
            btnSubmit.text = "Next Question"
            mSelectedOptionPosition = 0

            tvOptionOne.setOnClickListener(null)
            tvOptionTwo.setOnClickListener(null)
            tvOptionThree.setOnClickListener(null)
            tvOptionFour.setOnClickListener(null)

            tvCheat.text = "Cheat ($mRemainingCheats/3)"
            tvCheat.setOnClickListener(null)
        } else {
            Toast.makeText(this, "All Cheats Used", Toast.LENGTH_SHORT).show()
            tvCheat.setOnClickListener(null)
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
            R.id.tv_back->{
                if(mCurrentPosition > 1) {
                    mSelectedOptionPosition = 0
                    mCurrentPosition --
                    setQuestion()
                    btnSubmit.text = "Next Question"
                }
            }
            R.id.tv_cheat-> {
                cheat()
            }
            R.id.btn_submit-> {
                if(mSelectedOptionPosition == 0) {
                    mCurrentPosition ++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size ->{
                            setQuestion()
                        }else ->{
                            Toast.makeText(this, "Quiz Completed, $mCorrectAnswers/${mQuestionsList!!.size}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            intent.putExtra(Constants.CHEATS_USED, 3-mRemainingCheats)
                            startActivity(intent)
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition -1)
                    if(question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    question.selectedAnswer = mSelectedOptionPosition
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                    tvCheat.setOnClickListener(null)
                    tvOptionOne.setOnClickListener(null)
                    tvOptionTwo.setOnClickListener(null)
                    tvOptionThree.setOnClickListener(null)
                    tvOptionFour.setOnClickListener(null)

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentPosition", mCurrentPosition)
        outState.putParcelableArrayList("questionsList", mQuestionsList)
        outState.putInt("selectedOptionPosition", mSelectedOptionPosition)
        outState.putInt("correctAnswers", mCorrectAnswers)
        outState.putString("userName", mUserName)
        outState.putInt("remainingCheats", mRemainingCheats )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        mCurrentPosition = savedInstanceState.getInt("currentPosition")
        @Suppress("DEPRECATION")
        mQuestionsList = savedInstanceState.getParcelableArrayList("questionsList")
        mSelectedOptionPosition = savedInstanceState.getInt("selectedOptionPosition", 0)
        mCorrectAnswers = savedInstanceState.getInt("correctAnswers")
        mUserName = savedInstanceState.getString("userName")
        mRemainingCheats = savedInstanceState.getInt("remainingCheats")
        tvCheat.text = "Cheat ($mRemainingCheats/3)"

        setQuestion()

        when(mSelectedOptionPosition) {
            1-> selectedOptionView(tvOptionOne, 1)
            2-> selectedOptionView(tvOptionTwo, 2)
            3-> selectedOptionView(tvOptionThree, 3)
            4-> selectedOptionView(tvOptionFour, 4)
        }
    }
}