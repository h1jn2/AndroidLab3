package com.example.ch1.section5_coroutine5

import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Channel 을 이용하는 coroutine builder, produce(), actor()

fun main() = runBlocking {
    val receiveChannel = produce<Int> {
        // 자동으로 productScope 가 준비되고 이 스코프 영역 내에서 실행
        // 별도의 channel 을 준비하지 않아도 send 가 가능
        // 이 builder 를 이용하고 있는 곳은 scope 를 그대로 상속받아서 productScope 가 준비
        repeat(5) {
            send(it)
            println("send $it")
            delay(100)
        }
    }

    receiveChannel.consumeEach {
        println("receive $it")
        delay(200)
    }
//    send 0
//    receive 0
//    receive 1
//    send 1
//    receive 2
//    send 2
//    receive 3
//    send 3
//    receive 4
//    send

    // 데이터를 수신해서 업무를 돌리는 코루틴 빌더
     val sendChannel = actor <Int>{
        // ActorScope 에 의해 실행, 수신 준비
        // 리턴은 SendChannel
        consumeEach {
            println("receive: $it")
        }
    }
    val job = launch {
        repeat(3) {
            delay(100)
            sendChannel.send(it)
        }
    }
    job.join()
    sendChannel.close()
    println()
}