package com.mauricio.quiencrees

class CartaTu(val revela: Int, val shots: Int) {

    // 0.No se revela
    // 1.Se revela a todos menos para ti
    // 2.Se revela
    // 3.Se revela la anterior

    fun print() {
        when (revela) {
            0 -> println("No se revela")
            1 -> println("Se revela a todos menos para ti")
            2 -> println("Se revela")
            3 -> println("Se revela la anterior")
        }
        println("Shots: $shots")
    }
}