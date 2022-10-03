package com.cucler.prokabaddi.fixturerecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FixtureViewModel : ViewModel() {
    private val repository: FixtureRepository
    private val _allFixture = MutableLiveData<List<FixtureModel>>()
    val allFixture: LiveData<List<FixtureModel>> = _allFixture

    init {
        repository = FixtureRepository().getInstance()
        repository.loadFixture(_allFixture)
    }
}