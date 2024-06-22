package com.csc2074.assignment1.geoquiz

object Constants {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_question"
    const val CORRECT_ANSWERS: String = "correct_answers"
    const val CHEATS_USED: String = "cheats_used"

    fun getQuestions(): ArrayList<Question>{
        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1,
            "What country does this flag belong to?",
            R.drawable.br_flag,
            "Argentina",
            "Belgium",
            "Brazil",
            "Cambodia",
            3,
            0
            )

        val que2 = Question(
            2,
            "What country does this flag belong to?",
            R.drawable.ja_flag,
            "Japan",
            "Denmark",
            "Ethiopia",
            "Finland",
            1,
            0
        )

        val que3 = Question(
            3,
            "What country does this flag belong to?",
            R.drawable.ni_flag,
            "Greece",
            "Nigeria",
            "Honduras",
            "Iceland",
            2,
            0
        )

        val que4 = Question(
            4,
            "What country does this flag belong to?",
            R.drawable.ca_flag,
            "Jamaica",
            "Kazakhstan",
            "Lebanon",
            "Canada",
            4,
            0
        )

        val que5 = Question(
            5,
            "What country does this flag belong to?",
            R.drawable.it_flag,
            "Italy",
            "Madagascar",
            "Nepal",
            "Oman",
            1,
            0
        )

        val que6 = Question(
            6,
            "What country does this flag belong to?",
            R.drawable.as_flag,
            "Pakistan",
            "Qatar",
            "Australia",
            "Romania",
            3,
            0
        )

        val que7 = Question(
            7,
            "What country does this flag belong to?",
            R.drawable.in_flag,
            "Serbia",
            "Thailand",
            "Ukraine",
            "India",
            4,
            0
        )

        val que8 = Question(
            8,
            "What country does this flag belong to?",
            R.drawable.eg_flag,
            "Egypt",
            "Vietnam",
            "Yemen",
            "Zimbabwe",
            1,
            0
        )

        val que9 = Question(
            9,
            "What country does this flag belong to?",
            R.drawable.my_flag,
            "Australia",
            "Malaysia",
            "Brazil",
            "Canada",
            2,
            0
        )

        val que10 = Question(
            10,
            "What country does this flag belong to?",
            R.drawable.ks_flag,
            "Egypt",
            "India",
            "South Korea",
            "Japan",
            3,
            0
        )

        questionsList.add(que1)
        questionsList.add(que2)
        questionsList.add(que3)
        questionsList.add(que4)
        questionsList.add(que5)
        questionsList.add(que6)
        questionsList.add(que7)
        questionsList.add(que8)
        questionsList.add(que9)
        questionsList.add(que10)

        return questionsList
    }
}