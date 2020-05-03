/* Copyright 2020 Matthew Coughlin */

package dev.mattcoughlin.concoctioncrafter

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import dev.mattcoughlin.concoctioncrafter.MainActivity.Companion.NOTIFICATION_MESSAGE
import dev.mattcoughlin.concoctioncrafter.MainActivity.Companion.NOTIFICATION_TITLE


class HopTimerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("Test", "Hop timer onReceive called")
        val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java) as NotificationManager

        if (intent != null) {
            Log.d("Test", "Intent was not null, sending notification")
            val title: String = intent.getStringExtra(NOTIFICATION_TITLE) as String
            val message: String = intent.getStringExtra(NOTIFICATION_MESSAGE) as String
            notificationManager.sendNotification(
                    title,
                    message,
                    context.getString(R.string.hop_channel_name),
                    context)
        }
    }
}