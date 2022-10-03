package com.cucler.prokabaddi.pointtablerecyclerview

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*

class PointTableRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("PointTable")

    @Volatile
    private var INSTANCE: PointTableRepository? = null

    fun getInstance(): PointTableRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = PointTableRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadPointTable(pointTableList: MutableLiveData<List<PointTableModel>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _pointTableList: List<PointTableModel> =
                        snapshot.children.map { dataSnapshot ->
                            dataSnapshot.getValue(PointTableModel::class.java)!!
                        }
                    pointTableList.postValue(_pointTableList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}