package com.nakul.guess_game.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.nakul.guess_game.databinding.DialogResultBinding
import com.nakul.guess_game.models.GameState
import com.nakul.guess_game.viewmodel.MainViewModel

/**
 * Created by Nakul
 * on 29,March,2022
 */
class ResultDialog(context: Context, private val state: GameState, private val vm: MainViewModel) :
    Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.textview.text = if (state == GameState.LOST) "Game lost!" else "You won!"
        binding.playAgainButton.setOnClickListener {
            vm.resetData()
            dismiss()
        }
    }
}