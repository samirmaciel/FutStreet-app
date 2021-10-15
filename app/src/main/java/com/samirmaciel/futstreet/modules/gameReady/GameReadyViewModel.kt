package com.samirmaciel.futstreet.modules.gameReady

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.shared.const.PREPLAY
import com.samirmaciel.futstreet.shared.model.Match
import kotlin.math.roundToInt

class GameReadyViewModel : ViewModel(){

    var nameTeamOne : MutableLiveData<String> = MutableLiveData("TeamOne")
    var nameTeamTwo : MutableLiveData<String> = MutableLiveData("TeamTwo")
    var shirtTeamOne : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeamTwo : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var scoreTeamOne : MutableLiveData<Int> = MutableLiveData(0)
    var scoreTeamTwo : MutableLiveData<Int> = MutableLiveData(0)
    var currentRound : MutableLiveData<Int> = MutableLiveData(1)
    var roundsLimit  : MutableLiveData<Int> = MutableLiveData(1)
    var timeLimit : MutableLiveData<Double> = MutableLiveData(0.0)
    var timeLimitParams : MutableLiveData<Double> = MutableLiveData(0.0)
    var textTimeView : MutableLiveData<String> = MutableLiveData("00:00")

    var gameState : MutableLiveData<Int> = MutableLiveData(PREPLAY)



    fun getTimeStringFromDouble(time : Double) : String{
        val resultInt = time.roundToInt()
        val minutes = resultInt %  86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60
        return makeTimeString( minutes, seconds)

    }

    fun saveMatch(match : Match){
        when(match.winner){
            0 -> println("EMPATE -----------")

            1 -> println(match.nameTeamOne)

            2 -> println(match.nameTeamTwo)
        }
    }


    private fun makeTimeString( minutes: Int, seconds: Int) : String{

        return String.format("%02d:%02d", minutes, seconds)
    }
}