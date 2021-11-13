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
import com.samirmaciel.futstreet.shared.const.*
import com.samirmaciel.futstreet.shared.model.TournamentMatch
import com.samirmaciel.futstreet.shared.service.BackgroundService

class MatchReadyFragment : Fragment(R.layout.fragment_matchready) {

    private var _binding : FragmentMatchreadyBinding? = null
    private val binding : FragmentMatchreadyBinding get() = _binding!!
    lateinit var serviceIntent : Intent
    private val viewModel : MatchReadyViewModel by activityViewModels()



    companion object {
        const val SCORE_OBSERVE = "SCORE_OBSERVE"
    }


    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            viewModel.timeLimit.value = intent.getDoubleExtra(BackgroundService.UPDATE_TIME, 0.0)
            viewModel.textTimeView.value = viewModel.getTimeStringFromDouble(viewModel.timeLimit.value!!)
            if(intent.getBooleanExtra(BackgroundService.IS_TIME_ENDED, false)){
                if(viewModel.roundsLimit.value == viewModel.currentRound.value){
                    viewModel.textTimeView.value = resources.getText(R.string.text_end_game).toString()
                    viewModel.gameState.value = FINISH
                }else{
                    viewModel.textTimeView.value = viewModel.getTimeStringFromDouble(viewModel.timeLimitParams.value!!)
                    viewModel.gameState.value = PREPLAY
                }
                viewModel.timeLimit.value = viewModel.timeLimitParams.value
                viewModel.currentRound.value = intent.getIntExtra(BackgroundService.CURRENT_ROUND, viewModel.currentRound.value!!)
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
        _binding = FragmentMatchreadyBinding.bind(view)

        getSettings()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(updateTime, IntentFilter(BackgroundService.UPDATE_ALL))

        binding.buttonAddGoalTeamOne.setOnClickListener{

            val alertScore = AlertDialog.Builder(requireContext()).apply {
                setTitle("${resources.getText(R.string.text_add_gol)} ${viewModel.nameTeam1.value.toString()}?")
                setPositiveButton(resources.getText(R.string.yes)) { _, _ -> addGolTeamOne() }
                setNegativeButton(resources.getText(R.string.no), null)
            }
            alertScore.create().show()

        }

        binding.buttonAddGoalTeamTwo.setOnClickListener{
            val alertScore = AlertDialog.Builder(requireContext()).apply {
                setTitle("${resources.getText(R.string.text_add_gol)} ${viewModel.nameTeam2.value.toString()}?")
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

        viewModel.nameTeam1.observe(this){
            binding.textNameTeamOne.setText(it)
        }

        viewModel.nameTeam2.observe(this){
            binding.textNameTeamTwo.setText(it)
        }

        viewModel.shirtTeam1.observe(this){
            binding.imageShirtTeamOne.setImageResource(it)
        }

        viewModel.shirtTeam2.observe(this){
            binding.imageShirtTeamTwo.setImageResource(it)
        }

        viewModel.textTimeView.observe(this){
            binding.textCurrentTime.setText(it)
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
        viewModel.scoreTeam1.value = 0
        viewModel.scoreTeam2.value = 0
        viewModel.currentRound.value = 1
        viewModel.textTimeView.value = viewModel.getTimeStringFromDouble(viewModel.timeLimitParams.value!!)
    }

    private fun startBackgroundService() {
        serviceIntent.putExtra(BackgroundService.SCORE_T1, viewModel.scoreTeam1.value)
        serviceIntent.putExtra(BackgroundService.SCORE_T2, viewModel.scoreTeam2.value)
        serviceIntent.putExtra(BackgroundService.NAME_TEAMONE, viewModel.nameTeam1.value)
        serviceIntent.putExtra(BackgroundService.NAME_TEAMTWO, viewModel.nameTeam2.value)
        serviceIntent.putExtra(BackgroundService.SHIRT_TEAMONE, viewModel.shirtTeam1.value)
        serviceIntent.putExtra(BackgroundService.SHIRT_TEAMTWO, viewModel.shirtTeam2.value)
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
            binding.textScoreTeamTwo.animate().apply {
                duration = 150
                scaleXBy(-0.5f)
                scaleYBy(-0.5f)
            }
        }.start()

    }

    private fun getSettings() {

        arguments?.getString("teamName1")?.let {
            viewModel.nameTeam1.value = it
        }

        arguments?.getString("teamName2")?.let {
            viewModel.nameTeam2.value = it
        }

        arguments?.getDouble("RoundTime")?.let {
            viewModel.timeLimit.value = it
            viewModel.timeLimitParams.value = it
            viewModel.textTimeView.value = viewModel.getTimeStringFromDouble(it)
        }

        arguments?.getInt("matchType")?.let {
            viewModel.matchType.value = it
        }

        arguments?.getInt("CurrentRound")?.let {
            viewModel.currentRound.value = it
        }

        arguments?.getInt("Rounds")?.let {
            viewModel.roundsLimit.value = it
        }

        arguments?.getInt("shirtTeam1", R.drawable.shirt_select)?.let {
            viewModel.shirtTeam1.value = it
        }

        arguments?.getInt("shirtTeam2", R.drawable.shirt_select)?.let{
            viewModel.shirtTeam2.value = it
        }

        arguments?.getInt("Rounds", 1)?.let {
            viewModel.roundsLimit.value = it
        }
    }

    private fun onCancelGame() {
        cleanViewModelData()
        requireActivity().stopService(serviceIntent)
        if(viewModel.matchType.value!! == MATCH_TOURNAMENT){
            findNavController().navigate(R.id.action_gameReadyFragment_to_homeFragment)
        }else{
            findNavController().navigate(R.id.action_gameReadyFragment_to_gameSettingFragment)
        }
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
        requireActivity().unregisterReceiver(updateTime)
        _binding = null
    }
}