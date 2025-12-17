package com.example.dados

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.dados.databinding.FragmentResultBinding

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

        list.forEach { str ->
            str.split("_")
        }


        btn.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_diceFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}