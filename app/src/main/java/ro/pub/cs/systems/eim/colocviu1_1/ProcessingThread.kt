package ro.pub.cs.systems.eim.colocviu1_1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Process
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date

class ProcessingThread(
    private val context:Context,
    private val instructions : String?,
) : Thread() {

    @Volatile private var isRunning = true

    override fun run() {
        while (isRunning) {
            Log.d(
                Constants.TAG,
                "Thread.run() was invoked, PID: " + Process.myPid() + " TID: " + Process.myTid()
            )
            sendMessage()
            sleep()
        }
    }

    private fun sleep() {
        try {
            Thread.sleep(Constants.SLEEP_TIME)
        } catch (interruptedException: InterruptedException) {
            interruptedException.printStackTrace()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun sendMessage() {
        val intent = Intent()

        intent.setAction(Constants.ACTION1)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        intent.putExtra(Constants.DATE, currentDate)
        intent.putExtra(Constants.INSTRUCTIONS, instructions)

        intent.setPackage(context.packageName)
        context.sendBroadcast(intent)
    }

    fun stopThread() {
        isRunning = false
        interrupt()
    }
}