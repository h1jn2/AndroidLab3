package com.example.ch3.section2_livedata

import android.util.Log
import androidx.lifecycle.LiveData

// 이 livedata 를 이용해 데이터를 발행하긴 하지만 api 를 직접 정의하기 위해
// 약간 조작해서 발행
class MyLiveData: LiveData<String>() {
    fun sayHello(name: String) {
        postValue("Hello $name")
    }

    override fun onActive() {
        super.onActive()
        Log.d("kkang", "MyLiveData onActive")
    }

    override fun onInactive() {
        super.onInactive()
        Log.d("kkang", "MyLiveData onInactive")
    }
}