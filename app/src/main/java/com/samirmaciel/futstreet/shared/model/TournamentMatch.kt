package com.samirmaciel.futstreet.shared.model

import androidx.lifecycle.MutableLiveData

data class TournamentMatch(

    val id : Long = 0,
    var winner : Team?,
    val nameTeamOne : String,
    val nameTeamTwo : String,
    var scoreTeamOne : Int = 0,
    var scoreTeamTwo : Int = 0,
    val shirtTeamOne : Int,
    val shirtTeamTwo : Int,
    var status : MutableLiveData<Int>
)
