package com.example.dados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// ADAPTADOR. Este recibe como parametro una lista de dados, que son utilizados para crear cada Item en el RecyclerView.
// El adaptador usa la estructura del layout, almacenandola en variables para su futuro uso.
class DiceAdapter(private val diceList: MutableList<DiceCardItem>) :
    RecyclerView.Adapter<DiceAdapter.DiceViewHolder>() {

    class DiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val diceImage: ImageView = view.findViewById(R.id.dice_card_img)
        val minusButton: Button = view.findViewById(R.id.dice_card_btn_minus)
        val countText: TextView = view.findViewById(R.id.dice_card_btn_text)
        val plusButton: Button = view.findViewById(R.id.dice_card_btn_plus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dice_card, parent, false)
        return DiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiceViewHolder, position: Int) {
        val diceItem = diceList[position]

        // Asignar la imagen correspondiente según el nombre del dado
        val imageResource = when (diceItem.diceName) {
            "D4" -> R.drawable.light_d4
            "D6" -> R.drawable.light_d6
            "D8" -> R.drawable.light_d8
            "D10" -> R.drawable.light_d10
            "D12" -> R.drawable.light_d12
            "D20" -> R.drawable.light_d20
            "D100" -> R.drawable.light_d100
            else -> R.drawable.light_d6 // Fallback por si acaso
        }
        holder.diceImage.setImageResource(imageResource)

        // Mostrar el contador actual
        holder.countText.text = diceItem.diceCount.toString()

        // Actualizar estado de los botones según el valor actual
        updateButtonStates(holder, diceItem.diceCount)

        // Listener del botón menos
        holder.minusButton.setOnClickListener {
            if (diceItem.diceCount > 1) {
                diceItem.diceCount--
                holder.countText.text = diceItem.diceCount.toString()
                updateButtonStates(holder, diceItem.diceCount)
            }
        }

        // Listener del botón más
        holder.plusButton.setOnClickListener {
            if (diceItem.diceCount < 9) {
                diceItem.diceCount++
                holder.countText.text = diceItem.diceCount.toString()
                updateButtonStates(holder, diceItem.diceCount)
            }
        }
    }

    private fun updateButtonStates(holder: DiceViewHolder, count: Int) {
        // Deshabilitar botón menos si estamos en el mínimo (1)
        holder.minusButton.isEnabled = count > 1

        // Deshabilitar botón más si estamos en el máximo (9)
        holder.plusButton.isEnabled = count < 9
    }

    override fun getItemCount(): Int = diceList.size
}