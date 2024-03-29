package com.samirmaciel.futstreet.modules.matchReady

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.shared.const.MATCH_FRIENDLY
import com.samirmaciel.futstreet.shared.const.PREPLAY
import com.samirmaciel.futstreet.shared.model.TournamentMatch
import kotlin.math.roundToInt

class MatchReadyViewModel : ViewModel(){

    var nameTeam1 : MutableLiveData<String> = MutableLiveData("TeamOne")
    var nameTeam2 : MutableLiveData<String> = MutableLiveData("TeamTwo")
    var shirtTeam1 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam2 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var scoreTeam1 : MutableLiveData<Int> = MutableLiveData(0)
    var scoreTeam2 : MutableLiveData<Int> = MutableLiveData(0)
    var currentRound : MutableLiveData<Int> = MutableLiveData(1)
    var roundsLimit  : MutableLiveData<Int> = MutableLiveData(1)
    var timeLimit : MutableLiveData<Double> = MutableLiveData(0.0)
    var timeLimitParams : MutableLiveData<Double> = MutableLiveData(0.0)
    var textTimeView : MutableLiveData<String> = MutableLiveData("00:00")
    var gameState : MutableLiveData<Int> = MutableLiveData(PREPLAY)
    var matchType : MutableLiveData<Int> = MutableLiveData(MATCH_FRIENDLY)

    fun getTimeStringFromDouble(time : Double) : String{
        val resultInt = time.roundToInt()
        val minutes = resultInt %  86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60
        return makeTimeString( minutes, seconds)
    }


    private fun makeTimeString( minutes: Int, seconds: Int) : String{

        return String.format("%02d:%02d", minutes, seconds)
    }
}