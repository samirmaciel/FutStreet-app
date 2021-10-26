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
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.ActivityMainBinding
import com.samirmaciel.futstreet.shared.const.*

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding get() = _binding!!

    private var topFragmentCurrentPage : Int = HOME_FRAGMENT
    private var bottomFragmentCurrentPage : Int = LAST_MATCHES_FRAGMENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        findNavController(R.id.topFragment).addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            @SuppressLint("ResourceType")
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                when(destination.label){
                    resources.getText(R.string.app_name) -> {
                        topFragmentCurrentPage = HOME_FRAGMENT
                        if(bottomFragmentCurrentPage == TOURNAMENT_FRAGMENT){
                            findNavController(R.id.bottomFragment).navigate(R.id.action_tournamentFragment_to_lastGamesFragment)
                        }
                    }
                }

                binding.appBarTitle.setText(destination.label)
            }
        })

        findNavController(R.id.bottomFragment).addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                when(destination.label){
                    "fragment_lastgames" ->{
                        bottomFragmentCurrentPage = LAST_MATCHES_FRAGMENT
                    }
                    "fragment_tournament" ->{
                        bottomFragmentCurrentPage = TOURNAMENT_FRAGMENT
                    }
                }
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}