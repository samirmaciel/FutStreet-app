package com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages.stages

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentQuartersBinding
import com.samirmaciel.futstreet.modules.tournament.TournamentViewModel
import com.samirmaciel.futstreet.shared.const.*

class QuartersTournamentFragment : Fragment(R.layout.fragment_tournament_quarters) {

    private var _binding : FragmentTournamentQuartersBinding? = null
    private val binding : FragmentTournamentQuartersBinding get() = _binding!!

    private val viewModel : TournamentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentQuartersBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()

        binding.stageCardView1.setOnClickListener{
            viewModel.matchStateQ1.value = MATCH_RUNNING
            viewModel.matchStateQ2.value = MATCH_WAITING
            viewModel.matchStateQ3.value = MATCH_WAITING
            viewModel.matchStateQ4.value = MATCH_WAITING
        }

        binding.stageCardView2.setOnClickListener{
            viewModel.matchStateQ1.value = MATCH_WAITING
            viewModel.matchStateQ2.value = MATCH_RUNNING
            viewModel.matchStateQ3.value = MATCH_WAITING
            viewModel.matchStateQ4.value = MATCH_WAITING
        }

        binding.stageCardView3.setOnClickListener{
            viewModel.matchStateQ1.value = MATCH_WAITING
            viewModel.matchStateQ2.value = MATCH_WAITING
            viewModel.matchStateQ3.value = MATCH_RUNNING
            viewModel.matchStateQ4.value = MATCH_WAITING
        }

        binding.stageCardView4.setOnClickListener{
            viewModel.matchStateQ1.value = MATCH_WAITING
            viewModel.matchStateQ2.value = MATCH_WAITING
            viewModel.matchStateQ3.value = MATCH_WAITING
            viewModel.matchStateQ4.value = MATCH_RUNNING
        }

        viewModel.matchStateQ1.observe(this){
            when(it){
                MATCH_WAITING -> {
                    binding.backgroundMatchState1.setBackgroundResource(R.color.yellow)
                    binding.textStageMatch1.setText(resources.getText(R.string.title_state_tournament_match_waiting))
                    binding.textStageMatch1.setTextColor(resources.getColor(R.color.black))
                    binding.stageCardView1.isClickable = false
                }

                MATCH_READY -> {
                    binding.backgroundMatchState1.setBackgroundResource(R.color.green)
                    binding.textStageMatch1.setText(resources.getText(R.string.title_state_tournament_match_ready))
                }

                MATCH_RUNNING -> {
                    sendToMatchReady(viewModel.nameQ11.value!!, viewModel.shirtQ11.value!!, viewModel.nameQ21.value!!,viewModel.shirtQ21.value!!)
                    binding.backgroundMatchState1.setBackgroundResource(R.color.green)
                    binding.textStageMatch1.setText(resources.getText(R.string.title_state_tournament_match_running))
                    binding.stageCardView1.isClickable = false
                }

                MATCH_ENDED ->{
                    binding.backgroundMatchState1.setBackgroundResource(R.color.red)
                    binding.textStageMatch1.setText(R.string.title_state_tournament_match_ended)
                    binding.stageCardView1.isClickable = false
                }
            }
        }

        viewModel.matchStateQ2.observe(this){
            when(it){
                MATCH_WAITING -> {
                    binding.backgroundMatchState2.setBackgroundResource(R.color.yellow)
                    binding.textStageMatch2.setText(resources.getText(R.string.title_state_tournament_match_waiting))
                    binding.textStageMatch2.setTextColor(resources.getColor(R.color.black))
                    binding.stageCardView2.isClickable = false
                }

                MATCH_READY -> {
                    binding.backgroundMatchState2.setBackgroundResource(R.color.green)
                    binding.textStageMatch2.setText(resources.getText(R.string.title_state_tournament_match_ready))
                    binding.stageCardView2.isClickable = true
                }

                MATCH_RUNNING -> {
                    sendToMatchReady(viewModel.nameQ32.value!!, viewModel.shirtQ32.value!!, viewModel.nameQ42.value!!,viewModel.shirtQ42.value!!)
                    binding.backgroundMatchState2.setBackgroundResource(R.color.green)
                    binding.textStageMatch2.setText(resources.getText(R.string.title_state_tournament_match_running))
                    binding.stageCardView2.isClickable = false
                }

                MATCH_ENDED ->{
                    binding.backgroundMatchState2.setBackgroundResource(R.color.red)
                    binding.textStageMatch2.setText(R.string.title_state_tournament_match_ended)
                    binding.stageCardView2.isClickable = false
                }
            }
        }

