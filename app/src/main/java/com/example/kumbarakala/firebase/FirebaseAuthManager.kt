package com.example.kumbarakala.firebase


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthManager {

    private val auth =
        FirebaseAuth.getInstance()

    /*
        CURRENT USER
     */

    fun getCurrentUser(): FirebaseUser? {

        return auth.currentUser
    }

    /*
        CURRENT USER ID
     */

    fun getCurrentUserId(): String {

        return auth.currentUser?.uid ?: ""
    }

    /*
        LOGOUT
     */

    fun logout() {

        auth.signOut()
    }
}