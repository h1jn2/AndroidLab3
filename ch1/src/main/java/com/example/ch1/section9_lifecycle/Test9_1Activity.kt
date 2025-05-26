package com.example.ch1.section9_lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch1.R
import com.example.ch1.databinding.ActivityTest91Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

// case1 - activity 자체를 coroutine scope 로
class Test9_1Activity : AppCompatActivity(), CoroutineScope {
    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityTest91Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 코루틴을 구동시켜서 특정 업무가 진행되게 하고자
        binding.button1.setOnClickListener {
            //test1 - error (Default)
//            CoroutineScope(Dispatchers.Default).launch {
//                var result = 0
//                repeat(5) {
//                    result += it
//                    delay(500)
//                }
//                binding.textView.text = "result: $result"
//            }

            // test2 - 정상 동작 (Main)
            // 모든 부분이 main thread 에 의해 처리 => 비효율적
//            CoroutineScope(Dispatchers.Main).launch {
//                var result = 0
//                repeat(5) {
//                    result += it
//                    delay(500)
//                }
//                binding.textView.text = "result: $result"
//            }

            // test3 - 정상 동작
            // 업무에 따라 dispatcher 를 적절하게 교체
            // 새로운 코루틴으로 교체하거나 scoping function 으로 교체하거나
            CoroutineScope(Dispatchers.Default).launch {
                var result = 0
                repeat(5) {
                    result += it
                    delay(500)
                }
                launch(Dispatchers.Main) {
                    binding.textView.text = "result: $result"
                }
            }
        }
        binding.button2.setOnClickListener {
            // 액티비티가 종료되더라도 코루틴은 계속 작동
//            CoroutineScope(Dispatchers.Default).launch {
//                repeat(5) {
//                    Log.d("kkang", "coroutine $it")
//                    delay(1000)
//                }
//            }

            // 현재 액티비티가 코루틴 스코프로 선언되어 있음
            // 개발자가 적절하게 코루틴 라이프사이클을 이용해 주어야 함
            launch {
                repeat(5) {
                    Log.d("kkang", "coroutine $it")
                    delay(1000)
                }
            }
        }
        binding.button3.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("kkang", "onDestroy")
        // 코루틴을 종료시켜야 액티비티 종료 시점에 코루틴도 종료
        job.cancel()
    }
}