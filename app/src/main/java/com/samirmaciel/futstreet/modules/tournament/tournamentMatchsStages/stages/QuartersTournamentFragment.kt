package com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages.stages

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.addTeamsToMatchsQuarters()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentQuartersBinding.bind(view)

    }

    override fun onResume() {
        super.onResume()


        viewModel.matchQ1.observe(this){
            updateMatch(binding.stageTeamName11, binding.stageTeamName21, binding.score11, binding.score21, binding.stageTeamShirt11, binding.stageTeamShirt21, it)
            Log.d("QUARTERSFRAGMENT", "QUARTERS " + viewModel.matchStateQ1.value!!)
        }

        viewModel.matchStateQ1.observe(this){
            updateStatusMatch(0, it, binding.backgroundStatusMatch1, binding.titleStatusMatch1, binding.matchCardView1, viewModel.matchQ1)
        }

        viewModel.matchQ2.observe(this){
            updateMatch(binding.stageTeamName32, binding.stageTeamName42, binding.score32, binding.score42, binding.stageTeamShirt32, binding.stageTeamShirt42, it)
        }

        viewModel.matchStateQ2.observe(this){
            updateStatusMatch(1, it, binding.backgroundStatusMatch2, binding.titleStatusMatch2, binding.matchCardView2, viewModel.matchQ2)
        }

        viewModel.matchQ3.observe(this){
            updateMatch(binding.stageTeamName53, binding.stageTeamName63, binding.score53, binding.score63, binding.stageTeamShirt53, binding.stageTeamShirt63, it)
        }

        viewModel.matchStateQ3.observe(this){
            updateStatusMatch(2, it, binding.backgroundStatusMatch3, binding.titleStatusMatch3, binding.matchCardView3, viewModel.matchQ3)
        }

        viewModel.matchQ4.observe(this){
            updateMatch(binding.stageTeamName74, binding.stageTeamName84, binding.score74, binding.score84, binding.stageTeamShirt74, binding.stageTeamShirt84, it)
        }

        viewModel.matchStateQ4.observe(this){
            updateStatusMatch(3 ,it , binding.backgroundStatusMatch4, binding.titleStatusMatch4, binding.matchCardView4, viewModel.matchQ4)
        }

        binding.matchCardView1.setOnClickListener{
            viewModel.matchStateQ1.value = MATCH_RUNNING
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ2)
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ3)
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ4)
        }

        binding.matchCardView2.setOnClickListener{
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ1)
            viewModel.matchStateQ2.value = MATCH_RUNNING
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ3)
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ4)
        }

        binding.matchCardView3.setOnClickListener{
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ1)
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ2)
            viewModel.matchStateQ3.value = MATCH_RUNNING
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ4)
        }

        binding.matchCardView4.setOnClickListener{
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ1)
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ2)
            checkStatusMatchAndChangeToWaiting(viewModel.matchStateQ3)
            viewModel.matchStateQ4.value = MATCH_RUNNING
        }
    }

    private fun updateMatch(nameT1 : TextView, nameT2 : TextView, scoreT1 : TextView, scoreT2 : TextView, shirtT1 : ImageView, shirtT2 : ImageView, match : TournamentMatch){
        nameT1.setText(match.nameTeamOne)
        nameT2.setText(match.nameTeamTwo)
        shirtT1.setImageResource(match.shirtTeamOne)
        shirtT2.setImageResource(match.shirtTeamTwo)
        scoreT1.setText(match.scoreTeamOne.toString())
        scoreT2.setText(match.scoreTeamTwo.toString())
    }

    private fun updateStatusMatch(matchID : Int, state : Int, backGroundMatch : LinearLayout, matchTitle : TextView,
                                  matchCard : CardView, match : MutableLiveData<TournamentMatch>){
        when(state){
            MATCH_WAITING -> {
                backGroundMatch.setBackgroundResource(R.color.yellow)
                matchTitle.setText(resources.getText(R.string.title_state_tournament_match_waiting))
                matchTitle.setTextColor(resources.getColor(R.color.black))
                matchCard.isClickable = false
            }

            MATCH_READY -> {
                backGroundMatch.setBackgroundResource(R.color.green)
                matchTitle.setTextColor(resources.getColor(R.color.white))
                matchTitle.setText(resources.getText(R.string.title_state_tournament_match_ready))
                matchCard.isClickable = true
            }

            MATCH_RUNNING -> {
                viewModel.currentMatchRunning.value = match
                backGroundMatch.setBackgroundResource(R.color.green)
                matchTitle.setText(resources.getText(R.string.title_state_tournament_match_running))
                matchCard.isClickable = false
                if(!viewModel.isRunningMatch){
                        requireActivity().findNavController(R.id.topFragment).navigate(R.id.action_waitingForMatchFragment_to_matchReadyTournamentFragment)
                    viewModel.timeLimit.value = viewModel.tournamentTimeLimit
                }
            }

            MATCH_ENDED ->{
                backGroundMatch.setBackgroundResource(R.color.red)
                matchTitle.setText(R.string.title_state_tournament_match_ended)
                matchCard.isClickable = false
                if(!viewModel.isRunningMatch){
                    updateStatusForOthersToReady(matchID)
                }
            }
        }
    }

    private fun updateStatusForOthersToReady(match : Int){

        when(match){
            0 -> {
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ2)
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ3)
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ4)
            }
            1 -> {
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ1)
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ3)
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ4)
            }
            2 -> {
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ1)
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ2)
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ4)
            }
            3 -> {
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ1)
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ2)
                checkStatusMatchAndChangeToReady(viewModel.matchStateQ3)
            }
        }
    }

    private fun checkStatusMatchAndChangeToReady(match : MutableLiveData<Int>){
        if(match.value!! == MATCH_WAITING){
            match.value = MATCH_READY
        }
    }

    private fun checkStatusMatchAndChangeToWaiting(match : MutableLiveData<Int>){
        if(match.value!! == MATCH_READY){
            match.value = MATCH_WAITING
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}