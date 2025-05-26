package com.example.ch1.section8_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

// stateIn, shareIn 의 WhileSubscribed

fun something() = flow<Int> {
    repeat(10) {
        delay(100)
        println("emit $it")
        emit(it)
    }
}.shareIn(
    // hotStream 에 의해 flow 동작하도록
    // 지정한 Scope 내에서 동작하는 코루틴을 만들어 flow 실행
    CoroutineScope(Dispatchers.IO),
    // 구독자가 있는 경우에만 동작, 구독자가 모두 사라지면 어떻게 움직일 것인 지
    SharingStarted.WhileSubscribed(stopTimeoutMillis = 200)
)

fun main() = runBlocking {
    val flow = something()
    delay(200)
    launch {
        withTimeout(300) {
            flow.collect() { println("job1 receive $it") }
        }
    }
    delay(1000)
}
// stopTimeoutMills = 0
//emit 0
//job1 receive 0
//emit 1
//job1 receive 1
// => 구독자가 나타나야 flow 데이터 발행
// => 구족자가 사라지면 바로 정지

// stopTimeMills = 200
//emit 0
//job1 receive 0
//emit 1
//job1 receive 1
//emit 2
//emit 3
// => 구독자가 나타나야 flow 데이터 발행
// => 구독자가 사라지더라도 200 동안 동작