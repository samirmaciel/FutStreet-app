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

        registerReceiver(receiverToTournamentPage, IntentFilter(GO_TO_TOURNAMENET_PAGE))
        registerReceiver(receiverToLastMatchesPage, IntentFilter(GOT_TO_LASTMATCHES_PAGE))

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

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            //binding.motionMainActivity.transitionToEnd()
        }, 2000)
    }

    private val receiverToTournamentPage : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {

            val bundle = Bundle().apply {
                putInt("shirtTeam1", intent.getIntExtra("shirtTeam1", R.drawable.shirt_select))
                putString("teamName1", intent.getStringExtra("teamName1"))

                putInt("shirtTeam2", intent.getIntExtra("shirtTeam2", R.drawable.shirt_select))
                putString("teamName2", intent.getStringExtra("teamName2"))

                putInt("shirtTeam3", intent.getIntExtra("shirtTeam3", R.drawable.shirt_select))
                putString("teamName3", intent.getStringExtra("teamName3"))

                putInt("shirtTeam4", intent.getIntExtra("shirtTeam4", R.drawable.shirt_select))
                putString("teamName4", intent.getStringExtra("teamName4"))

                putInt("shirtTeam5", intent.getIntExtra("shirtTeam5", R.drawable.shirt_select))
                putString("teamName5", intent.getStringExtra("teamName5"))

                putInt("shirtTeam6", intent.getIntExtra("shirtTeam6", R.drawable.shirt_select))
                putString("teamName6", intent.getStringExtra("teamName6"))

                putInt("shirtTeam7", intent.getIntExtra("shirtTeam7", R.drawable.shirt_select))
                putString("teamName7", intent.getStringExtra("teamName7"))

                putInt("shirtTeam8", intent.getIntExtra("shirtTeam8", R.drawable.shirt_select))
                putString("teamName8", intent.getStringExtra("teamName8"))

            }

            findNavController(R.id.bottomFragment).navigate(R.id.action_lastGamesFragment_to_tournamentFragment, bundle)
        }
    }

    private val receiverToLastMatchesPage : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            findNavController(R.id.bottomFragment).navigate(R.id.action_tournamentFragment_to_lastGamesFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}