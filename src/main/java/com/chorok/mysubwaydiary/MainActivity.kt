package com.chorok.mysubwaydiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chorok.mysubwaydiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSub.setOnClickListener {

            val intent = Intent(this, CommonPage::class.java)
            startActivity(intent)

        }

        binding.btnDiary.setOnClickListener {

            val intent = Intent(this, DiaryPage::class.java)
            startActivity(intent)

        }

    }
}