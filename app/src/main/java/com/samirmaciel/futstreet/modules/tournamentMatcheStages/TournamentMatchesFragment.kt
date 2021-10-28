package com.samirmaciel.futstreet.modules.tournamentMatcheStages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentBinding
import com.samirmaciel.futstreet.shared.adapter.TabPagerAdapter
import com.samirmaciel.futstreet.shared.model.Team

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
                    tab.text = resources.getText(R.string.title_tab_quarters)
                }

                1 -> {
                    tab.text = resources.getText(R.string.title_tab_semi)
                }

                2 -> {
                    tab.text = resources.getText(R.string.title_tab_final)
                }
            }
        }.attach()
    }

    private fun getTournamentParams(){

        val argumentKeyToName = "teamName"
        val argumentKeyToShirt = "shirtTeam"

        val listOfTeams = mutableListOf<Team>()

        for (i in 1..8){
            listOfTeams.add(Team(name = arguments?.getString("${argumentKeyToName}$i")!!, shirt = arguments?.getInt("${argumentKeyToShirt}$i")!!))
        }

        listOfTeams.shuffle()

        for (i in 0..7){
            matchesViewModel.getMapWithQuartersNamesLiveData()[i]!!.value = listOfTeams[i].name
            matchesViewModel.getMapWithQuartersShirtLiveData()[i]!!.value = listOfTeams[i].shirt
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}