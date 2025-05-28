package com.example.ch3.section1_viewmodel

import androidx.lifecycle.ViewModel

class MyViewModel1: ViewModel() {
    // view 에서 이용할 property, function 선언
    var data: String = "hello"

    fun someFun(): String {
        return "someFun result"
    }
}