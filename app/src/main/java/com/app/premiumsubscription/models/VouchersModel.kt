package com.app.premiumsubscription.models

data class VouchersModel(
    val realprice:Int,
    val ourprice:Int,
    val voucherName:String,
    val description:String,
    var picLink:String=""
)