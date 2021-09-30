package com.samirmaciel.futstreet.shared.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.samirmaciel.futstreet.MainActivity
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.shared.const.CHANNEL_ID
import com.samirmaciel.futstreet.shared.const.NOTIFICATION_ID
import java.util.*
import kotlin.math.roundToInt

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
        createNotificationChannel()
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
                callNotification(getTimeStringFromDouble(timeLimit))
                sendBroadcast(intentUpdate)
                Log.d("TIMESERVICE", "run: " + timeLimit)
            }
        }
    }

    private fun callNotification(notificationString : String){

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        val pedingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        var notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_sports_soccer_24)
            .setContentTitle("FutStreet")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(pedingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentText(notificationString)
        startForeground(NOTIFICATION_ID, notification.build())
    }


    @SuppressLint("WrongConstant")
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "NotificationChannel"
            val descriptionText = "Canal de notificações"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager : NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getTimeStringFromDouble(time : Double) : String{
        val resultInt = time.roundToInt()
        val minutes = resultInt %  86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString( minutes, seconds)

    }

    private fun makeTimeString( minutes: Int, seconds: Int) : String{

        return String.format("%02d:%02d", minutes, seconds)
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        timer.cancel()
        Log.d("DEBUGTESTE", "SERVICE onDestroy: ")
    }
}