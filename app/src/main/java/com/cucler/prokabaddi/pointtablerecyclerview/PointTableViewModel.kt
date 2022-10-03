package com.cucler.prokabaddi.pointtablerecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PointTableViewModel : ViewModel() {
    private val repository: PointTableRepository
    private val _allPointTable = MutableLiveData<List<PointTableModel>>()
    val allPointTable: LiveData<List<PointTableModel>> = _allPointTable

    init {
        repository = PointTableRepository().getInstance()
        repository.loadPointTable(_allPointTable)
    }
}