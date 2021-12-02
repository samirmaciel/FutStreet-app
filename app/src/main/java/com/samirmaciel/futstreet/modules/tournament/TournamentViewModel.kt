package com.samirmaciel.futstreet.modules.tournament

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.shared.const.*
import com.samirmaciel.futstreet.shared.model.Team
import com.samirmaciel.futstreet.shared.model.TournamentMatch
import kotlin.math.roundToInt

class TournamentViewModel : ViewModel() {

    var isRunningMatch = false

    //Teams
    var team1 : MutableLiveData<Team> = MutableLiveData(Team(name = "team1", shirt = R.drawable.shirt_select ))
    var team2 : MutableLiveData<Team> = MutableLiveData(Team(name = "team2", shirt = R.drawable.shirt_select ))
    var team3 : MutableLiveData<Team> = MutableLiveData(Team(name = "team3", shirt = R.drawable.shirt_select ))
    var team4 : MutableLiveData<Team> = MutableLiveData(Team(name = "team4", shirt = R.drawable.shirt_select ))
    var team5 : MutableLiveData<Team> = MutableLiveData(Team(name = "team5", shirt = R.drawable.shirt_select ))
    var team6 : MutableLiveData<Team> = MutableLiveData(Team(name = "team6", shirt = R.drawable.shirt_select ))
    var team7 : MutableLiveData<Team> = MutableLiveData(Team(name = "team7", shirt = R.drawable.shirt_select ))
    var team8 : MutableLiveData<Team> = MutableLiveData(Team(name = "team8", shirt = R.drawable.shirt_select ))

    //Quarters Matchs
    var matchQ1 : MutableLiveData<TournamentMatch> = MutableLiveData()
    var matchQ2 : MutableLiveData<TournamentMatch> = MutableLiveData()
    var matchQ3 : MutableLiveData<TournamentMatch> = MutableLiveData()
    var matchQ4 : MutableLiveData<TournamentMatch> = MutableLiveData()

    //Semi matchs
    var matchS1 : MutableLiveData<TournamentMatch> = MutableLiveData()
    var matchS2 : MutableLiveData<TournamentMatch> = MutableLiveData()

    //Final match
    var matchF : MutableLiveData<TournamentMatch> = MutableLiveData()

    //Match QUARTERS state
    var matchStateQ1 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)
    var matchStateQ2 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)
    var matchStateQ3 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)
    var matchStateQ4 : MutableLiveData<Int> = MutableLiveData(MATCH_READY)

    //Match SEMI state
    var matchStateS1 : MutableLiveData<Int> = MutableLiveData(MATCH_WAITING)
    var matchStateS2 : MutableLiveData<Int> = MutableLiveData(MATCH_WAITING)

    //Match FINAL state
    var matchStateF1 : MutableLiveData<Int> = MutableLiveData(MATCH_WAITING)

    var tournamentTimeLimit : Double = 0.0

    // Match Ready
    var nameTeam1MR : MutableLiveData<String> = MutableLiveData("TeamOne")
    var nameTeam2MR : MutableLiveData<String> = MutableLiveData("TeamTwo")
    var shirtTeam1MR : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam2MR : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var scoreTeam1 : MutableLiveData<Int> = MutableLiveData(0)
    var scoreTeam2 : MutableLiveData<Int> = MutableLiveData(0)
    var currentRound : MutableLiveData<Int> = MutableLiveData(1)
    var roundsLimit  : MutableLiveData<Int> = MutableLiveData(1)
    var timeLimit : MutableLiveData<Double> = MutableLiveData(tournamentTimeLimit)
    var timeLimitParams : MutableLiveData<Double> = MutableLiveData(0.0)
    var textTimeView : MutableLiveData<String> = MutableLiveData("00:00")
    var gameState : MutableLiveData<Int> = MutableLiveData(PREPLAY)

    // Match current on MATCHREADY
    var currentMatchRunning : MutableLiveData<MutableLiveData<TournamentMatch>> = MutableLiveData()

    fun addTeamsSortedToMatchsQuarters(){

        val teams = getTeams()
        teams.shuffle()
        val matchs = getQuartersMatchs()
        val matchStatus = getQuartersMatchsStatus()
        var teamOneIndice = 0
        var teamTwoIndice = 1

        for(i in matchs.indices){

            matchs[i].value = TournamentMatch(
                winner = 0,
                nameTeamOne = teams[teamOneIndice].name,
                nameTeamTwo = teams[teamTwoIndice].name,
                shirtTeamOne = teams[teamOneIndice].shirt,
                shirtTeamTwo = teams[teamTwoIndice].shirt,
                status = matchStatus[i]
            )
            teamOneIndice = teamOneIndice + 2
            teamTwoIndice = teamTwoIndice + 2
        }
    }

    fun getTeams() : MutableList<Team>{
        return mutableListOf(
            team1.value!!,
            team2.value!!,
            team3.value!!,
            team4.value!!,
            team5.value!!,
            team6.value!!,
            team7.value!!,
            team8.value!!,
        )
    }

    fun getQuartersMatchsStatus() : List<MutableLiveData<Int>>{
        return listOf(
            matchStateQ1,
            matchStateQ2,
            matchStateQ3,
            matchStateQ4
        )
    }

    fun getQuartersMatchs() : List<MutableLiveData<TournamentMatch>>{
        return listOf(
            matchQ1,
            matchQ2,
            matchQ3,
            matchQ4
        )
    }

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

    fun getListOfShirtsResourceIDs() : Map<Int, Int>{
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