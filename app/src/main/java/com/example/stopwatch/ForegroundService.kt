package com.example.stopwatch

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class ForegroundService : Service() {
    private val channel_id = "ForegroundService"

    //start service and stop service
    companion object{
        fun startService(context:Context,string: String){
            val startIntent=Intent(context,ForegroundService::class.java)
            startIntent.putExtra("okayy",string)
            ContextCompat.startForegroundService(context,startIntent)
        }
        fun stopService(context: Context){
            val stopIntent=Intent(context,ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }


    //onBind
    override fun onBind(p0: Intent): IBinder? {
        return null;
    }

    //onStart
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getStringExtra("input extra")
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, channel_id)
            .setContentTitle("Foreground Service Kotlin")
            .build()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        startForegroundService(notificationIntent)
        return START_NOT_STICKY

    }
    //creating the notification Channel
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(channel_id, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

}