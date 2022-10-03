package com.cucler.prokabaddi.teamrecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamViewModel : ViewModel() {
    private val repository: TeamRepository
    private val _allTeams = MutableLiveData<List<TeamModel>>()
    val allTeams: LiveData<List<TeamModel>> = _allTeams

    init {
        repository = TeamRepository().getInstance()
        repository.loadTeams(_allTeams)
    }
}