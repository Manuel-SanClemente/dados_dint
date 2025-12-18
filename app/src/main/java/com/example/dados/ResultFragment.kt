package com.example.dados

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.dados.databinding.FragmentResultBinding
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ttd = ResultFragmentArgs.fromBundle(requireArguments()).result

        val list = ttd.split(" ")

        val result = binding.resultTxt
        val desc = binding.resultDetails
        val btn = binding.resultNext

        var resultNumber: Int = 0
        var txtcontent: String = ""

        for (i in 0 until list.lastIndex) {
            var j = list[i].split("_")
            var dicetopass = j[0]
            var resultadocalc = calculateRolls(j[0], j[1].toInt())
            txtcontent += "$dicetopass: $resultadocalc \n"
            resultNumber = calculateTotalRolls(resultadocalc)
        }

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val dateformatted = formatter.format(date)

        result.text = resultNumber.toString()
        desc.text = txtcontent

        val toHistory= "${result.text} ${desc.text} $dateformatted"

        btn.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_diceFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun calculateRolls(dice: String, rolls: Int): List<Int>? {
        val result = when (dice) {
            "D4" -> List (rolls) { Random.nextInt(0, 4) }
            "D6" -> List (rolls) { Random.nextInt(0, 6) }
            "D8" -> List (rolls) { Random.nextInt(0, 8) }
            "D10" -> List (rolls) { Random.nextInt(0, 10) }
            "D12" -> List (rolls) { Random.nextInt(0, 12) }
            "D20" -> List (rolls) { Random.nextInt(0, 20) }
            "D100" -> List (rolls) { Random.nextInt(0, 100) }
            else -> null
        }
        return result
    }

    fun calculateTotalRolls(rolls: List<Int>?): Int {
        var toreturn = 0

        for (i in 0 until rolls!!.size) {
            toreturn += rolls[i]
        }

        return toreturn
    }
}