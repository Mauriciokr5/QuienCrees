package com.mauricio.quiencrees

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class BarajaQuien(private val context: Context) {
    public val baraja: MutableList<String> = leerFrases()

    // Get phrases from the file phrases.txt and store them in a MutableList
    private fun leerFrases(): MutableList<String> {
        val frases = mutableListOf<String>()

        try {
            val inputStream = context.resources.openRawResource(R.raw.frases)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            var linea: String?
            while (bufferedReader.readLine().also { linea = it } != null) {
                linea?.let {
                    frases.add(it)
                }
            }

            bufferedReader.close()
            frases.shuffle()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return frases
    }
}
