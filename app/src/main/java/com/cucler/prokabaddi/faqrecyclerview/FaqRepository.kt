package com.cucler.prokabaddi.faqrecyclerview

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*

class FaqRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Faq")

    @Volatile
    private var INSTANCE: FaqRepository? = null

    fun getInstance(): FaqRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = FaqRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadFaq(faqList: MutableLiveData<List<FaqModel>>) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _faqList: List<FaqModel> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(FaqModel::class.java)!!
                    }
                    faqList.postValue(_faqList)
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}