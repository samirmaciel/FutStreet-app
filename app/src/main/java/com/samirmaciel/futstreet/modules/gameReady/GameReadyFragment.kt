package com.samirmaciel.futstreet.modules.gameReady

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentGamereadyBinding
import com.samirmaciel.futstreet.shared.service.BackgroundService
import kotlin.math.roundToInt

class GameReadyFragment : Fragment(R.layout.fragment_gameready) {

    private var _binding : FragmentGamereadyBinding? = null
    private val binding : FragmentGamereadyBinding get() = _binding!!
    lateinit var serviceIntent : Intent
    private val viewModel : GameReadyViewModel by activityViewModels()


    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            viewModel.timeLimit.value = intent.getDoubleExtra(BackgroundService.UPDATE_TIME, viewModel.timeLimit.value!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DEBUGTESTE", "FRAGMENT onCreate: ")
        serviceIntent = Intent(requireContext().applicationContext, BackgroundService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(BackgroundService.UPDATE_ALL))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGamereadyBinding.bind(view)
        getSettings()

        binding.buttonStart.setOnClickListener{
            serviceIntent.putExtra(BackgroundService.NAME_TEAMONE, viewModel.nameTeamOne.value)
            serviceIntent.putExtra(BackgroundService.NAME_TEAMTWO, viewModel.nameTeamTwo.value)
            serviceIntent.putExtra(BackgroundService.SHIRT_TEAMONE, viewModel.shirtTeamOne.value)
            serviceIntent.putExtra(BackgroundService.SHIRT_TEAMTWO, viewModel.shirtTeamTwo.value)
            serviceIntent.putExtra(BackgroundService.TIME_LIMIT, viewModel.timeLimit.value)
            requireActivity().startService(serviceIntent)
        }

        binding.buttonGameCancel.setOnClickListener{
            requireActivity().stopService(serviceIntent)
            findNavController().navigate(R.id.action_gameReadyFragment_to_gameSettingFragment)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.timeLimit.observe(this){
            binding.textCurrentTime.setText(viewModel.getTimeStringFromDouble())
        }
    }

    private fun getSettings() {

        arguments?.getString("NameTeamOne")?.let {
            viewModel.nameTeamOne.value = it
        }

        arguments?.getString("NameTeamTwo")?.let {
            viewModel.nameTeamTwo.value = it
        }

        arguments?.getDouble("roundTime")?.let {
            viewModel.timeLimit.value = it
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

        binding.textCurrentTime.setText(viewModel.getTimeStringFromDouble())


    }

    override fun onResume() {
        super.onResume()
        Log.d("DEBUGTESTE", "FRAGMENT onResume: ")
        requireActivity().registerReceiver(updateTime, IntentFilter(BackgroundService.UPDATE_ALL))

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

        viewModel.timeLimit.observe(this){
            binding.textCurrentTime.setText(viewModel.getTimeStringFromDouble())
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("DEUBGTESTE", "FRAGMENT onStop: ")
        requireActivity().unregisterReceiver(updateTime)
    }


    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(updateTime)
        Log.d("DEBUGTESTE", "FRAGMENT onDestroy: ")
        _binding = null
    }
}