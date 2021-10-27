package com.samirmaciel.futstreet.modules.tournamentMatcheStages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentBinding
import com.samirmaciel.futstreet.shared.adapter.TabPagerAdapter

class TournamentMatchesFragment : Fragment(R.layout.fragment_tournament) {

    private var _binding : FragmentTournamentBinding? = null
    private val binding : FragmentTournamentBinding get() = _binding!!

    private val matchesViewModel : TournamentMatchesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getTournamentParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentBinding.bind(view)
        initComponets()
    }


    private fun initComponets(){
        val adapter = TabPagerAdapter(childFragmentManager, lifecycle)
        binding.tournamenteViewPager.adapter = adapter
        binding.tournamentTab.tabSelectedIndicator.alpha = 0

        TabLayoutMediator(binding.tournamentTab, binding.tournamenteViewPager){ tab,position ->

            when(position){

                0 -> {
                    tab.text = "Quartas"
                }

                1 -> {
                    tab.text = "Semi"
                }

                2 -> {
                    tab.text = "Final"
                }
            }
        }.attach()
    }

    private fun getTournamentParams(){
        matchesViewModel.nameQ11.value = arguments?.getString("teamName1")
        matchesViewModel.shirtQ11.value = arguments?.getInt("shirtTeam1", R.drawable.shirt_select)
        matchesViewModel.nameQ21.value = arguments?.getString("teamName2")
        matchesViewModel.shirtQ21.value = arguments?.getInt("shirtTeam2", R.drawable.shirt_select)
        matchesViewModel.nameQ32.value = arguments?.getString("teamName3")
        matchesViewModel.shirtQ32.value = arguments?.getInt("shirtTeam3", R.drawable.shirt_select)
        matchesViewModel.nameQ42.value = arguments?.getString("teamName4")
        matchesViewModel.shirtQ42.value = arguments?.getInt("shirtTeam4", R.drawable.shirt_select)
        matchesViewModel.nameQ53.value = arguments?.getString("teamName5")
        matchesViewModel.shirtQ53.value = arguments?.getInt("shirtTeam5", R.drawable.shirt_select)
        matchesViewModel.nameQ63.value = arguments?.getString("teamName6")
        matchesViewModel.shirtQ63.value = arguments?.getInt("shirtTeam6", R.drawable.shirt_select)
        matchesViewModel.nameQ74.value = arguments?.getString("teamName7")
        matchesViewModel.shirtQ74.value = arguments?.getInt("shirtTeam7", R.drawable.shirt_select)
        matchesViewModel.nameQ84.value = arguments?.getString("teamName8")
        matchesViewModel.shirtQ84.value = arguments?.getInt("shirtTeam8", R.drawable.shirt_select)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}