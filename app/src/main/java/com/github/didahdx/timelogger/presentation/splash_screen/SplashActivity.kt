package com.github.didahdx.timelogger.presentation.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.didahdx.timelogger.R
import com.github.didahdx.timelogger.presentation.clock_screen.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ThemeTimeLogger)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}