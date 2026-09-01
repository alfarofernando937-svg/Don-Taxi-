package com.don.taxi.app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ConductorActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null
    private lateinit var lista: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.setPadding(30, 30, 30, 30)
        pantalla.setBackgroundColor(Color.WHITE)

        val titulo = TextView(this)
        titulo.text = "🚕 ÁREA DE CONDUCTOR"
        titulo.textSize = 28f
        titulo.setTextColor(Color.BLACK)
        titulo.gravity = Gravity.CENTER
        titulo.setPadding(0, 0, 0, 30)

        lista = LinearLayout(this)
        lista.orientation = LinearLayout.VERTICAL

        val volver = Button(this)
        volver.text = "🔙 VOLVER"

        pantalla.addView(titulo)
        pantalla.addView(lista)
        pantalla.addView(volver)

        setContentView(pantalla)

        volver.setOnClickListener {
            finish()
        }

        escucharSolicitudes()
    }

    private fun escucharSolicitudes() {

        listener = db.collection("solicitudes")
            .addSnapshotListener { snapshots, error ->

                if (error != null) {
                    mostrarMensaje("❌ Error: ${error.message}")
                    return@addSnapshotListener
                }

                lista.removeAllViews()

                if (snapshots == null || snapshots.isEmpty) {
                    val vacio = TextView(this)
                    vacio.text = "No hay solicitudes disponibles"
                    vacio.textSize = 18f
                    vacio.setTextColor(Color.BLACK)
                    vacio.gravity = Gravity.CENTER
                    vacio.setPadding(10, 30, 10, 30)

                    lista.addView(vacio)
                    return@addSnapshotListener
                }

                for (documento in snapshots.documents) {

                    val origen =
                        documento.getString("origen") ?: "Sin origen"

                    val destino =
                        documento.getString("destino") ?: "Sin destino"

                    val estadoOriginal =
                        documento.getString("estado") ?: "sin estado"

                    val estado =
                        estadoOriginal.trim().lowercase()

                    val viaje = TextView(this)

                    viaje.text =
                        "📍 Recogida: $origen\n" +
                        "🏁 Destino: $destino\n" +
                        "📌 Estado: $estadoOriginal"

                    viaje.textSize = 18f
                    viaje.setTextColor(Color.BLACK)
                    viaje.setPadding(10, 20, 10, 10)

                    lista.addView(viaje)

                    if (estado == "pendiente") {

                        val aceptar = Button(this)
                        aceptar.text = "✅ ACEPTAR VIAJE"

                        lista.addView(aceptar)

                        aceptar.setOnClickListener {

                            aceptar.isEnabled = false
                            aceptar.text = "⏳ ACEPTANDO..."

                            documento.reference
                                .update("estado", "aceptado")
                                .addOnSuccessListener {

                                    aceptar.text = "✅ VIAJE ACEPTADO"

                                    mostrarMensaje(
                                        "🚕 Viaje aceptado correctamente"
                                    )
                                }
                                .addOnFailureListener { e ->

                                    aceptar.isEnabled = true
                                    aceptar.text = "✅ ACEPTAR VIAJE"

                                    mostrarMensaje(
                                        "❌ Error: ${e.message}"
                                    )
                                }
                        }
                    }

                    val separador = TextView(this)
                    separador.text = "━━━━━━━━━━━━━━━━"
                    separador.gravity = Gravity.CENTER
                    separador.setPadding(0, 10, 0, 10)

                    lista.addView(separador)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.remove()
    }

    private fun mostrarMensaje(mensaje: String) {

        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }
}