package dev.mattcoughlin.concoctioncrafter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BoilTimerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.startActivity(intent)
    }
}