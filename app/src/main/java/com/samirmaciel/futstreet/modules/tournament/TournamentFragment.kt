package com.samirmaciel.futstreet.modules.tournament

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentBinding

class TournamentFragment : Fragment(R.layout.fragment_tournament) {

    private var _binding : FragmentTournamentBinding? = null
    private val binding : FragmentTournamentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}