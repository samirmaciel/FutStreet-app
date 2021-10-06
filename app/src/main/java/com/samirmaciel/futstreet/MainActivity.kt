package com.samirmaciel.futstreet

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.samirmaciel.futstreet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val navController = findNavController(R.id.fragment)
    }

    private fun histoyIsEmpty(isEmpity : Boolean){
        if(isEmpity){
            binding.textHintEmptyHistory.visibility = View.VISIBLE
        }else{
            binding.textHintEmptyHistory.visibility = View.GONE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}