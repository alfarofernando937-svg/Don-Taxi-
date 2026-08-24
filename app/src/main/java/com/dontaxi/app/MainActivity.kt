package com.dontaxi.app

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 60, 40, 40)
        }

        val title = TextView(this).apply {
            text = "DON TAXI"
            textSize = 32f
        }

        val welcome = TextView(this).apply {
            text = "Bienvenido a Don Taxi"
            textSize = 20f
        }

        val requestButton = Button(this).apply {
            text = "Solicitar viaje"
        }

        layout.addView(title)
        layout.addView(welcome)
        layout.addView(requestButton)

        setContentView(layout)
    }
}