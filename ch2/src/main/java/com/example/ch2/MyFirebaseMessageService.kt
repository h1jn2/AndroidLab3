package com.example.ch2

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageService : FirebaseMessagingService() {
    // 앱 install 되자마자 fcm 서버가 전달하는 토큰을 전달하기 위해 자동 호출
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("kkang", "fcm token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("kkang", "fcm message ${message.data}")
    }
}