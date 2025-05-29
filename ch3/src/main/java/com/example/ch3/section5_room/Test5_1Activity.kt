package com.example.ch3.section5_room

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch3.R
import com.example.ch3.databinding.ActivityTest51Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import androidx.room.Room

class Test5_1Activity : AppCompatActivity(), CoroutineScope {
    lateinit var dao: UserDAO
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityTest51Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        job = Job()

        // database 초기화, 앱 내에서 한번만
        // room 을 이용한 dbms 작업 (DAO 함수 호출)은 가급적 background Thread 에 의해 실행을 권장
        // main thread 에 의해 실행됨을 허용하라
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "test"
        ).allowMainThreadQueries().build()

        // 필요한 곳에서 dao 획득하여 함수 호출 후 원하는 dbms 실행
        dao = db.userDao()

        binding.button.setOnClickListener {
            // id 를 모두 0으로 지정한 이유는 Entity 클래스 내에 id 가 primary Key 로 선언되어 있고
            // auto generate 로 선언되어 있어서 insert 시에 자동 값 증가되도록 하기 위함
            val user1 = User(0, "gildong", "hong")
            val user2 = User(0, "gildong2", "hong2")
            val user3 = User(0, "gildong3", "hong3")

            dao.insertUser(user1)
            dao.insertUser(user2)
            dao.insertUser(user3)

            var resultText = ""

            dao.getAll().forEach {
                resultText += "$it \n"
            }
            binding.resultView.text = resultText
        }
    }
}