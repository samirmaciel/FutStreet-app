package com.samirmaciel.futstreet.modules.gameReady

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentGamereadyBinding

class GameReadyFragment : Fragment(R.layout.fragment_gameready) {

    private var _binding : FragmentGamereadyBinding? = null
    private val binding : FragmentGamereadyBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGamereadyBinding.bind(view)

        binding.titleText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_gameReadyFragment_to_homeFragment))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}