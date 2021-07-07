package com.example.promoclicktask.ui.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.promoclicktask.databinding.ItemViewpagerRvBinding
import com.example.promoclicktask.pojo.details.Gallary


class ViewPagerAdapter(val context: Context) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    private var items = arrayListOf<Gallary>()

    fun changeData(newData: ArrayList<Gallary>) {
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

    inner class ViewHolder(val binding: ItemViewpagerRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Gallary) = with(itemView) {

            Glide.with(context)
                .load(item.images)
                .fitCenter()
                .into(binding.imgViewpager)

            if (itemClickListener != null) {
                setOnClickListener {
                    itemClickListener!!.onClick(item.images)
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemViewpagerRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items.get(position))

    class ItemsDiffCallback(
        private val oldData: ArrayList<Gallary>,
        private val newData: ArrayList<Gallary>
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
        fun onClick(image: String)
    }
}
