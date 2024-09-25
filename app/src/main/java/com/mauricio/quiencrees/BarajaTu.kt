package com.mauricio.quiencrees

import kotlin.random.Random

class BarajaTu(tamBaraja: Int) {
    public val baraja: List<CartaTu>
    private val maxShots = 4
    private val numeros = listOf(1, 2, 3, 4)
    private val probabilidades = listOf(0.4, 0.3, 0.2, 0.1)

    init {
        baraja = generaBaraja(tamBaraja)
    }

    private fun generaBaraja(tamBaraja: Int): List<CartaTu> {
        val cartas = mutableListOf<CartaTu>()
        val random = Random
        var randomRevela: Int
        var randomShots: Int
        for (i in 0 until tamBaraja) {
            do {// Do-while loop to ensure that a "The previous one is revealed" card isn't after a "Revealed" card
                if (i==0)// Prevent the 'The previous one is revealed' card  from being the first.
                    randomRevela = random.nextInt(3)
                randomRevela = random.nextInt(4)
            } while (randomRevela == 3 && (cartas.isEmpty() || cartas[i - 1].revela == 2))

            randomShots = elegirNumeroConProbabilidad(numeros, probabilidades)
            val ct = CartaTu(randomRevela, randomShots)
            cartas.add(ct)
        }
        return cartas
    }

    fun elegirNumeroConProbabilidad(numeros: List<Int>, probabilidades: List<Double>): Int {
        require(numeros.size == probabilidades.size) { "La lista de números y probabilidades deben tener la misma longitud" }

        val random = Random.nextDouble()
        var acumulativo = 0.0

        for (i in numeros.indices) {
            acumulativo += probabilidades[i]
            if (random < acumulativo) {
                return numeros[i]
            }
        }

        // En caso de que no se elija ningún número, regresa el último
        return numeros.last()
    }


}
