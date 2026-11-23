package com.example.projek_farrell.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projek_farrell.databinding.ItemForumBinding

// Adapter RecyclerView untuk postingan Forum Warga
class ForumAdapter(
    private val forumList: List<ForumModel>
) : RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {

    class ForumViewHolder(val binding: ItemForumBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val binding = ItemForumBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ForumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val post = forumList[position]
        with(holder.binding) {
            tvAvatar.text = post.nama.take(1).uppercase()
            tvNama.text = post.nama
            tvWaktu.text = post.waktu
            tvIsi.text = post.isi
        }
    }

    override fun getItemCount(): Int = forumList.size
}
