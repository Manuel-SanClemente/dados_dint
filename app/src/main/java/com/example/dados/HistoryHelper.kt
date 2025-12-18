package com.example.dados

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale

object HistoryHelper {

    private const val PREFS_NAME = "DiceHistory"
    private const val HISTORY_KEY = "history"

    fun saveToHistory(context: Context, result: String, dices: String, dateTime: String) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentHistory = sharedPrefs.getString(HISTORY_KEY, "") ?: ""

        val newEntry = "$result;;$dices;;$dateTime"
        val updatedHistory = if (currentHistory.isEmpty()) {
            newEntry
        } else {
            "$newEntry|$currentHistory"
        }

        sharedPrefs.edit().putString(HISTORY_KEY, updatedHistory).apply()
    }

    fun loadHistory(context: Context): List<HistoryCard> {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val historyString = sharedPrefs.getString(HISTORY_KEY, "") ?: ""

        return if (historyString.isNotEmpty()) {
            historyString.split("|").mapNotNull { entry ->
                val parts = entry.split(";;")
                if (parts.size == 3) {
                    HistoryCard(
                        result = parts[0],
                        dices = parts[1],
                        dateTime = parts[2]
                    )
                } else null
            }
        } else {
            emptyList()
        }
    }

    fun filterHistoryByDays(context: Context, days: Int): List<HistoryCard> {
        val allHistory = loadHistory(context)
        val currentDate = Calendar.getInstance()
        val cutoffDate = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -days)
        }

        return allHistory.filter { entry ->
            try {
                val formatter = SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault())
                val entryDate = formatter.parse(entry.dateTime)
                entryDate?.let {
                    val entryCalendar = Calendar.getInstance().apply { time = it }
                    entryCalendar.after(cutoffDate) || entryCalendar == cutoffDate
                } ?: false
            } catch (e: Exception) {
                false
            }
        }
    }

    fun clearHistory(context: Context) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs.edit().remove(HISTORY_KEY).apply()
    }

    // Funciones de estadísticas
    fun getTotalRolls(historyList: List<HistoryCard>): Int {
        return historyList.size
    }

    fun getAverageResult(historyList: List<HistoryCard>): Double {
        if (historyList.isEmpty()) return 0.0

        val total = historyList.sumOf { it.result.toIntOrNull() ?: 0 }
        return total.toDouble() / historyList.size
    }

    fun getModeResult(historyList: List<HistoryCard>): Int {
        if (historyList.isEmpty()) return 0

        val frequencyMap = mutableMapOf<Int, Int>()
        historyList.forEach { entry ->
            val result = entry.result.toIntOrNull() ?: 0
            frequencyMap[result] = (frequencyMap[result] ?: 0) + 1
        }

        return frequencyMap.maxByOrNull { it.value }?.key ?: 0
    }
}