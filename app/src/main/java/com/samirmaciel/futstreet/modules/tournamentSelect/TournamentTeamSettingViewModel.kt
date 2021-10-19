package com.samirmaciel.futstreet.modules.tournamentSelect

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R

class TournamentTeamSettingViewModel : ViewModel() {

    var shirtTeam1 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam2 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam3 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam4 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam5 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam6 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam7 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeam8 : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)

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