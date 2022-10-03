package com.cucler.prokabaddi.teamrecyclerview

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*

class TeamRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Teams")

    @Volatile
    private var INSTANCE: TeamRepository? = null

    fun getInstance(): TeamRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = TeamRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadTeams(teamList: MutableLiveData<List<TeamModel>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _teamList: List<TeamModel> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(TeamModel::class.java)!!
                    }
                    teamList.postValue(_teamList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}