package com.app.premiumsubscription.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.premiumsubscription.R
import com.app.premiumsubscription.models.VouchersModel
import com.bumptech.glide.Glide

class VouchersAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VouchersModel>() {

        override fun areItemsTheSame(
            oldItem: VouchersModel,
            newItem: VouchersModel
        ): Boolean {
            return oldItem.voucherName == newItem.voucherName
        }

        override fun areContentsTheSame(
            oldItem: VouchersModel,
            newItem: VouchersModel
        ): Boolean {
            return oldItem.voucherName == newItem.voucherName
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VouchersViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VouchersViewholder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<VouchersModel>) {
        differ.submitList(list)
    }

    class VouchersViewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: VouchersModel) = with(itemView) {
            itemView.setOnClickListener {
            }

            val knowmore = findViewById<CardView>(R.id.knowmorecd)
            knowmore.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            val im = findViewById<ImageView>(R.id.preview)
            val realprice = findViewById<TextView>(R.id.realprice)
            val ourprice = findViewById<TextView>(R.id.ourprice)

            Glide.with(im).load(item.picLink).into(im)
            val title = findViewById<TextView>(R.id.title)
            realprice.text = "Real Price : Rs${item.realprice}"
            ourprice.text = "Our Price : Rs${item.ourprice}"
            title.text = item.voucherName
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: VouchersModel)
    }
}