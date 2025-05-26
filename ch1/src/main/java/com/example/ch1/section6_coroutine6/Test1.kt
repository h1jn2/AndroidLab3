package com.example.ch1.section6_coroutine6

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun something(): Flow<Int> = flow {
    repeat(2) {
        delay(100)
        println("emit: $it")
        emit(it)
    }
}

fun main() = runBlocking {
    val myFlow = something()
    // flow 가 만들어지기는 했지만 아직 구독자가 나타나기 전
    // cold stream 이기 때문에 flow 실행되지 않음
    println("main 1")
    myFlow.collect {
        println("collect 1: $it")
    }
    println("main 2")
//    main 1
//    emit: 0
//    collect 1: 0
//    emit: 1
//    collect 1: 1
//    main 2

    // 만약 구독을 비동기로 하고싶다면 코루틴 만들어서 구독하도록
    launch {
        myFlow.collect {
            println("collect 2: $it")
        }
    }
    println("main 3")
//    main 3
//    emit: 0
//    collect 2: 0
//    emit: 1
//    collect 2: 1
    // flow 구독자가 다시 나타난 경우 다시 처음부터 실행
    // 동시에 구독자가 여러명인 경우
    launch {
        myFlow.collect {
            println("collect 3: $it")
        }
    }
//    emit: 0
//    collect 2: 0
//    emit: 0
//    collect 3: 0
//    emit: 1
//    collect 2: 1
//    emit: 1
//    collect 3: 1
    delay(2000)
}