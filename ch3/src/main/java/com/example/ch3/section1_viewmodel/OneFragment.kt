package com.example.ch3.section1_viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ch3.databinding.FragmentOneBinding

class OneFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOneBinding.inflate(inflater)

        // case1 - viewModels()
        // viewModels() 를 이용해서 fragment 의 viewModel 을 준비하면 fragment scope 내에서 싱글톤
        // 동일 activity 내에 여러 fragment 가 나오고 그곳에서 동일 타입의 viewModel 을 사용한다고 하더라도
        // fragment 단위로 viewModel 객체 생성
//        val viewModel: MyApplicationViewModel by viewModels()

        // case2 - activityViewModels()
        // viewModel 의 owner 가 activity 로 변경되어서
        // 같은 activity 내에 실행되는 fragment 끼리 viewModel 객체가 공유됨
        val viewModel: MyApplicationViewModel by activityViewModels()

        binding.button1.setOnClickListener {
            viewModel.count++
        }

        binding.button2.setOnClickListener {
            Toast.makeText(activity, "count: ${viewModel.count}", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}