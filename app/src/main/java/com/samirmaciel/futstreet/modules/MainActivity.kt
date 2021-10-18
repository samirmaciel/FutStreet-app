package com.samirmaciel.futstreet.modules

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding get() = _binding!!

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

    private fun histoyIsEmpty(isEmpity : Boolean){
        if(isEmpity){
            //binding.textHintEmptyHistory.visibility = View.VISIBLE
        }else{
            //binding.textHintEmptyHistory.visibility = View.GONE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}