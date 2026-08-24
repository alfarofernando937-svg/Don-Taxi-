package com.dontaxi.app

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import java.net.URLEncoder

class MainActivity : Activity() {

    private val whatsapp = "50370838437"

    private val amarillo = Color.rgb(255, 193, 7)
    private val negro = Color.rgb(20, 20, 20)
    private val blanco = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mostrarMenu()
    }

    private fun mostrarMenu() {

        val contenido = LinearLayout(this)
        contenido.orientation = LinearLayout.VERTICAL
        contenido.setPadding(25, 30, 25, 30)
        contenido.setBackgroundColor(blanco)

        // ENCABEZADO
        val encabezado = LinearLayout(this)
        encabezado.orientation = LinearLayout.VERTICAL
        encabezado.gravity = Gravity.CENTER
        encabezado.setPadding(10, 25, 10, 30)
        encabezado.setBackgroundColor(amarillo)

        val icono = TextView(this)
        icono.text = "🚕"
        icono.textSize = 55f
        icono.gravity = Gravity.CENTER
        encabezado.addView(icono)

        val titulo = TextView(this)
        titulo.text = "DON TAXI"
        titulo.textSize = 36f
        titulo.setTypeface(null, Typeface.BOLD)
        titulo.gravity = Gravity.CENTER
        titulo.setTextColor(negro)
        encabezado.addView(titulo)

        val lema = TextView(this)
        lema.text = "Llevándote con bien a cada destino"
        lema.textSize = 17f
        lema.gravity = Gravity.CENTER
        lema.setTextColor(negro)
        encabezado.addView(lema)

        contenido.addView(encabezado)

        val pregunta = TextView(this)
        pregunta.text = "¿Qué servicio necesitas?"
        pregunta.textSize = 20f
        pregunta.setTypeface(null, Typeface.BOLD)
        pregunta.gravity = Gravity.CENTER
        pregunta.setTextColor(negro)
        pregunta.setPadding(0, 25, 0, 15)
        contenido.addView(pregunta)

        agregarBoton(contenido, "🚕  SOLICITAR TAXI") {
            mostrarSolicitud()
        }

        agregarBoton(contenido, "📅  PROGRAMAR VIAJE") {
            mostrarProgramarViaje()
        }

        agregarBoton(contenido, "💰  COTIZAR VIAJE") {
            mostrarCotizacion()
        }

        agregarBoton(contenido, "🛒  COMPRA EN SUPERMERCADO") {
            mostrarSupermercado()
        }

        agregarBoton(contenido, "💳  PAGO DE RECIBOS") {
            mostrarPagoRecibos()
        }

        agregarBoton(contenido, "📞  CONTACTAR A DON TAXI") {
            contactarWhatsApp()
        }

        val pie = TextView(this)
        pie.text = "\n🚕 Don Taxi\nLlevándote con bien a cada destino"
        pie.textSize = 14f
        pie.gravity = Gravity.CENTER
        pie.setTextColor(Color.DKGRAY)
        pie.setPadding(0, 20, 0, 20)
        contenido.addView(pie)

        val scroll = ScrollView(this)
        scroll.addView(contenido)

        setContentView(scroll)
    }

    private fun agregarBoton(
        pantalla: LinearLayout,
        texto: String,
        accion: () -> Unit
    ) {

        val boton = Button(this)

        boton.text = texto
        boton.textSize = 16f
        boton.setTypeface(null, Typeface.BOLD)
        boton.setTextColor(negro)
        boton.setBackgroundColor(amarillo)
        boton.setPadding(10, 18, 10, 18)

        val parametros = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        parametros.setMargins(0, 7, 0, 7)

        boton.setOnClickListener {
            accion()
        }

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
        enviar.setBackgroundColor(amarillo)

        enviar.setOnClickListener {

            val o = origen.text.toString().trim()
            val d = destino.text.toString().trim()

            if (o.isEmpty() || d.isEmpty()) {

                Toast.makeText(
                    this,
                    "Completa origen y destino",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                abrirWhatsApp("""
                    🚕 SOLICITUD DE TAXI - DON TAXI
                    
                    📍 Origen:
                    $o
                    
                    🏁 Destino:
                    $d
                """.trimIndent())
            }
        }

        pantalla.addView(enviar)
        agregarVolver(pantalla)

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
        enviar.setBackgroundColor(amarillo)

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
        agregarVolver(pantalla)

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
        enviar.setBackgroundColor(amarillo)

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
        agregarVolver(pantalla)

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
        enviar.setBackgroundColor(amarillo)

        enviar.setOnClickListener {

            val lista = productos.text.toString().trim()
            val compra = supermercado.text.toString().trim()
            val entregaLugar = entrega.text.toString().trim()
            val fechaCompra = fecha.text.toString().trim()
            val info = detalles.text.toString().trim()

            if (
                lista.isEmpty() ||
                compra.isEmpty() ||
                entregaLugar.isEmpty() ||
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
                    $compra
                    
                    📍 Lugar de entrega:
                    $entregaLugar
                    
                    📅 Fecha:
                    $fechaCompra
                    
                    📝 Detalles:
                    ${if (info.isEmpty()) "Sin detalles adicionales" else info}
                """.trimIndent())
            }
        }

        pantalla.addView(enviar)
        agregarVolver(pantalla)

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
        enviar.setBackgroundColor(amarillo)

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
        agregarVolver(pantalla)

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
        pantalla.setPadding(25, 30, 25, 30)
        pantalla.setBackgroundColor(Color.WHITE)

        val encabezado = TextView(this)

        encabezado.text = "🚕 DON TAXI"
        encabezado.textSize = 25f
        encabezado.setTypeface(null, Typeface.BOLD)
        encabezado.gravity = Gravity.CENTER
        encabezado.setTextColor(negro)

        pantalla.addView(encabezado)

        val titulo = TextView(this)

        titulo.text = tituloTexto
        titulo.textSize = 26f
        titulo.setTypeface(null, Typeface.BOLD)
        titulo.gravity = Gravity.CENTER
        titulo.setTextColor(negro)
        titulo.setPadding(0, 25, 0, 20)

        pantalla.addView(titulo)

        return pantalla
    }

    private fun agregarVolver(
        pantalla: LinearLayout
    ) {

        val volver = Button(this)

        volver.text = "🏠 VOLVER AL INICIO"
        volver.setBackgroundColor(amarillo)

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