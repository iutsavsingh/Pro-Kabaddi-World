package com.cucler.prokabaddi.fixturerecyclerview

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*

class FixtureRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Fixture")

    @Volatile
    private var INSTANCE: FixtureRepository? = null

    fun getInstance(): FixtureRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = FixtureRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadFixture(fixtureList: MutableLiveData<List<FixtureModel>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _fixtureList: List<FixtureModel> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(FixtureModel::class.java)!!
                    }
                    fixtureList.postValue(_fixtureList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}