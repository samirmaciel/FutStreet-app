package com.samirmaciel.futstreet.modules.tournamentSelect

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R

class TournamentTeamSettingViewModel : ViewModel() {
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