package com.samirmaciel.futstreet.modules.tournament

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TournamentViewModel : ViewModel() {

    //Quarters Teams
    var nameQ11 : MutableLiveData<String> = MutableLiveData()
    var shirtQ11 : MutableLiveData<Int> = MutableLiveData()
    var nameQ21 : MutableLiveData<String> = MutableLiveData()
    var shirtQ21 : MutableLiveData<Int> = MutableLiveData()

    var nameQ32 : MutableLiveData<String> = MutableLiveData()
    var shirtQ32 : MutableLiveData<Int> = MutableLiveData()
    var nameQ42 : MutableLiveData<String> = MutableLiveData()
    var shirtQ42 : MutableLiveData<Int> = MutableLiveData()

    var nameQ53 : MutableLiveData<String> = MutableLiveData()
    var shirtQ53 : MutableLiveData<Int> = MutableLiveData()
    var nameQ63 : MutableLiveData<String> = MutableLiveData()
    var shirtQ63 : MutableLiveData<Int> = MutableLiveData()

    var nameQ74 : MutableLiveData<String> = MutableLiveData()
    var shirtQ74 : MutableLiveData<Int> = MutableLiveData()
    var nameQ84 : MutableLiveData<String> = MutableLiveData()
    var shirtQ84 : MutableLiveData<Int> = MutableLiveData()



    //Semi Teams
    var nameS11 : MutableLiveData<String> = MutableLiveData()
    var shirtS11 : MutableLiveData<Int> = MutableLiveData()
    var nameS21 : MutableLiveData<String> = MutableLiveData()
    var shirtS21 : MutableLiveData<Int> = MutableLiveData()

    var nameS32 : MutableLiveData<String> = MutableLiveData()
    var shirtS32 : MutableLiveData<Int> = MutableLiveData()
    var nameS42 : MutableLiveData<String> = MutableLiveData()
    var shirtS42 : MutableLiveData<Int> = MutableLiveData()

    //Final Team
    var nameF11 : MutableLiveData<String> = MutableLiveData()
    var shirtF11 : MutableLiveData<Int> = MutableLiveData()
    var nameF21 : MutableLiveData<String> = MutableLiveData()
    var shirtF21 : MutableLiveData<Int> = MutableLiveData()

}