package com.example.projek_farrell.laporan

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projek_farrell.R
import com.example.projek_farrell.data.entity.LaporanEntity
import com.example.projek_farrell.databinding.ItemLaporanBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Adapter RecyclerView: mengubah setiap LaporanEntity menjadi satu baris item_laporan.xml
class LaporanAdapter(
    private val laporanList: List<LaporanEntity>,
    private val onDelete: (LaporanEntity) -> Unit,
    private val onItemClick: (LaporanEntity) -> Unit
) : RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder>() {

    // Format tanggal dari createdAt (milidetik) menjadi "16 Nov 2026"
    private val formatTanggal = SimpleDateFormat("d MMM yyyy", Locale("id", "ID"))

    class LaporanViewHolder(val binding: ItemLaporanBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val binding = ItemLaporanBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LaporanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        val laporan = laporanList[position]
        val tanggal = formatTanggal.format(Date(laporan.createdAt))

        with(holder.binding) {
            // 1. Isi teks
            tvJudul.text = laporan.judul
            tvKeterangan.text = "${laporan.kategori} • $tanggal"
            tvStatus.text = laporan.status

            // 2. Ikon berbeda sesuai kategori
            val ikon = when (laporan.kategori) {
                "Jalan Rusak" -> R.drawable.ic_place
                "Sampah" -> R.drawable.ic_eco
                "Banjir" -> R.drawable.ic_weather
                "Lampu Jalan" -> R.drawable.ic_bolt
                else -> R.drawable.ic_pengaduan
            }
            imgKategori.setImageResource(ikon)

            // 3. Warna status: emas = Diproses, teal = Selesai (if + else wajib, karena baris didaur ulang)
            if (laporan.status == "Selesai") {
                tvStatus.setBackgroundResource(R.drawable.bg_chip)
                tvStatus.setTextColor(Color.parseColor("#FF087F75"))
            } else {
                tvStatus.setBackgroundResource(R.drawable.bg_chip_gold)
                tvStatus.setTextColor(Color.parseColor("#FFB8975A"))
            }

            // 4. Klik baris -> buka halaman Status Pengaduan
            root.setOnClickListener { onItemClick(laporan) }

            // 5. Tombol hapus -> dialog konfirmasi -> callback ke Activity
            btnHapus.setOnClickListener {
                MaterialAlertDialogBuilder(holder.itemView.context)
                    .setTitle("Hapus Laporan")
                    .setMessage("Yakin ingin menghapus laporan \"${laporan.judul}\"?")
                    .setPositiveButton("Ya") { _, _ -> onDelete(laporan) }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = laporanList.size
}
