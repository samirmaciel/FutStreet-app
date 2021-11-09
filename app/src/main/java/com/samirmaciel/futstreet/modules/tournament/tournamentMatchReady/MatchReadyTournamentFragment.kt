package com.samirmaciel.futstreet.modules.matchReady

import android.content.*
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentMatchreadyBinding
import com.samirmaciel.futstreet.databinding.FragmentTournamentMatchreadyBinding
import com.samirmaciel.futstreet.modules.tournament.TournamentViewModel
import com.samirmaciel.futstreet.shared.const.*
import com.samirmaciel.futstreet.shared.model.Match
import com.samirmaciel.futstreet.shared.service.BackgroundService

class MatchReadyTournamentFragment : Fragment(R.layout.fragment_tournament_matchready) {

    private var _binding : FragmentTournamentMatchreadyBinding? = null
    private val binding : FragmentTournamentMatchreadyBinding get() = _binding!!
    lateinit var serviceIntent : Intent
    private val viewModelDefault : MatchReadyViewModel by activityViewModels()
    private val viewModelTournament : TournamentViewModel by activityViewModels()



    companion object {
        const val SCORE_OBSERVE = "SCORE_OBSERVE"
    }


    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            viewModelDefault.timeLimit.value = intent.getDoubleExtra(BackgroundService.UPDATE_TIME, 0.0)
            viewModelDefault.textTimeView.value = viewModelDefault.getTimeStringFromDouble(viewModelDefault.timeLimit.value!!)
            if(intent.getBooleanExtra(BackgroundService.IS_TIME_ENDED, false)){
                if(viewModelDefault.roundsLimit.value == viewModelDefault.currentRound.value){
                    viewModelDefault.textTimeView.value = resources.getText(R.string.text_end_game).toString()
                    viewModelDefault.gameState.value = FINISH
                }else{
                    viewModelDefault.textTimeView.value = viewModelDefault.getTimeStringFromDouble(viewModelDefault.timeLimitParams.value!!)
                    viewModelDefault.gameState.value = PREPLAY
                }
                viewModelDefault.timeLimit.value = viewModelDefault.timeLimitParams.value
                viewModelDefault.currentRound.value = intent.getIntExtra(BackgroundService.CURRENT_ROUND, viewModelDefault.currentRound.value!!)
                requireActivity().stopService(serviceIntent)
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

        getSettings()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(updateTime, IntentFilter(BackgroundService.UPDATE_ALL))

        binding.buttonAddGoalTeamOne.setOnClickListener{

            val alertScore = AlertDialog.Builder(requireContext()).apply {
                setTitle("${resources.getText(R.string.text_add_gol)} ${viewModelDefault.nameTeam1.value.toString()}?")
                setPositiveButton(resources.getText(R.string.yes)) { _, _ -> addGolTeamOne() }
                setNegativeButton(resources.getText(R.string.no), null)
            }
            alertScore.create().show()

        }

        binding.buttonAddGoalTeamTwo.setOnClickListener{
            val alertScore = AlertDialog.Builder(requireContext()).apply {
                setTitle("${resources.getText(R.string.text_add_gol)} ${viewModelDefault.nameTeam2.value.toString()}?")
                setPositiveButton(resources.getText(R.string.yes)){ _, _  -> addGolTeamTwo() }
                setNegativeButton(resources.getText(R.string.no), null)
            }
            alertScore.create().show()
        }

        binding.buttonStart.setOnClickListener{
            when(viewModelDefault.gameState.value!!){
                PREPLAY -> {
                    startBackgroundService()
                    viewModelDefault.gameState.value = PLAYING
                }

                PLAYING ->{
                    requireActivity().stopService(serviceIntent)
                    viewModelDefault.gameState.value = PAUSED
                }

                PAUSED ->{
                    startBackgroundService()
                    viewModelDefault.gameState.value = PLAYING
                }

                FINISH -> {
                    restartBackgroundService()
                    viewModelDefault.gameState.value = PREPLAY
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

        viewModelDefault.nameTeam1.observe(this){
            binding.textNameTeamOne.setText(it)
        }

        viewModelDefault.nameTeam2.observe(this){
            binding.textNameTeamTwo.setText(it)
        }

        viewModelDefault.shirtTeam1.observe(this){
            binding.imageShirtTeamOne.setImageResource(it)
        }

        viewModelDefault.shirtTeam2.observe(this){
            binding.imageShirtTeamTwo.setImageResource(it)
        }

        viewModelDefault.textTimeView.observe(this){
            binding.textCurrentTime.setText(it)
        }

        viewModelDefault.currentRound.observe(this){
            binding.textCurrentRound.setText("${viewModelDefault.currentRound.value}°")
        }

        viewModelDefault.scoreTeam1.observe(this){
            binding.textScoreTeamOne.setText(it.toString())
            val scoreIntent = Intent(SCORE_OBSERVE)
            scoreIntent.putExtra(BackgroundService.SCORE_T1, viewModelDefault.scoreTeam1.value!!)
            requireActivity().sendBroadcast(scoreIntent)
        }

        viewModelDefault.scoreTeam2.observe(this){
            binding.textScoreTeamTwo.setText(it.toString())
            val scoreIntent = Intent(SCORE_OBSERVE)
            scoreIntent.putExtra(BackgroundService.SCORE_T2, viewModelDefault.scoreTeam2.value!!)
            requireActivity().sendBroadcast(scoreIntent)
        }

        viewModelDefault.gameState.observe(this){
            when(viewModelDefault.gameState.value!!){
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
                    viewModelDefault.saveMatch(Match(winner = getWinnerMath(viewModelDefault.scoreTeam1.value!!, viewModelDefault.scoreTeam2.value!!),
                        nameTeamOne = viewModelDefault.nameTeam1.value!!,
                        nameTeamTwo = viewModelDefault.nameTeam2.value!!,
                        scoreTeamOne = viewModelDefault.scoreTeam1.value!!,
                        scoreTeamTwo = viewModelDefault.scoreTeam2.value!!,
                        shirtTeamOne = viewModelDefault.shirtTeam1.value!!,
                        shirtTeamTwo = viewModelDefault.scoreTeam2.value!!,
                        rounds = viewModelDefault.roundsLimit.value!!,
                        time = viewModelDefault.timeLimitParams.value!!
                    ))
                }
            }
        }
    }

    private fun getWinnerMath(teamOne : Int, teamTwo : Int) : Int {
        if(teamOne > teamTwo){
            return 1
        }else if(teamOne < teamTwo){
            return 2
        }else{
            return 0
        }
    }

    private fun buttonAnimateVisibility(button : Button){
        button.animate().apply {
            duration = 100
            scaleXBy(2f)
            scaleY(2f)
            setInterpolator(AccelerateDecelerateInterpolator())

        }
    }

    private fun restartBackgroundService(){
        viewModelDefault.scoreTeam1.value = 0
        viewModelDefault.scoreTeam2.value = 0
        viewModelDefault.currentRound.value = 1
        viewModelDefault.textTimeView.value = viewModelDefault.getTimeStringFromDouble(viewModelDefault.timeLimitParams.value!!)
    }

    private fun startBackgroundService() {
        serviceIntent.putExtra(BackgroundService.SCORE_T1, viewModelDefault.scoreTeam1.value)
        serviceIntent.putExtra(BackgroundService.SCORE_T2, viewModelDefault.scoreTeam2.value)
        serviceIntent.putExtra(BackgroundService.NAME_TEAMONE, viewModelDefault.nameTeam1.value)
        serviceIntent.putExtra(BackgroundService.NAME_TEAMTWO, viewModelDefault.nameTeam2.value)
        serviceIntent.putExtra(BackgroundService.SHIRT_TEAMONE, viewModelDefault.shirtTeam1.value)
        serviceIntent.putExtra(BackgroundService.SHIRT_TEAMTWO, viewModelDefault.shirtTeam2.value)
        serviceIntent.putExtra(BackgroundService.TIME_LIMIT, viewModelDefault.timeLimit.value)
        serviceIntent.putExtra(BackgroundService.ROUND_LIMIT, viewModelDefault.roundsLimit.value)
        serviceIntent.putExtra(BackgroundService.CURRENT_ROUND, viewModelDefault.currentRound.value)
        requireActivity().startService(serviceIntent)
    }

    private fun addGolTeamOne() {
        binding.textScoreTeamOne.animate().apply {
            duration = 150
            scaleXBy(0.5f)
            scaleYBy(0.5f)

        }.withEndAction {
            viewModelDefault.scoreTeam1.value = viewModelDefault.scoreTeam1.value!! + 1
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
            viewModelDefault.scoreTeam2.value = viewModelDefault.scoreTeam2.value!! + 1
            binding.textScoreTeamTwo.animate().apply {
                duration = 150
                scaleXBy(-0.5f)
                scaleYBy(-0.5f)
            }
        }.start()

    }

    private fun getSettings() {

        arguments?.getString("teamName1")?.let {
            viewModelDefault.nameTeam1.value = it
        }

        arguments?.getString("teamName2")?.let {
            viewModelDefault.nameTeam2.value = it
        }

        arguments?.getDouble("RoundTime")?.let {
            viewModelDefault.timeLimit.value = it
            viewModelDefault.timeLimitParams.value = it
            viewModelDefault.textTimeView.value = viewModelDefault.getTimeStringFromDouble(it)
        }

        arguments?.getInt("matchType")?.let {
            viewModelDefault.matchType.value = it
        }

        arguments?.getInt("CurrentRound")?.let {
            viewModelDefault.currentRound.value = it
        }

        arguments?.getInt("Rounds")?.let {
            viewModelDefault.roundsLimit.value = it
        }

        arguments?.getInt("shirtTeam1", R.drawable.shirt_select)?.let {
            viewModelDefault.shirtTeam1.value = it
        }

        arguments?.getInt("shirtTeam2", R.drawable.shirt_select)?.let{
            viewModelDefault.shirtTeam2.value = it
        }

        arguments?.getInt("Rounds", 1)?.let {
            viewModelDefault.roundsLimit.value = it
        }
    }

    private fun onCancelGame() {
        cleanViewModelData()
        requireActivity().stopService(serviceIntent)
        findNavController().navigate(R.id.action_matchReadyTournamentFragment_to_homeFragment)

    }

    private fun cleanViewModelData(){
        viewModelDefault.gameState.value = PREPLAY
        viewModelDefault.currentRound.value = 1
        viewModelTournament.matchStateQ4.value = MATCH_READY
        viewModelTournament.matchStateQ3.value = MATCH_READY
        viewModelTournament.matchStateQ2.value = MATCH_READY
        viewModelTournament.matchStateQ1.value = MATCH_READY
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(updateTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().stopService(serviceIntent)
        requireActivity().unregisterReceiver(updateTime)
        _binding = null
    }
}