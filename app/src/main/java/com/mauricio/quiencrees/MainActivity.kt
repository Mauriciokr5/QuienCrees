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
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import android.widget.ImageView




class MainActivity : AppCompatActivity() {

    private lateinit var barajaQuien: BarajaQuien
    private lateinit var barajaTu: BarajaTu
    private lateinit var cardContainer: LinearLayout
    private lateinit var buttonNext: Button
    private lateinit var textViewWelcome: TextView
    private lateinit var textViewTitleApp: TextView
    private var currentIndex = 0
    private var previousCardView: CardView? = null
    private var nextCard = 0
    private lateinit var cartaTuActual: CartaTu
    private lateinit var cardView: CardView
    private lateinit var typeFaceBig : Typeface
    private lateinit var typeFaceReg :Typeface

    private var lightColor : Int = 0
    private var darkColor :Int = 0
    private var quienCardColor :Int = 0
    private var tuCardColor :Int = 0
    private var instructionCardColor:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        typeFaceBig = Typeface.createFromAsset(assets,"fonts/Gotham Regular.otf")
        typeFaceReg = Typeface.createFromAsset(assets,"fonts/Gotham Light.otf")

        lightColor = ContextCompat.getColor(this, R.color.light_color)
        darkColor = ContextCompat.getColor(this, R.color.dark_color)
        quienCardColor = ContextCompat.getColor(this, R.color.quien_card_color)
        tuCardColor = ContextCompat.getColor(this, R.color.tu_card_color)
        instructionCardColor = ContextCompat.getColor(this, R.color.instruction_card_color)


        barajaQuien = BarajaQuien(this)
        barajaTu = BarajaTu(barajaQuien.baraja.size)

        cardContainer = findViewById(R.id.cardContainer)



        cardView = findViewById(R.id.cardView)
        cardView.setCardBackgroundColor(instructionCardColor)

        textViewWelcome = findViewById(R.id.textViewWelcome)
        textViewWelcome.typeface = typeFaceReg
        textViewWelcome.setTextColor(lightColor)

        textViewTitleApp = findViewById(R.id.textViewTitleApp)
        textViewTitleApp.typeface = typeFaceBig
        textViewTitleApp.setTextColor(lightColor)

        buttonNext = findViewById(R.id.buttonNext)
        buttonNext.setBackgroundColor(lightColor)
        buttonNext.setBackgroundResource(R.drawable.rounded_button_background)
        buttonNext.typeface = typeFaceBig
        buttonNext.setTextColor(darkColor)


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
                    /*if(true){//Condicion de compra

                    }*/
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
        cardView.setCardBackgroundColor(quienCardColor)

        val textViewTitle = cardView.findViewById<TextView>(R.id.textViewQuienCardTitle)
        textViewTitle.text = "Quien crees que..."
        textViewTitle.typeface = typeFaceReg
        textViewTitle.setTextColor(lightColor)


        val textViewContent = cardView.findViewById<TextView>(R.id.textViewQuienCardContent)
        textViewContent.text = text
        textViewContent.typeface = typeFaceBig
        textViewContent.setTextColor(lightColor)

        val textViewQuienCardInstru = cardView.findViewById<TextView>(R.id.textViewQuienCardInstru)
        textViewQuienCardInstru.text = "Toca para continuar"
        textViewQuienCardInstru.typeface = typeFaceReg
        textViewQuienCardInstru.setTextColor(lightColor)



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
        val cardView = inflater.inflate(R.layout.instruction_card_view_item, cardContainer, false) as CardView
        cardView.radius = resources.getDimension(R.dimen.card_radius)
        cardView.cardElevation = resources.getDimension(R.dimen.card_elevation)
        cardView.setCardBackgroundColor(instructionCardColor)

        val textViewInstruCardContent = cardView.findViewById<TextView>(R.id.textViewInstruCardContent)
        textViewInstruCardContent.text = textInstruction
        textViewInstruCardContent.typeface = typeFaceBig
        textViewInstruCardContent.setTextColor(lightColor)

        val textViewInstruCardInstru = cardView.findViewById<TextView>(R.id.textViewInstruCardInstru)
        textViewInstruCardInstru.text = "Toca para continuar"
        textViewInstruCardInstru.typeface = typeFaceReg
        textViewInstruCardInstru.setTextColor(lightColor)





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
        val cardView = inflater.inflate(R.layout.tu_card_view_item, cardContainer, false) as CardView
        cardView.radius = resources.getDimension(R.dimen.card_radius)
        cardView.cardElevation = resources.getDimension(R.dimen.card_elevation)
        cardView.setCardBackgroundColor(tuCardColor)

        val textViewTuCardTitle = cardView.findViewById<TextView>(R.id.textViewTuCardTitle)
        when (carta.revela) {
            0 ->textViewTuCardTitle.text = "No se revela"
            1 -> textViewTuCardTitle.text = "Se revela a todos menos a ti"
            2 -> textViewTuCardTitle.text = "Se revela"
            3 -> textViewTuCardTitle.text = "Se revela la anterior"
        }
        textViewTuCardTitle.typeface = typeFaceBig
        textViewTuCardTitle.setTextColor(lightColor)



        val textViewTuCardInstru = cardView.findViewById<TextView>(R.id.textViewTuCardInstru)
        textViewTuCardInstru.text = "Toca para continuar"
        textViewTuCardInstru.typeface = typeFaceReg
        textViewTuCardInstru.setTextColor(lightColor)




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
