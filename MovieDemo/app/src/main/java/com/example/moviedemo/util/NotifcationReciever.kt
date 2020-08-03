package com.example.moviedemo.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.example.moviedemo.R
import com.example.moviedemo.screen.main.MainActivity
import timber.log.Timber

class NotifcationReciever : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.extras?.let {
            val movieid=it.getInt("movieid")
            val title=it.getString("title")
            showNotification(p0!!,movieid,title)

        }
    }
    var i=1;
    private fun showNotification(context:Context, movieid:Int, title:String?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //PENDING INTENT FOR CLICK ON NOTIFICATION
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("startFromNotification",true)
            putExtra("movieid",movieid)
            putExtra("title",title)
            action = "myaction"

        }
        Timber.i("Send extra: ${notificationIntent.extras}")
        val contentIntent: PendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //NOTIFICATION
        val builder= NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_star_border_black_24dp)
            .setContentTitle("My notification")
            .setContentText("My content text $movieid $title")
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(longArrayOf(1000,2000,1000))
            .setChannelId("channelid")
            .setContentIntent(contentIntent);
        //CHANNEL FORN ANDROID > 8
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name = "My channel name"
            val descriptionText = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channelid", name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Register the channel with the system
        notificationManager.notify(i++, builder.build());


    }
}