package com.mauricio.quiencrees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.widget.TextView
import android.view.View


class MainActivity : AppCompatActivity() {

    private lateinit var barajaQuien: BarajaQuien
    private lateinit var barajaTu: BarajaTu
    private lateinit var cardContainer: LinearLayout
    private lateinit var buttonNext: Button
    private var currentIndex = 0
    private var previousCardView: CardView? = null
    private var nextCard = 0
    private lateinit var cartaTuActual: CartaTu
    private lateinit var cardView: CardView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        barajaQuien = BarajaQuien(this)
        barajaTu = BarajaTu(barajaQuien.baraja.size)
        cardContainer = findViewById(R.id.cardContainer)
        buttonNext = findViewById(R.id.buttonNext)
        cardView = findViewById(R.id.cardView)

        buttonNext.setOnClickListener {
            showNextFrase()
        }
    }

    private fun showNextFrase() {
        if (previousCardView != null) {
            cardContainer.removeView(previousCardView)
        }else{
            cardView.visibility = View.GONE
        }

        if (currentIndex < barajaQuien.baraja.size) {


            when (nextCard) {
                0 ->{//Evento Carta Quien
                    createQuienCardView(barajaQuien.baraja[currentIndex])
                    currentIndex++
                    nextCard = 1
                }
                1 -> {
                    createInstructionCardView("Pasa el telefono a quien creas")
                    nextCard = 2
                }
                2 -> {
                    cartaTuActual = barajaTu.baraja[currentIndex]
                    createTuCardView(cartaTuActual)
                    when (cartaTuActual.revela) {
                        0 -> nextCard = 3 //No se revela
                        1 -> nextCard = 4 //Se revela a todos menosa ti
                        2 -> nextCard = 5 //Se revela
                        3 -> nextCard = 6 //Se revela la anterior
                    }
                }
                3 -> {
                    createInstructionCardView("Quien la compra?")
                    if(true){//Condicion de compra

                    }
                    nextCard = 0
                }
                4 -> {
                    createInstructionCardView("Pasa el telefono a los demas jugadores")
                    nextCard = 5
                }
                5 -> {
                    createQuienCardView(barajaQuien.baraja[currentIndex-1])
                    nextCard = 0
                }
                6 -> {
                    createQuienCardView(barajaQuien.baraja[currentIndex-2])
                    nextCard = 0
                }
            }
        } else {
            cardView.visibility = View.VISIBLE
            currentIndex = 0
        }
    }


    private fun createQuienCardView(text: String) {
        val inflater = LayoutInflater.from(this)
        val cardView = inflater.inflate(R.layout.quien_card_view_item, cardContainer, false) as CardView
        cardView.radius = resources.getDimension(R.dimen.card_radius)
        cardView.cardElevation = resources.getDimension(R.dimen.card_elevation)

        val textViewContent = cardView.findViewById<TextView>(R.id.textViewCardContent)
        textViewContent.text = text

        // Configura el título u otra información si es necesario
        val textViewTitle = cardView.findViewById<TextView>(R.id.textViewCardTitle)
        textViewTitle.text = "Quien crees que..."

        cardView.setOnClickListener {
            showNextFrase()
        }

        cardContainer.addView(cardView)
        animateCardView(cardView)

        // Actualizar la tarjeta anterior
        previousCardView = cardView
    }

    private fun createInstructionCardView(textInstruction: String) {
        val inflater = LayoutInflater.from(this)
        val cardView = inflater.inflate(R.layout.quien_card_view_item, cardContainer, false) as CardView
        cardView.radius = resources.getDimension(R.dimen.card_radius)
        cardView.cardElevation = resources.getDimension(R.dimen.card_elevation)

        val textViewTitle = cardView.findViewById<TextView>(R.id.textViewCardTitle)
        textViewTitle.text = "Quien crees que..."

        val textViewContent = cardView.findViewById<TextView>(R.id.textViewCardContent)
        textViewContent.text = textInstruction



        cardView.setOnClickListener {
            showNextFrase()
        }

        cardContainer.addView(cardView)
        animateCardView(cardView)

        // Actualizar la tarjeta anterior
        previousCardView = cardView
    }

    private fun createTuCardView(carta: CartaTu) {
        val inflater = LayoutInflater.from(this)
        val cardView = inflater.inflate(R.layout.quien_card_view_item, cardContainer, false) as CardView
        cardView.radius = resources.getDimension(R.dimen.card_radius)
        cardView.cardElevation = resources.getDimension(R.dimen.card_elevation)

        val textViewContent = cardView.findViewById<TextView>(R.id.textViewCardContent)
        when (carta.revela) {
            0 ->textViewContent.text = "No se revela"
            1 -> textViewContent.text = "Se revela a todos menos a ti"
            2 -> textViewContent.text = "Se revela"
            3 -> textViewContent.text = "Se revela la anterior"
        }


        // Configura el título u otra información si es necesario
        val textViewTitle = cardView.findViewById<TextView>(R.id.textViewCardTitle)
        textViewTitle.text = "Shots: "+carta.shots

        cardView.setOnClickListener {
            showNextFrase()
        }

        cardContainer.addView(cardView)
        animateCardView(cardView)

        // Actualizar la tarjeta anterior
        previousCardView = cardView
    }


    private fun animateCardView(cardView: CardView) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        cardView.startAnimation(anim)
    }
}
