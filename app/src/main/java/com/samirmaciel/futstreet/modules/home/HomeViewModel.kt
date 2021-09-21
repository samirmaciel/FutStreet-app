package com.samirmaciel.futstreet.modules.home

import androidx.lifecycle.ViewModel
import com.samirmaciel.futstreet.R

class HomeViewModel : ViewModel() {



    fun getDescriptions() : MutableList<Int>{
        return arrayListOf(
            R.string.description_one,
            R.string.description_two,
            R.string.description_three
        )
    }
}