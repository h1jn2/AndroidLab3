package com.example.ch1.section4_coroutine4

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

// coroutineScope, supervisorScope

val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    println("exception handler: ${throwable.message}")
}

suspend fun something1() = coroutineScope {
    println("thread1 name: ${Thread.currentThread().name}, ${coroutineContext[CoroutineName]}")
    launch {
        repeat(3) {
            delay(300)
            println("coroutine1 $it")
        }
    }

    launch {
        repeat(3) {
            delay(300)
            println("coroutine2 $it")
            if (it == 1) throw Exception("coroutine1 exception")
        }
    }
}

suspend fun something2() = supervisorScope {
    println("thread1 name: ${Thread.currentThread().name}, ${coroutineContext[CoroutineName]}")
    launch {
        repeat(3) {
            delay(300)
            println("coroutine1 $it")
        }
    }

    launch {
        repeat(3) {
            delay(300)
            println("coroutine2 $it")
            if (it == 1) throw Exception("coroutine1 exception")
        }
    }
}

fun main() = runBlocking {
    val scope1 = CoroutineScope(
        newSingleThreadContext("myThread1") +
                CoroutineName("myCoroutine1") + exceptionHandler
    )
    scope1.launch {
        something1()
    }.join()

    println()

    val scope2 = CoroutineScope(
        newSingleThreadContext("myThread2") +
                CoroutineName("myCoroutine2") + exceptionHandler
    )
    scope2.launch {
        something2()
    }.join()
}
//thread1 name: myThread1, CoroutineName(myCoroutine1)
//coroutine1 0
//coroutine2 0
//coroutine1 1
//coroutine2 1
//exception handler: coroutine1 exception
//
//thread1 name: myThread2, CoroutineName(myCoroutine2)
//coroutine1 0
//coroutine2 0
//coroutine1 1
//coroutine2 1
//exception handler: coroutine1 exception
//coroutine1 2
// => 에러 발생되면 동일 scoping function 내의 모든 coroutine 이 다 취소