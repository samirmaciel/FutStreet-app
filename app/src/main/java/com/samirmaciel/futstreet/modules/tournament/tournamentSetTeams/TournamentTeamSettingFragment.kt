package com.samirmaciel.futstreet.modules.tournament.tournamentSetTeams

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addValueStringResourcesDefaultTeamNames()
    }

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

            val listSortedTeams = geSortedTeams()

            for (i in 0..7){
                viewModel.getTeamNameMap()[i]!!.value = listSortedTeams[i].name
                viewModel.getTeamShirtMap()[i]!!.value = listSortedTeams[i].shirt
            }


            findNavController().navigate(R.id.action_tournamentSelectFragment_to_tournamentMatchSettingsFragment)
        }

        binding.selectShirtTeam1Tournament.setOnClickListener{ view ->
            //callAlertShirtSelect(viewModel.shirtTeam1)

            getInputShirtImage(binding.selectShirtTeam1Tournament, "shirt1")

        }



        binding.selectShirtTeam2Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.shirtTeam2) }
        binding.selectShirtTeam3Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.shirtTeam3) }
        binding.selectShirtTeam4Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.shirtTeam4) }
        binding.selectShirtTeam5Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.shirtTeam5) }
        binding.selectShirtTeam6Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.shirtTeam6) }
        binding.selectShirtTeam7Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.shirtTeam7) }
        binding.selectShirtTeam8Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.shirtTeam8) }

        viewModel.shirtTeam1.observe(this){ animateShirt(binding.selectShirtTeam1Tournament, it) }
        viewModel.shirtTeam2.observe(this){ animateShirt(binding.selectShirtTeam2Tournament, it) }
        viewModel.shirtTeam3.observe(this){ animateShirt(binding.selectShirtTeam3Tournament, it) }
        viewModel.shirtTeam4.observe(this){ animateShirt(binding.selectShirtTeam4Tournament, it) }
        viewModel.shirtTeam5.observe(this){ animateShirt(binding.selectShirtTeam5Tournament, it) }
        viewModel.shirtTeam6.observe(this){ animateShirt(binding.selectShirtTeam6Tournament, it) }
        viewModel.shirtTeam7.observe(this){ animateShirt(binding.selectShirtTeam7Tournament, it) }
        viewModel.shirtTeam8.observe(this){ animateShirt(binding.selectShirtTeam8Tournament, it) }

    }

    private fun geSortedTeams() : MutableList<Team> {
        val listTeams = mutableListOf<Team>()

        for (i in 0..7){
            listTeams.add(Team(name = viewModel.getTeamNameMap()[i]!!.value!!, shirt = viewModel.getTeamShirtMap()[i]!!.value!!))
        }
        listTeams.shuffle()

        return listTeams
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

    private fun getInputShirtImage(imageView: ImageView, shirt : String){
        val shirtSelection = ShirtSelectionDialog {
            for ((k, v) in viewModel.getListOfShirts()) {
                if (it == k) {
                    viewModel.mapShirtImageView[shirt] = v
                    animateShirt(imageView, v)
                }
            }
        }
        shirtSelection.show(childFragmentManager, SHIRT_SELECTION_FRAGMENT)
    }

    private fun callAlertShirtSelect(liveData : MutableLiveData<Int>){
        val shirtSelection = ShirtSelectionDialog {
            for ((k, v) in viewModel.getListOfShirts()) {
                if (it == k) {
                   liveData.value = v
                }
            }
        }
        shirtSelection.show(childFragmentManager, SHIRT_SELECTION_FRAGMENT)
    }

    private fun saveInputTeamsNamesInViewModel(){
        if(binding.tournamentInputTeamName1.text.isNotEmpty()){
            viewModel.teamName1.value = binding.tournamentInputTeamName1.text.toString()
        }

        if(binding.tournamentInputTeamName2.text.isNotEmpty()){
            viewModel.teamName2.value = binding.tournamentInputTeamName2.text.toString()
        }

        if(binding.tournamentInputTeamName3.text.isNotEmpty()){
            viewModel.teamName3.value = binding.tournamentInputTeamName3.text.toString()
        }

        if(binding.tournamentInputTeamName4.text.isNotEmpty()){
            viewModel.teamName4.value = binding.tournamentInputTeamName4.text.toString()
        }

        if(binding.tournamentInputTeamName5.text.isNotEmpty()){
            viewModel.teamName5.value = binding.tournamentInputTeamName5.text.toString()
        }

        if(binding.tournamentInputTeamName6.text.isNotEmpty()){
            viewModel.teamName6.value = binding.tournamentInputTeamName6.text.toString()
        }

        if(binding.tournamentInputTeamName7.text.isNotEmpty()){
            viewModel.teamName7.value = binding.tournamentInputTeamName7.text.toString()
        }

        if(binding.tournamentInputTeamName8.text.isNotEmpty()){
            viewModel.teamName8.value = binding.tournamentInputTeamName8.text.toString()
        }
    }

    private fun addValueStringResourcesDefaultTeamNames(){
        viewModel.teamName1.value = resources.getText(R.string.input_hint_team1).toString()
        viewModel.teamName2.value = resources.getText(R.string.input_hint_team2).toString()
        viewModel.teamName3.value = resources.getText(R.string.input_hint_team3).toString()
        viewModel.teamName4.value = resources.getText(R.string.input_hint_team4).toString()
        viewModel.teamName5.value = resources.getText(R.string.input_hint_team5).toString()
        viewModel.teamName6.value = resources.getText(R.string.input_hint_team6).toString()
        viewModel.teamName7.value = resources.getText(R.string.input_hint_team7).toString()
        viewModel.teamName8.value = resources.getText(R.string.input_hint_team8).toString()
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}