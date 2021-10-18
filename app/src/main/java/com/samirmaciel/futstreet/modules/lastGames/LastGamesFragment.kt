package com.samirmaciel.futstreet.modules.lastGames

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentLastgamesBinding

class LastGamesFragment : Fragment(R.layout.fragment_lastgames) {

    private var _binding : FragmentLastgamesBinding? = null
    private val binding : FragmentLastgamesBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLastgamesBinding.bind(view)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}