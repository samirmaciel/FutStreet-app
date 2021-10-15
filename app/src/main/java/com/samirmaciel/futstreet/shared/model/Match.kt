package com.samirmaciel.futstreet.shared.model

data class Match(

    val id : Long = 0,
    val winner : Int,
    val nameTeamOne : String,
    val nameTeamTwo : String,
    val scoreTeamOne : Int,
    val scoreTeamTwo : Int,
    val shirtTeamOne : Int,
    val shirtTeamTwo : Int,
    val rounds : Int,
    val time : Double

)
