package com.note.shop.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.note.shop.R
import com.note.shop.databinding.FragmentIntroBinding


class IntroFragment : Fragment(R.layout.fragment_intro) {
    private var fragmentIntroBinding: FragmentIntroBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentIntroBinding.bind(view)
        fragmentIntroBinding = binding

        val startBtn: AppCompatButton = view.findViewById(R.id.startButton)
        startBtn.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentIntroBinding = null
    }
}