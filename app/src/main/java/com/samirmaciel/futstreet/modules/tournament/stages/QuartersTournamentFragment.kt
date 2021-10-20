package com.samirmaciel.futstreet.modules.tournament.stages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentQuartersBinding

class QuartersTournamentFragment : Fragment(R.layout.fragment_tournament_quarters) {

    private var _binding : FragmentTournamentQuartersBinding? = null
    private val binding : FragmentTournamentQuartersBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentQuartersBinding.bind(view)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}