package com.samirmaciel.futstreet.modules.matchReady

import android.content.*
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentMatchreadyBinding
import com.samirmaciel.futstreet.modules.tournament.TournamentViewModel
import com.samirmaciel.futstreet.shared.const.*
import com.samirmaciel.futstreet.shared.model.Team
import com.samirmaciel.futstreet.shared.model.TournamentMatch
import com.samirmaciel.futstreet.shared.service.BackgroundService

class MatchReadyTournamentFragment : Fragment(R.layout.fragment_tournament_matchready) {

    private var _binding : FragmentTournamentMatchreadyBinding? = null
    private val binding : FragmentTournamentMatchreadyBinding get() = _binding!!
    lateinit var serviceIntent : Intent
    private val viewModel : TournamentViewModel by activityViewModels()

    companion object {
        const val SCORE_OBSERVE = "SCORE_OBSERVE"
        const val PENALTY_NOTIFY = "PENALTY_NOTIFY"
    }

    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val currentMatch = viewModel.currentMatchRunning.value!!.value!!
            if(viewModel.gameState.value != PENALTY){
                viewModel.timeLimit.value = intent.getDoubleExtra(BackgroundService.UPDATE_TIME, 0.0)
            }
            currentMatch.scoreTeamOne = intent.getIntExtra(BackgroundService.SCORE_T1, currentMatch.scoreTeamOne)
            currentMatch.scoreTeamTwo = intent.getIntExtra(BackgroundService.SCORE_T2, currentMatch.scoreTeamTwo)
            viewModel.currentMatchRunning.value!!.value = currentMatch
            if(intent.getBooleanExtra(BackgroundService.IS_TIME_ENDED, false)){
                if(viewModel.roundsLimit.value == viewModel.currentRound.value){
                    if(currentMatch.scoreTeamOne == currentMatch.scoreTeamTwo){
                        setCurrentMatchToPenaltyShootout()
                        val penaltyIntent = Intent(PENALTY_NOTIFY)
                        penaltyIntent.putExtra(PENALTY_NOTIFY, PENALTY_NOTIFY)
                        requireActivity().sendBroadcast(penaltyIntent)
                    }else{
                        currentMatchEndClean(currentMatch)
                    }

                }else{
                    viewModel.textTimeView.value = viewModel.getTimeStringFromDouble(viewModel.tournamentTimeLimit)
                    viewModel.timeLimit.value = viewModel.tournamentTimeLimit
                    viewModel.gameState.value = PREPLAY
                    viewModel.currentRound.value = intent.getIntExtra(BackgroundService.CURRENT_ROUND, viewModel.currentRound.value!!)
                    requireActivity().stopService(serviceIntent)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceIntent = Intent(requireContext().applicationContext, BackgroundService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(BackgroundService.UPDATE_ALL))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentMatchreadyBinding.bind(view)
        getSettings(viewModel.currentMatchRunning.value!!.value!!)
        viewModel.isMatchRunning = true
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(updateTime, IntentFilter(BackgroundService.UPDATE_ALL))

        binding.buttonAddGoalTeamOne.setOnClickListener{

            val alertScore = AlertDialog.Builder(requireContext()).apply {
                setTitle("${resources.getText(R.string.text_add_gol)} ${viewModel.nameTeam1MR.value.toString()}?")
                setPositiveButton(resources.getText(R.string.yes)) { _, _ -> addGolTeamOne() }
                setNegativeButton(resources.getText(R.string.no), null)
            }
            alertScore.create().show()

        }

        binding.buttonAddGoalTeamTwo.setOnClickListener{
            val alertScore = AlertDialog.Builder(requireContext()).apply {
                setTitle("${resources.getText(R.string.text_add_gol)} ${viewModel.nameTeam2MR.value.toString()}?")
                setPositiveButton(resources.getText(R.string.yes)){ _, _  -> addGolTeamTwo() }
                setNegativeButton(resources.getText(R.string.no), null)
            }
            alertScore.create().show()
        }

        binding.buttonStart.setOnClickListener{
            when(viewModel.gameState.value!!){
                PREPLAY -> {
                    startBackgroundService()
                    viewModel.gameState.value = PLAYING
                }
                PLAYING ->{
                    requireActivity().stopService(serviceIntent)
                    viewModel.gameState.value = PAUSED
                }
                PAUSED ->{
                    startBackgroundService()
                    viewModel.gameState.value = PLAYING
                }
                FINISH -> {
                    restartBackgroundService()
                    viewModel.gameState.value = PREPLAY
                }
                PENALTY -> {
                    if(viewModel.currentMatchRunning.value!!.value!!.scoreTeamOne != viewModel.currentMatchRunning.value!!.value!!.scoreTeamTwo){
                        val currentMatch : TournamentMatch = viewModel.currentMatchRunning.value!!.value!!
                        currentMatch.winner = getWinnerMath(currentMatch)
                        currentMatchEndClean(currentMatch)
                        viewModel.currentMatchRunning.value!!.value = currentMatch
                        viewModel.currentMatchRunning.value!!.value!!.status.value = MATCH_ENDED

                    }else{
                        Snackbar.make(requireView(), "Encerramento apenas será feito com um vencedor!", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
        binding.buttonGameCancel.setOnClickListener{
            val alertCancel = AlertDialog.Builder(requireContext()).apply {
                setTitle(resources.getText(R.string.text_ask_cancel_game))
                setPositiveButton(resources.getText(R.string.yes)) { _, _ -> onCancelGame() }
                setNegativeButton(resources.getText(R.string.no), null)
            }
            alertCancel.create().show()

        }

        viewModel.nameTeam1MR.observe(this){
            binding.textNameTeamOne.setText(it)
        }

        viewModel.nameTeam2MR.observe(this){
            binding.textNameTeamTwo.setText(it)
        }

        viewModel.shirtTeam1MR.observe(this){
            binding.imageShirtTeamOne.setImageResource(it)
        }

        viewModel.shirtTeam2MR.observe(this){
            binding.imageShirtTeamTwo.setImageResource(it)
        }

        viewModel.timeLimit.observe(this){
            binding.textCurrentTime.setText(viewModel.getTimeStringFromDouble(it))
        }

        viewModel.currentRound.observe(this){
            binding.textCurrentRound.setText("${viewModel.currentRound.value}°")
        }

        viewModel.scoreTeam1.observe(this){
            binding.textScoreTeamOne.setText(it.toString())
            val scoreIntent = Intent(SCORE_OBSERVE)
            scoreIntent.putExtra(BackgroundService.SCORE_T1, viewModel.scoreTeam1.value!!)
            requireActivity().sendBroadcast(scoreIntent)
        }

        viewModel.scoreTeam2.observe(this){
            binding.textScoreTeamTwo.setText(it.toString())
            val scoreIntent = Intent(SCORE_OBSERVE)
            scoreIntent.putExtra(BackgroundService.SCORE_T2, viewModel.scoreTeam2.value!!)
            requireActivity().sendBroadcast(scoreIntent)
        }

        viewModel.gameState.observe(this){
            when(viewModel.gameState.value!!){
                PREPLAY -> {
                    binding.buttonAddGoalTeamOne.isEnabled = false
                    binding.buttonAddGoalTeamTwo.isEnabled = false
                    binding.motionLayoutButtonsGoals.transitionToStart()
                    binding.buttonStart.setText(resources.getText(R.string.title_button_start))
                }

                PLAYING ->{
                    binding.buttonAddGoalTeamOne.isEnabled = true
                    binding.buttonAddGoalTeamTwo.isEnabled = true
                    binding.motionLayoutButtonsGoals.transitionToEnd()
                    binding.buttonStart.setText(resources.getText(R.string.title_button_pause))
                }

                PAUSED ->{
                    binding.buttonStart.setText(resources.getText(R.string.title_button_continue))
                }

                FINISH -> {
                    binding.buttonAddGoalTeamOne.isEnabled = false
                    binding.buttonAddGoalTeamTwo.isEnabled = false
                    binding.motionLayoutButtonsGoals.transitionToStart()
                    binding.buttonStart.setText(resources.getText(R.string.title_button_restart))
                }

                PENALTY -> {
                    binding.textCurrentTime.text  ="Pênaltis"
                    binding.buttonStart.text = "Encerrar"
                    binding.textCurrentRound.visibility = View.GONE
                }
            }
        }

    }

    private fun setCurrentMatchToPenaltyShootout(){
        viewModel.gameState.value = PENALTY
    }

    private fun getWinnerMath(match: TournamentMatch) : Team {
        if(match.scoreTeamOne > match.scoreTeamTwo){
            return Team(
                name = match.nameTeamOne,
                shirt = match.shirtTeamOne
            )
        }else{
            return Team(
                name = match.nameTeamTwo,
                shirt = match.shirtTeamTwo
            )
        }
    }

//    private fun buttonAnimateVisibility(button : Button){
//        button.animate().apply {
//            duration = 100
//            scaleXBy(2f)
//            scaleY(2f)
//            setInterpolator(AccelerateDecelerateInterpolator())
//
//        }
//    }

    private fun restartBackgroundService(){
        viewModel.scoreTeam1.value = 0
        viewModel.scoreTeam2.value = 0
        viewModel.currentRound.value = 1
        viewModel.textTimeView.value = viewModel.getTimeStringFromDouble(viewModel.timeLimitParams.value!!)
    }

    private fun startBackgroundService() {
        serviceIntent.putExtra(BackgroundService.SCORE_T1, viewModel.scoreTeam1.value)
        serviceIntent.putExtra(BackgroundService.SCORE_T2, viewModel.scoreTeam2.value)
        serviceIntent.putExtra(BackgroundService.NAME_TEAMONE, viewModel.nameTeam1MR.value)
        serviceIntent.putExtra(BackgroundService.NAME_TEAMTWO, viewModel.nameTeam2MR.value)
        serviceIntent.putExtra(BackgroundService.SHIRT_TEAMONE, viewModel.shirtTeam1MR.value)
        serviceIntent.putExtra(BackgroundService.SHIRT_TEAMTWO, viewModel.shirtTeam2MR.value)
        serviceIntent.putExtra(BackgroundService.TIME_LIMIT, viewModel.timeLimit.value)
        serviceIntent.putExtra(BackgroundService.ROUND_LIMIT, viewModel.roundsLimit.value)
        serviceIntent.putExtra(BackgroundService.CURRENT_ROUND, viewModel.currentRound.value)
        requireActivity().startService(serviceIntent)
    }

    private fun addGolTeamOne() {
        binding.textScoreTeamOne.animate().apply {
            duration = 150
            scaleXBy(0.5f)
            scaleYBy(0.5f)

        }.withEndAction {
            viewModel.scoreTeam1.value = viewModel.scoreTeam1.value!! + 1
            viewModel.currentMatchRunning.value!!.value!!.scoreTeamOne = viewModel.currentMatchRunning.value!!.value!!.scoreTeamOne + 1
            binding.textScoreTeamOne.animate().apply {
                duration = 150
                scaleXBy(-0.5f)
                scaleYBy(-0.5f)
            }
        }.start()

    }

    private fun addGolTeamTwo() {
        binding.textScoreTeamTwo.animate().apply {
            duration = 150
            scaleYBy(0.5f)
            scaleXBy(0.5f)
        }.withEndAction {
            viewModel.scoreTeam2.value = viewModel.scoreTeam2.value!! + 1
            viewModel.currentMatchRunning.value!!.value!!.scoreTeamTwo = viewModel.currentMatchRunning.value!!.value!!.scoreTeamTwo + 1
            binding.textScoreTeamTwo.animate().apply {
                duration = 150
                scaleXBy(-0.5f)
                scaleYBy(-0.5f)
            }
        }.start()

    }

    private fun getSettings(match : TournamentMatch) {

        viewModel.nameTeam1MR.value = match.nameTeamOne
        viewModel.nameTeam2MR.value = match.nameTeamTwo
        viewModel.textTimeView.value = viewModel.getTimeStringFromDouble(viewModel.tournamentTimeLimit)
        viewModel.shirtTeam1MR.value = match.shirtTeamOne
        viewModel.shirtTeam2MR.value = match.shirtTeamTwo
    }

    private fun onCancelGame() {
        cleanViewModelData()
        requireActivity().stopService(serviceIntent)
        findNavController().navigate(R.id.action_matchReadyTournamentFragment_to_homeFragment)

    }

    private fun currentMatchEndClean(currentMatch : TournamentMatch){
        viewModel.textTimeView.value = resources.getText(R.string.text_end_game).toString()
        viewModel.gameState.value = PREPLAY
        viewModel.scoreTeam1.value = 0
        viewModel.scoreTeam2.value = 0
        viewModel.timeLimit.value = viewModel.tournamentTimeLimit
        viewModel.isMatchRunning = false
        requireActivity().stopService(serviceIntent)
        findNavController().navigateUp()
        currentMatch.status.value = MATCH_ENDED
    }

    private fun cleanViewModelData(){
        viewModel.gameState.value = PREPLAY
        viewModel.currentRound.value = 1
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(updateTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().stopService(serviceIntent)
        _binding = null
    }

}