// GirosFragment.kt
package com.example.dados

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dados.databinding.FragmentGirosBinding
import com.google.android.material.snackbar.Snackbar

class GirosFragment : Fragment() {

    private var _binding: FragmentGirosBinding? = null
    private val binding: FragmentGirosBinding
        get() = _binding!!

    private lateinit var diceAdapter: DiceAdapter
    private val diceList = mutableListOf<DiceCardItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGirosBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val msg = GirosFragmentArgs.fromBundle(requireArguments()).dices
        val dices = msg.split(" ").filter { it.isNotEmpty() } // Filtrar strings vacíos

        // Crear la lista de DiceItem a partir de los dados seleccionados
        dices.forEach { diceName ->
            diceList.add(DiceCardItem(diceName, 1)) // Empieza con 1 giro cada uno
        }

        // Configurar el RecyclerView
        diceAdapter = DiceAdapter(diceList)
        binding.howmanyList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = diceAdapter
        }

        val btonback = binding.howmanyBtnGoback
        val btonnext = binding.howmanyBtnNext

        btonback.setOnClickListener {
            view.findNavController().navigate(R.id.action_girosFragment_to_diceFragment)
        }

        btonnext.setOnClickListener {
            // TODO: Pasar la información de diceList al siguiente fragmento (ResultFragment)
            // Cada elemento de diceList contiene el nombre del dado y cuántos giros tiene
            var msg=""

            diceList.forEach { (diceName, diceCount) ->
                msg += diceName+"_"+diceCount+" "
            }

            val actions = GirosFragmentDirections.actionGirosFragmentToResultFragment(msg)
            view.findNavController().navigate(actions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}