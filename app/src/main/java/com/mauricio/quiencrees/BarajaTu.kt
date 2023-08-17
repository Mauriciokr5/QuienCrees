package com.mauricio.quiencrees

import kotlin.random.Random

class BarajaTu(tamBaraja: Int) {
    public val baraja: List<CartaTu>
    private val maxShots = 4

    init {
        baraja = generaBaraja(tamBaraja)
    }

    private fun generaBaraja(tamBaraja: Int): List<CartaTu> {
        val cartas = mutableListOf<CartaTu>()
        val random = Random
        var randomRevela: Int
        var randomShots: Int
        for (i in 0 until tamBaraja) {
            do {
                randomRevela = random.nextInt(4)
            } while (randomRevela == 3 && (cartas.isEmpty() || cartas[i - 1].revela == 2))

            randomShots = random.nextInt(maxShots - 1 + 1) + 1
            val ct = CartaTu(randomRevela, randomShots)
            cartas.add(ct)
        }
        return cartas
    }
}
