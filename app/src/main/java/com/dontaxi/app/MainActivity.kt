package com.don.taxi.app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.gravity = Gravity.CENTER
        pantalla.setPadding(40, 40, 40, 40)
        pantalla.setBackgroundColor(Color.WHITE)

        val titulo = TextView(this)
        titulo.text = "🚕 DON TAXI"
        titulo.textSize = 32f
        titulo.setTextColor(Color.BLACK)
        titulo.gravity = Gravity.CENTER

        val subtitulo = TextView(this)
        subtitulo.text = "Tu servicio de confianza"
        subtitulo.textSize = 18f
        subtitulo.gravity = Gravity.CENTER

        val cliente = Button(this)
        cliente.text = "👤 SOY CLIENTE"

        val conductor = Button(this)
        conductor.text = "🚕 SOY CONDUCTOR"

        val administrador = Button(this)
        administrador.text = "👑 ADMINISTRADOR"

        pantalla.addView(titulo)
        pantalla.addView(subtitulo)

        agregarEspacio(pantalla, 30)

        pantalla.addView(cliente)
        agregarEspacio(pantalla, 15)

        pantalla.addView(conductor)
        agregarEspacio(pantalla, 15)

        pantalla.addView(administrador)

        setContentView(pantalla)

        cliente.setOnClickListener {
            mostrarMensaje("Área de cliente")
        }

        cliente.setOnClickListener {
    val intent = android.content.Intent(this, ClienteActivity::class.java)
    startActivity(intent)
}

        administrador.setOnClickListener {
            mostrarMensaje("Área de administrador")
        }
    }

    private fun agregarEspacio(layout: LinearLayout, espacio: Int) {
        val espacioView = TextView(this)
        espacioView.height = espacio
        layout.addView(espacioView)
    }

    private fun mostrarMensaje(mensaje: String) {
        android.widget.Toast.makeText(
            this,
            mensaje,
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}