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
import android.view.Gravity
import androidx.core.content.ContextCompat
import android.widget.ImageView




class MainActivity : AppCompatActivity() {

    // Declare properties for various UI elements and variables
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

    // Color variables
    private var lightColor : Int = 0
    private var darkColor :Int = 0
    private var quienCardColor :Int = 0
    private var tuCardColor :Int = 0
    private var instructionCardColor:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize typefaces and colors
        typeFaceBig = Typeface.createFromAsset(assets,"fonts/Gotham Regular.otf")
        typeFaceReg = Typeface.createFromAsset(assets,"fonts/Gotham Light.otf")

        lightColor = ContextCompat.getColor(this, R.color.light_color)
        darkColor = ContextCompat.getColor(this, R.color.dark_color)
        quienCardColor = ContextCompat.getColor(this, R.color.quien_card_color)
        tuCardColor = ContextCompat.getColor(this, R.color.tu_card_color)
        instructionCardColor = ContextCompat.getColor(this, R.color.instruction_card_color)

        // Creation of the two decks "Quien" and "Tu"
        barajaQuien = BarajaQuien(this)
        barajaTu = BarajaTu(barajaQuien.baraja.size)

        // Initialize UI elements from layout
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

        // Set click listener for "Empezar" button
        buttonNext.setOnClickListener {
            showNextFrase()
        }
    }
    // Logic to show the next card or instruction
    private fun showNextFrase() {
        if (previousCardView != null) {
            cardContainer.removeView(previousCardView)
        }else{
            cardView.visibility = View.GONE
        }

        if (currentIndex < barajaQuien.baraja.size) {


            when (nextCard) {
                0 ->{//Show Card Quien
                    createQuienCardView(barajaQuien.baraja[currentIndex])
                    currentIndex++
                    nextCard = 1
                }
                1 -> {//Card to give the phone to selected player
                    createInstructionCardView("Pasa el telefono a QUIEN CREAS")
                    nextCard = 2
                }
                2 -> {// Card showing "Tu" information
                    cartaTuActual = barajaTu.baraja[currentIndex]
                    createTuCardView(cartaTuActual)
                    when (cartaTuActual.revela) {
                        0 -> nextCard = 3 // No se revela (Not revealed)
                        1 -> nextCard = 4 // Se revela a todos menos a ti (Revealed to everyone except you)
                        2 -> nextCard = 5 // Se revela (Revealed)
                        3 -> nextCard = 6 // Se revela la anterior (The previous one is revealed)
                    }
                }
                3 -> {// Card for buying option
                    createCompraCardView()
                    nextCard = 7
                }
                4 -> {// Card to give the phone to the others players
                    createInstructionCardView("Pasa el telefono a los DEMÁS jugadores")
                    nextCard = 5
                }
                5 -> {// Show actual Quien card again
                    createQuienCardView(barajaQuien.baraja[currentIndex-1])
                    nextCard = 7
                }
                6 -> {// Show previous Quien card again
                    if(currentIndex >= 2)
                        createQuienCardView(barajaQuien.baraja[currentIndex-2])
                    nextCard = 7
                }
                7 -> {// Card to pass phone to the next player
                    createInstructionCardView("Pasa el telefono al SIGUIENTE jugador")
                    nextCard = 0
                }
                8 ->{// Card to pass phone to the buyer
                    createInstructionCardView("Pasa el telefono al COMPRADOR")
                    nextCard = 6
                }
            }
        } else {
            cardView.visibility = View.VISIBLE
            currentIndex = 0
        }
    }

    // Functions to create different types of card views
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

    private fun createCompraCardView() {
        val inflater = LayoutInflater.from(this)
        val cardView = inflater.inflate(R.layout.compra_card_view_item, cardContainer, false) as CardView
        cardView.radius = resources.getDimension(R.dimen.card_radius)
        cardView.cardElevation = resources.getDimension(R.dimen.card_elevation)
        cardView.setCardBackgroundColor(instructionCardColor)

        val textViewInstruCardContent = cardView.findViewById<TextView>(R.id.textViewInstruCardContent)
        textViewInstruCardContent.text = "¿Alguien paga para ver?"
        textViewInstruCardContent.typeface = typeFaceBig
        textViewInstruCardContent.setTextColor(lightColor)


        val buttonSiCompra = cardView.findViewById<TextView>(R.id.buttonSiCompra)
        buttonSiCompra.setBackgroundColor(lightColor)
        buttonSiCompra.setBackgroundResource(R.drawable.rounded_button_background)
        buttonSiCompra.typeface = typeFaceBig
        buttonSiCompra.setTextColor(darkColor)
        buttonSiCompra.setOnClickListener {
            nextCard = 8
            showNextFrase()
        }


        val buttonNoCompra = cardView.findViewById<TextView>(R.id.buttonNoCompra)
        buttonNoCompra.setBackgroundColor(lightColor)
        buttonNoCompra.setBackgroundResource(R.drawable.rounded_button_background)
        buttonNoCompra.typeface = typeFaceBig
        buttonNoCompra.setTextColor(darkColor)
        buttonNoCompra.setOnClickListener {
            nextCard = 7
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

        val linearLayout = cardView.findViewById<LinearLayout>(R.id.llimages)

        for (i in 0 until carta.shots) {
            val imageViewShot = ImageView(this)
            val layoutParams = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.shot_image_width), // Cambia esta dimensión
                resources.getDimensionPixelSize(R.dimen.shot_image_height) // Cambia esta dimensión
            )
            layoutParams.gravity = Gravity.CENTER
            layoutParams.marginStart = resources.getDimensionPixelSize(R.dimen.shot_margin)
            imageViewShot.layoutParams = layoutParams
            imageViewShot.setImageResource(R.drawable.shot) // Cambia esto por tu imagen
            linearLayout.addView(imageViewShot)
        }

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

    // Function to animate card view
    private fun animateCardView(cardView: CardView) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        cardView.startAnimation(anim)
    }
}
