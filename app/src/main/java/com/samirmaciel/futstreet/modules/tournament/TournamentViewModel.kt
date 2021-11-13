package com.samirmaciel.futstreet.modules.tournament

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.shared.const.*
import com.samirmaciel.futstreet.shared.model.TournamentMatch
import kotlin.math.roundToInt

class TournamentViewModel : ViewModel() {

    //Match QUARTERS state
    var matchStateQ1 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)
    var matchStateQ2 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)
    var matchStateQ3 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)
    var matchStateQ4 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)

    //Match SEMI state
    var matchStateS1 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)
    var matchStateS2 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)

    //Match FINAL state
    var matchStateF1 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)

    var roundsOfPlay : MutableLiveData<Int> = MutableLiveData(1)
    var timeForRound : MutableLiveData<Int> = MutableLiveData(0)

    //Teams shirts color
    var shirtTeam1 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam2 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam3 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam4 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam5 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam6 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam7 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam8 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)

    // Teams names strings
    var teamName1 : MutableLiveData<String> = MutableLiveData()
    var teamName2 : MutableLiveData<String> = MutableLiveData()
    var teamName3 : MutableLiveData<String> = MutableLiveData()
    var teamName4 : MutableLiveData<String> = MutableLiveData()
    var teamName5 : MutableLiveData<String> = MutableLiveData()
    var teamName6 : MutableLiveData<String> = MutableLiveData()
    var teamName7 : MutableLiveData<String> = MutableLiveData()
    var teamName8 : MutableLiveData<String> = MutableLiveData()

    //Quarters Teams
    var nameQ11 : MutableLiveData<String> = MutableLiveData()
    var shirtQ11 : MutableLiveData<Int> = MutableLiveData()
    var nameQ21 : MutableLiveData<String> = MutableLiveData()
    var shirtQ21 : MutableLiveData<Int> = MutableLiveData()

    var nameQ32 : MutableLiveData<String> = MutableLiveData()
    var shirtQ32 : MutableLiveData<Int> = MutableLiveData()
    var nameQ42 : MutableLiveData<String> = MutableLiveData()
    var shirtQ42 : MutableLiveData<Int> = MutableLiveData()

    var nameQ53 : MutableLiveData<String> = MutableLiveData()
    var shirtQ53 : MutableLiveData<Int> = MutableLiveData()
    var nameQ63 : MutableLiveData<String> = MutableLiveData()
    var shirtQ63 : MutableLiveData<Int> = MutableLiveData()

    var nameQ74 : MutableLiveData<String> = MutableLiveData()
    var shirtQ74 : MutableLiveData<Int> = MutableLiveData()
    var nameQ84 : MutableLiveData<String> = MutableLiveData()
    var shirtQ84 : MutableLiveData<Int> = MutableLiveData()

    //Semi Teams
    var nameS11 : MutableLiveData<String> = MutableLiveData()
    var shirtS11 : MutableLiveData<Int> = MutableLiveData()
    var nameS21 : MutableLiveData<String> = MutableLiveData()
    var shirtS21 : MutableLiveData<Int> = MutableLiveData()

    var nameS32 : MutableLiveData<String> = MutableLiveData()
    var shirtS32 : MutableLiveData<Int> = MutableLiveData()
    var nameS42 : MutableLiveData<String> = MutableLiveData()
    var shirtS42 : MutableLiveData<Int> = MutableLiveData()

    //Final Team
    var nameF11 : MutableLiveData<String> = MutableLiveData()
    var shirtF11 : MutableLiveData<Int> = MutableLiveData()
    var nameF21 : MutableLiveData<String> = MutableLiveData()
    var shirtF21 : MutableLiveData<Int> = MutableLiveData()

    // Match Ready
    var nameTeam1MR : MutableLiveData<String> = MutableLiveData("TeamOne")
    var nameTeam2MR : MutableLiveData<String> = MutableLiveData("TeamTwo")
    var shirtTeam1MR : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam2MR : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var scoreTeam1 : MutableLiveData<Int> = MutableLiveData(0)
    var scoreTeam2 : MutableLiveData<Int> = MutableLiveData(0)
    var currentRound : MutableLiveData<Int> = MutableLiveData(1)
    var roundsLimit  : MutableLiveData<Int> = MutableLiveData(1)
    var timeLimit : MutableLiveData<Double> = MutableLiveData(0.0)
    var timeLimitParams : MutableLiveData<Double> = MutableLiveData(0.0)
    var textTimeView : MutableLiveData<String> = MutableLiveData("00:00")
    var gameState : MutableLiveData<Int> = MutableLiveData(PREPLAY)

    // Match current on MATCHREADY
    var currentMatchRunning : MutableLiveData<TournamentMatch> = MutableLiveData()


    fun getTimeStringFromDouble(time : Double) : String{
        val resultInt = time.roundToInt()
        val minutes = resultInt %  86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60
        return makeTimeString( minutes, seconds)
    }

    fun saveMatch(tournamentMatch : TournamentMatch){
        when(tournamentMatch.winner){
            0 -> println("EMPATE -----------")

            1 -> println(tournamentMatch.nameTeamOne)

            2 -> println(tournamentMatch.nameTeamTwo)
        }
    }

    private fun makeTimeString( minutes: Int, seconds: Int) : String{

        return String.format("%02d:%02d", minutes, seconds)
    }


    fun getMapWithQuartersNamesLiveData() : Map<Int, MutableLiveData<String>>{
        return mapOf<Int, MutableLiveData<String>>(
            0 to nameQ11,
            1 to nameQ21,
            2 to nameQ32,
            3 to nameQ42,
            4 to nameQ53,
            5 to nameQ63,
            6 to nameQ74,
            7 to nameQ84
        )
    }

    fun getMapWithQuartersShirtLiveData() : Map<Int, MutableLiveData<Int>>{
        return mapOf(
            0 to shirtQ11,
            1 to shirtQ21,
            2 to shirtQ32,
            3 to shirtQ42,
            4 to shirtQ53,
            5 to shirtQ63,
            6 to shirtQ74,
            7 to shirtQ84
        )
    }

    fun getTeamNameMap() : Map<Int, MutableLiveData<String>> {
        return mapOf(
            0 to teamName1,
            1 to teamName2,
            2 to teamName3,
            3 to teamName4,
            4 to teamName5,
            5 to teamName6,
            6 to teamName7,
            7 to teamName8
        )
    }

    fun getTeamShirtMap() : Map<Int, MutableLiveData<Int>> {
        return mapOf(
            0 to shirtTeam1,
            1 to shirtTeam2,
            2 to shirtTeam3,
            3 to shirtTeam4,
            4 to shirtTeam5,
            5 to shirtTeam6,
            6 to shirtTeam7,
            7 to shirtTeam8
        )
    }

    fun getListOfShirts() : Map<Int, Int>{
        return mapOf(
            101 to R.drawable.shirt_blue,
            202 to R.drawable.shirt_red,
            303 to R.drawable.shirt_orange,
            404 to R.drawable.shirt_brown,
            505 to R.drawable.shirt_black,
            606 to R.drawable.shirt_pink,
            707 to R.drawable.shirt_green,
            808 to R.drawable.shirt_yellow,
            909 to R.drawable.shirt_select
        )
    }

}