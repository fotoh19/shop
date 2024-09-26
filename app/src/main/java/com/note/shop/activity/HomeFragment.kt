package com.note.shop.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.note.shop.adapter.CategoryAdapter
import com.note.shop.adapter.RecommendedAdapter
import com.note.shop.adapter.SliderAdapter
import com.note.shop.model.SliderModel
import com.note.shop.R
import com.note.shop.viewmodel.MainViewModel
import com.note.shop.viewmodel.UiState
import com.note.shop.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private val viewModel: MainViewModel by lazy { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        fragmentHomeBinding = binding

        initBanner()
        initCategory()
        initRecommended()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        fragmentHomeBinding!!.cartBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment2)
        }
    }

    private fun initRecommended() {
        fragmentHomeBinding!!.progresRecomm.visibility = View.VISIBLE

        viewModel.recommended.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    fragmentHomeBinding!!.progresRecomm.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    fragmentHomeBinding!!.progresRecomm.visibility = View.GONE
                    fragmentHomeBinding!!.viewRecomm.layoutManager =
                        GridLayoutManager(requireContext(), 2)

                    val recommendedAdapter = RecommendedAdapter()
                    fragmentHomeBinding!!.viewRecomm.adapter = recommendedAdapter
                    recommendedAdapter.submitList(state.data)
                }
                is UiState.Error -> {
                    fragmentHomeBinding!!.progresRecomm.visibility = View.GONE
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.loadRecommended()
    }

    private fun initCategory() {
        fragmentHomeBinding?.progressCategory?.visibility = View.VISIBLE

        viewModel.categories.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    fragmentHomeBinding?.progressCategory?.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    fragmentHomeBinding?.progressCategory?.visibility = View.GONE
                    fragmentHomeBinding?.viewCategory?.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                    val categoryAdapter = CategoryAdapter()
                    fragmentHomeBinding?.viewCategory?.adapter = categoryAdapter
                    categoryAdapter.submitList(state.data)
                }
                is UiState.Error -> {
                    fragmentHomeBinding?.progressCategory?.visibility = View.GONE
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.loadCategory()
    }

    private fun banners(image: List<SliderModel>) {
        fragmentHomeBinding!!.viewPager2.adapter =
            SliderAdapter(image, fragmentHomeBinding!!.viewPager2)
        fragmentHomeBinding!!.viewPager2.clipToPadding = false
        fragmentHomeBinding!!.viewPager2.clipChildren = false
        fragmentHomeBinding!!.viewPager2.offscreenPageLimit = 3
        fragmentHomeBinding!!.viewPager2.getChildAt(0).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        fragmentHomeBinding!!.viewPager2.setPageTransformer(compositePageTransformer)

        if (image.size > 1) {
            fragmentHomeBinding!!.dotindecator.visibility = View.VISIBLE
            fragmentHomeBinding!!.dotindecator.attachTo(fragmentHomeBinding!!.viewPager2)
        }
    }

    private fun initBanner() {
        fragmentHomeBinding!!.progressBarSlider.visibility = View.VISIBLE

        viewModel.banners.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    fragmentHomeBinding!!.progressBarSlider.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    fragmentHomeBinding!!.progressBarSlider.visibility = View.GONE
                    banners(state.data)
                }
                is UiState.Error -> {
                    fragmentHomeBinding!!.progressBarSlider.visibility = View.GONE
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.loadBanners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHomeBinding = null
    }
}
