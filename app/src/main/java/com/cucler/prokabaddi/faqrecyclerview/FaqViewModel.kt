package com.cucler.prokabaddi.faqrecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FaqViewModel : ViewModel() {

    private val repository: FaqRepository
    private val _allFaq = MutableLiveData<List<FaqModel>>()
    val allFaq: LiveData<List<FaqModel>> = _allFaq

    init {
        repository = FaqRepository().getInstance()
        repository.loadFaq(_allFaq)
    }

}