package com.samirmaciel.futstreet.modules.gameSetting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentGamesettingsBinding

class GameSettingFragment : Fragment(R.layout.fragment_gamesettings){

    private var _binding : FragmentGamesettingsBinding? = null
    private val binding : FragmentGamesettingsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      _binding = FragmentGamesettingsBinding.bind(view)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}