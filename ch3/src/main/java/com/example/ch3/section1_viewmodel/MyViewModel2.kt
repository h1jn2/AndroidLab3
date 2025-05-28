package com.example.ch3.section1_viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class MyViewModel2: ViewModel() {
    var count = 0

    init {
        Log.d("kkang", "MyViewModel2 Create")
    }

    // ViewModel 객체가 소멸될 때 호출
    override fun onCleared() {
        super.onCleared()
        Log.d("kkang", "MyViewModel2 Cleared")
    }
}