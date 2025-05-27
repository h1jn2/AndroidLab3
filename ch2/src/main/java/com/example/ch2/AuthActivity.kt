package com.example.ch2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch2.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding

    fun changeVisibility(mode: String) {
        if (mode == "login") {
            binding.run {
                authMainTextView.text = "${MyApplication.email} 님 반갑습니다"
                logoutButton.visibility = View.VISIBLE
                goSignUpButton.visibility = View.GONE
                authEmailEditText.visibility = View.GONE
                authPasswordEditText.visibility = View.GONE
                signUpButton.visibility = View.GONE
                logInButton.visibility = View.GONE
            }
        } else if (mode == "logout") {
            binding.run {
                authMainTextView.text = "로그인 하거나 회원가입 해주세요"
                logoutButton.visibility = View.GONE
                goSignUpButton.visibility = View.VISIBLE
                authEmailEditText.visibility = View.VISIBLE
                authPasswordEditText.visibility = View.VISIBLE
                signUpButton.visibility = View.GONE
                logInButton.visibility = View.VISIBLE
            }
        } else if (mode == "signup") {
            binding.run {
                logoutButton.visibility = View.GONE
                goSignUpButton.visibility = View.GONE
                authEmailEditText.visibility = View.VISIBLE
                authPasswordEditText.visibility = View.VISIBLE
                signUpButton.visibility = View.VISIBLE
                logInButton.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)

        // 이 액티비티의 초기 상태
        if (MyApplication.checkAuth()) {
            changeVisibility("login")
        } else {
            changeVisibility("logout")
        }

        binding.logoutButton.setOnClickListener {
            MyApplication.auth.signOut()
            MyApplication.email = null
            changeVisibility("logout")
        }
        binding.goSignUpButton.setOnClickListener {
            changeVisibility("signup")
        }
        binding.signUpButton.setOnClickListener {
            //회원가입....
            //유저 입력 데이터 획득...
            val email = binding.authEmailEditText.text.toString()
            val password = binding.authPasswordEditText.text.toString()

            //firebase 에.. 유저 입력 데이터 전달.. 회원가입.. 결과는 콜백으로..
            MyApplication.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    //화면에 입력된 데이터 지우고..
                    binding.authEmailEditText.text.clear()
                    binding.authPasswordEditText.text.clear()
                    if(task.isSuccessful){
                        //서버 등록이 성공했다면..
                        //검증 이메일을 발송....
                        MyApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { sendTask ->
                                if(sendTask.isSuccessful){
                                    //검증 이메일 발송이 성공한 경우...
                                    Toast.makeText(baseContext, "회원가입이 성공했습니다. 전송된 이메일을 확인해 주세요.",
                                        Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                }else {
                                    Toast.makeText(baseContext, "메일 전송 실패", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                }
                            }
                    }else {
                        Toast.makeText(baseContext, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        changeVisibility("logout")
                        Log.d("kkang", task.exception.toString())
                    }
                }
        }

        binding.logInButton.setOnClickListener {
            val email = binding.authEmailEditText.text.toString()
            val password = binding.authPasswordEditText.text.toString()
            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.authEmailEditText.text.clear()
                    binding.authPasswordEditText.text.clear()
                    if (task.isSuccessful) {
                        if (MyApplication.checkAuth()) {
                            MyApplication.email = email
                            changeVisibility("login")
                        } else {
                            Toast.makeText(baseContext, "전송된 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}