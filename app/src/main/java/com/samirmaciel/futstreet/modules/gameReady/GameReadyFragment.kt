package com.samirmaciel.futstreet.modules.gameReady

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentGamereadyBinding

class GameReadyFragment : Fragment(R.layout.fragment_gameready) {

    private var _binding : FragmentGamereadyBinding? = null
    private val binding : FragmentGamereadyBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGamereadyBinding.bind(view)

        getSettings()

        binding.buttonStart.setOnClickListener{

        }

        binding.buttonGameCancel.setOnClickListener{
            findNavController().navigate(R.id.action_gameReadyFragment_to_gameSettingFragment)
        }
    }

    private fun getSettings() {
        binding.textNameTeamOne.setText(arguments?.getString("NameTeamOne"))
        binding.textNameTeamTwo.setText(arguments?.getString("NameTeamTwo"))

        arguments?.getInt("ShirtTeamOne", R.drawable.shirt_select)?.let {
            binding.imageShirtTeamOne.setImageResource(
                it
            )
        }

        arguments?.getInt("ShirtTeamTwo", R.drawable.shirt_select)?.let{
            binding.imageShirtTeamTwo.setImageResource(
                it
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}