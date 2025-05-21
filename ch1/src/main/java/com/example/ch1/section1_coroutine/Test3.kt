package com.example.ch1.section1_coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 코루틴을 실행시키는 thread pool
fun main() = runBlocking {
    listOf("one", "two", "three").forEachIndexed { index, value ->
        // 코루틴을 구동시키면서 어느 thread pool 애서 구동될 지를 정할 수 있음
        launch(Dispatchers.Default) {
            // 이 부분을 실행시켰던 thread
            println("coroutine $value start: ${Thread.currentThread().name}")

            // 업무가 중단되는 즉, 쉬고있는 상황이 발생

            // test1
//            Thread.sleep(100L + index * 100L)

            // test2
            delay(100L + index * 100L)
            println("coroutine $value end: ${Thread.currentThread().name}")
        }
    }
    Thread.sleep(2000)
}

// test1
//coroutine one start: DefaultDispatcher-worker-1
//coroutine two start: DefaultDispatcher-worker-2
//coroutine three start: DefaultDispatcher-worker-3
//coroutine one end: DefaultDispatcher-worker-1
//coroutine two end: DefaultDispatcher-worker-2
//coroutine three end: DefaultDispatcher-worker-3
// => 각 코루틴을 실행시킨 스레드에 의해 업무 대기 상태 후 동일 스레드에서 끝났음
// => 각 코루틴을 실행시킨 스레드가 유휴 상태에서 다른 업무를 처리하지 못하고 놀고 있었음

// test2
//coroutine one start: DefaultDispatcher-worker-1
//coroutine two start: DefaultDispatcher-worker-2
//coroutine three start: DefaultDispatcher-worker-3
//coroutine one end: DefaultDispatcher-worker-3
//coroutine two end: DefaultDispatcher-worker-3
//coroutine three end: DefaultDispatcher-worker-3
// => delay 로 유휴 상태를 만든 경우
// => 동시에 코루틴 3개를 구동시킨 것이므로 시작은 스레드 3개를 사용했음
// => 유휴 상태 후에 각 코루틴 종료 시점에 동작하고 있는 스레드는 1개