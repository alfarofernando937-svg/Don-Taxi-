package com.dontaxi.app

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
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

        val contenido = LinearLayout(this)
        contenido.orientation = LinearLayout.VERTICAL
        contenido.setPadding(30, 40, 30, 40)

        val encabezado = LinearLayout(this)
        encabezado.orientation = LinearLayout.VERTICAL
        encabezado.gravity = Gravity.CENTER
        encabezado.setPadding(0, 0, 0, 30)

        val titulo = TextView(this)
        titulo.text = "🚕 DON TAXI"
        titulo.textSize = 34f
        titulo.setTypeface(null, Typeface.BOLD)
        titulo.gravity = Gravity.CENTER
        titulo.setTextColor(Color.BLACK)
        encabezado.addView(titulo)

        val subtitulo = TextView(this)
        subtitulo.text = "Tu taxi de confianza"
        subtitulo.textSize = 18f
        subtitulo.gravity = Gravity.CENTER
        subtitulo.setTextColor(Color.DKGRAY)
        encabezado.addView(subtitulo)

        contenido.addView(encabezado)

        agregarBotonProfesional(
            contenido,
            "🚕  SOLICITAR TAXI"
        ) {
            mostrarSolicitud()
        }

        agregarBotonProfesional(
            contenido,
            "📅  PROGRAMAR VIAJE"
        ) {
            mostrarProgramarViaje()
        }

        agregarBotonProfesional(
            contenido,
            "💰  COTIZAR VIAJE"
        ) {
            mostrarCotizacion()
        }

        agregarBotonProfesional(
            contenido,
            "🛒  COMPRA EN SUPERMERCADO"
        ) {
            mostrarSupermercado()
        }

        agregarBotonProfesional(
            contenido,
            "💳  PAGO DE RECIBOS"
        ) {
            mostrarPagoRecibos()
        }

        agregarBotonProfesional(
            contenido,
            "📞  CONTACTAR A DON TAXI"
        ) {
            contactarWhatsApp()
        }

        val pie = TextView(this)
        pie.text = "\nServicio rápido y confiable"
        pie.textSize = 14f
        pie.gravity = Gravity.CENTER
        pie.setTextColor(Color.GRAY)
        contenido.addView(pie)

        val scroll = ScrollView(this)
        scroll.addView(contenido)

        setContentView(scroll)
    }

    private fun agregarBotonProfesional(
        pantalla: LinearLayout,
        texto: String,
        accion: () -> Unit
    ) {

        val boton = Button(this)

        boton.text = texto
        boton.textSize = 16f
        boton.setTypeface(null, Typeface.BOLD)
        boton.setTextColor(Color.BLACK)
        boton.setPadding(10, 20, 10, 20)

        boton.setOnClickListener {
            accion()
        }

        val parametros = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        parametros.setMargins(0, 8, 0, 8)

        pantalla.addView(boton, parametros)
    }

    private fun mostrarSolicitud() {

        val pantalla = crearPantalla("🚕 SOLICITAR TAXI")

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

        val pantalla = crearPantalla("📅 PROGRAMAR VIAJE")

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

            if (
                o.isEmpty() ||
                d.isEmpty() ||
                f.isEmpty() ||
                h.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Completa todos los datos",
                    Toast.LENGTH_SHORT
                ).show()

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

        val pantalla = crearPantalla("💰 COTIZAR VIAJE")

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
        enviar.text = "📲 Solicitar cotización"

        enviar.setOnClickListener {

            val o = origen.text.toString().trim()
            val d = destino.text.toString().trim()
            val info = detalles.text.toString().trim()

            if (o.isEmpty() || d.isEmpty()) {

                Toast.makeText(
                    this,
                    "Completa origen y destino",
                    Toast.LENGTH_SHORT
                ).show()

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

        val pantalla = crearPantalla("🛒 COMPRA EN SUPERMERCADO")

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
        enviar.text = "📲 Solicitar compra"

        enviar.setOnClickListener {

            val lista = productos.text.toString().trim()
            val lugarCompra = supermercado.text.toString().trim()
            val lugarEntrega = entrega.text.toString().trim()
            val fechaCompra = fecha.text.toString().trim()
            val info = detalles.text.toString().trim()

            if (
                lista.isEmpty() ||
                lugarCompra.isEmpty() ||
                lugarEntrega.isEmpty() ||
                fechaCompra.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Completa los datos de la compra",
                    Toast.LENGTH_SHORT
                ).show()

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

        val pantalla = crearPantalla("💳 PAGO DE RECIBOS")

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
        enviar.text = "📲 Solicitar servicio"

        enviar.setOnClickListener {

            val recibo = tipo.text.toString().trim()
            val compania = empresa.text.toString().trim()
            val cantidad = monto.text.toString().trim()
            val ubicacion = lugar.text.toString().trim()
            val info = detalles.text.toString().trim()

            if (
                recibo.isEmpty() ||
                compania.isEmpty() ||
                cantidad.isEmpty() ||
                ubicacion.isEmpty()
            ) {

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

        abrirWhatsApp(
            "Hola Don Taxi, quiero información sobre sus servicios."
        )
    }

    private fun crearPantalla(
        tituloTexto: String
    ): LinearLayout {

        val pantalla = LinearLayout(this)

        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.setPadding(30, 30, 30, 30)

        val titulo = TextView(this)

        titulo.text = tituloTexto
        titulo.textSize = 28f
        titulo.gravity = Gravity.CENTER
        titulo.setTypeface(null, Typeface.BOLD)

        pantalla.addView(titulo)

        return pantalla
    }

    private fun agregarBotonVolver(
        pantalla: LinearLayout
    ) {

        val volver = Button(this)

        volver.text = "🏠 Volver al inicio"

        volver.setOnClickListener {
            mostrarMenu()
        }

        pantalla.addView(volver)
    }

    private fun abrirWhatsApp(
        mensaje: String
    ) {

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
}