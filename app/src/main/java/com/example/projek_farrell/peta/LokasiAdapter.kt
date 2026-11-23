package com.example.projek_farrell.peta

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projek_farrell.databinding.ItemLokasiBinding

// Adapter RecyclerView untuk daftar lokasi di Peta Kota
class LokasiAdapter(
    private val lokasiList: List<LokasiModel>,
    private val onRute: (LokasiModel) -> Unit
) : RecyclerView.Adapter<LokasiAdapter.LokasiViewHolder>() {

    class LokasiViewHolder(val binding: ItemLokasiBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LokasiViewHolder {
        val binding = ItemLokasiBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LokasiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LokasiViewHolder, position: Int) {
        val lokasi = lokasiList[position]
        with(holder.binding) {
            tvNama.text = lokasi.nama
            tvAlamat.text = lokasi.alamat
            tvKategori.text = lokasi.kategori.uppercase()
            btnRute.setOnClickListener { onRute(lokasi) }
        }
    }

    override fun getItemCount(): Int = lokasiList.size
}
