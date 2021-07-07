package com.example.promoclicktask.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.promoclicktask.databinding.FragmentDetailsBinding
import com.example.promoclicktask.pojo.details.Gallary
import com.example.promoclicktask.pojo.details.Products
import com.example.promoclicktask.pojo.details.RelatedProduct
import com.example.promoclicktask.pojo.details.Review
import com.example.promoclicktask.ui.details.adapter.RelatedRecyclerAdapter
import com.example.promoclicktask.ui.details.adapter.ReviewsAdapter
import com.example.promoclicktask.ui.details.adapter.ViewPagerAdapter
import com.example.promoclicktask.ui.home.HomeViewModel
import com.example.promoclicktask.utils.Resource
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var relatedRecyclerAdapter: RelatedRecyclerAdapter
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val args by navArgs<DetailsFragmentArgs>()

    companion object {
        var loc: LocationChange? = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root



        relatedRecyclerAdapter = RelatedRecyclerAdapter(requireContext())
        binding.relatedProductsRv.adapter = relatedRecyclerAdapter

        reviewsAdapter = ReviewsAdapter(requireContext())
        binding.reviewsRv.adapter = reviewsAdapter

        viewPagerAdapter = ViewPagerAdapter(requireContext())
        binding.viewpagerRV.adapter = viewPagerAdapter

        binding.backIc.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToNavigationHome()
            view.findNavController().navigate(action)
        }


        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsViewModel.getData(args.id)

        detailsViewModel.data.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.viewPagerProgress.showShimmerAdapter()
                }
                Resource.Status.SUCCESS -> {
                    binding.viewPagerProgress.hideShimmerAdapter()
                    Log.i("TAG", it.data!!.Products.Gallary.toString())
                    if (it.data.Products.Gallary.isNotEmpty()) {
                        viewPagerAdapter.changeData(it.data.Products.Gallary as ArrayList<Gallary>)
                    }

                    if (it.data.Products.review.isNotEmpty()) {
                        reviewsAdapter.changeData(it.data.Products.review as ArrayList<Review>)
                    }

                    if (it.data.Related_product.isNotEmpty()) {
                        relatedRecyclerAdapter.changeData(it!!.data!!.Related_product as ArrayList<RelatedProduct>)
                    }

                    if (loc != null) {
                        val currentLatLong = LatLng(
                            it.data.Products.lat.toDouble(),
                            it.data.Products.lng.toDouble()
                        )
                        loc!!.onChange(currentLatLong)
                    }
                    updateDetails(it.data.Products)
                }
                Resource.Status.ERROR -> {
                    Log.i("TAG", it.message.toString())
                    binding.viewPagerProgress.showShimmerAdapter()
                }
            }
        })


    }

    private fun updateDetails(products: Products) {

//        val date = SimpleDateFormat("dd-MM-yyyy").parse("14-02-2018")
//        Log.i("MyApp", "updateDetails: " + date.time)
//
//        val calendar = Calendar.getInstance()
//        calendar.add(Calendar.DAY_OF_YEAR, -3)
//
//        Log.i("MyApp", "90 days ago:" + calendar.time.toString())


        binding.address.text = products!!.address
        binding.phone.text = products!!.mobile
        binding.email.text = products!!.email
        binding.description.text = products!!.description
        binding.endDate.text ="exp_date " + products!!.exp_date
        binding.title.text = products!!.name
        binding.view.text = products!!.view.toString()
        binding.priceTv.text = products!!.new_price.toString()
        binding.oldPriceTv.text = products!!.old_price.toString()

        viewPagerAdapter.itemClickListener = object : ViewPagerAdapter.ItemClickListener {
            override fun onClick(image: String) {
                Glide.with(context!!)
                    .load(image)
                    .fitCenter()
                    .into(binding.viewpagerImg)
            }
        }

             Glide.with(requireContext())
                .load(products.image)
                .fitCenter()
                .into(binding.viewpagerImg)


    }

    interface LocationChange {
        fun onChange(location: LatLng)
    }
}