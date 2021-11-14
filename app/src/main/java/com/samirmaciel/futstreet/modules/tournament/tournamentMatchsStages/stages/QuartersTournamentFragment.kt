package com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages.stages

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentQuartersBinding
import com.samirmaciel.futstreet.modules.tournament.TournamentViewModel
import com.samirmaciel.futstreet.shared.const.*
import com.samirmaciel.futstreet.shared.model.TournamentMatch

class QuartersTournamentFragment : Fragment(R.layout.fragment_tournament_quarters) {

    private var _binding : FragmentTournamentQuartersBinding? = null
    private val binding : FragmentTournamentQuartersBinding get() = _binding!!

    private val viewModel : TournamentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentQuartersBinding.bind(view)
        viewModel.addQuartersTeams()

    }

    override fun onResume() {
        super.onResume()


        viewModel.matchQ1.observe(this){
            Log.d("TOURNAMENTTEST", "${it.nameTeamOne} - ${it.nameTeamTwo} ")
        }

        binding.matchCardView1.setOnClickListener{
            viewModel.matchStateQ1.value = MATCH_RUNNING
            viewModel.matchStateQ2.value = MATCH_WAITING
            viewModel.matchStateQ3.value = MATCH_WAITING
            viewModel.matchStateQ4.value = MATCH_WAITING
        }

        binding.matchCardView2.setOnClickListener{
            viewModel.matchStateQ1.value = MATCH_WAITING
            viewModel.matchStateQ2.value = MATCH_RUNNING
            viewModel.matchStateQ3.value = MATCH_WAITING
            viewModel.matchStateQ4.value = MATCH_WAITING
        }

        binding.matchCardView3.setOnClickListener{
            viewModel.matchStateQ1.value = MATCH_WAITING
            viewModel.matchStateQ2.value = MATCH_WAITING
            viewModel.matchStateQ3.value = MATCH_RUNNING
            viewModel.matchStateQ4.value = MATCH_WAITING
        }

        binding.matchCardView4.setOnClickListener{
            viewModel.matchStateQ1.value = MATCH_WAITING
            viewModel.matchStateQ2.value = MATCH_WAITING
            viewModel.matchStateQ3.value = MATCH_WAITING
            viewModel.matchStateQ4.value = MATCH_RUNNING
        }

        viewModel.matchStateQ1.observe(this){
            configStateMatchElements(it, binding.backgroundStatusMatch1, binding.titleStatusMatch1, binding.matchCardView1, viewModel.nameQ11.value!!,
            viewModel.shirtQ11.value!!, viewModel.nameQ21.value!!, viewModel.shirtQ21.value!!)
        }

        viewModel.matchStateQ2.observe(this){
            configStateMatchElements(it, binding.backgroundStatusMatch2, binding.titleStatusMatch2, binding.matchCardView2,viewModel.nameQ32.value!!,
                viewModel.shirtQ32.value!!, viewModel.nameQ42.value!!, viewModel.shirtQ42.value!!)
        }

        viewModel.matchStateQ3.observe(this){
            configStateMatchElements(it, binding.backgroundStatusMatch3, binding.titleStatusMatch3, binding.matchCardView3, viewModel.nameQ53.value!!,
                viewModel.shirtQ53.value!!, viewModel.nameQ63.value!!, viewModel.shirtQ63.value!!)
        }

        viewModel.matchStateQ4.observe(this){
            configStateMatchElements(it, binding.backgroundStatusMatch4, binding.titleStatusMatch4, binding.matchCardView4, viewModel.nameQ74.value!!,
                viewModel.shirtQ74.value!!, viewModel.nameQ84.value!!, viewModel.shirtQ84.value!!)
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


    private fun configStateMatchElements(state : Int, backGroundMatch : LinearLayout, matchTitle : TextView,
                                         matchCard : CardView, teamName1 : String, teamShirt1 : Int, teamName2 : String, teamShirt2 : Int ){
        when(state){
            MATCH_WAITING -> {
                backGroundMatch.setBackgroundResource(R.color.yellow)
                matchTitle.setText(resources.getText(R.string.title_state_tournament_match_waiting))
                matchTitle.setTextColor(resources.getColor(R.color.black))
                matchCard.isClickable = false
            }

            MATCH_READY -> {
                backGroundMatch.setBackgroundResource(R.color.green)
                matchTitle.setText(resources.getText(R.string.title_state_tournament_match_ready))
                matchCard.isClickable = true
            }

            MATCH_RUNNING -> {
                //sendToMatchReady(teamName1, teamShirt1, teamName2, teamShirt2)
                viewModel.currentMatchRunning.value = TournamentMatch(
                    id = 1,
                    winner = 1,
                    nameTeamOne = viewModel.nameQ11.value!!,
                    nameTeamTwo = viewModel.nameQ21.value!!,
                    scoreTeamOne = 0,
                    scoreTeamTwo = 0,
                    shirtTeamOne = viewModel.shirtQ11.value!!,
                    shirtTeamTwo = viewModel.shirtQ21.value!!,
                    status = MATCH_READY
                )
                backGroundMatch.setBackgroundResource(R.color.green)
                matchTitle.setText(resources.getText(R.string.title_state_tournament_match_running))
                matchCard.isClickable = false
                requireActivity().findNavController(R.id.topFragment).navigate(R.id.action_waitingForMatchFragment_to_matchReadyTournamentFragment)
            }

            MATCH_ENDED ->{
                backGroundMatch.setBackgroundResource(R.color.red)
                matchTitle.setText(R.string.title_state_tournament_match_ended)
                matchCard.isClickable = false
            }
        }
    }

    private fun setStateOfMatch(matchSelect : Int){
        when(matchSelect){
            1 -> {
                viewModel.matchStateQ1.value = MATCH_RUNNING
                viewModel.matchStateQ2.value = MATCH_WAITING
                viewModel.matchStateQ3.value = MATCH_WAITING
                viewModel.matchStateQ4.value = MATCH_WAITING
            }

            2 -> {
                viewModel.matchStateQ1.value = MATCH_WAITING
                viewModel.matchStateQ2.value = MATCH_RUNNING
                viewModel.matchStateQ3.value = MATCH_WAITING
                viewModel.matchStateQ4.value = MATCH_WAITING
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}