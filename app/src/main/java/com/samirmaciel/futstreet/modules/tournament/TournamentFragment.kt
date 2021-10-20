package com.samirmaciel.futstreet.modules.tournament

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentTournamentBinding
import com.samirmaciel.futstreet.shared.adapter.TabPagerAdapter

class TournamentFragment : Fragment(R.layout.fragment_tournament) {

    private var _binding : FragmentTournamentBinding? = null
    private val binding : FragmentTournamentBinding get() = _binding!!

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}