package com.nakul.guess_game.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nakul.guess_game.R
import com.nakul.guess_game.databinding.DialogNumberSelectionBinding
import com.nakul.guess_game.viewmodel.MainViewModel

/**
 * Created by Nakul
 * on 29,March,2022
 */
class SelectionDialog(context: Context, private val vm: MainViewModel, val finishAction: ()-> Unit) :
    Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: DialogNumberSelectionBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.dialog_number_selection, null, false)
        binding.vm = vm
        setContentView(binding.root)
        this.setCancelable(false)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAction()
    }
}