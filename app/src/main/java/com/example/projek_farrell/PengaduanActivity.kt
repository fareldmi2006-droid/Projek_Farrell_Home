package com.example.projek_farrell

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projek_farrell.databinding.ActivityPengaduanBinding
import com.google.android.material.chip.Chip

class PengaduanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPengaduanBinding

    // Menyimpan kategori yang sedang dipilih (awalnya "Jalan Rusak")
    private var kategoriDipilih = "Jalan Rusak"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi ViewBinding
        binding = ActivityPengaduanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Toolbar + tombol back (Tahap 0)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Form Pengaduan"
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener { finish() }

        // 2. Deteksi chip kategori yang dipilih (Tahap 2)
        binding.chipGroupKategori.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull()
            if (selectedChipId != null) {
                val chip = group.findViewById<Chip>(selectedChipId)
                kategoriDipilih = chip.text.toString()
                Toast.makeText(this, "Kategori: $kategoriDipilih", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Tombol Kirim: validasi lalu tampilkan ringkasan laporan (Tahap 1)
        binding.btnKirim.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val judul = binding.etJudul.text.toString().trim()

            // Hapus pesan error lama dulu
            binding.tilNama.error = null
            binding.tilJudul.error = null

            if (nama.isEmpty()) {
                binding.tilNama.error = "Nama pelapor wajib diisi"
            } else if (judul.isEmpty()) {
                binding.tilJudul.error = "Judul laporan wajib diisi"
            } else {
                Toast.makeText(
                    this,
                    "Laporan terkirim!\n$judul\nKategori: $kategoriDipilih\nPelapor: $nama",
                    Toast.LENGTH_LONG
                ).show()
                finish()   // kembali ke Beranda
            }
        }
    }
}
