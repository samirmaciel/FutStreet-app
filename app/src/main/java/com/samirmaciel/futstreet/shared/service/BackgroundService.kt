package com.samirmaciel.futstreet.shared.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*

class BackgroundService : Service(){

    private val timer = Timer()

    var timeLimit : Double = 0.0

    var scoreTeamOne : Int = 0
    var scoreTeamTwo : Int = 0

    var currentRound : Int = 1
    var roundLimit : Int = 1

    val intentUpdate = Intent(UPDATE_ALL)
    val intentTimeEnd = Intent(END_TIME)



    companion object {
        const val SCORE_T1 = "SCORE_T1"
        const val SCORE_T2 = "SCORE_T2"
        const val TIME_LIMIT = "TIME_LIMIT"
        const val CURRENT_ROUND = "CURRENT_ROUND"
        const val UPDATE_TIME = "UPDATE_TIME"
        const val UPDATE_ALL = "UPDATE_ALL"
        const val END_TIME = "END_TIME"
        const val IS_TIME_ENDED = "IS_TIME_ENDED"
    }


    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        timeLimit = intent.getDoubleExtra(TIME_LIMIT, 0.0)
        timer.scheduleAtFixedRate(TimerLine(), 0, 1000)
        Log.d("TIMESERVICE", "onStartCommand: ")
        return START_NOT_STICKY
    }

    private inner class TimerLine : TimerTask(){
        override fun run() {
            if(timeLimit == 0.0) {
                if(currentRound < roundLimit){
                    currentRound++
                }
                intentTimeEnd.putExtra(CURRENT_ROUND, currentRound)
                intentTimeEnd.putExtra(IS_TIME_ENDED, true)
                sendBroadcast(intentTimeEnd)

            }else{
                timeLimit--
                intentUpdate.putExtra(UPDATE_TIME, timeLimit)
                intentUpdate.putExtra(SCORE_T1, scoreTeamOne)
                intentUpdate.putExtra(SCORE_T2, scoreTeamTwo)
                intentUpdate.putExtra(CURRENT_ROUND, currentRound)
                sendBroadcast(intentUpdate)
                Log.d("TIMESERVICE", "run: " + timeLimit)
            }
        }
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}