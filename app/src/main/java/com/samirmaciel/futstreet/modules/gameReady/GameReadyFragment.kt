package com.samirmaciel.futstreet.modules.gameReady

import android.content.*
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentGamereadyBinding
import com.samirmaciel.futstreet.shared.const.PAUSED
import com.samirmaciel.futstreet.shared.const.PLAYING
import com.samirmaciel.futstreet.shared.const.PREPLAY
import com.samirmaciel.futstreet.shared.const.FINISH
import com.samirmaciel.futstreet.shared.model.Match
import com.samirmaciel.futstreet.shared.service.BackgroundService

class GameReadyFragment : Fragment(R.layout.fragment_gameready) {

    private var _binding : FragmentGamereadyBinding? = null
    private val binding : FragmentGamereadyBinding get() = _binding!!
    lateinit var serviceIntent : Intent
    private val viewModel : GameReadyViewModel by activityViewModels()


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
        _binding = FragmentGamereadyBinding.bind(view)

        getSettings()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(updateTime, IntentFilter(BackgroundService.UPDATE_ALL))

        binding.buttonAddGoalTeamOne.setOnClickListener{

            val alertScore = AlertDialog.Builder(requireContext()).apply {
                setTitle("${resources.getText(R.string.text_add_gol)} ${viewModel.nameTeamOne.value.toString()}?")
                setPositiveButton(resources.getText(R.string.yes)) { _, _ -> addGolTeamOne() }
                setNegativeButton(resources.getText(R.string.no), null)
            }
            alertScore.create().show()

        }

