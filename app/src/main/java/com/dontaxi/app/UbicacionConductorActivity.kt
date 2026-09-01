package com.don.taxi.app

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.firebase.firestore.FirebaseFirestore

class UbicacionConductorActivity : AppCompatActivity() {

    private lateinit var proveedorUbicacion: FusedLocationProviderClient
    private lateinit var estado: TextView

    private val db = FirebaseFirestore.getInstance()

    private val callback = object : LocationCallback() {

        override fun onLocationResult(resultado: LocationResult) {

            val ubicacion = resultado.lastLocation

            if (ubicacion != null) {
                guardarUbicacion(ubicacion)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            proveedorUbicacion =
                LocationServices.getFusedLocationProviderClient(this)

            crearPantalla()

        } catch (e: Exception) {

            Toast.makeText(
                this,
                "❌ Error al iniciar ubicación: ${e.message}",
                Toast.LENGTH_LONG
            ).show()

            return
        }
    }

    private fun crearPantalla() {

        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.setPadding(30, 30, 30, 30)

        estado = TextView(this)
        estado.text = "📍 Ubicación detenida"
        estado.textSize = 20f
        estado.setPadding(0, 0, 0, 30)

        val iniciar = Button(this)
        iniciar.text = "📍 COMPARTIR UBICACIÓN"

        val detener = Button(this)
        detener.text = "⛔ DETENER UBICACIÓN"

        val volver = Button(this)
        volver.text = "🔙 VOLVER"

        pantalla.addView(estado)
        pantalla.addView(iniciar)
        pantalla.addView(detener)
        pantalla.addView(volver)

        setContentView(pantalla)

        iniciar.setOnClickListener {
            comprobarPermiso()
        }

        detener.setOnClickListener {
            detenerUbicacion()
        }

        volver.setOnClickListener {
            finish()
        }
    }

    private fun comprobarPermiso() {

        try {

            val permisoFino = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            val permisoAproximado = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            if (
                permisoFino != PackageManager.PERMISSION_GRANTED &&
                permisoAproximado != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    100
                )

                return
            }

            iniciarUbicacion()

        } catch (e: Exception) {

            mostrarMensaje(
                "❌ Error de permisos: ${e.message}"
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        resultados: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            resultados
        )

        if (requestCode == 100) {

            if (
                resultados.isNotEmpty() &&
                resultados.any {
                    it == PackageManager.PERMISSION_GRANTED
                }
            ) {

                iniciarUbicacion()

            } else {

                mostrarMensaje(
                    "❌ Don Taxi necesita permiso de ubicación"
                )
            }
        }
    }

    private fun iniciarUbicacion() {

        try {

            val permisoFino = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            val permisoAproximado = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            if (
                permisoFino != PackageManager.PERMISSION_GRANTED &&
                permisoAproximado != PackageManager.PERMISSION_GRANTED
            ) {

                mostrarMensaje(
                    "❌ No tenemos permiso de ubicación"
                )

                return
            }

            val solicitud = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                5000L
            )
                .setMinUpdateIntervalMillis(3000L)
                .build()

            proveedorUbicacion.requestLocationUpdates(
                solicitud,
                callback,
                mainLooper
            )

            estado.text = "🟢 Compartiendo ubicación"

            mostrarMensaje(
                "📍 Ubicación activada"
            )

        } catch (e: SecurityException) {

            mostrarMensaje(
                "❌ Android bloqueó el GPS: ${e.message}"
            )

        } catch (e: Exception) {

            mostrarMensaje(
                "❌ Error al iniciar GPS: ${e.message}"
            )
        }
    }

    private fun detenerUbicacion() {

        try {

            proveedorUbicacion.removeLocationUpdates(
                callback
            )

            estado.text = "⛔ Ubicación detenida"

            mostrarMensaje(
                "Ubicación detenida"
            )

        } catch (e: Exception) {

            mostrarMensaje(
                "❌ Error: ${e.message}"
            )
        }
    }

    private fun guardarUbicacion(
        ubicacion: Location
    ) {

        val datos = hashMapOf(
            "latitud" to ubicacion.latitude,
            "longitud" to ubicacion.longitude,
            "fecha" to System.currentTimeMillis()
        )

        db.collection("conductores")
            .document("conductor_actual")
            .set(datos)
            .addOnFailureListener { error ->

                mostrarMensaje(
                    "❌ Error Firebase: ${error.message}"
                )
            }
    }

    override fun onDestroy() {

        try {
            proveedorUbicacion.removeLocationUpdates(callback)
        } catch (_: Exception) {
        }

        super.onDestroy()
    }

    private fun mostrarMensaje(mensaje: String) {

        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }
}