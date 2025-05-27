package com.example.ch2

import androidx.multidex.MultiDexApplication
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

// multidex 설정
class MyApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
    companion object {
        val auth: FirebaseAuth by lazy {
            Firebase.auth
        }
        val db: FirebaseFirestore by lazy {
            FirebaseFirestore.getInstance()
        }
        var email: String? = null

        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                if (currentUser.isEmailVerified) {
                    // 회원가입을 해서 이메일 검증까지 완료된 상태
                    true
                } else {
                    false
                }
            } ?: let {
                false
            }
        }
    }
}