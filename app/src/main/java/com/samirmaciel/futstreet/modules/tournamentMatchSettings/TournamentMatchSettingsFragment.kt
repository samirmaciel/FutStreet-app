package com.samirmaciel.futstreet.modules.tournamentMatchSettings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentmatchsettingsBinding
import com.samirmaciel.futstreet.shared.const.MATCH_TOURNAMENT

class TournamentMatchSettingsFragment : Fragment(R.layout.fragment_tournamentmatchsettings) {

    private var _binding : FragmentTournamentmatchsettingsBinding? = null
    private val binding : FragmentTournamentmatchsettingsBinding get() = _binding!!

    private val viewModel : TournamentMatchSettingsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMatchArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentmatchsettingsBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()

        binding.buttonReady.setOnClickListener{
            if(saveTempData()){
                findNavController().navigate(R.id.action_tournamentMatchSettingsFragment_to_gameReadyFragment, setBundleWithArguments())
            }
        }

        binding.buttonCancel.setOnClickListener{
            findNavController().navigate(R.id.action_gameSettingFragment_to_homeFragment)
        }
    }

    private fun setBundleWithArguments() : Bundle {
        val arguments = Bundle().apply {
            putInt("matchType", MATCH_TOURNAMENT)
            putString("teamName1", viewModel.teamName1.value!!)
            putString("teamName2", viewModel.teamName2.value!!)
            putInt("shirtTeam1", viewModel.shirtTeam1.value!!)
            putInt("shirtTeam2", viewModel.shirtTeam2.value!!)
            putInt("Rounds", viewModel.roundsOfPlay.value!!)
            putInt("CurrentRound", 1)
            putDouble("RoundTime", viewModel.timeForRound.value!!.toDouble() * 60)
        }

        return arguments
    }

    private fun saveTempData() : Boolean{
        if (binding.inputMinutesOfRound.text.toString().isNotEmpty()){
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

    private fun getMatchArguments(){
        viewModel.teamName1.value = arguments?.getString("teamName1")
        viewModel.teamName2.value = arguments?.getString("teamName2")
        viewModel.shirtTeam1.value = arguments?.getInt("shirtTeam1", R.drawable.shirt_select)
        viewModel.shirtTeam2.value = arguments?.getInt("shirtTeam2", R.drawable.shirt_select)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}