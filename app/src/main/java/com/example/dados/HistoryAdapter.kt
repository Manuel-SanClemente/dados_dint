package com.example.dados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val historyList: List<HistoryCard>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultText: TextView = itemView.findViewById(R.id.history_card_result)
        val dateTimeText: TextView = itemView.findViewById(R.id.history_card_datetime)
        val dicesText: TextView = itemView.findViewById(R.id.history_card_dices)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_card, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.resultText.text = item.result
        holder.dateTimeText.text = item.dateTime
        holder.dicesText.text = item.dices
    }

    override fun getItemCount(): Int = historyList.size
}