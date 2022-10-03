package com.cucler.prokabaddi.coachrecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoachViewModel : ViewModel() {
    private val repository: CoachRepository
    private val _allCoach = MutableLiveData<List<CoachModel>>()
    val allCoach: LiveData<List<CoachModel>> = _allCoach

    init {
        repository = CoachRepository().getInstance()
        repository.loadCoach(_allCoach)
    }
}