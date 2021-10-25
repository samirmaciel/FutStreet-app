package com.samirmaciel.futstreet.modules.tournamentMatchSettings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R

class TournamentMatchSettingsViewModel : ViewModel() {

    var shirtTeam1 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam2 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)

    var teamName1 : MutableLiveData<String> = MutableLiveData("Time1")
    var teamName2 : MutableLiveData<String> = MutableLiveData("Time2")

    var roundsOfPlay : MutableLiveData<Int> = MutableLiveData(1)
    var timeForRound : MutableLiveData<Int> = MutableLiveData(0)
}