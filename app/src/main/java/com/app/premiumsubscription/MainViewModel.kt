package com.app.premiumsubscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.premiumsubscription.Utils.DataState
import com.app.premiumsubscription.data.FireBaseDao
import com.app.premiumsubscription.models.PremiumSubscriptionsModel
import com.app.premiumsubscription.models.VouchersModel
import com.app.premiumsubscription.state.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(): ViewModel() {
    private val dao:FireBaseDao = FireBaseDao()
    private val _mainViewSate: MutableLiveData<MainViewState> = MutableLiveData()
    val mainViewSate:LiveData<MainViewState> = _mainViewSate

    private val _mainDataSate: MutableLiveData<DataState<MainViewState>> = MutableLiveData()
    val mainDataSate: LiveData<DataState<MainViewState>> = _mainDataSate

    private fun getMainViewState(): MainViewState {
        return _mainViewSate.value ?: MainViewState()
    }

    fun setPremiumList(list: List<PremiumSubscriptionsModel>) {
        val update = getMainViewState()
        update.premiumList = list
        _mainViewSate.value = update
    }


    fun setVoucherList(list: List<VouchersModel>) {
        val update = getMainViewState()
        update.voucherList = list
        _mainViewSate.value = update
    }

    fun initShoppingItem(){
        dao.getData().observeForever {
            _mainDataSate.value=it
        }
    }
}