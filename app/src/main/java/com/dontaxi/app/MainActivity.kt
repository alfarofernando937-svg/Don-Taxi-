package com.dontaxi.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.gravity = Gravity.CENTER
        pantalla.setPadding(30, 30, 30, 30)

        val titulo = TextView(this)
        titulo.text = "🚕 DON TAXI"
        titulo.textSize = 32f
        titulo.gravity = Gravity.CENTER
        titulo.setTextColor(Color.BLACK)

        pantalla.addView(titulo)

        val bienvenida = TextView(this)
        bienvenida.text = "¿Qué servicio necesitas?"
        bienvenida.textSize = 20f
        bienvenida.gravity = Gravity.CENTER
        bienvenida.setPadding(0, 30, 0, 30)

        pantalla.addView(bienvenida)

        agregarBoton(pantalla, "🚕 Solicitar taxi")
        agregarBoton(pantalla, "📅 Programar viaje")
        agregarBoton(pantalla, "🛒 Compra en supermercado")
        agregarBoton(pantalla, "💳 Pago de recibos")
        agregarBoton(pantalla, "💰 Cotizar viaje")
        agregarBoton(pantalla, "📞 Contactar a Don Taxi")

        setContentView(pantalla)
    }

    private fun agregarBoton(pantalla: LinearLayout, texto: String) {
        val boton = Button(this)
        boton.text = texto
        boton.textSize = 16f

        boton.setOnClickListener {
            Toast.makeText(
                this,
                "$texto - Próximamente",
                Toast.LENGTH_SHORT
            ).show()
        }

        pantalla.addView(boton)
    }
}