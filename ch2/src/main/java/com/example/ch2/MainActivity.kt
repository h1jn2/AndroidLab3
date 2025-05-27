package com.example.ch2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)

        binding.addFab.setOnClickListener {
            if (MyApplication.checkAuth()) {
                startActivity(Intent(this, AddActivity::class.java))
            } else {
                Toast.makeText(this, "로그인 후 이용해 주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(!MyApplication.checkAuth()){
            binding.logoutTextView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }else {
            binding.logoutTextView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            makeRecyclerView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, AuthActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    private fun makeRecyclerView() {
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.adapter = MyAdapter(this, itemList)
                binding.recyclerView.addItemDecoration(DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL
                ))
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "error $exception");
            }


    }
}