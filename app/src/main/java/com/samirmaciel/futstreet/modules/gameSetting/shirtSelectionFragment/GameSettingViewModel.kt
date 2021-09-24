package com.samirmaciel.futstreet.modules.gameSetting.shirtSelectionFragment

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R

class GameSettingViewModel : ViewModel() {


    fun getListOfShirts() : Map<Int, Int>{
        return mapOf(
            101 to R.drawable.shirt_blue,
            202 to R.drawable.shirt_red,
            303 to R.drawable.shirt_orange,
            404 to R.drawable.shirt_brown,
            505 to R.drawable.shirt_black,
            606 to R.drawable.shirt_pink,
            707 to R.drawable.shirt_green,
            808 to R.drawable.shirt_yellow
        )
    }
}