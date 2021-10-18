package com.samirmaciel.futstreet.modules.tournamentSelect

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentselectBinding
import com.samirmaciel.futstreet.shared.const.GOT_TO_LASTMATCHES_PAGE
import com.samirmaciel.futstreet.shared.const.GO_TO_TOURNAMENET_PAGE


class TournamentSelectFragment : Fragment(R.layout.fragment_tournamentselect) {

    private var _binding : FragmentTournamentselectBinding? = null
    private val binding : FragmentTournamentselectBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentselectBinding.bind(view)

    }

    override fun onResume() {
        super.onResume()

        binding.buttonCancelTournament.setOnClickListener{
            val alert = AlertDialog.Builder(requireContext()).apply {
                setTitle(resources.getText(R.string.text_ask_cancel_tournament))
                setPositiveButton(resources.getText(R.string.yes)){
                    _, _, -> findNavController().navigate(R.id.action_tournamentSelectFragment_to_homeFragment)
                }
                setNegativeButton(resources.getText(R.string.no), null)
            }.create().show()
        }

        binding.buttonReadyTournament.setOnClickListener{
            val intent = Intent(GO_TO_TOURNAMENET_PAGE)
            requireActivity().sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}