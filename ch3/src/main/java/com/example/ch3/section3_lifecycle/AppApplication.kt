package com.example.ch3.section3_lifecycle

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // 앱의 lifecycle observer 등록
        ProcessLifecycleOwner.get().lifecycle.addObserver(MyProcessLifecycleObserver())
    }
}