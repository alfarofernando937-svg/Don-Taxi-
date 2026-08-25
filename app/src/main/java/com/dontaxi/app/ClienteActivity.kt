package com.don.taxi.app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.gravity = Gravity.CENTER
        pantalla.setPadding(30, 30, 30, 30)
        pantalla.setBackgroundColor(Color.WHITE)

        val titulo = TextView(this)
        titulo.text = "👤 ÁREA DE CLIENTE"
        titulo.textSize = 28f
        titulo.setTextColor(Color.BLACK)
        titulo.gravity = Gravity.CENTER

        val solicitar = Button(this)
        solicitar.text = "📍 SOLICITAR TAXI"

        val programar = Button(this)
        programar.text = "📅 PROGRAMAR VIAJE"

        val cotizacion = Button(this)
        cotizacion.text = "💰 SOLICITAR COTIZACIÓN"

        val supermercado = Button(this)
        supermercado.text = "🛒 COMPRA EN SUPERMERCADO"

        val recibos = Button(this)
        recibos.text = "🧾 PAGO DE RECIBOS"

        val volver = Button(this)
        volver.text = "🔙 VOLVER"

        pantalla.addView(titulo)
        pantalla.addView(solicitar)
        pantalla.addView(programar)
        pantalla.addView(cotizacion)
        pantalla.addView(supermercado)
        pantalla.addView(recibos)
        pantalla.addView(volver)

        setContentView(pantalla)

        solicitar.setOnClickListener {
            mostrarMensaje("Solicitar taxi")
        }

        programar.setOnClickListener {
            mostrarMensaje("Programar viaje")
        }

        cotizacion.setOnClickListener {
            mostrarMensaje("Solicitar cotización")
        }

        supermercado.setOnClickListener {
            mostrarMensaje("Compra en supermercado")
        }

        recibos.setOnClickListener {
            mostrarMensaje("Pago de recibos")
        }

        volver.setOnClickListener {
            finish()
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        android.widget.Toast.makeText(
            this,
            mensaje,
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}