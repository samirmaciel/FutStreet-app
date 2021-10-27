package com.samirmaciel.futstreet.modules.tournamentOptions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentOptionsBinding

class TournamentOptionsFragment : Fragment(R.layout.fragment_tournament_options) {

    private var _binding : FragmentTournamentOptionsBinding? = null
    private val binding : FragmentTournamentOptionsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentOptionsBinding.bind(view)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}