package com.samirmaciel.futstreet.modules.gameSetting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentGamesettingsBinding
import com.samirmaciel.futstreet.modules.gameSetting.shirtSelectionFragment.ShirtSelectionDialog

class GameSettingFragment : Fragment(R.layout.fragment_gamesettings){

    private var _binding : FragmentGamesettingsBinding? = null
    private val binding : FragmentGamesettingsBinding get() = _binding!!
    private val viewModel : GameSettingViewModel by activityViewModels()

    private val SHIRT_SELECTION_FRAGMENT = "SHIRT_SELECTION_FRAGMENT"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      _binding = FragmentGamesettingsBinding.bind(view)

    }

    override fun onStart() {
        super.onStart()

        binding.selectShirtTeam1.setOnClickListener{
            val shirtSelection = ShirtSelectionDialog {
                for ((k, v) in viewModel.getListOfShirts()) {
                    if (it == k) {
                        binding.selectShirtTeam1.setImageResource(v)
                    }
                }
            }

            shirtSelection.show(childFragmentManager, SHIRT_SELECTION_FRAGMENT)
        }

        binding.selectShirtTeam2.setOnClickListener{
            val shirtSelectionDialog = ShirtSelectionDialog{
                for((k , v) in viewModel.getListOfShirts()){
                    if(it == k){
                        binding.selectShirtTeam2.setImageResource(v)
                    }
                }
            }

            shirtSelectionDialog.show(childFragmentManager, SHIRT_SELECTION_FRAGMENT)
        }

        binding.buttonReady.setOnClickListener{
            findNavController().navigate(R.id.action_gameSettingFragment_to_gameReadyFragment)
        }

        binding.buttonCancel.setOnClickListener{
            findNavController().navigate(R.id.action_gameSettingFragment_to_homeFragment)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}