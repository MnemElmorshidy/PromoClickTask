package com.example.promoclicktask.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.promoclicktask.api.RetrofitHandler
import com.example.promoclicktask.pojo.home.Data
import com.example.promoclicktask.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _data = MutableLiveData<Resource<Data>>()
    val data: MutableLiveData<Resource <Data>> get() = _data


   private fun getData() = viewModelScope.launch {
        _data.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getData()
        if (response.isSuccessful) {
            if (response.body() != null)
                _data.value = Resource.success(response.body()!!.data)
        } else {
            _data.value = Resource.error(response.errorBody().toString())
        }
    }

    init {
        getData()
    }
}