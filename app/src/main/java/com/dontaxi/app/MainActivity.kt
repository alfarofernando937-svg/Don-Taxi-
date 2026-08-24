package com.don.taxi.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val texto = TextView(this)
        texto.text = "DON TAXI\n\nBienvenido"
        texto.textSize = 28f
        texto.gravity = android.view.Gravity.CENTER

        setContentView(texto)
    }
}