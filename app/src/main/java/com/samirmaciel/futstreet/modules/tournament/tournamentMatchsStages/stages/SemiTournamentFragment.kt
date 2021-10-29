package com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages.stages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentSemiBinding

class SemiTournamentFragment : Fragment(R.layout.fragment_tournament_semi) {

    private var _binding : FragmentTournamentSemiBinding? = null
    private val binding : FragmentTournamentSemiBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentSemiBinding.bind(view)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}