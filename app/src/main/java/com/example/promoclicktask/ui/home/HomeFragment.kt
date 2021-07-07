package com.example.promoclicktask.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.promoclicktask.databinding.FragmentHomeBinding
import com.example.promoclicktask.pojo.home.HotProductPaidStatu
import com.example.promoclicktask.ui.home.adapter.HotDiscountRecyclerAdapter
import com.example.promoclicktask.ui.home.adapter.SaleViewPagerAdapter
import com.example.promoclicktask.ui.home.adapter.SponsorsRecyclerAdapter
import com.example.promoclicktask.ui.home.adapter.VendorsRecyclerAdapter
import com.example.promoclicktask.utils.Resource

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sponsorsRecyclerAdapter: SponsorsRecyclerAdapter
    private lateinit var vendorsRecyclerAdapter: VendorsRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPagerAdapter: SaleViewPagerAdapter
    private lateinit var hotDiscountRecyclerAdapter: HotDiscountRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        viewPagerAdapter = SaleViewPagerAdapter(requireContext())
        binding.saleViewPager.adapter = viewPagerAdapter

        sponsorsRecyclerAdapter = SponsorsRecyclerAdapter(requireContext())
        binding.sponsorsRv.adapter = sponsorsRecyclerAdapter

        vendorsRecyclerAdapter = VendorsRecyclerAdapter(requireContext())
        binding.vendorsRv.adapter = vendorsRecyclerAdapter


        hotDiscountRecyclerAdapter = HotDiscountRecyclerAdapter(requireContext())
        binding.hotProductRv.adapter = hotDiscountRecyclerAdapter

        hotDiscountRecyclerAdapter.itemClickListener =
            object : HotDiscountRecyclerAdapter.ItemClickListener {
                override fun onClick(id: Int) {
                    val action = HomeFragmentDirections.actionNavigationHomeToDetailsFragment(id)
                    view.findNavController().navigate(action)
                    Log.i("TAG2", id.toString())

                }
            }

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.data.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.viewPagerProgress.showShimmerAdapter()
                }
                Resource.Status.ERROR -> {
                    Log.i("TAG", "error")
                    binding.viewPagerProgress.showShimmerAdapter()
                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        binding.viewPagerProgress.hideShimmerAdapter()
                        viewPagerAdapter.changeData(it!!.Sliders)
                        binding.dotsIndicator.setViewPager2(binding.saleViewPager)
                        sponsorsRecyclerAdapter.changeData(it!!.Sponsors)
                        vendorsRecyclerAdapter.changeData(it!!.vendor)
                        hotDiscountRecyclerAdapter.changeData(it!!.hot_product_paid_status as ArrayList<HotProductPaidStatu>)
                    }
                }
            }
        })


    }

}