package com.den.gorobets.giphyseacher.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.den.gorobets.giphyseacher.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiphySearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giphy_search)

    }
}