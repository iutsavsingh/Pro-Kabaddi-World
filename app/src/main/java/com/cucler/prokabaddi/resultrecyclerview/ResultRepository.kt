package com.cucler.prokabaddi.resultrecyclerview

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*

class ResultRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Result")

    @Volatile
    private var INSTANCE: ResultRepository? = null

    fun getInstance(): ResultRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = ResultRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadResult(resultList: MutableLiveData<List<ResultModel>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _resultList: List<ResultModel> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(ResultModel::class.java)!!
                    }
                    resultList.postValue(_resultList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}