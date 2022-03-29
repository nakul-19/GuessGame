package com.nakul.guess_game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nakul.guess_game.models.GameState
import com.nakul.guess_game.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val vm by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: com.nakul.guess_game.databinding.ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = vm
        setContentView(binding.root)

        vm.state.observe(this) {
            if (it != GameState.PLAYING)
                ResultDialog(this, it, vm).apply {
                    create()
                    show()
                }
        }

    }
}