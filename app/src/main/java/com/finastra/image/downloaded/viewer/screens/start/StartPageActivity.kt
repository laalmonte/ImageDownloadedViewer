package com.finastra.image.downloaded.viewer.screens.start

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.finastra.image.downloaded.viewer.R
import com.finastra.image.downloaded.viewer.screens.main.MainActivity

class StartPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)
        Handler(Looper.getMainLooper()).postDelayed({
            goToMain()
            finish()
        }, 3000)

    }

    private fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}