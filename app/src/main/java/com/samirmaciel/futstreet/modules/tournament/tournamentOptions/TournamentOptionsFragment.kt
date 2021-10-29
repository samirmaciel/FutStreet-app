package com.samirmaciel.futstreet.modules.tournament.tournamentOptions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentOptionsBinding

class TournamentOptionsFragment : Fragment(R.layout.fragment_tournament_options) {

    private var _binding : FragmentTournamentOptionsBinding? = null
    private val binding : FragmentTournamentOptionsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentOptionsBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()

        binding.buttonNewTournament.setOnClickListener{
            findNavController().navigate(R.id.action_tournamentOptionsFragment_to_tournamentSelectFragment)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}