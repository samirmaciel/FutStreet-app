package com.samirmaciel.futstreet.modules.friendlyMatch.matchSetting.shirtSelectionFragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.SelectShirtGridBinding
import com.samirmaciel.futstreet.shared.const.*

class ShirtSelectionDialog(private val onClick : (Int) -> Unit) : DialogFragment(R.layout.select_shirt_grid){

    private var _binding : SelectShirtGridBinding? = null
    private val binding : SelectShirtGridBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SelectShirtGridBinding.bind(view)
        dialog?.window?.setBackgroundDrawable(
            ColorDrawable(
                Color
            .TRANSPARENT)
        )
    }

    override fun onStart() {
        super.onStart()

        binding.shirtBlue.setOnClickListener{
            onClick(BLUE_SHIRT)
            dismiss()
        }

        binding.shirtRed.setOnClickListener{
            onClick(RED_SHIRT)
            dismiss()
        }

        binding.shirtOrange.setOnClickListener{
            onClick(ORANGE_SHIRT)
            dismiss()
        }

        binding.shirtBrown.setOnClickListener{
            onClick(BROWN_SHIRT)
            dismiss()
        }

        binding.shirtBlack.setOnClickListener{
            onClick(BLACK_SHIRT)
            dismiss()
        }

        binding.shirtPink.setOnClickListener {
            onClick(PINK_SHIRT)
            dismiss()
        }

        binding.shirtGreen.setOnClickListener{
            onClick(GREEN_SHIRT)
            dismiss()
        }

        binding.shirtYellow.setOnClickListener{
            onClick(YELLOW_SHIRT)
            dismiss()
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}