package com.samirmaciel.futstreet.modules.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.samirmaciel.futstreet.R
import com.samirmaciel.futstreet.databinding.FragmentHomeBinding
import com.samirmaciel.futstreet.shared.adapter.SlideDescriptionPager
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding get() = _binding!!
    lateinit var mSlideAdapter : SlideDescriptionPager
    private val timer = Timer()
    private val viewModel : HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initPagerSlide(viewModel.getDescriptions())
    }

    override fun onStart() {
        super.onStart()


        binding.buttonNewPlay.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_gameSettingFragment) }
        binding.buttonChampionship.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_tournamentOptionsFragment)
        }
    }


    private fun initPagerSlide(mList : MutableList<Int>){
       mSlideAdapter = SlideDescriptionPager(requireContext(), mList)
       binding.viewPageDescription.adapter = mSlideAdapter
        binding.tabIndicator.setupWithViewPager(binding.viewPageDescription)
    }

    inner class SlideTimer(private val fragment : Fragment) : TimerTask(){
        override fun run() {
            fragment.requireActivity().runOnUiThread(object : Runnable{
                override fun run() {
                    if(mSlideAdapter.mStringList.size > 0){
                        if(binding.viewPageDescription.currentItem < mSlideAdapter.mStringList.size - 1){
                            binding.viewPageDescription.currentItem++

                        }else{
                            binding.viewPageDescription.currentItem = 0
                        }
                    }
                }

            })
        }

    }

    override fun onResume() {
        super.onResume()
        timer.scheduleAtFixedRate(SlideTimer(this), 2000, 4000 )
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        _binding = null
    }
}