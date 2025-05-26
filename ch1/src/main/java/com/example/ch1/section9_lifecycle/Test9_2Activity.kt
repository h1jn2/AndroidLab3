package com.example.ch1.section9_lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.ch1.R
import com.example.ch1.databinding.ActivityTest92Binding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Test9_2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityTest92Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button2.setOnClickListener {
            // 코루틴을 실행시키려면 먼저 scope 가 선언되어 있어야 함
            // - activity, fragment 에서 CoroutineScope 를 구현하여 자체가 코루틴 스코프가 되거나
            // - CoroutineScope() 함수를 이용해 스코프를 만들어 사용

            // 액태비티, 프래그먼트 생존 기간에만 동작해야하는 코루프를 만드려면 라이프사이클에 신경
            // 액티비티, 프래그먼트, viewModel 등과 동일 라이프 사이클로 동작하는 scope
            lifecycleScope.launch {
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
    }
}