package com.example.ch1.section8_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking

// flowOn - stateIn
fun main() = runBlocking {
    // flowOn -> intermediate operator -> context 교체
    val flow1 = flow<Int> {
        println("flow1 ${Thread.currentThread().name}")
        repeat(3) { emit(it) }
    }.flowOn(Dispatchers.IO)

    delay(200)
    println("step1")
    flow1.collect {
        println("collect $it on ${Thread.currentThread().name}")
    }
//flowOn() 은 terminal operator 가 아니기 때문에 그 자체로 flow 를 실행시킬 수 없음
// -> 단지 flow 의 context 정보 교체가 목적
//step1
//flow1 DefaultDispatcher-worker-1
//collect 0 on main
//collect 1 on main
//collect 2 on main

    println()

    // stateIn()
    val scope = CoroutineScope(Dispatchers.Default + Job())
    val flow2 = flow {
        println("flow2: ${Thread.currentThread().name}")
        repeat(3) { emit(it) }
    } // 여기까지는 coldStream
        .stateIn(   // terminal operator -> 지정된 scope 를 이용한 coroutine 을 이용하여 flow 실행
            // coldStream 으로 선언된 flow 를 hotStream 으로 변형
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = 0
        )
    delay(200)
    println("step2")
    flow2.take(3).collect {
        println("flow2 collect $it")
    }
    scope.cancel()
//    flow2: DefaultDispatcher-worker-1
//    step2
//    flow2 collect 2
//    => collect 되기 전에 flow 가 먼저 실행
}
