package com.example.promoclicktask.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.promoclicktask.databinding.ItemHotProductRvBinding
import com.example.promoclicktask.pojo.home.HotProductPaidStatu

class HotDiscountRecyclerAdapter(val context: Context) : RecyclerView.Adapter<HotDiscountRecyclerAdapter.ViewHolder>() {
    private var items = arrayListOf<HotProductPaidStatu>()

    fun changeData(newData:ArrayList<HotProductPaidStatu>){
        val oldData = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                oldData,
                newData
            )
        )
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    var itemClickListener : ItemClickListener? = null

    inner class ViewHolder(val binding: ItemHotProductRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : HotProductPaidStatu) = with(itemView){

            Glide.with(context)
                .load(item.image)
                .fitCenter()
                .into(binding.imgProductSaleIv)

            binding.productNameSaleTc.text = item.name
            binding.costSaleTv.text = item.new_price.toString()
            binding.oldCostSaleTv.text = item.old_price.toString()
            binding.endDate.text = "exp_date " + item.exp_date
            binding.ratingTv.text = item.rate.toString()
            binding.ratingBar.rating = (item.rate / 2.0f)


            if(itemClickListener != null) {
                setOnClickListener {
                    itemClickListener!!.onClick(item.id)
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHotProductRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items.get(position))

    class ItemsDiffCallback(
        private val oldData:ArrayList<HotProductPaidStatu>,
        private val newData:ArrayList<HotProductPaidStatu>
    ): DiffUtil.Callback() {
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
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

    }
    interface ItemClickListener{
        fun onClick(id: Int)
    }
}




