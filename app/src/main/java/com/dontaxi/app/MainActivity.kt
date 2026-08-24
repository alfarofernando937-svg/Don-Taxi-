package com.dontaxi.app

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val texto = TextView(this)
        texto.text = "DON TAXI\n\nBienvenido"
        texto.textSize = 28f
        texto.gravity = Gravity.CENTER

        setContentView(texto)
    }
}