package com.samirmaciel.futstreet.modules.gameSetting

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentGamesettingsBinding

class GameSettingFragment : Fragment(R.layout.fragment_gamesettings){

    private var _binding : FragmentGamesettingsBinding? = null
    private val binding : FragmentGamesettingsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      _binding = FragmentGamesettingsBinding.bind(view)

    }

    override fun onStart() {
        super.onStart()

        binding.selectShirtTeam1.setOnClickListener{

            val layoutInflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(R.layout.select_shirt_grid, null)

            val alert = AlertDialog.Builder(requireContext()).apply {
                setView(view)
            }
            alert.create()
            alert.show()
            val shirtBlue = view.findViewById<ImageView>(R.id.shirtBlue)

            shirtBlue.setOnClickListener{
                Toast.makeText(requireContext(), "Camisa Azul Selecionada", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonReady.setOnClickListener{
            findNavController().navigate(R.id.action_gameSettingFragment_to_gameReadyFragment)
        }

        binding.buttonCancel.setOnClickListener{
            findNavController().navigate(R.id.action_gameSettingFragment_to_homeFragment)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}