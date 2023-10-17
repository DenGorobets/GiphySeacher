package com.den.gorobets.giphyseacher.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.den.gorobets.giphyseacher.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GiphySplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giphy_splash)

        splashCountdown(3000)
    }

    private fun startSearchActivity() {
        startActivity(Intent(this, GiphySearchActivity::class.java))
        finish()
    }

    private fun splashCountdown(time: Long) {
        lifecycleScope.launch {
            delay(time)
            startSearchActivity()
        }
    }
}