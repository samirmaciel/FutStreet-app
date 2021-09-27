package com.samirmaciel.futstreet.modules.gameSetting

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentGamesettingsBinding
import com.samirmaciel.futstreet.modules.gameSetting.shirtSelectionFragment.ShirtSelectionDialog
import kotlin.math.roundToInt

class GameSettingFragment : Fragment(R.layout.fragment_gamesettings){

    private var _binding : FragmentGamesettingsBinding? = null
    private val binding : FragmentGamesettingsBinding get() = _binding!!
    private val viewModel : GameSettingViewModel by activityViewModels()

    private val SHIRT_SELECTION_FRAGMENT = "SHIRT_SELECTION_FRAGMENT"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      _binding = FragmentGamesettingsBinding.bind(view)

    }

    override fun onStart() {
        super.onStart()


        binding.selectShirtTeam1.setOnClickListener{
            val shirtSelection = ShirtSelectionDialog {
                for ((k, v) in viewModel.getListOfShirts()) {
                    if (it == k) {
                        binding.selectShirtTeam1.setImageResource(v)
                        viewModel.shirtTeamOne.value = v
                    }
                }
            }

            shirtSelection.show(childFragmentManager, SHIRT_SELECTION_FRAGMENT)
        }

        binding.selectShirtTeam2.setOnClickListener{
            val shirtSelectionDialog = ShirtSelectionDialog{
                for((k , v) in viewModel.getListOfShirts()){
                    if(it == k){
                        binding.selectShirtTeam2.setImageResource(v)
                        viewModel.shirtTeamTwo.value = v
                    }
                }
            }

            shirtSelectionDialog.show(childFragmentManager, SHIRT_SELECTION_FRAGMENT)
        }

        binding.buttonReady.setOnClickListener{
            if(saveTempData()){
                findNavController().navigate(R.id.action_gameSettingFragment_to_gameReadyFragment, getBundleWithArguments())
            }

        }

        binding.buttonCancel.setOnClickListener{
            findNavController().navigate(R.id.action_gameSettingFragment_to_homeFragment)
        }
    }


    private fun saveTempData() : Boolean{
        if (checkInputs()){
            viewModel.nameTeamOne.value = binding.inputNameTeamOne.text.toString()
            viewModel.nameTeamTwo.value = binding.inputNameTeamTwo.text.toString()
            if(binding.inputNumberOfTimes.text.toString().isNotEmpty()){
                viewModel.roundsOfPlay.value = binding.inputNumberOfTimes.text.toString().toInt()
            }
            if (binding.inputMinutesOfRound.text.toString().isNotEmpty()){
                viewModel.timeForRound.value = binding.inputMinutesOfRound.text.toString().toDouble()
            }

            return true
        }else{
            Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return false
        }

    }

    private fun checkInputs() : Boolean{
        if(binding.inputNameTeamOne.text.toString().isNotEmpty() && binding.inputNameTeamTwo.text.toString().isNotEmpty()){
            return binding.inputMinutesOfRound.text.toString().isNotEmpty()
        }else{
            return false
        }

    }

    override fun onResume() {
        super.onResume()

        viewModel.shirtTeamTwo.observe(this){
            binding.selectShirtTeam2.setImageResource(it)
        }

        viewModel.shirtTeamOne.observe(this){
            binding.selectShirtTeam1.setImageResource(it)
        }

        viewModel.nameTeamOne.observe(this){
            binding.inputNameTeamOne.setText(it)
        }

        viewModel.nameTeamTwo.observe(this){
            binding.inputNameTeamTwo.setText(it)
        }

        viewModel.roundsOfPlay.observe(this){
            if(it > 1){
                binding.inputNumberOfTimes.setText(it.toString())
            }

        }

        viewModel.timeForRound.observe(this){
            if(it.roundToInt() > 0){
                binding.inputMinutesOfRound.setText(it.roundToInt().toString())
            }

        }
    }

    private fun getBundleWithArguments() : Bundle {
        val arguments = Bundle().apply {
            putString("NameTeamOne", viewModel.nameTeamOne.value!!)
            putString("NameTeamTwo", viewModel.nameTeamTwo.value!!)
            putInt("ShirtTeamOne", viewModel.shirtTeamOne.value!!)
            putInt("ShirtTeamTwo", viewModel.shirtTeamTwo.value!!)
            putInt("Rounds", viewModel.roundsOfPlay.value!!)
            putDouble("Time", viewModel.timeForRound.value!!)
        }

        return arguments
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}