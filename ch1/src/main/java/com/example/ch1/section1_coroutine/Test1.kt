package com.example.ch1.section1_coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 코루틴은 비동기를 목적으로
// runBlocking 은 main 함수 혹은 테스트 함수에서 coroutine api 를 사용할 목적으로 설계
fun main() = runBlocking {
    // main thread 에 의해 실행 시작
    println("step 1")

    // 코루틴 실행
    // 메인 스레드와 동시에 실행되기 때문에 코루틴을 실행시킨 스레드(main thread) 를 멈추게(suspension) 하지 않음
    // launch = coroutine builder 코루틴을 하나 만들고 실행
    // 리턴 값은 코루틴에 의해 실행되는 업무를 지칭 여러가지 제어
    val job = launch {
        var sum = 0
        for (i in 1..100) {
            delay(100)
            sum += i
        }
        println("coroutine $sum")
    }

    println("step 2")
    // job 으로 표현되는 코루틴이 끝날 때 까지 대기
    // main 함수에서 테스트하여 함수가 끝나면 process 가 종료됨
    job.join()
    println("main end")

}

// => 코루틴을 구동시켰던 main thread 가 coroutine 이 끝날 때까지 대기하지 않고 같이 움직임 -> 비동기