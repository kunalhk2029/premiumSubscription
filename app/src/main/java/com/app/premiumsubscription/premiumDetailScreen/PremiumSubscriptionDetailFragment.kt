package com.app.premiumsubscription.premiumDetailScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.premiumsubscription.R
import com.app.premiumsubscription.databinding.FragmentPremiumSubscriptionDetailBinding
import com.bumptech.glide.Glide

class PremiumSubscriptionDetailFragment : Fragment(R.layout.fragment_premium_subscription_detail) {
    private var binding: FragmentPremiumSubscriptionDetailBinding? = null
    lateinit var subscriptionName:String
    lateinit var realPrice:String
    lateinit var ourPrice:String
    lateinit var description:String
    lateinit var duration:String
    lateinit var picLink:String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentPremiumSubscriptionDetailBinding.bind(view)
        subscriptionName=requireArguments().getString("subscriptionName")!!
        realPrice=requireArguments().getString("realPrice")!!
        ourPrice=requireArguments().getString("ourPrice")!!
        description=requireArguments().getString("description")!!
        duration=requireArguments().getString("duration")!!
        picLink=requireArguments().getString("picLink")!!
        binding?.let {
            Glide.with((it.pic)).load(picLink).into(it.pic)
           it.realprice.text=realPrice
           it.premiumname.text=subscriptionName
           it.ourprice.text=ourPrice
           it.duration.text=duration
           it.description.text=description
            it.buybt.setOnClickListener {
                val text = "Hello I would like to buy $subscriptionName from your app."
                val inent = Intent().apply {
                    action=Intent.ACTION_VIEW
                    data= Uri.parse("https://wa.me/91-8178738180?text=$text")
                }
                startActivity(inent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding= null
    }
}