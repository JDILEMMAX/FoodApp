package com.example.foodapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.foodapp.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        // we can configure the window to fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAGS_CHANGED
        )
        // handler function to handle the period/time delayed
        Handler().postDelayed({
            // create intent to load main activity after displaying splash_screen
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            // use finish function to kill/finish the current activity
            finish()
        },3000
        )
    }
}