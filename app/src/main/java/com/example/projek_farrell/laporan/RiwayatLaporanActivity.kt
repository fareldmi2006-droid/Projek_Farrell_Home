package com.example.projek_farrell.laporan

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projek_farrell.R
import com.example.projek_farrell.data.AppDatabase
import com.example.projek_farrell.data.entity.LaporanEntity
import com.example.projek_farrell.databinding.ActivityRiwayatLaporanBinding
import kotlinx.coroutines.launch

class RiwayatLaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatLaporanBinding
    private lateinit var db: AppDatabase
    private lateinit var adapter: LaporanAdapter

    // semuaLaporan = isi database, tampil = yang sedang ditampilkan (hasil filter)
    private val semuaLaporan = mutableListOf<LaporanEntity>()
    private val tampil = mutableListOf<LaporanEntity>()
    private var filterStatus = "Semua"

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

        // 2. Siapkan database dan RecyclerView
        db = AppDatabase.getInstance(this)
        adapter = LaporanAdapter(tampil) { laporan -> hapusLaporan(laporan) }
        binding.rvLaporan.layoutManager = LinearLayoutManager(this)
        binding.rvLaporan.adapter = adapter
        binding.rvLaporan.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        // 3. Filter dengan ChipGroup (Pertemuan 9)
        binding.chipGroupStatus.setOnCheckedStateChangeListener { _, checkedIds ->
            filterStatus = when (checkedIds.firstOrNull()) {
                R.id.chipDiproses -> "Diproses"
                R.id.chipSelesai -> "Selesai"
                else -> "Semua"
            }
            terapkanFilter()
        }
    }

    // Setiap halaman tampil lagi, baca ulang data dari database
    override fun onResume() {
        super.onResume()
        muatLaporan()
    }

    // READ: ambil semua laporan dari Room
    private fun muatLaporan() {
        lifecycleScope.launch {
            val hasil = db.laporanDao().getAll()
            semuaLaporan.clear()
            semuaLaporan.addAll(hasil)
            terapkanFilter()
        }
    }

    // DELETE: hapus dari Room, lalu muat ulang
    private fun hapusLaporan(laporan: LaporanEntity) {
        lifecycleScope.launch {
            db.laporanDao().delete(laporan)
            muatLaporan()
        }
    }

    // Menyaring daftar sesuai chip yang dipilih
    private fun terapkanFilter() {
        val hasil = if (filterStatus == "Semua") semuaLaporan
                    else semuaLaporan.filter { it.status == filterStatus }
        tampil.clear()
        tampil.addAll(hasil)
        adapter.notifyDataSetChanged()

        // Tulisan "Belum ada laporan" kalau kosong
        binding.tvKosong.visibility = if (tampil.isEmpty()) View.VISIBLE else View.GONE
    }
}
