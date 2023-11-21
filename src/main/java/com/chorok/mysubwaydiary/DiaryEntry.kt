package com.chorok.mysubwaydiary

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DiaryEntry(
    val text: String,
    val timestamp: Long // 시간 정보를 저장할 필드

) {
    // 시간 정보를 문자열로 변환하는 함수
    fun getFormattedTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }
//    // 삭제
//    fun delete(){
//
//    }
}
