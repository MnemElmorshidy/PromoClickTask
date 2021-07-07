package com.example.promoclicktask.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.promoclicktask.databinding.ItemCategoryRvBinding
import com.example.promoclicktask.pojo.home.Sponsor

class SponsorsRecyclerAdapter(val context: Context): RecyclerView.Adapter<SponsorsRecyclerAdapter.ViewHolder>() {
    private var items = listOf<Sponsor>()
    fun changeData(newData: List<Sponsor>) {
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

    class ViewHolder(val binding: ItemCategoryRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: Sponsor) = with(itemView) {
            Glide.with(context)
                .load(item.image)
                .fitCenter()
                .into(binding.ivCategoryItem)

            setOnClickListener {}

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {

            return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(items.get(position))


    class ItemsDiffCallback(
        private val oldData: List<Sponsor>,
        private val newData: List<Sponsor>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].image == newData[newItemPosition].image
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

}