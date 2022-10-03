package com.cucler.prokabaddi.coachrecyclerview

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*

class CoachRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Coach")

    @Volatile
    private var INSTANCE: CoachRepository? = null

    fun getInstance(): CoachRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = CoachRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadCoach(coachList: MutableLiveData<List<CoachModel>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _coachList: List<CoachModel> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(CoachModel::class.java)!!
                    }
                    coachList.postValue(_coachList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}