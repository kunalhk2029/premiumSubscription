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
import com.app.premiumsubscription.models.PremiumSubscriptionsModel
import com.bumptech.glide.Glide

class PremiumSubscriptionAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PremiumSubscriptionsModel>() {

        override fun areItemsTheSame(
            oldItem: PremiumSubscriptionsModel,
            newItem: PremiumSubscriptionsModel
        ): Boolean {
            return oldItem.subscriptionName == newItem.subscriptionName
        }

        override fun areContentsTheSame(
            oldItem: PremiumSubscriptionsModel,
            newItem: PremiumSubscriptionsModel
        ): Boolean {
            return oldItem.subscriptionName == newItem.subscriptionName
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return premiumSubcriptionViewholder(
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
            is premiumSubcriptionViewholder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<PremiumSubscriptionsModel>) {
        differ.submitList(list)
    }

    class premiumSubcriptionViewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: PremiumSubscriptionsModel) = with(itemView) {
            itemView.setOnClickListener {
            }
            val im = findViewById<ImageView>(R.id.preview)
            val realprice = findViewById<TextView>(R.id.realprice)
            val ourprice = findViewById<TextView>(R.id.ourprice)
            val title = findViewById<TextView>(R.id.title)
            val knowmore = findViewById<CardView>(R.id.knowmorecd)
            knowmore.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            Glide.with(im).load(item.picLink).into(im)
            realprice.text = "Real Price : Rs${item.realprice}"
            ourprice.text = "Our Price : Rs${item.ourprice}"
            title.text=item.subscriptionName

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: PremiumSubscriptionsModel)
    }
}