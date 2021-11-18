package com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages.stages

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentFinalBinding
import com.samirmaciel.futstreet.modules.tournament.TournamentViewModel

class FinalTournamentFragment : Fragment(R.layout.fragment_tournament_final) {

    private var _binding : FragmentTournamentFinalBinding? = null
    private val binding : FragmentTournamentFinalBinding get() = _binding!!
    private val viewModel : TournamentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentFinalBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        Log.d("QUARTERSFRAGMENT", "FINAL: " + viewModel.matchStateQ1.value!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}