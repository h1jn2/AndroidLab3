package com.example.ch1.section3_coroutine3

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Job, CompletableJob

fun main() = runBlocking {
    // launch { } 가 리턴시킨 Job은 Job 타입
    // => 코루틴 내부에서 완료 여부를 지정할 때
    // 내부적으로 실행이 끝나거나 exception 이 발생됐을 때 완료되었다고 상태 표현
    val job1 = launch {
        repeat(2) {
            delay(200)
            println("job1 $it")
        }
    }
    job1.join() // complete 상태가 될 때까지 대기
    println("main step1")
//    job1 0
//    job1 1
//    main step1
    // => 내부에서 complete 상태로 만들었기 때문에 join() 아래까지 실행

    // 코루틴 외부에서 job 을 선언해서 코루틴에 지정하는 경우 CompletableJob 타입
    // 내부가 끝났다고 하더라도 외부에서 종료 선언을 하지 않는 한 complete 상태가 안 됨
    // 외부에서 종료 여부를 결정
    val job2: CompletableJob = Job()
    launch(job2) {
        repeat(2) {
            delay(200)
            println("job2 $it")
        }
    }
//    job2.join()   // 아래줄 실행 안 됨
    job2.cancel()
    println("main step2")

    val job3: CompletableJob = Job()
    launch(job3) {
        repeat(2) {
            delay(200)
            println("job3 $it")
        }
    }
    delay(500)
    var isCompleted = job3.complete()   // 명시적으로 외부에서 complete 상태로 만든 경우 cancel 효과
    println("main step3 ${isCompleted}")    // main step3 true
    // => 업무가 정상적으로 진행되었고 현재 complete 상태

    // 이미 complete 상태의 job 으로 다른 업무 진행하려고 한 경우
    launch(job3) {
        repeat(2) {
            delay(200)
            println("job4 $it")
        }
    }
    delay(500)
    isCompleted = job3.complete()
    println("main step4 ${isCompleted}")    // main step3 false
    // => false: 이미 종료된 job 이라는 의미
}