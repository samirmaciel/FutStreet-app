package com.samirmaciel.futstreet.modules.gameReady

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class GameReadyViewModel : ViewModel(){


    var scoreTeamOne : MutableLiveData<Int> = MutableLiveData(0)
    var scoreTeamTwo : MutableLiveData<Int> = MutableLiveData(0)
    var currentRound : MutableLiveData<Int> = MutableLiveData(1)
    var roundsLimit  : MutableLiveData<Int> = MutableLiveData(1)
    var timeLimit : MutableLiveData<Double> = MutableLiveData(0.0)



    fun getTimeStringFromDouble() : String{
        val resultInt = timeLimit.value!!.roundToInt()
        val minutes = resultInt %  86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60
        return makeTimeString( minutes, seconds)

    }


    private fun makeTimeString( minutes: Int, seconds: Int) : String{

        return String.format("%02d:%02d", minutes, seconds)
    }
}