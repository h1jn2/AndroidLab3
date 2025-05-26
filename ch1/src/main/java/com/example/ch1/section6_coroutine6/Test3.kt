package com.example.ch1.section6_coroutine6

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

// MutableStateFlow
// hot stream - 어떤 특정 상태(값)를 발행하고 그 값이 변경(상태 변경) 시에
// 구독자들을 움직이게 해서 업무 처리가 되도록

val stateFlow = MutableStateFlow(0) // 상태를 표현함이 목적이므로 초기값 있어야 함

// 개발자 업무 함수, 이 함수 실행에 의해 상태가 발생하게 된다는 가정
suspend fun changeState(data: Int) {
    stateFlow.emit(data)

}

fun main() = runBlocking {
    changeState(1)
}