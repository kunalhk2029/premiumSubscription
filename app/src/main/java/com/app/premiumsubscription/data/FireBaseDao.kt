package com.app.premiumsubscription.data

import androidx.lifecycle.MutableLiveData
import com.app.premiumsubscription.Utils.DataState
import com.app.premiumsubscription.models.PremiumSubscriptionsModel
import com.app.premiumsubscription.models.VouchersModel
import com.app.premiumsubscription.state.MainViewState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FireBaseDao {
    private val db = Firebase.firestore


    fun getData(): MutableLiveData<DataState<MainViewState>> {
        return object : MutableLiveData<DataState<MainViewState>>() {
            override fun onActive() {
                super.onActive()
                var error = false
                val plist = mutableListOf<PremiumSubscriptionsModel>()
                val vlist = mutableListOf<VouchersModel>()
                postValue(DataState.loading())
                val premiumCollection = db.collection("premium")
                val voucherCollection = db.collection("voucher")
                CoroutineScope(IO).launch {
                    var s = 0
                    premiumCollection.get().addOnCompleteListener {
                        if (it.isSuccessful) {
                            plist.addAll(it.result.documents.map {
                                PremiumSubscriptionsModel(
                                    realprice = it.get("realPrice").toString().toInt(),
                                    ourprice = it.get("ourPrice").toString().toInt(),
                                    subscriptionName = it.get("name").toString(),
                                    description = it.get("description").toString(),
                                    picLink = it.get("picLink").toString(),
                                    duration = it.get("duration").toString()
                                )
                            })
                            s++
                        } else {
                            s++
                            println("Debug error 1 = " + it.exception?.message)
                            error = true
                        }
                    }

                    voucherCollection.get().addOnCompleteListener {
                        if (it.isSuccessful) {
                            vlist.addAll(it.result.documents.map {
                                VouchersModel(
                                    realprice = it.get("realPrice").toString().toInt(),
                                    ourprice = it.get("ourPrice").toString().toInt(),
                                    voucherName = it.get("name").toString(),
                                    description = it.get("description").toString(),
                                    picLink = it.get("picLink").toString(),
                                )
                            })
                            s++
                        } else {
                            s++
                            println("Debug error 2 = " + it.exception?.message)
                            error = true
                        }
                    }

                    while (s != 2) {
                        delay(500L)
                    }
                    if (error == false) {
                        postValue(DataState.success(MainViewState(plist, vlist)))
                    } else {
                        postValue(DataState.error("Error"))
                    }
                }
            }
        }
    }
}