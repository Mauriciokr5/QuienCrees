package com.mauricio.quiencrees

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class BarajaQuien(private val context: Context, val checklist: BooleanArray) {
    public val baraja: MutableList<String> = leerFrases()

    // Get phrases from the file phrases.txt and store them in a MutableList
    private fun leerFrases(): MutableList<String> {
        val frases = mutableListOf<String>()

        val files = intArrayOf(R.raw.chill,R.raw.hostil,R.raw.picante)

        try {

            checklist.forEachIndexed { index, isChecked ->
                val file = files.getOrNull(index)
                if (file != null && isChecked) {
                    val inputStream = context.resources.openRawResource(file)
                    val bufferedReader = BufferedReader(InputStreamReader(inputStream))

                    var linea: String?
                    while (bufferedReader.readLine().also { linea = it } != null) {
                        linea?.let {
                            frases.add(it)
                        }
                    }

                    bufferedReader.close()
                }
            }

            frases.shuffle()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return frases
    }
}
