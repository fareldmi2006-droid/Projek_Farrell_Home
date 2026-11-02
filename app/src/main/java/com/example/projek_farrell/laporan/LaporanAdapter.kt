package com.example.projek_farrell.laporan

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.projek_farrell.R
import com.example.projek_farrell.databinding.ItemLaporanBinding
import com.google.android.material.snackbar.Snackbar

// Custom Adapter: mengubah setiap LaporanModel menjadi satu baris item_laporan.xml
// (pola yang sama dengan MessageAdapter di Pertemuan 7)
class LaporanAdapter(
    context: Context,
    private val laporanList: List<LaporanModel>
) : ArrayAdapter<LaporanModel>(context, 0, laporanList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 1. Pakai ulang baris lama kalau ada, kalau belum ada buat baru
        val binding = if (convertView == null) {
            ItemLaporanBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemLaporanBinding.bind(convertView)
        }

        // 2. Ambil data pada posisi ini
        val laporan = laporanList[position]

        // 3. Isi tampilan dengan data
        binding.tvJudul.text = laporan.judul
        binding.tvKeterangan.text = "${laporan.kategori} • ${laporan.tanggal}"
        binding.tvStatus.text = laporan.status

        // 4. Ikon berbeda sesuai kategori
        val ikon = when (laporan.kategori) {
            "Jalan Rusak" -> R.drawable.ic_place
            "Sampah" -> R.drawable.ic_eco
            "Banjir" -> R.drawable.ic_weather
            "Lampu Jalan" -> R.drawable.ic_bolt
            else -> R.drawable.ic_pengaduan
        }
        binding.imgKategori.setImageResource(ikon)

        // 5. Warna status: emas = Diproses, teal = Selesai
        if (laporan.status == "Selesai") {
            binding.tvStatus.setBackgroundResource(R.drawable.bg_chip)
            binding.tvStatus.setTextColor(Color.parseColor("#FF087F75"))
        } else {
            binding.tvStatus.setBackgroundResource(R.drawable.bg_chip_gold)
            binding.tvStatus.setTextColor(Color.parseColor("#FFB8975A"))
        }

        // 6. Klik baris -> tampilkan Snackbar
        binding.root.setOnClickListener {
            Snackbar.make(binding.root, "${laporan.judul} (${laporan.status})", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }
}
