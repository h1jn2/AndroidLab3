package com.example.ch1.section5_coroutine5

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Channel Buffer 이용
// 발행과 구독이 별개이므로 둘 간의 속도 차이가 발생할 수 있음
// 기본은 발행해야 구독, 구독해야 발행 미리 움직일 수는 없음

fun main() = runBlocking {
    // test1 - buffer 개념 없는 경우
//    val channel = Channel<Int> ()
//
//    launch {
//        repeat(5) {
//            channel.send(it)
//            println("send $it")
//            delay(500)
//        }
//        channel.close()
//    }
//    for (data in channel) {
//        println("receive $data")
//        delay(300)
//    }
//    send 0
//    receive 0
//    send 1
//    receive 1
//    send 2
//    receive 2
//    send 3
//    receive 3
//    send 4
//    receive 4

    // test2 - 버퍼 적용
    // 수신을 하지 않았다고 하더라도 미리 발행할 수 있음
    // 버퍼 여유가 없으면 더이상 발행이 되지 않고 대기
//    val channel = Channel<Int> (capacity = 2)
//
//    launch {
//        repeat(5) {
//            channel.send(it)
//            println("send $it")
//            delay(200)
//        }
//        channel.close()
//    }
//    for (data in channel) {
//        println("receive $data")
//        delay(300)
//    }
//send 0
//receive 0
//send 1
//receive 1
//send 2
//receive 2
//send 3
//send 4
//receive 3
//receive 4

    // test3 - 버퍼가 다 찼다고 하더라도 계속 데이터를 발행하고 싶을 때
    // 이번에 발행했던 데이터를 덮어쓰게 해야함
    // 구독 측에서 밣애했던 데이터를 구독하지 못하는 데이터가 발생할 수 있음
    val channel = Channel<Int> (capacity = 2, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    launch {
        repeat(5) {
            channel.send(it)
            println("send $it")
            delay(200)
        }
        channel.close()
    }
    for (data in channel) {
        println("receive $data")
        delay(400)
    }
//    send 0
//    receive 0
//    send 1
//    send 2
//    receive 1
//    send 3
//    send 4
//    receive 3
//    receive 4
}