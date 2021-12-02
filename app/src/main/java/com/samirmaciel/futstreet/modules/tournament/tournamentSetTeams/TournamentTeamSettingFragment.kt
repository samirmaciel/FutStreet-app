package com.samirmaciel.futstreet.modules.tournament.tournamentSetTeams

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentteamsettingBinding
import com.samirmaciel.futstreet.modules.matchFriendlySetting.shirtSelectionFragment.ShirtSelectionDialog
import com.samirmaciel.futstreet.modules.tournament.TournamentViewModel

import com.samirmaciel.futstreet.shared.const.SHIRT_SELECTION_FRAGMENT
import com.samirmaciel.futstreet.shared.model.Team


class TournamentTeamSettingFragment : Fragment(R.layout.fragment_tournamentteamsetting) {

    private var _binding : FragmentTournamentteamsettingBinding? = null
    private val binding : FragmentTournamentteamsettingBinding get() = _binding!!

    private val viewModel : TournamentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTournamentteamsettingBinding.bind(view)

    }

    override fun onResume() {
        super.onResume()

        binding.buttonCancelTournament.setOnClickListener{
            findNavController().navigate(R.id.action_tournamentSelectFragment_to_homeFragment)
        }

        binding.buttonReadyTournament.setOnClickListener{

            saveInputTeamsNamesInViewModel()

            findNavController().navigate(R.id.action_tournamentSelectFragment_to_tournamentMatchSettingsFragment)
        }
        
        binding.selectShirtTeam1Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.team1) }
        binding.selectShirtTeam2Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.team2) }
        binding.selectShirtTeam3Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.team3) }
        binding.selectShirtTeam4Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.team4) }
        binding.selectShirtTeam5Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.team5) }
        binding.selectShirtTeam6Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.team6) }
        binding.selectShirtTeam7Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.team7) }
        binding.selectShirtTeam8Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.team8) }

        viewModel.team1.observe(this){ animateShirt(binding.selectShirtTeam1Tournament, it.shirt) }
        viewModel.team2.observe(this){ animateShirt(binding.selectShirtTeam2Tournament, it.shirt) }
        viewModel.team3.observe(this){ animateShirt(binding.selectShirtTeam3Tournament, it.shirt) }
        viewModel.team4.observe(this){ animateShirt(binding.selectShirtTeam4Tournament, it.shirt) }
        viewModel.team5.observe(this){ animateShirt(binding.selectShirtTeam5Tournament, it.shirt) }
        viewModel.team6.observe(this){ animateShirt(binding.selectShirtTeam6Tournament, it.shirt) }
        viewModel.team7.observe(this){ animateShirt(binding.selectShirtTeam7Tournament, it.shirt) }
        viewModel.team8.observe(this){ animateShirt(binding.selectShirtTeam8Tournament, it.shirt) }

    }

    private fun animateShirt(imageView : ImageView, shirt : Int){
        imageView.animate().apply {
            duration = 100
            scaleYBy(0.5f)
            scaleXBy(0.5f)
        }.withEndAction {
            imageView.setImageResource(shirt)
            imageView.animate().apply {
                duration = 100
                scaleYBy(-0.5f)
                scaleXBy(-0.5f)
            }
        }.start()
    }


    private fun callAlertShirtSelect(teamLiveData: MutableLiveData<Team>){
        val shirtSelection = ShirtSelectionDialog {
            for ((k, v) in viewModel.getListOfShirtsResourceIDs()) {
                if (it == k) {
                    val team = teamLiveData.value!!
                    team.shirt = v
                    teamLiveData.value = team
                }
            }
        }
        shirtSelection.show(childFragmentManager, SHIRT_SELECTION_FRAGMENT)
    }

    private fun saveInputTeamsNamesInViewModel(){
        if(binding.tournamentInputTeamName1.text.isNotEmpty()){
            viewModel.team1.value!!.name = binding.tournamentInputTeamName1.text.toString()
        }
        if(binding.tournamentInputTeamName2.text.isNotEmpty()){
            viewModel.team2.value!!.name = binding.tournamentInputTeamName2.text.toString()
        }
        if(binding.tournamentInputTeamName3.text.isNotEmpty()){
            viewModel.team3.value!!.name = binding.tournamentInputTeamName3.text.toString()
        }
        if(binding.tournamentInputTeamName4.text.isNotEmpty()){
            viewModel.team4.value!!.name = binding.tournamentInputTeamName4.text.toString()
        }
        if(binding.tournamentInputTeamName5.text.isNotEmpty()){
            viewModel.team5.value!!.name = binding.tournamentInputTeamName5.text.toString()
        }
        if(binding.tournamentInputTeamName6.text.isNotEmpty()){
            viewModel.team6.value!!.name = binding.tournamentInputTeamName6.text.toString()
        }
        if(binding.tournamentInputTeamName7.text.isNotEmpty()){
            viewModel.team7.value!!.name = binding.tournamentInputTeamName7.text.toString()
        }
        if(binding.tournamentInputTeamName8.text.isNotEmpty()){
            viewModel.team8.value!!.name = binding.tournamentInputTeamName8.text.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}