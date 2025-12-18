package com.example.dados

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class StadisticsFragment : Fragment() {

    private lateinit var tiradasTxt: TextView
    private lateinit var mediaTxt: TextView
    private lateinit var modaTxt: TextView

    private lateinit var filter1d: MaterialButton
    private lateinit var filter7d: MaterialButton
    private lateinit var filter1m: MaterialButton
    private lateinit var filter1y: MaterialButton

    private var currentFilter: Int = -1 // -1 = todos, 1 = 1 día, 7 = 7 días, 30 = 1 mes, 365 = 1 año

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stadistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar vistas
        tiradasTxt = view.findViewById(R.id.tiradas_txt)
        mediaTxt = view.findViewById(R.id.media_txt)
        modaTxt = view.findViewById(R.id.moda_txt)

        filter1d = view.findViewById(R.id.filter_1d)
        filter7d = view.findViewById(R.id.filter_7d)
        filter1m = view.findViewById(R.id.filter_1m)
        filter1y = view.findViewById(R.id.filter_1y)

        // Configurar listeners de botones
        filter1d.setOnClickListener {
            currentFilter = 1
            updateButtonStates(filter1d)
            updateStatistics()
        }

        filter7d.setOnClickListener {
            currentFilter = 7
            updateButtonStates(filter7d)
            updateStatistics()
        }

        filter1m.setOnClickListener {
            currentFilter = 30
            updateButtonStates(filter1m)
            updateStatistics()
        }

        filter1y.setOnClickListener {
            currentFilter = 365
            updateButtonStates(filter1y)
            updateStatistics()
        }

        // Cargar estadísticas iniciales (todos los datos)
        currentFilter = -1
        updateStatistics()
    }

    private fun updateButtonStates(selectedButton: MaterialButton) {
        // Resetear todos los botones
        filter1d.isSelected = false
        filter7d.isSelected = false
        filter1m.isSelected = false
        filter1y.isSelected = false

        // Marcar el botón seleccionado
        selectedButton.isSelected = true
    }

    private fun updateStatistics() {
        // Obtener el historial filtrado
        val historyList = if (currentFilter == -1) {
            HistoryHelper.loadHistory(requireContext())
        } else {
            HistoryHelper.filterHistoryByDays(requireContext(), currentFilter)
        }

        // Calcular estadísticas
        val totalTiradas = HistoryHelper.getTotalRolls(historyList)
        val media = HistoryHelper.getAverageResult(historyList)
        val moda = HistoryHelper.getModeResult(historyList)

        // Actualizar las vistas
        tiradasTxt.text = totalTiradas.toString()
        mediaTxt.text = if (totalTiradas > 0) "%.2f".format(media) else "0"
        modaTxt.text = if (totalTiradas > 0) moda.toString() else "0"
    }

    override fun onResume() {
        super.onResume()
        // Actualizar estadísticas cuando se vuelve al fragmento
        updateStatistics()
    }
}