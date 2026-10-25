package com.example.projek_farrell

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projek_farrell.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi ViewBinding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Ambil judul projek yang dikirim dari HomeFragment
        val judul = intent.getStringExtra("EXTRA_JUDUL")
        binding.tvJudulDetail.text = judul

        // 2. Tampilkan gambar projek
        binding.ivLogoDetail.setImageResource(R.drawable.img_logo_nuceos)
        binding.ivMockup.setImageResource(R.drawable.img_detail_banner)

        // 3. Isi daftar fitur aplikasi
        binding.tvFitur.text = "• Beranda & cuaca\n• Peta Kota\n• Layanan Publik\n• Buat & pantau pengaduan\n• Berita & Forum warga\n• Profil warga"

        // 4. Tombol kembali ke MainActivity
        binding.btnKembali.setOnClickListener {
            finish()
        }
    }
}
