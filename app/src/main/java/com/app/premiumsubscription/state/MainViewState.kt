package com.app.premiumsubscription.state

import com.app.premiumsubscription.models.PremiumSubscriptionsModel
import com.app.premiumsubscription.models.VouchersModel

data class MainViewState(
    var premiumList: List<PremiumSubscriptionsModel>?=null,
    var voucherList: List<VouchersModel>?=null)
