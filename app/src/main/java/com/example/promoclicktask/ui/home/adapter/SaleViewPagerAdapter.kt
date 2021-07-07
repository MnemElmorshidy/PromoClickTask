package com.example.promoclicktask.ui.home.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.promoclicktask.databinding.ItemSaleViewpagerBinding
import com.example.promoclicktask.pojo.home.Slider


class SaleViewPagerAdapter(var context: Context) : RecyclerView.Adapter<SaleViewPagerAdapter.ViewHolder>() {
    private var data = listOf<Slider>()


    fun changeData(newData: List<Slider>) {
        val oldData = data
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                oldData,
                newData
            )
        )
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    inner  class ViewHolder(val binding: ItemSaleViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Slider) = with(itemView) {

            Glide.with(context)
                .load(product.image)
                .centerCrop()
                .into(binding.imageView)

            binding.saleTitleVp.text = product.title!!

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSaleViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])


        Glide.with(context)
            .load(data[position].image)
            .centerCrop()
            .into(holder.binding.imageView)

        holder.binding.saleTitleVp.text = data[position].title!!
        Log.i("TAG", "onBindViewHolder: ")
    }

    class ItemsDiffCallback(
        private val oldData: List<Slider>,
        private val newData: List<Slider>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

    }
}