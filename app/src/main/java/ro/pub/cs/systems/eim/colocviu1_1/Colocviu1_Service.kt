package ro.pub.cs.systems.eim.colocviu1_1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class Colocviu1_Service : Service() {

    private var processingThread: ProcessingThread? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(Constants.TAG, "onCreate() method was invoked")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(Constants.TAG, "onBind() method was invoked")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(Constants.TAG, "onUnbind() method was invoked")
        return true
    }

    override fun onRebind(intent: Intent?) {
        Log.d(Constants.TAG, "onRebind() method was invoked")
    }

    override fun onDestroy() {
        Log.d(Constants.TAG, "onDestroy() method was invoked")
        processingThread?.stopThread()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(Constants.TAG, "onStartCommand() method was invoked")
        val instructions : String? = intent?.getStringExtra(Constants.INSTRUCTIONS)
        processingThread = ProcessingThread(this, instructions)
        processingThread?.start()
        return START_REDELIVER_INTENT
    }

}