package com.note.shop.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.note.shop.adapter.CartAdapter
import com.note.shop.helper.ManagmentCart
import com.note.shop.model.ItemsModel
import com.note.shop.R
import com.note.shop.databinding.FragmentCartBinding
import com.note.shop.helper.ChangeNumberItemsListener
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class CartFragment : Fragment(R.layout.fragment_cart) {
    private var fragmentCartBinding: FragmentCartBinding? = null
    private lateinit var managmentCart: ManagmentCart


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCartBinding.bind(view)
        fragmentCartBinding = binding

        managmentCart = ManagmentCart(requireContext())

        setVariable()
        initCartList()
        calculatorCart()

    }

    private fun initCartList() {
        viewLifecycleOwner.lifecycleScope.launch {
            val listCart = managmentCart.getListCart()
            fragmentCartBinding!!.viewCart.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            fragmentCartBinding!!.viewCart.adapter =
                CartAdapter(listCart as ArrayList<ItemsModel>, requireContext(), viewLifecycleOwner.lifecycleScope, object :
                    ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculatorCart()
                    }
                })
            with(fragmentCartBinding!!) {
                emptyTxt.visibility =
                    if (listCart.isEmpty()) View.VISIBLE else View.GONE
                scrollView3.visibility =
                    if (listCart.isEmpty()) View.GONE else View.VISIBLE
            }
        }
    }





    private fun setVariable() {
        fragmentCartBinding!!.apply {
            backBtn.setOnClickListener {
                findNavController().navigate(R.id.action_cartFragment2_to_homeFragment2)
            }

            method1.setOnClickListener {
                method1.setBackgroundResource(R.drawable.green_bg_selected)
                methosIc1.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green))
                methodTitle1.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                methosSub1.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

                method2.setBackgroundResource(R.drawable.grey_bg_selected)
                methodIc2.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))
                methodTitle2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                methodSub2.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            }

            method2.setOnClickListener {
                method2.setBackgroundResource(R.drawable.green_bg_selected)
                methodIc2.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green))
                methodTitle2.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                methodSub2.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

                method1.setBackgroundResource(R.drawable.grey_bg_selected)
                methosIc1.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))
                methodTitle1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                methosSub1.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            }
        }
    }


    private fun calculatorCart() {
        viewLifecycleOwner.lifecycleScope.launch {
            val totalFee = managmentCart.getTotalFee()
            val percentTax = 0.02
            val delivery = 10.0

            val tax = (totalFee * percentTax).let { (it * 100).roundToLong() / 100.0 }
            val total = (totalFee + tax + delivery).let { (it * 100).roundToLong() / 100.0 }
            val itemTotal = (totalFee * 100).roundToInt() / 100.0

            fragmentCartBinding?.let { binding ->
                binding.totalFeeTxt.text = String.format("$%.2f", itemTotal)
                binding.taxTxt.text = String.format("$%.2f", tax)
                binding.deliveryTxt.text = String.format("$%.2f", delivery)
                binding.totalTxt.text = String.format("$%.2f", total)
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        fragmentCartBinding = null
    }
}