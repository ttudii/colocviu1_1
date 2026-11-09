package ro.pub.cs.systems.eim.colocviu1_1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1SecondaryActivity : AppCompatActivity() {
    private var serviceStarted  = false
    private val buttonListener = ButtonListener()
    private inner class ButtonListener : View.OnClickListener {
        override fun onClick(view: View?) {
            when (view?.id) {
                R.id.register -> {
                    setResult(RESULT_OK, Intent())
                    finish()
                }
                R.id.cancel -> {
                    setResult(RESULT_CANCELED, Intent())
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_secondary)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.register).setOnClickListener(buttonListener)
        findViewById<Button>(R.id.cancel).setOnClickListener(buttonListener)

        val intent = intent
        if (intent != null) {
            val instructions = intent.getStringExtra(Constants.INSTRUCTIONS)
            findViewById<TextView>(R.id.instructions).text = instructions.toString()
        }

        if(!serviceStarted){ startService() }
    }

    private fun startService(){
        val totalClicks: Int = (findViewById<TextView>(R.id.total_clicks).text.toString()).toInt()
        if(totalClicks > Constants.THRESHOLD) {
            val instructions = (findViewById<TextView>(R.id.instructions).text.toString())
            val intent = Intent(this, Colocviu1_Service::class.java).apply {
                putExtra(Constants.INSTRUCTIONS, instructions)
            }
            startService(intent)
            serviceStarted = true
        }
    }

    override fun onDestroy() {
        val intent = Intent(this, Colocviu1_Service::class.java)

        stopService(intent)
        serviceStarted = false
        super.onDestroy()
    }
}