package com.chorok.mysubwaydiary

import android.content.Context
import java.io.*

object FileUtils {
    // 텍스트를 파일에 저장하는 함수
    fun saveTextToFile(context: Context, fileName: String, text: String) {
        try {
            val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(outputStream))
            writer.write(text)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 파일에서 텍스트를 읽어오는 함수
    fun loadTextFromFile(context: Context, fileName: String): String {
        try {
            val inputStream = context.openFileInput(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val text = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                text.append(line)
            }
            inputStream.close()
            return text.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }
}
