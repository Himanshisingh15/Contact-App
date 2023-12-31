package com.example.fakeapistoreapp.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ApiViewModel: ViewModel() {
    var apiRepository = Repository()
    var liveData: MutableLiveData<ProductList>? = null
    var isLoading: MutableLiveData<Boolean> = apiRepository.isLoading

    fun getProducts(): MutableLiveData<ProductList>{
        liveData = apiRepository.getProducts()
       return liveData!!


    }
}