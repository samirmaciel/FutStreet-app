package com.samirmaciel.futstreet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("DEBUGTESTE", "MAIN onCreate: ")
        val navController = findNavController(R.id.fragment)
    }

    override fun onResume() {
        super.onResume()
        Log.d("DEBUGTESTE", "MAIN onResume: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("DEBUGTESTE", "MAIN onStop: ")
    }
}