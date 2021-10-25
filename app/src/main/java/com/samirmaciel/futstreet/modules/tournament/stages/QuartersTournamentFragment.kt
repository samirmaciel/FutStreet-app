package com.samirmaciel.futstreet.modules.tournament.stages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentQuartersBinding
import com.samirmaciel.futstreet.modules.tournament.TournamentViewModel

class QuartersTournamentFragment : Fragment(R.layout.fragment_tournament_quarters) {

    private var _binding : FragmentTournamentQuartersBinding? = null
    private val binding : FragmentTournamentQuartersBinding get() = _binding!!

    private val viewModel : TournamentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentQuartersBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()

        viewModel.nameQ11.observe(this){
            binding.stageTeamName11.setText(it)
        }
        viewModel.shirtQ11.observe(this){
            binding.stageTeamShirt11.setImageResource(it)
        }

        viewModel.nameQ21.observe(this){
            binding.stageTeamName21.setText(it)
        }
        viewModel.shirtQ21.observe(this){
            binding.stageTeamShirt21.setImageResource(it)
        }

        viewModel.nameQ32.observe(this){
            binding.stageTeamName32.setText(it)
        }
        viewModel.shirtQ32.observe(this){
            binding.stageTeamShirt32.setImageResource(it)
        }

        viewModel.nameQ42.observe(this){
            binding.stageTeamName42.setText(it)
        }

        viewModel.shirtQ42.observe(this){
            binding.stageTeamShirt42.setImageResource(it)
        }

        viewModel.nameQ53.observe(this){
            binding.stageTeamName53.setText(it)
        }

        viewModel.shirtQ53.observe(this){
            binding.stageTeamShirt53.setImageResource(it)
        }

        viewModel.nameQ63.observe(this){
            binding.stageTeamName63.setText(it)
        }

        viewModel.shirtQ63.observe(this){
            binding.stageTeamShirt63.setImageResource(it)
        }

        viewModel.nameQ74.observe(this){
            binding.stageTeamName74.setText(it)
        }

        viewModel.shirtQ74.observe(this){
            binding.stageTeamShirt74.setImageResource(it)
        }

        viewModel.nameQ84.observe(this){
            binding.stageTeamName84.setText(it)
        }

        viewModel.shirtQ84.observe(this){
            binding.stageTeamShirt84.setImageResource(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}