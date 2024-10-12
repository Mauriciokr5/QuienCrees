package com.mauricio.quiencrees

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Habilitar el botón de regreso en la ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        videoView = findViewById(R.id.videoView)
        val videoUri: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.tutorial_video)

        // Configuración del MediaController
        val mediaController = MediaController(this)


        // Configura el VideoView
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(videoUri)
        videoView.requestFocus()

        // Inicia el video cuando esté listo
        videoView.setOnPreparedListener { mediaPlayer ->
            // Obtener la relación de aspecto del video
            val videoWidth = mediaPlayer.videoWidth
            val videoHeight = mediaPlayer.videoHeight
            val videoProportion = videoWidth.toFloat() / videoHeight.toFloat()

            mediaController.setPadding(0, 500, 0, 0)

            // Inicia el video
            videoView.start()
            mediaController.show(0) // Mostrar controles indefinidamente
        }
    }

    // Manejar el evento del botón de regreso en la ActionBar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Volver a la actividad principal
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // Liberar recursos cuando la actividad se destruya
    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback() // Libera los recursos del video
    }
}
