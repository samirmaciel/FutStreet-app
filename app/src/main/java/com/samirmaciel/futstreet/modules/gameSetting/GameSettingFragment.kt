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
import com.samirmaciel.futstreet.shared.const.SHIRT_SELECTION_FRAGMENT

class GameSettingFragment : Fragment(R.layout.fragment_gamesettings){

    private var _binding : FragmentGamesettingsBinding? = null
    private val binding : FragmentGamesettingsBinding get() = _binding!!
    private val viewModel : GameSettingViewModel by activityViewModels()


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
            cleanViewModelData()
            findNavController().navigate(R.id.action_gameSettingFragment_to_homeFragment)
        }
    }


    override fun onResume() {
        super.onResume()

        viewModel.shirtTeamTwo.observe(this){
            binding.selectShirtTeam2.animate().apply {
                duration = 100
                scaleXBy(0.5f)
                scaleYBy(0.5f)
            }.withEndAction {
                binding.selectShirtTeam2.setImageResource(it)
                binding.selectShirtTeam2.animate().apply {
                    duration = 100
                    scaleYBy(-0.5f)
                    scaleXBy(-0.5f)
                }
            }.start()

        }

        viewModel.shirtTeamOne.observe(this){
            binding.selectShirtTeam1.animate().apply {
                duration = 100
                scaleYBy(0.5f)
                scaleXBy(0.5f)
            }.withEndAction {
                binding.selectShirtTeam1.setImageResource(it)
                binding.selectShirtTeam1.animate().apply {
                    duration = 100
                    scaleYBy(-0.5f)
                    scaleXBy(-0.5f)
                }
            }.start()

        }

        viewModel.nameTeamOne.observe(this){
            if(!it.equals(resources.getText(R.string.input_hint_team1))){
                binding.inputNameTeamOne.setText(it)
            }
        }

        viewModel.nameTeamTwo.observe(this){
            if(!it.equals(resources.getText(R.string.input_hint_team2))){
                binding.inputNameTeamTwo.setText(it)
            }
        }

        viewModel.roundsOfPlay.observe(this){
            if(it > 1){
                binding.inputNumberOfTimes.setText(it.toString())
            }

        }

        viewModel.timeForRound.observe(this){
            if(it > 0){
                binding.inputMinutesOfRound.setText(it.toString())
            }

        }
    }

    private fun saveTempData() : Boolean{
        if (checkInputs()){
            if(binding.inputNameTeamOne.text.isNotEmpty()){
                viewModel.nameTeamOne.value = binding.inputNameTeamOne.text.toString()
            }
            if(binding.inputNameTeamTwo.text.isNotEmpty()){
                viewModel.nameTeamTwo.value = binding.inputNameTeamTwo.text.toString()
            }

            if(binding.inputNumberOfTimes.text.isNotEmpty()){
                viewModel.roundsOfPlay.value = binding.inputNumberOfTimes.text.toString().toInt()
            }

            if(binding.inputNumberOfTimes.text.toString().isNotEmpty()){
                viewModel.roundsOfPlay.value = binding.inputNumberOfTimes.text.toString().toInt()
            }

            if (binding.inputMinutesOfRound.text.toString().isNotEmpty()){
                viewModel.timeForRound.value = binding.inputMinutesOfRound.text.toString().toInt()
            }
            return true
        }else{
            Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun checkInputs() = binding.inputMinutesOfRound.text.toString().isNotEmpty()


    private fun getBundleWithArguments() : Bundle {
        val arguments = Bundle().apply {
            putString("NameTeamOne", viewModel.nameTeamOne.value!!)
            putString("NameTeamTwo", viewModel.nameTeamTwo.value!!)
            putInt("ScoreTeamOne", 0)
            putInt("ScoreTeamTwo", 0)
            putInt("ShirtTeamOne", viewModel.shirtTeamOne.value!!)
            putInt("ShirtTeamTwo", viewModel.shirtTeamTwo.value!!)
            putInt("Rounds", viewModel.roundsOfPlay.value!!)
            putInt("CurrentRound", 1)
            putDouble("RoundTime", viewModel.timeForRound.value!!.toDouble() * 60)
        }

        return arguments
    }

    private fun cleanViewModelData(){
        viewModel.nameTeamOne.value = ""
        viewModel.nameTeamTwo.value = ""
        viewModel.shirtTeamOne.value = R.drawable.shirt_select
        viewModel.shirtTeamTwo.value = R.drawable.shirt_select
        viewModel.timeForRound.value = 0
        viewModel.roundsOfPlay.value = 1
    }

    override fun onStop() {
        super.onStop()
        Log.d("TESTEVIEWMODEL", "onStop: " + viewModel.timeForRound.value)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}