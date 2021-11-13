package com.samirmaciel.futstreet.shared.model

import androidx.lifecycle.MutableLiveData

data class TournamentMatch(

    val id : Long = 0,
    var winner : Int,
    val nameTeamOne : String,
    val nameTeamTwo : String,
    val scoreTeamOne : Int,
    val scoreTeamTwo : Int,
    val shirtTeamOne : Int,
    val shirtTeamTwo : Int,
    val status : MutableLiveData<Int>
)