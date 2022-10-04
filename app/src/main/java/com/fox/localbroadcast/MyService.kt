package com.fox.localbroadcast

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.concurrent.thread

class MyService: Service() {

private val localBroadcastManager by lazy {
    LocalBroadcastManager.getInstance(this)
}

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            for (i in 1 .. 10) {
                Thread.sleep(1000)
                Intent(LOADED).apply {
                    putExtra(EXTRA_KEY, i * 10)
                    localBroadcastManager.sendBroadcast(this)
                }
            }
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object{
        const val LOADED = "loaded"
        const val EXTRA_KEY = "extra_key"
    }
}