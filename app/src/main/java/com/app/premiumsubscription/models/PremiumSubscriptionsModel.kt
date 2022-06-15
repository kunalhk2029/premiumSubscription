package com.app.premiumsubscription.models

data class PremiumSubscriptionsModel(
    val realprice: Int,
    val ourprice: Int,
    val subscriptionName: String,
    val description: String,
    var picLink: String = "",
    var duration: String = "",
)