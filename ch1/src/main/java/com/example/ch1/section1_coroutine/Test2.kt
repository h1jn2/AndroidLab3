package com.example.ch1.section1_coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val count = 10_000

    var time = measureTimeMillis {
        // thread 를 10000 개 구동시켜서 각각의 업무 처리되게
        val threadJob = List(count) {
            thread {
                Thread.sleep(1000)
                print(".")
            }
        }
        threadJob.forEach { it.join() } // 모든 스레드 끝날 때 까지 대기
    }
    println()
    println("thread $count, total time: $time")

    // 동일 업무를 코루틴으로 작성
    time = measureTimeMillis {
        val coroutineJob = List(count) {
            launch {
                delay(1000)
                print(".")
            }
        }
        coroutineJob.forEach { it.join() }
    }
    println()
    println("coroutine $count, total time: $time")
}

// => thread 10000 개를 만들면 실행 시에 10000 개의 스레드가 만들어졌다가 종료
// => 코드에서 비동기 업무는 10000개를 만들었지만 그 코루틴을 실행시킨
// thread 가 10000 개라는 의미는 아님