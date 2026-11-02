package com.example.projek_farrell.laporan

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projek_farrell.R
import com.example.projek_farrell.databinding.ActivityRiwayatLaporanBinding

class RiwayatLaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatLaporanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi ViewBinding
        binding = ActivityRiwayatLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Riwayat Laporan"
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener { finish() }

        // 2. Saat pertama dibuka, tampilkan semua laporan
        tampilkanLaporan("Semua")

        // 3. Filter dengan ChipGroup
        binding.chipGroupStatus.setOnCheckedStateChangeListener { _, checkedIds ->
            when (checkedIds.firstOrNull()) {
                R.id.chipDiproses -> tampilkanLaporan("Diproses")
                R.id.chipSelesai -> tampilkanLaporan("Selesai")
                else -> tampilkanLaporan("Semua")
            }
        }
    }

    // Menyaring daftar sesuai status, lalu memasang adapter ke ListView
    private fun tampilkanLaporan(status: String) {
        val hasil = if (status == "Semua") {
            LaporanData.daftarLaporan
        } else {
            LaporanData.daftarLaporan.filter { it.status == status }
        }

        val adapter = LaporanAdapter(this, hasil)
        binding.listLaporan.adapter = adapter

        // Tampilkan tulisan "Belum ada laporan" kalau hasil kosong
        binding.tvKosong.visibility = if (hasil.isEmpty()) View.VISIBLE else View.GONE
    }
}
