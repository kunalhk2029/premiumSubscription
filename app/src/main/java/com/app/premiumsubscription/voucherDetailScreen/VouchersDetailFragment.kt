package com.app.premiumsubscription.voucherDetailScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.premiumsubscription.R
import com.app.premiumsubscription.databinding.FragmentPremiumSubscriptionDetailBinding
import com.app.premiumsubscription.databinding.FragmentVouchersDetailBinding
import com.bumptech.glide.Glide


class VouchersDetailFragment : Fragment(R.layout.fragment_vouchers_detail) {

    var binding:FragmentVouchersDetailBinding?=null
    lateinit var voucherName:String
    lateinit var realPrice:String
    lateinit var ourPrice:String
    lateinit var description:String
    lateinit var picLink:String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentVouchersDetailBinding.bind(view)

        voucherName=requireArguments().getString("voucherName")!!
        realPrice=requireArguments().getString("realPrice")!!
        ourPrice=requireArguments().getString("ourPrice")!!
        description=requireArguments().getString("description")!!
        picLink=requireArguments().getString("picLink")!!
        binding?.let {
            Glide.with((it.pic)).load(picLink).into(it.pic)
            it.premiumname.text=voucherName
            it.realprice.text=realPrice
            it.ourprice.text=ourPrice
            it.description.text=description
            it.buybt.setOnClickListener {
                val text = "Hello I would like to buy $voucherName from your app."
                val inent = Intent().apply {
                    action= Intent.ACTION_VIEW
                    data= Uri.parse("https://wa.me/91-8178738180?text=$text")
                }
                println(text)
                startActivity(inent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}