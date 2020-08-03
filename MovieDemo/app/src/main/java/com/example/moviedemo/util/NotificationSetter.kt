package com.example.moviedemo.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class NotificationSetter{
    companion object{
        fun setNotification(movieid: Int, title: String, context: Context, date: Date) {
            val timeNoti = Calendar.getInstance().apply {
                time = date
            }

            val intent = Intent(context, NotifcationReciever::class.java)
            intent.putExtra("movieid", movieid)
            intent.putExtra("title", title)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                movieid,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            am.setExact(AlarmManager.RTC_WAKEUP, timeNoti.timeInMillis, pendingIntent)
        }

        fun removeNotification(movieid: Int, title: String, context: Context) {
            val intent = Intent(context, NotifcationReciever::class.java)
            intent.putExtra("movieid", movieid)
            intent.putExtra("title", title)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                movieid,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(pendingIntent)
        }
    }
}