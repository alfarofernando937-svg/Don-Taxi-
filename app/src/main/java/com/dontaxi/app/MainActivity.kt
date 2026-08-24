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

        agregarBoton(pantalla, "🚕 Solicitar taxi") { mostrarSolicitud() }
        agregarBoton(pantalla, "📅 Programar viaje") { mostrarProgramarViaje() }
        agregarBoton(pantalla, "💰 Cotizar viaje") { mostrarCotizacion() }
        agregarBoton(pantalla, "🛒 Compra en supermercado") { mostrarSupermercado() }
        agregarBoton(pantalla, "💳 Pago de recibos") { mostrarPagoRecibos() }
        agregarBoton(pantalla, "📞 Contactar a Don Taxi") { contactarWhatsApp() }

        setContentView(pantalla)
    }

    private fun mostrarSolicitud() {
        val pantalla = crearPantalla("🚕 Solicitar taxi")

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
                Toast.makeText(this, "Completa origen y destino", Toast.LENGTH_SHORT).show()
            } else {
                abrirWhatsApp("""
                    🚕 SOLICITUD DE TAXI - DON TAXI
                    
                    📍 Origen:
                    $lugarOrigen
                    
                    🏁 Destino:
                    $lugarDestino
                """.trimIndent())
            }
        }

        pantalla.addView(enviar)
        agregarBotonVolver(pantalla)
        setContentView(pantalla)
    }

    private fun mostrarProgramarViaje() {
        val pantalla = crearPantalla("📅 Programar viaje")

        val origen = EditText(this)
        origen.hint = "¿Dónde te recogemos?"
        pantalla.addView(origen)

        val destino = EditText(this)
        destino.hint = "¿A dónde vas?"
        pantalla.addView(destino)

        val fecha = EditText(this)
        fecha.hint = "Fecha del viaje"
        pantalla.addView(fecha)

        val hora = EditText(this)
        hora.hint = "Hora del viaje"
        pantalla.addView(hora)

        val enviar = Button(this)
        enviar.text = "📲 Programar por WhatsApp"

        enviar.setOnClickListener {
            val o = origen.text.toString().trim()
            val d = destino.text.toString().trim()
            val f = fecha.text.toString().trim()
            val h = hora.text.toString().trim()

            if (o.isEmpty() || d.isEmpty() || f.isEmpty() || h.isEmpty()) {
                Toast.makeText(this, "Completa todos los datos", Toast.LENGTH_SHORT).show()
            } else {
                abrirWhatsApp("""
                    📅 VIAJE PROGRAMADO - DON TAXI
                    
                    📍 Origen:
                    $o
                    
                    🏁 Destino:
                    $d
                    
                    📅 Fecha:
                    $f
                    
                    🕐 Hora:
                    $h
                """.trimIndent())
            }
        }

        pantalla.addView(enviar)
        agregarBotonVolver(pantalla)
        setContentView(pantalla)
    }

    private fun mostrarCotizacion() {
        val pantalla = crearPantalla("💰 Cotizar viaje")

        val origen = EditText(this)
        origen.hint = "¿Dónde te recogemos?"
        pantalla.addView(origen)

        val destino = EditText(this)
        destino.hint = "¿A dónde vas?"
        pantalla.addView(destino)

        val detalles = EditText(this)
        detalles.hint = "Detalles adicionales"
        pantalla.addView(detalles)

        val enviar = Button(this)
        enviar.text = "📲 Solicitar cotización por WhatsApp"

        enviar.setOnClickListener {
            val o = origen.text.toString().trim()
            val d = destino.text.toString().trim()
            val info = detalles.text.toString().trim()

            if (o.isEmpty() || d.isEmpty()) {
                Toast.makeText(this, "Completa origen y destino", Toast.LENGTH_SHORT).show()
            } else {
                abrirWhatsApp("""
                    💰 SOLICITUD DE COTIZACIÓN - DON TAXI
                    
                    📍 Origen:
                    $o
                    
                    🏁 Destino:
                    $d
                    
                    📝 Detalles:
                    ${if (info.isEmpty()) "Sin detalles adicionales" else info}
                    
                    Por favor, indicar el precio del viaje.
                """.trimIndent())
            }
        }

        pantalla.addView(enviar)
        agregarBotonVolver(pantalla)
        setContentView(pantalla)
    }

    private fun mostrarSupermercado() {
        val pantalla = crearPantalla("🛒 Compra en supermercado")

        val productos = EditText(this)
        productos.hint = "¿Qué productos necesitas?"
        pantalla.addView(productos)

        val supermercado = EditText(this)
        supermercado.hint = "¿En qué supermercado?"
        pantalla.addView(supermercado)

        val entrega = EditText(this)
        entrega.hint = "¿Dónde debemos entregar?"
        pantalla.addView(entrega)

        val fecha = EditText(this)
        fecha.hint = "¿Cuándo lo necesitas?"
        pantalla.addView(fecha)

        val detalles = EditText(this)
        detalles.hint = "Detalles adicionales"
        pantalla.addView(detalles)

        val enviar = Button(this)
        enviar.text = "📲 Solicitar compra por WhatsApp"

        enviar.setOnClickListener {
            val lista = productos.text.toString().trim()
            val lugarCompra = supermercado.text.toString().trim()
            val lugarEntrega = entrega.text.toString().trim()
            val fechaCompra = fecha.text.toString().trim()
            val info = detalles.text.toString().trim()

            if (lista.isEmpty() || lugarCompra.isEmpty() ||
                lugarEntrega.isEmpty() || fechaCompra.isEmpty()) {
                Toast.makeText(this, "Completa los datos de la compra", Toast.LENGTH_SHORT).show()
            } else {
                abrirWhatsApp("""
                    🛒 SERVICIO DE COMPRA - DON TAXI
                    
                    🛍️ Productos:
                    $lista
                    
                    🏪 Supermercado:
                    $lugarCompra
                    
                    📍 Lugar de entrega:
                    $lugarEntrega
                    
                    📅 Fecha:
                    $fechaCompra
                    
                    📝 Detalles:
                    ${if (info.isEmpty()) "Sin detalles adicionales" else info}
                """.trimIndent())
            }
        }

        pantalla.addView(enviar)
        agregarBotonVolver(pantalla)
        setContentView(pantalla)
    }

    private fun mostrarPagoRecibos() {
        val pantalla = crearPantalla("💳 Pago de recibos")

        val tipo = EditText(this)
        tipo.hint = "¿Qué recibo deseas pagar?"
        pantalla.addView(tipo)

        val empresa = EditText(this)
        empresa.hint = "Empresa o servicio"
        pantalla.addView(empresa)

        val monto = EditText(this)
        monto.hint = "Monto aproximado"
        pantalla.addView(monto)

        val lugar = EditText(this)
        lugar.hint = "¿Dónde debemos realizar el pago?"
        pantalla.addView(lugar)

        val detalles = EditText(this)
        detalles.hint = "Detalles adicionales"
        pantalla.addView(detalles)

        val enviar = Button(this)
        enviar.text = "📲 Solicitar servicio por WhatsApp"

        enviar.setOnClickListener {
            val recibo = tipo.text.toString().trim()
            val compania = empresa.text.toString().trim()
            val cantidad = monto.text.toString().trim()
            val ubicacion = lugar.text.toString().trim()
            val info = detalles.text.toString().trim()

            if (recibo.isEmpty() || compania.isEmpty() ||
                cantidad.isEmpty() || ubicacion.isEmpty()) {

                Toast.makeText(
                    this,
                    "Completa los datos del recibo",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                abrirWhatsApp("""
                    💳 SERVICIO DE PAGO DE RECIBO - DON TAXI
                    
                    🧾 Recibo:
                    $recibo
                    
                    🏢 Empresa o servicio:
                    $compania
                    
                    💵 Monto aproximado:
                    $cantidad
                    
                    📍 Lugar:
                    $ubicacion
                    
                    📝 Detalles:
                    ${if (info.isEmpty()) "Sin detalles adicionales" else info}
                """.trimIndent())
            }
        }

        pantalla.addView(enviar)
        agregarBotonVolver(pantalla)
        setContentView(pantalla)
    }

    private fun contactarWhatsApp() {
        abrirWhatsApp("Hola Don Taxi, quiero información sobre sus servicios.")
    }

    private fun crearPantalla(tituloTexto: String): LinearLayout {
        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.setPadding(30, 30, 30, 30)

        val titulo = TextView(this)
        titulo.text = tituloTexto
        titulo.textSize = 28f
        titulo.gravity = Gravity.CENTER
        pantalla.addView(titulo)

        return pantalla
    }

    private fun abrirWhatsApp(mensaje: String) {
        try {
            val texto = URLEncoder.encode(mensaje, "UTF-8")
            val url = "https://wa.me/$whatsapp?text=$texto"

            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            )

        } catch (e: Exception) {
            Toast.makeText(
                this,
                "No se pudo abrir WhatsApp",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun agregarBotonVolver(pantalla: LinearLayout) {
        val volver = Button(this)
        volver.text = "Volver"
        volver.setOnClickListener { mostrarMenu() }
        pantalla.addView(volver)
    }

    private fun agregarBoton(
        pantalla: LinearLayout,
        texto: String,
        accion: () -> Unit
    ) {
        val boton = Button(this)
        boton.text = texto
        boton.textSize = 16f
        boton.setOnClickListener { accion() }
        pantalla.addView(boton)
    }
}