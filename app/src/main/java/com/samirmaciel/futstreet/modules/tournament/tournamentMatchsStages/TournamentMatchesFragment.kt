package com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentBinding
import com.samirmaciel.futstreet.modules.tournament.TournamentViewModel
import com.samirmaciel.futstreet.shared.adapter.TabPagerAdapter

class TournamentMatchesFragment : Fragment(R.layout.fragment_tournament) {

    private var _binding : FragmentTournamentBinding? = null
    private val binding : FragmentTournamentBinding get() = _binding!!

    private val viewModel : TournamentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTournamentBinding.bind(view)
        initComponents()
    }

    override fun onResume() {
        super.onResume()

        setTeamsOnQuarters()
    }


    private fun initComponents(){
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

    private fun setTeamsOnQuarters(){

        for (i in 0..7){
            viewModel.getMapWithQuartersNamesLiveData()[i]!!.value = viewModel.getTeamNameMap()[i]!!.value
            viewModel.getMapWithQuartersShirtLiveData()[i]!!.value = viewModel.getTeamShirtMap()[i]!!.value
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}