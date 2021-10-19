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
            val intent = Intent(GO_TO_TOURNAMENET_PAGE)
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



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}