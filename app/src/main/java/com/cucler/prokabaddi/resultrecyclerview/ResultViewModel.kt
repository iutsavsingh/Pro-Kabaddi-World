package com.cucler.prokabaddi.resultrecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {
    private val repository: ResultRepository
    private val _allResult = MutableLiveData<List<ResultModel>>()
    val allResult: LiveData<List<ResultModel>> = _allResult

    init {
        repository = ResultRepository().getInstance()
        repository.loadResult(_allResult)
    }
}