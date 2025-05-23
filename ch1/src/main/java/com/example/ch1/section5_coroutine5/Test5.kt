package com.example.ch1.section5_coroutine5

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// fan out

fun main() = runBlocking{
    val myChannel1 = produce<Int> {
        var x = 1
        while (true) {
            delay(100)
            send(x++)
        }
    }

    launch {
        myChannel1.consumeEach {
            println("job1 receive $it")
        }
    }
    launch {
        myChannel1.consumeEach {
            println("job2 receive $it")
        }
    }
    launch {
        myChannel1.consumeEach {
            println("job3 receive $it")
        }
    }

    delay(3000)
    myChannel1.cancel()
}
// fan out 1:N 가능
// 하나의 데이터를 여러 구독자가 같이 받을 수는 없음
// 구독의 순서를 보장할 수는 없음
// => 1:1, 1:n, n:1, n:m 모두 가능하긴 하지만 주로 1:1에 이용