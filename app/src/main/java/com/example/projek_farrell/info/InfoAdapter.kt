package com.example.projek_farrell.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projek_farrell.databinding.ItemInfoBinding

// Adapter RecyclerView: dipakai bersama oleh tab Berita dan tab Fasilitas
class InfoAdapter(
    private val infoList: List<InfoModel>,
    private val onItemClick: (InfoModel) -> Unit
) : RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {

    // ViewHolder: menyimpan komponen satu item lewat binding
    inner class InfoViewHolder(val binding: ItemInfoBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Membuat tampilan item (hanya sebanyak yang muat di layar)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val binding = ItemInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return InfoViewHolder(binding)
    }

    // Mengisi item dengan data pada posisi tertentu
    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val item = infoList[position]
        with(holder.binding) {
            tvJudul.text = item.judul
            tvKeterangan.text = item.keterangan
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .into(imgInfo)
            root.setOnClickListener { onItemClick(item) }
        }
    }

    // Jumlah data
    override fun getItemCount(): Int = infoList.size
}
