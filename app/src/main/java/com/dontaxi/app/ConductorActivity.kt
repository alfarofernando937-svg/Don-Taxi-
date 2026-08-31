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
            .whereEqualTo("estado", "pendiente")
            .addSnapshotListener { snapshots, error ->

                if (error != null) {
                    mostrarMensaje("❌ Error al cargar solicitudes")
                    return@addSnapshotListener
                }

                lista.removeAllViews()

                if (snapshots == null || snapshots.isEmpty) {
                    val vacio = TextView(this)
                    vacio.text = "No hay solicitudes pendientes"
                    vacio.textSize = 18f
                    vacio.setPadding(10, 30, 10, 30)
                    lista.addView(vacio)
                    return@addSnapshotListener
                }

                for (documento in snapshots.documents) {

                    val origen =
                        documento.getString("origen") ?: "Sin origen"

                    val destino =
                        documento.getString("destino") ?: "Sin destino"

                    val tarjeta = LinearLayout(this)
                    tarjeta.orientation = LinearLayout.VERTICAL
                    tarjeta.setPadding(20, 20, 20, 20)

                    val viaje = TextView(this)
                    viaje.text =
                        "📍 Recogida: $origen\n🏁 Destino: $destino"
                    viaje.textSize = 18f
                    viaje.setTextColor(Color.BLACK)

                    val aceptar = Button(this)
                    aceptar.text = "✅ ACEPTAR VIAJE"

                    tarjeta.addView(viaje)
                    tarjeta.addView(aceptar)

                    lista.addView(tarjeta)

                    aceptar.setOnClickListener {

                        aceptarViaje(
                            documento.id,
                            aceptar
                        )
                    }
                }
            }
    }

    private fun aceptarViaje(
        idSolicitud: String,
        boton: Button
    ) {

        boton.isEnabled = false
        boton.text = "⏳ ACEPTANDO..."

        db.collection("solicitudes")
            .document(idSolicitud)
            .update(
                "estado",
                "aceptado"
            )
            .addOnSuccessListener {

                mostrarMensaje("🚕 Viaje aceptado")

                boton.text = "✅ VIAJE ACEPTADO"
            }
            .addOnFailureListener {

                boton.isEnabled = true
                boton.text = "✅ ACEPTAR VIAJE"

                mostrarMensaje(
                    "❌ No se pudo aceptar el viaje"
                )
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