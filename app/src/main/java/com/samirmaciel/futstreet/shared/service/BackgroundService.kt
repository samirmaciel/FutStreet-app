package com.samirmaciel.futstreet.shared.service

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.samirmaciel.futstreet.modules.MainActivity
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.modules.gameReady.GameReadyFragment
import com.samirmaciel.futstreet.shared.const.CHANNEL_ID
import com.samirmaciel.futstreet.shared.const.NOTIFICATION_ID
import java.util.*
import kotlin.math.roundToInt

class BackgroundService : Service(){

    private val timer = Timer()

    var isTimeEnded : Boolean = false

    var timeLimit : Double = 0.0

    var nameTeamOne : String = "TeamOne"
    var nameTeamTwo : String = "TeamTwo"

    var shirtTeamOne : Int = R.drawable.shirt_select
    var shirtTeamTwo : Int = R.drawable.shirt_select

    var scoreTeamOne : Int = 0
    var scoreTeamTwo : Int = 0

    var currentRound : Int = 1
    var roundLimit : Int = 1

    val intentUpdate = Intent(UPDATE_ALL)
    val intentTimeEnd = Intent(UPDATE_ALL)



    companion object {
        const val SCORE_T1 = "SCORE_T1"
        const val SCORE_T2 = "SCORE_T2"
        const val NAME_TEAMONE = "NAME_TEAMONE"
        const val NAME_TEAMTWO = "NAME_TEAMTWO"
        const val SHIRT_TEAMONE = "SHIRT_TEAMONE"
        const val SHIRT_TEAMTWO = "SHIRT_TEAMTWO"
        const val TIME_LIMIT = "TIME_LIMIT"
        const val ROUND_LIMIT = "ROUND_LIMIT"
        const val CURRENT_ROUND = "CURRENT_ROUND"
        const val UPDATE_TIME = "UPDATE_TIME"
        const val UPDATE_ALL = "UPDATE_ALL"
        const val IS_TIME_ENDED = "IS_TIME_ENDED"
    }



    override fun onCreate() {
        super.onCreate()
        registerReceiver(scoreObserve, IntentFilter(GameReadyFragment.SCORE_OBSERVE))

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createNotificationChannel()
        scoreTeamOne = intent.getIntExtra(SCORE_T1, 0)
        scoreTeamTwo = intent.getIntExtra(SCORE_T2, 0)
        timeLimit = intent.getDoubleExtra(TIME_LIMIT, 0.0)
        timeLimit = intent.getDoubleExtra(TIME_LIMIT, 0.0)
        roundLimit = intent.getIntExtra(ROUND_LIMIT, 1)
        currentRound = intent.getIntExtra(CURRENT_ROUND, 1)
        nameTeamOne = intent.getStringExtra(NAME_TEAMONE).toString()
        nameTeamTwo = intent.getStringExtra(NAME_TEAMTWO).toString()
        shirtTeamOne = intent.getIntExtra(SHIRT_TEAMONE, R.drawable.shirt_pink)
        shirtTeamTwo = intent.getIntExtra(SHIRT_TEAMTWO, R.drawable.shirt_pink)
        timer.scheduleAtFixedRate(TimerLine(), 0, 1000)
        return START_NOT_STICKY
    }

    private val scoreObserve : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            scoreTeamOne = intent.getIntExtra(SCORE_T1, scoreTeamOne)
            scoreTeamTwo = intent.getIntExtra(SCORE_T2, scoreTeamTwo)
        }

    }

    private inner class TimerLine : TimerTask(){
        override fun run() {
            if(timeLimit == 0.0) {
                if(currentRound == roundLimit){
                    callNotification(resources.getText(R.string.text_match_finish) as String, nameTeamOne, nameTeamTwo, scoreTeamOne, scoreTeamTwo, shirtTeamOne, shirtTeamTwo)
                }
                if(!isTimeEnded){
                    if(currentRound < roundLimit){
                        currentRound++
                        isTimeEnded = true
                    }
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
                callNotification(getTimeStringFromDouble(timeLimit), nameTeamOne, nameTeamTwo, scoreTeamOne, scoreTeamTwo, shirtTeamOne, shirtTeamTwo)
                sendBroadcast(intentUpdate)
            }
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun callNotification(time : String, nameTeamOne : String, nameTeamTwo : String, scoreTeamOne : Int, scoreTeamTwo : Int, shirtTeamOne : Int, shirtTeamTwo : Int){

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        val pedingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notificationLayout = RemoteViews(packageName, R.layout.notification_layout)
        notificationLayout.setTextViewText(R.id.textTime, time)
        notificationLayout.setTextViewText(R.id.textTeamOneName, nameTeamOne)
        notificationLayout.setTextViewText(R.id.textTeamTwoName, nameTeamTwo)
        notificationLayout.setImageViewResource(R.id.shirtTeamOne, shirtTeamOne)
        notificationLayout.setImageViewResource(R.id.shirtTeamTwo, shirtTeamTwo)
        notificationLayout.setTextViewText(R.id.textScore, "$scoreTeamOne X $scoreTeamTwo")

        var notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_sports_soccer_24)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(pedingIntent)
            .setColor(resources.getColor(R.color.blue))
            .setColorized(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentText(time)
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
        unregisterReceiver(scoreObserve)
        stopForeground(true)
        timer.cancel()
        Log.d("DEBUGTESTE", "SERVICE onDestroy: ")
    }
}