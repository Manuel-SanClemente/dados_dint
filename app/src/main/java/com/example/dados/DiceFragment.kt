package com.example.dados

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.dados.databinding.FragmentDiceBinding
import com.google.android.material.snackbar.Snackbar

class DiceFragment : Fragment() {

    private var _binding: FragmentDiceBinding? = null
    private val binding: FragmentDiceBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiceBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bton = binding.dicesNext

        val d4 = binding.d4Card
        val d6 = binding.d6Card
        val d8 = binding.d8Card
        val d10 = binding.d10Card
        val d12 = binding.d12Card
        val d20 = binding.d20Card
        val d100 = binding.d100Card

        d4.setOnClickListener {
            d4.isChecked = !d4.isChecked
        }

        d6.setOnClickListener {
            d6.isChecked = !d6.isChecked
        }

        d8.setOnClickListener {
            d8.isChecked = !d8.isChecked
        }

        d10.setOnClickListener {
            d10.isChecked = !d10.isChecked
        }

        d12.setOnClickListener {
            d12.isChecked = !d12.isChecked
        }

        d20.setOnClickListener {
            d20.isChecked = !d20.isChecked
        }

        d100.setOnClickListener {
            d100.isChecked = !d100.isChecked
        }

        bton.setOnClickListener {
            var msg = ""
            if (d4.isChecked) { msg += "D4 " }
            if (d6.isChecked) { msg += "D6 " }
            if (d8.isChecked) { msg += "D8 " }
            if (d10.isChecked) { msg += "D10 " }
            if (d12.isChecked) { msg += "D12 " }
            if (d20.isChecked) { msg += "D20 " }
            if (d100.isChecked) { msg += "D100 " }

            if (msg == "") {
                Snackbar.make(it, "No se ha seleccionado ningun dado", Snackbar.LENGTH_LONG).show()
            } else {
                val actions = DiceFragmentDirections.actionDiceFragmentToGirosFragment(msg)
                view.findNavController().navigate(actions)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}