        viewModel.matchStateQ3.observe(this){
            when(it){
                MATCH_WAITING -> {
                    binding.backgroundMatchState3.setBackgroundResource(R.color.yellow)
                    binding.textStageMatch3.setText(resources.getText(R.string.title_state_tournament_match_waiting))
                    binding.textStageMatch3.setTextColor(resources.getColor(R.color.black))
                    binding.stageCardView3.isClickable = false
                }

                MATCH_READY -> {
                    binding.backgroundMatchState3.setBackgroundResource(R.color.green)
                    binding.textStageMatch3.setText(resources.getText(R.string.title_state_tournament_match_ready))
                    binding.stageCardView3.isClickable = true
                }

                MATCH_RUNNING -> {
                    sendToMatchReady(viewModel.nameQ53.value!!, viewModel.shirtQ53.value!!, viewModel.nameQ63.value!!,viewModel.shirtQ63.value!!)
                    binding.backgroundMatchState3.setBackgroundResource(R.color.green)
                    binding.textStageMatch3.setText(resources.getText(R.string.title_state_tournament_match_running))
                    binding.stageCardView3.isClickable = false
                }

                MATCH_ENDED ->{
                    binding.backgroundMatchState3.setBackgroundResource(R.color.red)
                    binding.textStageMatch3.setText(R.string.title_state_tournament_match_ended)
                    binding.stageCardView3.isClickable = false
                }
            }
        }

        viewModel.matchStateQ4.observe(this){
            when(it){
                MATCH_WAITING -> {
                    binding.backgroundMatchState4.setBackgroundResource(R.color.yellow)
                    binding.textStageMatch4.setText(resources.getText(R.string.title_state_tournament_match_waiting))
                    binding.textStageMatch4.setTextColor(resources.getColor(R.color.black))
                    binding.stageCardView4.isClickable = false
                }

                MATCH_READY -> {
                    binding.backgroundMatchState4.setBackgroundResource(R.color.green)
                    binding.textStageMatch4.setText(resources.getText(R.string.title_state_tournament_match_ready))
                    binding.stageCardView4.isClickable = true
                }

                MATCH_RUNNING -> {
                    sendToMatchReady(viewModel.nameQ74.value!!, viewModel.shirtQ74.value!!, viewModel.nameQ84.value!!,viewModel.shirtQ84.value!!)
                    binding.backgroundMatchState4.setBackgroundResource(R.color.green)
                    binding.textStageMatch4.setText(resources.getText(R.string.title_state_tournament_match_running))
                    binding.stageCardView4.isClickable = false
                }

                MATCH_ENDED ->{
                    binding.backgroundMatchState4.setBackgroundResource(R.color.red)
                    binding.textStageMatch4.setText(R.string.title_state_tournament_match_ended)
                    binding.stageCardView4.isClickable = false
                }
            }
        }

        viewModel.nameQ11.observe(this){
            binding.stageTeamName11.setText(it)
        }
        viewModel.shirtQ11.observe(this){
            binding.stageTeamShirt11.setImageResource(it)
        }

        viewModel.nameQ21.observe(this){
            binding.stageTeamName21.setText(it)
        }
        viewModel.shirtQ21.observe(this){
            binding.stageTeamShirt21.setImageResource(it)
        }

        viewModel.nameQ32.observe(this){
            binding.stageTeamName32.setText(it)
        }
        viewModel.shirtQ32.observe(this){
            binding.stageTeamShirt32.setImageResource(it)
        }

        viewModel.nameQ42.observe(this){
            binding.stageTeamName42.setText(it)
        }

        viewModel.shirtQ42.observe(this){
            binding.stageTeamShirt42.setImageResource(it)
        }

        viewModel.nameQ53.observe(this){
            binding.stageTeamName53.setText(it)
        }

        viewModel.shirtQ53.observe(this){
            binding.stageTeamShirt53.setImageResource(it)
        }

        viewModel.nameQ63.observe(this){
            binding.stageTeamName63.setText(it)
        }

        viewModel.shirtQ63.observe(this){
            binding.stageTeamShirt63.setImageResource(it)
        }

        viewModel.nameQ74.observe(this){
            binding.stageTeamName74.setText(it)
        }

        viewModel.shirtQ74.observe(this){
            binding.stageTeamShirt74.setImageResource(it)
        }

        viewModel.nameQ84.observe(this){
            binding.stageTeamName84.setText(it)
        }

        viewModel.shirtQ84.observe(this){
            binding.stageTeamShirt84.setImageResource(it)
        }
    }

    private fun sendToMatchReady(team1 : String, shirt1 : Int, team2 : String, shirt2 : Int){
        val arguments = Bundle().apply {
            putInt("matchType", MATCH_TOURNAMENT)
            putString("teamName1", team1)
            putString("teamName2", team2)
            putInt("shirtTeam1", shirt1)
            putInt("shirtTeam2", shirt2)
            putInt("Rounds", viewModel.roundsOfPlay.value!!)
            putInt("CurrentRound", 1)
            putDouble("RoundTime", viewModel.timeForRound.value!!.toDouble() * 60)
        }

        requireActivity().findNavController(R.id.topFragment).navigate(R.id.action_waitingForMatchFragment_to_gameReadyFragment, arguments)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}