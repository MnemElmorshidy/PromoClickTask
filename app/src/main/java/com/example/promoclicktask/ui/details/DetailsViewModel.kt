package com.example.promoclicktask.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.promoclicktask.api.RetrofitHandler
import com.example.promoclicktask.pojo.details.DetailsResponse
import com.example.promoclicktask.utils.Resource
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val _data = MutableLiveData<Resource<DetailsResponse>>()
    val data: MutableLiveData<Resource<DetailsResponse>> get() = _data


      fun getData(id: Int) = viewModelScope.launch {
        _data.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getDetails(id)
        if (response.isSuccessful) {
            if (response.body() != null)
                _data.value = Resource.success(response.body()!!.data)
        } else {
            _data.value = Resource.error(response.errorBody().toString())
        }
    }

}