        binding.buttonAddGoalTeamTwo.setOnClickListener{
            val alertScore = AlertDialog.Builder(requireContext()).apply {
                setTitle("${resources.getText(R.string.text_add_gol)} ${viewModel.nameTeamTwo.value.toString()}?")
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

        viewModel.nameTeamOne.observe(this){
            binding.textNameTeamOne.setText(it)
        }

        viewModel.nameTeamTwo.observe(this){
            binding.textNameTeamTwo.setText(it)
        }

        viewModel.shirtTeamOne.observe(this){
            binding.imageShirtTeamOne.setImageResource(it)
        }

        viewModel.shirtTeamTwo.observe(this){
            binding.imageShirtTeamTwo.setImageResource(it)
        }

        viewModel.textTimeView.observe(this){
            binding.textCurrentTime.setText(it)
        }

        viewModel.currentRound.observe(this){
            binding.textCurrentRound.setText("${viewModel.currentRound.value}°")
        }

        viewModel.scoreTeamOne.observe(this){
            binding.textScoreTeamOne.setText(it.toString())
        }

        viewModel.scoreTeamTwo.observe(this){
            binding.textScoreTeamTwo.setText(it.toString())
        }

        viewModel.gameState.observe(this){
            when(viewModel.gameState.value!!){
                PREPLAY -> {
                    binding.buttonAddGoalTeamOne.isEnabled = false
                    binding.buttonAddGoalTeamTwo.isEnabled = false
                    binding.motionLayoutButtonsGoals.transitionToStart()
                    binding.buttonStart.setText(resources.getText(R.string.Start))
                }

                PLAYING ->{
                    binding.buttonAddGoalTeamOne.isEnabled = true
                    binding.buttonAddGoalTeamTwo.isEnabled = true
                    binding.motionLayoutButtonsGoals.transitionToEnd()
                    binding.buttonStart.setText(resources.getText(R.string.Pause))
                }

                PAUSED ->{
                    binding.buttonStart.setText(resources.getText(R.string.Continue))
                }

                FINISH -> {
                    binding.buttonAddGoalTeamOne.isEnabled = false
                    binding.buttonAddGoalTeamTwo.isEnabled = false
                    binding.motionLayoutButtonsGoals.transitionToStart()
                    binding.buttonStart.setText(resources.getText(R.string.Restart))
                    viewModel.saveMatch(Match(winner = getWinnerMath(viewModel.scoreTeamOne.value!!, viewModel.scoreTeamTwo.value!!),
                        nameTeamOne = viewModel.nameTeamOne.value!!,
                        nameTeamTwo = viewModel.nameTeamTwo.value!!,
                        scoreTeamOne = viewModel.scoreTeamOne.value!!,
                        scoreTeamTwo = viewModel.scoreTeamTwo.value!!,
                        shirtTeamOne = viewModel.shirtTeamOne.value!!,
                        shirtTeamTwo = viewModel.scoreTeamTwo.value!!,
                        rounds = viewModel.roundsLimit.value!!,
                        time = viewModel.timeLimitParams.value!!
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
        viewModel.scoreTeamOne.value = 0
        viewModel.scoreTeamTwo.value = 0
        viewModel.currentRound.value = 1
        viewModel.textTimeView.value = viewModel.getTimeStringFromDouble(viewModel.timeLimitParams.value!!)
    }

    private fun startBackgroundService() {
        serviceIntent.putExtra(BackgroundService.SCORE_T1, viewModel.scoreTeamOne.value)
        serviceIntent.putExtra(BackgroundService.SCORE_T2, viewModel.scoreTeamTwo.value)
        serviceIntent.putExtra(BackgroundService.NAME_TEAMONE, viewModel.nameTeamOne.value)
        serviceIntent.putExtra(BackgroundService.NAME_TEAMTWO, viewModel.nameTeamTwo.value)
        serviceIntent.putExtra(BackgroundService.SHIRT_TEAMONE, viewModel.shirtTeamOne.value)
        serviceIntent.putExtra(BackgroundService.SHIRT_TEAMTWO, viewModel.shirtTeamTwo.value)
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
            viewModel.scoreTeamOne.value = viewModel.scoreTeamOne.value!! + 1
            binding.textScoreTeamOne.animate().apply {
                duration = 150
                scaleXBy(-0.5f)
                scaleYBy(-0.5f)
            }
        }.start()

        val scoreIntent = Intent(SCORE_OBSERVE)
        scoreIntent.putExtra(BackgroundService.SCORE_T1, viewModel.scoreTeamOne.value!!)
        requireActivity().sendBroadcast(scoreIntent)
    }

    private fun addGolTeamTwo() {
        binding.textScoreTeamTwo.animate().apply {
            duration = 150
            scaleYBy(0.5f)
            scaleXBy(0.5f)
        }.withEndAction {
            viewModel.scoreTeamTwo.value = viewModel.scoreTeamTwo.value!! + 1
            binding.textScoreTeamTwo.animate().apply {
                duration = 150
                scaleXBy(-0.5f)
                scaleYBy(-0.5f)
            }
        }.start()
        val scoreIntent = Intent(SCORE_OBSERVE)
        scoreIntent.putExtra(BackgroundService.SCORE_T2, viewModel.scoreTeamTwo.value!!)
        requireActivity().sendBroadcast(scoreIntent)
    }

    private fun getSettings() {

        arguments?.getString("NameTeamOne")?.let {
            viewModel.nameTeamOne.value = it
        }

        arguments?.getString("NameTeamTwo")?.let {
            viewModel.nameTeamTwo.value = it
        }

        arguments?.getInt("ScoreTeamOne")?.let {
            viewModel.scoreTeamOne.value = it
        }

        arguments?.getInt("ScoreTeamTwo")?.let {
            viewModel.scoreTeamTwo.value = it
        }

        arguments?.getDouble("RoundTime")?.let {
            viewModel.timeLimit.value = it
            viewModel.timeLimitParams.value = it
            viewModel.textTimeView.value = viewModel.getTimeStringFromDouble(it)
        }

        arguments?.getInt("CurrentRound")?.let {
            viewModel.currentRound.value = it
        }

        arguments?.getInt("Rounds")?.let {
            viewModel.roundsLimit.value = it
        }

        arguments?.getInt("ShirtTeamOne", R.drawable.shirt_select)?.let {
            viewModel.shirtTeamOne.value = it
        }

        arguments?.getInt("ShirtTeamTwo", R.drawable.shirt_select)?.let{
            viewModel.shirtTeamTwo.value = it
        }

        arguments?.getInt("Rounds", 1)?.let {
            viewModel.roundsLimit.value = it
        }


    }

    private fun onCancelGame() {
        cleanViewModelData()
        requireActivity().stopService(serviceIntent)
        findNavController().navigate(R.id.action_gameReadyFragment_to_gameSettingFragment)
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