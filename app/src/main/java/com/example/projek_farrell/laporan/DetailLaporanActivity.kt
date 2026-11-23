package com.example.projek_farrell.laporan

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.projek_farrell.R
import com.example.projek_farrell.data.AppDatabase
import com.example.projek_farrell.data.entity.LaporanEntity
import com.example.projek_farrell.databinding.ActivityDetailLaporanBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Poster 06: Status Pengaduan. Data diambil dari Room berdasarkan id yang dikirim lewat Intent
class DetailLaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLaporanBinding
    private lateinit var db: AppDatabase
    private var laporanId = 0

    companion object {
        const val EXTRA_ID = "EXTRA_ID"

        // Nomor laporan dibuat dari tahun + id, contoh: NC-2026-00007
        fun nomorLaporan(laporan: LaporanEntity): String {
            val tahun = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(laporan.createdAt))
            return String.format("NC-%s-%05d", tahun, laporan.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Status Pengaduan"
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener { finish() }

        // 2. Ambil id laporan dari Intent, lalu baca dari database
        db = AppDatabase.getInstance(this)
        laporanId = intent.getIntExtra(EXTRA_ID, 0)
        muatLaporan()
    }

    // READ: ambil satu laporan berdasarkan id
    private fun muatLaporan() {
        lifecycleScope.launch {
            val laporan = db.laporanDao().getById(laporanId)
            if (laporan == null) {
                Toast.makeText(this@DetailLaporanActivity, "Laporan tidak ditemukan", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                tampilkan(laporan)
            }
        }
    }

    private fun tampilkan(laporan: LaporanEntity) {
        val formatTanggal = SimpleDateFormat("d MMM yyyy", Locale("id", "ID"))
        val formatWaktu = SimpleDateFormat("d MMM • HH:mm", Locale("id", "ID"))
        val selesai = laporan.status == "Selesai"

        // Kartu nomor laporan
        binding.tvNomor.text = nomorLaporan(laporan)
        binding.tvDibuat.text = "Dibuat ${formatTanggal.format(Date(laporan.createdAt))}"
        if (selesai) {
            binding.tvStatus.text = "SELESAI"
            binding.tvStatus.setBackgroundResource(R.drawable.bg_chip)
            binding.tvStatus.setTextColor(Color.parseColor("#FF087F75"))
        } else {
            binding.tvStatus.text = "SEDANG DIPROSES"
            binding.tvStatus.setBackgroundResource(R.drawable.bg_chip_gold)
            binding.tvStatus.setTextColor(Color.parseColor("#FFB8975A"))
        }

        // Timeline: hijau = sudah, emas = sedang berjalan, abu-abu = belum
        binding.tvKet1.text = formatWaktu.format(Date(laporan.createdAt))
        binding.tvKet2.text = "Laporan sudah dicek petugas"
        binding.tvKet3.text = if (selesai) "Penanganan sudah dilakukan" else "Penjadwalan tim teknis"
        binding.tvKet4.text = if (selesai) "Masalah sudah diselesaikan" else "Menunggu tindak lanjut"
        warnaiLangkah(binding.dot1, R.color.teal)
        warnaiLangkah(binding.dot2, R.color.teal)
        warnaiLangkah(binding.dot3, if (selesai) R.color.teal else R.color.champagne_gold)
        warnaiLangkah(binding.dot4, if (selesai) R.color.teal else R.color.outline)
        binding.garis1.setBackgroundResource(R.color.teal)
        binding.garis2.setBackgroundResource(R.color.teal)
        binding.garis3.setBackgroundResource(if (selesai) R.color.teal else R.color.outline)

        // Detail pengaduan
        binding.tvJudul.text = laporan.judul
        binding.tvKategori.text = laporan.kategori
        binding.tvLokasi.text = laporan.lokasi.ifEmpty { "Tidak diisi" }
        binding.tvPelapor.text = laporan.pelapor
        binding.tvDeskripsi.text = laporan.deskripsi.ifEmpty { "Tidak diisi" }

        // Tombol simulasi petugas hanya muncul kalau laporan belum selesai
        binding.btnSelesai.visibility = if (selesai) View.GONE else View.VISIBLE
        binding.btnSelesai.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Tandai Selesai")
                .setMessage("Ubah status laporan ini menjadi Selesai?")
                .setPositiveButton("Ya") { _, _ -> tandaiSelesai(laporan) }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    // UPDATE: ubah status di Room, lalu tampilkan ulang
    private fun tandaiSelesai(laporan: LaporanEntity) {
        lifecycleScope.launch {
            db.laporanDao().update(laporan.copy(status = "Selesai"))
            Toast.makeText(this@DetailLaporanActivity, "Status diubah menjadi Selesai", Toast.LENGTH_SHORT).show()
            muatLaporan()
        }
    }

    private fun warnaiLangkah(dot: ImageView, warna: Int) {
        dot.setColorFilter(ContextCompat.getColor(this, warna))
    }
}
