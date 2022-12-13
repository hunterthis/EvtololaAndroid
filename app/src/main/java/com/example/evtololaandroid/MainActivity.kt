package com.example.evtololaandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pilotbutton = findViewById<Button>(R.id.pilotbutton)

            pilotbutton.setOnClickListener{
                val intent = Intent(this@MainActivity, PilotMapActivity::class.java)
                startActivity(intent)
        }
        val passengerbutton = findViewById<Button>(R.id.passengerbutton)
        passengerbutton.setOnClickListener{
            val intent = Intent(this@MainActivity, PassengerMapActivity::class.java)
            startActivity(intent)
        }
    }
}