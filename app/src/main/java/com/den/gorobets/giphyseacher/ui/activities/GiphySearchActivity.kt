package com.den.gorobets.giphyseacher.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.den.gorobets.giphyseacher.R
import com.den.gorobets.giphyseacher.databinding.ActivityGiphySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiphySearchActivity : AppCompatActivity() {

    private val controller by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment).findNavController()
    }
    private val binding by lazy { ActivityGiphySearchBinding.inflate(layoutInflater) }
    private val topAppBarBind by lazy { AppBarConfiguration(controller.graph) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(controller, topAppBarBind)
    }
}