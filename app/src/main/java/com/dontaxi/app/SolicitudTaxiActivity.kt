package com.don.taxi.app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SolicitudTaxiActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.setPadding(30, 30, 30, 30)
        pantalla.gravity = Gravity.CENTER
        pantalla.setBackgroundColor(Color.WHITE)

        val titulo = TextView(this)
        titulo.text = "🚕 SOLICITAR TAXI"
        titulo.textSize = 28f
        titulo.setTextColor(Color.BLACK)
        titulo.gravity = Gravity.CENTER

        val ubicacion = EditText(this)
        ubicacion.hint = "📍 ¿Dónde te recogemos?"
        ubicacion.setSingleLine(true)

        val destino = EditText(this)
        destino.hint = "🏁 ¿A dónde vas?"
        destino.setSingleLine(true)

        val cotizar = Button(this)
        cotizar.text = "💰 COTIZAR VIAJE"

        val solicitar = Button(this)
        solicitar.text = "🚕 SOLICITAR TAXI"

        val volver = Button(this)
        volver.text = "🔙 VOLVER"

        pantalla.addView(titulo)
        pantalla.addView(ubicacion)
        pantalla.addView(destino)
        pantalla.addView(cotizar)
        pantalla.addView(solicitar)
        pantalla.addView(volver)

        setContentView(pantalla)

        cotizar.setOnClickListener {

            val origen = ubicacion.text.toString().trim()
            val llegada = destino.text.toString().trim()

            if (origen.isEmpty() || llegada.isEmpty()) {

                mostrarMensaje(
                    "Completa el punto de recogida y el destino"
                )

            } else {

                mostrarMensaje(
                    "Cotización solicitada"
                )
            }
        }

        solicitar.setOnClickListener {

            val origen = ubicacion.text.toString().trim()
            val llegada = destino.text.toString().trim()

            if (origen.isEmpty() || llegada.isEmpty()) {

                mostrarMensaje(
                    "Completa el punto de recogida y el destino"
                )

                return@setOnClickListener
            }

            val solicitud = hashMapOf(
                "origen" to origen,
                "destino" to llegada,
                "estado" to "pendiente",
                "fecha" to System.currentTimeMillis()
            )

            solicitar.isEnabled = false
            solicitar.text = "⏳ ENVIANDO..."

            db.collection("solicitudes")
                .add(solicitud)
                .addOnSuccessListener {

                    solicitar.isEnabled = true
                    solicitar.text = "🚕 SOLICITAR TAXI"

                    mostrarMensaje(
                        "🚕 Solicitud enviada correctamente"
                    )
                }
                .addOnFailureListener { error ->

                    solicitar.isEnabled = true
                    solicitar.text = "🚕 SOLICITAR TAXI"

                    mostrarMensaje(
                        "❌ Error: ${error.message}"
                    )
                }
        }

        volver.setOnClickListener {
            finish()
        }
    }

    private fun mostrarMensaje(mensaje: String) {

        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }
}