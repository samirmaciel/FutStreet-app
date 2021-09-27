package com.samirmaciel.futstreet.modules.gameSetting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.shared.const.SELECT_SHIRT

class GameSettingViewModel : ViewModel() {

    var shirtTeamOne : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)
    var shirtTeamTwo : MutableLiveData<Int> = MutableLiveData(R.drawable.shirt_select)

    var nameTeamOne : MutableLiveData<String> = MutableLiveData()
    var nameTeamTwo : MutableLiveData<String> = MutableLiveData()

    var roundsOfPlay : MutableLiveData<Int> = MutableLiveData(1)
    var timeForRound : MutableLiveData<Double> = MutableLiveData(0.0)


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