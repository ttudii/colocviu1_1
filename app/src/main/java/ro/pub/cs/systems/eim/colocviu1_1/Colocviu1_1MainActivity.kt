package ro.pub.cs.systems.eim.colocviu1_1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1MainActivity : AppCompatActivity() {

    private val buttonListener = ButtonListener()
    private inner class ButtonListener : View.OnClickListener {
        override fun onClick(view: View?) {
            when (view?.id) {
                R.id.north-> concatenateToText(R.id.north)
                R.id.south -> concatenateToText(R.id.south)
                R.id.west -> concatenateToText(R.id.west)
                R.id.east -> concatenateToText(R.id.east)
                R.id.secondary_activity -> navigateToSecondaryActivity()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.north).setOnClickListener(buttonListener)
        findViewById<Button>(R.id.south).setOnClickListener(buttonListener)
        findViewById<Button>(R.id.east).setOnClickListener(buttonListener)
        findViewById<Button>(R.id.west).setOnClickListener(buttonListener)
        findViewById<Button>(R.id.secondary_activity).setOnClickListener(buttonListener)
    }

    fun concatenateToText(id: Int) {
        val coord = findViewById<Button>(id).text.toString()
        Log.d(Constants.TAG, "coord: $coord")
        val text = findViewById<TextView>(R.id.nsew).text.toString()
        Log.d(Constants.TAG, "text: $text")
        val result = text.plus(" ").plus(coord)
        Log.d(Constants.TAG, "res: $result")
        findViewById<TextView>(R.id.nsew).text = result

        val totalClicks = (findViewById<TextView>(R.id.total_clicks).text.toString()).toInt() + 1
        findViewById<TextView>(R.id.total_clicks).text = totalClicks.toString()
    }

    protected override fun onSaveInstanceState(saveInstanceState: Bundle) {
        super.onSaveInstanceState(saveInstanceState)
        Log.d(Constants.TAG, "onSaveInstanceState() method invoked")

        val totalClicks: TextView = findViewById(R.id.total_clicks)
        saveInstanceState.putInt(Constants.TOTAL_CLICKS, (totalClicks.getText().toString()).toInt())
    }

    protected override fun onRestoreInstanceState(@NonNull saveInstanceState: Bundle) {
        super.onRestoreInstanceState(saveInstanceState)
        Log.d(Constants.TAG, "onRestoreInstanceState() method invoked")

        val totalClicks: TextView = findViewById(R.id.total_clicks)
        totalClicks.text = saveInstanceState.getInt(Constants.TOTAL_CLICKS).toString()
    }
    fun navigateToSecondaryActivity() {
        val instructions = findViewById<TextView>(R.id.nsew).text.toString()

        val intent = Intent(this, Colocviu1_1SecondaryActivity::class.java).apply {
            putExtra(Constants.INSTRUCTIONS, instructions)
        }
        startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            Constants.SECONDARY_ACTIVITY_REQUEST_CODE -> {
                when(resultCode) {
                    RESULT_OK -> Toast.makeText(this, "Result from intent is : RESULT_OK", Toast.LENGTH_LONG).show()
                    RESULT_CANCELED -> Toast.makeText(this, "Result from intent is : RESULT_CANCELED", Toast.LENGTH_LONG).show()
                }
                findViewById<TextView>(R.id.total_clicks).text = "0"
                findViewById<TextView>(R.id.nsew).text = ""
            }
        }
    }
}