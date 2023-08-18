package com.mauricio.quiencrees

class CartaTu(val revela: Int, val shots: Int) {

    fun print() {
        when (revela) {
            0 -> println("No se revela")// No se revela (Not revealed)
            1 -> println("Se revela a todos menos para ti") // Se revela a todos menos a ti (Revealed to everyone except you)
            2 -> println("Se revela")// Se revela (Revealed)
            3 -> println("Se revela la anterior") // Se revela la anterior (The previous one is revealed)
        }
        println("Shots: $shots")
    }
}