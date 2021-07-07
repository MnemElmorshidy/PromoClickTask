package com.example.promoclicktask.ui.details.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.promoclicktask.databinding.ItemRelatedProductRvBinding
import com.example.promoclicktask.pojo.details.RelatedProduct
import com.example.promoclicktask.pojo.home.RecommendPaidStatu

class RelatedRecyclerAdapter(val context: Context) :
    RecyclerView.Adapter<RelatedRecyclerAdapter.ViewHolder>() {
    private var items = arrayListOf<RelatedProduct>()

    fun changeData(newData: ArrayList<RelatedProduct>) {
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

    var itemClickListener: ItemClickListener? = null

    inner class ViewHolder(val binding: ItemRelatedProductRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: RelatedProduct) = with(itemView) {

            Glide.with(context)
                .load(product.image)
                .fitCenter()
                .into(binding.imgProductSaleIv)

            binding.productNameSaleTc.text = product.name
            binding.dateTv.text = "exp_date " + product.exp_date
            binding.oldCostSaleTv.text = product.old_price.toString() + " EGP"
            binding.costSaleTv.text = product.new_price.toString() + " EGP"

            binding.ratingBar.rating = ((product.rate / 2.0).toFloat())

            if (itemClickListener != null) {
                setOnClickListener {
                    itemClickListener!!.onClick(it, product)
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRelatedProductRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items.get(position))

    class ItemsDiffCallback(
        private val oldData: ArrayList<RelatedProduct>,
        private val newData: ArrayList<RelatedProduct>
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
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

    }

    interface ItemClickListener {
        fun onClick(view: View, item: RelatedProduct)
    }
}