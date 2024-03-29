package com.samirmaciel.futstreet.modules.tournament.tournamentMatchsSettings

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentmatchsettingsBinding
import com.samirmaciel.futstreet.modules.tournament.TournamentViewModel
import com.samirmaciel.futstreet.shared.const.MATCH_READY

class TournamentMatchSettingsFragment : Fragment(R.layout.fragment_tournamentmatchsettings) {

    private var _binding : FragmentTournamentmatchsettingsBinding? = null
    private val binding : FragmentTournamentmatchsettingsBinding get() = _binding!!

    private val viewModel : TournamentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentmatchsettingsBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()

        binding.buttonReady.setOnClickListener{
            if(saveTempData()){
                requireActivity().findNavController(R.id.bottomFragment).navigate(R.id.action_lastGamesFragment_to_tournamentFragment)
                findNavController().navigate(R.id.action_tournamentMatchSettingsFragment_to_waitingForMatchFragment)
            }
        }

        binding.buttonCancel.setOnClickListener{
            val alert = AlertDialog.Builder(requireContext()).apply {
                setTitle(resources.getText(R.string.text_ask_cancel_tournament))
                setPositiveButton(resources.getText(R.string.yes)){ _, _ ->
                    cancelTournament()
                }
                setNegativeButton(resources.getText(R.string.no), null)
            }.create().show()
        }
    }

    private fun cancelTournament(){
        setDefaultValuesToMatchStates()
        requireActivity().findNavController(R.id.bottomFragment).navigateUp()
        findNavController().navigate(R.id.action_tournamentMatchSettingsFragment_to_homeFragment)
    }

    private fun saveTempData() : Boolean{
        if (binding.inputMinutesOfRound.text.toString().isNotEmpty()){
            if(binding.inputNumberOfTimes.text.isNotEmpty()){
                viewModel.roundsLimit.value = binding.inputNumberOfTimes.text.toString().toInt()
            }
            if (binding.inputMinutesOfRound.text.toString().isNotEmpty()){
                viewModel.tournamentTimeLimit = binding.inputMinutesOfRound.text.toString().toInt().toDouble() * 60
                viewModel.timeLimit.value = binding.inputMinutesOfRound.text.toString().toInt().toDouble() * 60
            }
            return true
        }else{
            Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun setDefaultValuesToMatchStates(){
        viewModel.matchStateQ1.value = MATCH_READY
        viewModel.matchStateQ2.value = MATCH_READY
        viewModel.matchStateQ3.value = MATCH_READY
        viewModel.matchStateQ4.value = MATCH_READY
        viewModel.matchStateS1.value = MATCH_READY
        viewModel.matchStateS2.value = MATCH_READY
        viewModel.matchStateF1.value = MATCH_READY
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}