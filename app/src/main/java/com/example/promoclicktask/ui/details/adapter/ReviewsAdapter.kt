package com.example.promoclicktask.ui.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
 import com.example.promoclicktask.databinding.ItemReviewsRvBinding
 import com.example.promoclicktask.pojo.details.Review

class ReviewsAdapter (val context: Context) :
    RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    private var items = arrayListOf<Review>()

    fun changeData(newData: ArrayList<Review>) {
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

    inner class ViewHolder(val binding: ItemReviewsRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) = with(itemView) {

            Glide.with(context)
                .load(item.image)
                .fitCenter()
                .into(binding.profileReviewImageView)

            binding.usernameReviewTextView.text = item.name
            binding.reviewTextView.text = item.comment
            binding.ratingReviewBar.rating = (item.rate / 2.0f)

            if (itemClickListener != null) {
                setOnClickListener {
                    itemClickListener!!.onClick(it, item)
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items.get(position))

    class ItemsDiffCallback(
        private val oldData: ArrayList<Review>,
        private val newData: ArrayList<Review>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].name == newData[newItemPosition].name
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
        fun onClick(view: View, item: Review)
    }
}