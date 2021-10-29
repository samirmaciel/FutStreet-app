package com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages.stages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentFinalBinding

class FinalTournamentFragment : Fragment(R.layout.fragment_tournament_final) {

    private var _binding : FragmentTournamentFinalBinding? = null
    private val binding : FragmentTournamentFinalBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentFinalBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}