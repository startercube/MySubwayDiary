package com.chorok.mysubwaydiary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chorok.mysubwaydiary.databinding.ListItemBinding

class DiaryEntryAdapter(private val entries: List<DiaryEntry>) :
    RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: DiaryEntry) {
            binding.textView.text = entry.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.bind(entry)

    }

    override fun getItemCount(): Int {
        return entries.size
    }
}
