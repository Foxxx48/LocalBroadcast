package com.fox.localbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.fox.localbroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val localBroadcastManager by lazy {
       LocalBroadcastManager.getInstance(this)
    }


    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding?: throw RuntimeException("ActivityMainBinding = null")

    private val reciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == MyService.LOADED) {
                val percent = intent.getIntExtra(MyService.EXTRA_KEY, 100500)
                binding.progressBar.progress = percent
                Toast.makeText(context, "Loaded $percent %", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startService(
                Intent(this,
                    MyService::class.java
                )
            )
        }

        val intentFilter = IntentFilter().apply {
            addAction(MyService.LOADED)
        }
        localBroadcastManager.registerReceiver(reciever, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        localBroadcastManager.unregisterReceiver(reciever)
    }
}