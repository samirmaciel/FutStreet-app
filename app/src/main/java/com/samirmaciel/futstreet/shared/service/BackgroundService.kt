package com.samirmaciel.futstreet.shared.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BackgroundService : Service(){

    var currentTime : Double = 0.0
    var limitTime : Double = 0.0

    var scoreTeamOne : Int = 0
    var scoreTeamTwo : Int = 0

    var currentRound : Int = 1


    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}