package com.samirmaciel.futstreet.shared.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages.stages.FinalTournamentFragment
import com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages.stages.QuartersTournamentFragment
import com.samirmaciel.futstreet.modules.tournament.tournamentMatchsStages.stages.SemiTournamentFragment


class TabPagerAdapter(private val fm : FragmentManager, private val lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){

            0 -> {
                QuartersTournamentFragment()
            }

            1 -> {
                SemiTournamentFragment()
            }

            2 -> {

                FinalTournamentFragment()
            }

            else -> {
                Fragment()
            }
        }
    }


}