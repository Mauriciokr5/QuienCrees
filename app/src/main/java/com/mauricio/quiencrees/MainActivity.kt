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
    private lateinit var cardContainer: LinearLayout
    private lateinit var buttonNext: Button
    private var currentIndex = 0
    private var previousCardView: CardView? = null
    private lateinit var cardView: CardView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        barajaQuien = BarajaQuien(this)
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

        }

        if (currentIndex < barajaQuien.baraja.size) {
            cardView.visibility = View.GONE
            createQuienCardView(barajaQuien.baraja[currentIndex])
            currentIndex++
        } else {
            cardView.visibility = View.VISIBLE
            currentIndex = 0
            // Aquí puedes manejar el caso de haber mostrado todas las frases
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



    private fun animateCardView(cardView: CardView) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        cardView.startAnimation(anim)
    }
}
