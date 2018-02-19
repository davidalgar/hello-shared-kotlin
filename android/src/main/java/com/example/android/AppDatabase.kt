package com.example.android

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.willowtreeapps.hellokotlin.AppState
import com.willowtreeapps.hellokotlin.Database

class AppDatabase : Database {

    private val ref = FirebaseDatabase.getInstance().reference

    override fun put(todos: AppState) {
        ref.setValue(todos).addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.e("AppDatabase", it.exception?.message, it.exception)
            }
        }
    }

    override fun observe(onChange: (AppState) -> Unit) {
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val state = snapshot.getValue(AppState::class.java)!!
                Log.d("AppDatabase", "state: $state")
                onChange(state)
            }

            override fun onCancelled(e: DatabaseError) {
                Log.e("AppDatabase", e.message, e.toException())
            }
        })
    }
}