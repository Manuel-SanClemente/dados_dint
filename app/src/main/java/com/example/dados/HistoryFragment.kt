package com.example.dados

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.history_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val historyList = loadHistoryFromPreferences()

        adapter = HistoryAdapter(historyList)
        recyclerView.adapter = adapter
    }

    private fun loadHistoryFromPreferences(): List<HistoryCard> {
        val sharedPrefs = requireContext().getSharedPreferences("DiceHistory", Context.MODE_PRIVATE)
        val historyString = sharedPrefs.getString("history", "") ?: ""

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
}