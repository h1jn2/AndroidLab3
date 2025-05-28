package com.example.ch3.section1_viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel

// ViewModel 생성 시 매개변수로 Application 객체를 전달하고 싶을 때
// 직접 생성하지 않고 ViewModelProvider 등으로 생성
class MyApplicationViewModel(application: Application):AndroidViewModel(application) {
    var count = 0
    init {
        Log.d("kkang", "MyApplicationViewModel Create")
    }

    fun someFun() {
        // ViewModel 이 View 는 아님
        // Application 을 이용해서 화면 제어가 가능하다고 하더라도 권장하지 않음
        Toast.makeText(getApplication(), "viewmodel toast", Toast.LENGTH_SHORT).show()
    }
}