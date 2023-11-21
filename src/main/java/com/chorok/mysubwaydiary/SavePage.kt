package com.chorok.mysubwaydiary
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chorok.mysubwaydiary.databinding.SavePageBinding
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SavePage : AppCompatActivity() {
    lateinit var binding: SavePageBinding
    private lateinit var adapter: DiaryEntryAdapter
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = SavePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("MyDiary", Context.MODE_PRIVATE)

        // RecyclerView 및 어댑터 설정
        val diaryEntries = loadDiaryEntries()

        adapter = DiaryEntryAdapter(diaryEntries)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.saveButton.setOnClickListener {
            val text = binding.editText.text.toString()
            val currentTimeMillis = System.currentTimeMillis() // 현재 시간 얻음
            //var currentTime = Calendar.getInstance().getTime();
            val entry = DiaryEntry(text, currentTimeMillis)
            diaryEntries.add(entry)

            // 텍스트를 저장
            saveDiaryEntries(diaryEntries)

            // 리스트를 시간 순으로 정렬
            diaryEntries.sortByDescending { it.timestamp }

            adapter.notifyDataSetChanged()
            onBackPressed() // 현재 화면을 닫음
        }
    }

    private fun saveDiaryEntries(diaryEntries: List<DiaryEntry>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonList = diaryEntries.map { gson.toJson(it) }
        // 시간 불러오기
        //val jsonList = diaryEntries.map { entry ->
        //        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(entry.timestamp))
        //        "$formattedDate ${entry.text}"
        //    }
        editor.putStringSet("diary_entries", jsonList.toSet())
        editor.apply()
    }


    private fun loadDiaryEntries(): MutableList<DiaryEntry> {
        val textSet = sharedPreferences.getStringSet("diary_entries", emptySet())
        val gson = Gson()
        val diaryEntries = textSet?.map { gson.fromJson(it, DiaryEntry::class.java) }?.toMutableList() ?: mutableListOf()


        diaryEntries.sortByDescending { it.timestamp }

        return diaryEntries
    }



}