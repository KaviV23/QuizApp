package com.csc2074.assignment1.geoquiz

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException

object Scoreboard {
    private var mScoreboard: ArrayList<Array<String>>? = null
    fun addEntry(context: Context, scoreboardEntry: Array<String>) {
        retrieveFromDisk(context)
        mScoreboard?.add(scoreboardEntry)
        saveToDisk(context, mScoreboard)
    }

    fun getScoreboard(context: Context): ArrayList<Array<String>>? {
        retrieveFromDisk(context)
        return mScoreboard
    }

    private fun retrieveFromDisk(context: Context) {
        val filePath = context.filesDir.path + "/scoreboard.json"

        try {
            val scoreboardJson = File(filePath).readText()
            val gson = Gson()
            val scoreboardType = object : TypeToken<ArrayList<Array<String>>>() {}.type
            mScoreboard = gson.fromJson<ArrayList<Array<String>>>(scoreboardJson, scoreboardType)
        } catch (e: IOException) {
            e.printStackTrace()
            mScoreboard = ArrayList()
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            mScoreboard = ArrayList()
        }
    }

    private fun saveToDisk(context: Context, scoreboard: ArrayList<Array<String>>?) {
        val gson = Gson()
        val scoreboardJson = gson.toJson(scoreboard)

        val filePath = context.filesDir.path + "/scoreboard.json"
        try {
            File(filePath).writeText(scoreboardJson)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}