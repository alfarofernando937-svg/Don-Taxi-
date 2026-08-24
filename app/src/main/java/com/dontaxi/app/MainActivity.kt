package com.dontaxi.app

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.net.URLEncoder

class MainActivity : Activity() {

    private val whatsapp = "50370838437"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mostrarMenu()
    }

    private fun mostrarMenu() {

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

        val solicitar = Button(this)
        solicitar.text = "🚕 Solicitar taxi"
        solicitar.setOnClickListener {
            mostrarSolicitud()
        }
        pantalla.addView(solicitar)

        val programar = Button(this)
        programar.text = "📅 Programar viaje"
        programar.setOnClickListener {
            mostrarProgramarViaje()
        }
        pantalla.addView(programar)

        agregarBoton(pantalla, "🛒 Compra en supermercado")
        agregarBoton(pantalla, "💳 Pago de recibos")
        agregarBoton(pantalla, "💰 Cotizar viaje")
        agregarBoton(pantalla, "📞 Contactar a Don Taxi")

        setContentView(pantalla)
    }

    private fun mostrarSolicitud() {

        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.setPadding(30, 30, 30, 30)

        val titulo = TextView(this)
        titulo.text = "🚕 Solicitar taxi"
        titulo.textSize = 28f
        titulo.gravity = Gravity.CENTER
        pantalla.addView(titulo)

        val origen = EditText(this)
        origen.hint = "¿Dónde te recogemos?"
        pantalla.addView(origen)

        val destino = EditText(this)
        destino.hint = "¿A dónde vas?"
        pantalla.addView(destino)

        val enviar = Button(this)
        enviar.text = "📲 Enviar solicitud por WhatsApp"

        enviar.setOnClickListener {

            val lugarOrigen = origen.text.toString().trim()
            val lugarDestino = destino.text.toString().trim()

            if (lugarOrigen.isEmpty() || lugarDestino.isEmpty()) {

                Toast.makeText(
                    this,
                    "Completa origen y destino",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val mensaje = """
                    🚕 SOLICITUD DE TAXI - DON TAXI
                    
                    📍 Origen:
                    $lugarOrigen
                    
                    🏁 Destino:
                    $lugarDestino
                """.trimIndent()

                abrirWhatsApp(mensaje)
            }
        }

        pantalla.addView(enviar)

        agregarBotonVolver(pantalla)

        setContentView(pantalla)
    }

    private fun mostrarProgramarViaje() {

        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.setPadding(30, 30, 30, 30)

        val titulo = TextView(this)
        titulo.text = "📅 Programar viaje"
        titulo.textSize = 28f
        titulo.gravity = Gravity.CENTER
        pantalla.addView(titulo)

        val origen = EditText(this)
        origen.hint = "¿Dónde te recogemos?"
        pantalla.addView(origen)

        val destino = EditText(this)
        destino.hint = "¿A dónde vas?"
        pantalla.addView(destino)

        val fecha = EditText(this)
        fecha.hint = "Fecha del viaje (ej. 25/08/2026)"
        pantalla.addView(fecha)

        val hora = EditText(this)
        hora.hint = "Hora del viaje (ej. 8:00 AM)"
        pantalla.addView(hora)

        val enviar = Button(this)
        enviar.text = "📲 Programar por WhatsApp"

        enviar.setOnClickListener {

            val lugarOrigen = origen.text.toString().trim()
            val lugarDestino = destino.text.toString().trim()
            val fechaViaje = fecha.text.toString().trim()
            val horaViaje = hora.text.toString().trim()

            if (
                lugarOrigen.isEmpty() ||
                lugarDestino.isEmpty() ||
                fechaViaje.isEmpty() ||
                horaViaje.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Completa todos los datos",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val mensaje = """
                    📅 VIAJE PROGRAMADO - DON TAXI
                    
                    📍 Origen:
                    $lugarOrigen
                    
                    🏁 Destino:
                    $lugarDestino
                    
                    📅 Fecha:
                    $fechaViaje
                    
                    🕐 Hora:
                    $horaViaje
                """.trimIndent()

                abrirWhatsApp(mensaje)
            }
        }

        pantalla.addView(enviar)

        agregarBotonVolver(pantalla)

        setContentView(pantalla)
    }

    private fun abrirWhatsApp(mensaje: String) {

        try {

            val texto = URLEncoder.encode(
                mensaje,
                "UTF-8"
            )

            val url = "https://wa.me/$whatsapp?text=$texto"

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )

            startActivity(intent)

        } catch (e: Exception) {

            Toast.makeText(
                this,
                "No se pudo abrir WhatsApp",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun agregarBotonVolver(
        pantalla: LinearLayout
    ) {

        val volver = Button(this)
        volver.text = "Volver"

        volver.setOnClickListener {
            mostrarMenu()
        }

        pantalla.addView(volver)
    }

    private fun agregarBoton(
        pantalla: LinearLayout,
        texto: String
    ) {

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