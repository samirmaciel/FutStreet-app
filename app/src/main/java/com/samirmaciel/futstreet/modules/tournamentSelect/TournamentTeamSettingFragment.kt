package com.samirmaciel.futstreet.modules.tournamentSelect

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentteamsettingBinding
import com.samirmaciel.futstreet.modules.gameSetting.shirtSelectionFragment.ShirtSelectionDialog

import com.samirmaciel.futstreet.shared.const.GO_TO_TOURNAMENET_PAGE
import com.samirmaciel.futstreet.shared.const.SHIRT_SELECTION_FRAGMENT


class TournamentTeamSettingFragment : Fragment(R.layout.fragment_tournamentteamsetting) {

    private var _binding : FragmentTournamentteamsettingBinding? = null
    private val binding : FragmentTournamentteamsettingBinding get() = _binding!!

    private val viewModel : TournamentTeamSettingViewModel by activityViewModels()

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
            val alert = AlertDialog.Builder(requireContext()).apply {
                setTitle(resources.getText(R.string.text_ask_cancel_tournament))
                setPositiveButton(resources.getText(R.string.yes)){
                        _, _, -> findNavController().navigate(R.id.action_tournamentSelectFragment_to_homeFragment)
                }
                setNegativeButton(resources.getText(R.string.no), null)
            }.create().show()
        }

        binding.buttonReadyTournament.setOnClickListener{
            saveInputTeamNames()
            val intent = Intent(GO_TO_TOURNAMENET_PAGE)
            intent.putExtra("shirtTeam1", viewModel.shirtTeam1.value)
            intent.putExtra("teamName1", viewModel.teamName1.value)

            intent.putExtra("shirtTeam2", viewModel.shirtTeam2.value)
            intent.putExtra("teamName2", viewModel.teamName2.value)

            intent.putExtra("shirtTeam3", viewModel.shirtTeam3.value)
            intent.putExtra("teamName3", viewModel.teamName3.value)

            intent.putExtra("shirtTeam4", viewModel.shirtTeam4.value)
            intent.putExtra("teamName4", viewModel.teamName4.value)

            intent.putExtra("shirtTeam5", viewModel.shirtTeam5.value)
            intent.putExtra("teamName5", viewModel.teamName5.value)

            intent.putExtra("shirtTeam6", viewModel.shirtTeam6.value)
            intent.putExtra("teamName6", viewModel.teamName6.value)

            intent.putExtra("shirtTeam7", viewModel.shirtTeam7.value)
            intent.putExtra("teamName7", viewModel.teamName7.value)

            intent.putExtra("shirtTeam8", viewModel.shirtTeam8.value)
            intent.putExtra("teamName8", viewModel.teamName8.value)

            requireActivity().sendBroadcast(intent)
        }

        binding.selectShirtTeam1Tournament.setOnClickListener{ callAlertShirtSelect(viewModel.shirtTeam1) }
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

    private fun saveInputTeamNames(){
        if(binding.tournamentInputTeamName1.text.isNotEmpty()){
            viewModel.teamName1.value = binding.tournamentInputTeamName2.text.toString()
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