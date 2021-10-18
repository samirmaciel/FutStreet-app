package com.samirmaciel.futstreet.modules

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding get() = _binding!!

    companion object {
        const val ACTION_TO_TORUNAMENT = "ACTION_TO_TOURNAMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(receiverBroadcast, IntentFilter(ACTION_TO_TORUNAMENT))

        findNavController(R.id.topFragment).addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            @SuppressLint("ResourceType")
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                binding.appBarTitle.setText(destination.label)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            //binding.motionMainActivity.transitionToEnd()
        }, 2000)
    }

    private val receiverBroadcast : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            findNavController(R.id.bottomFragment).navigate(R.id.action_lastGamesFragment_to_tournamentFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}