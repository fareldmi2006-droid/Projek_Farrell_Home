package com.example.projek_farrell.tutorial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.projek_farrell.databinding.ActivityTutorialBinding

class TutorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi ViewBinding
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Daftar halaman tutorial (Tahap 5)
        val fragmentsList = listOf<Fragment>(
            Tutorial1Fragment(),
            Tutorial2Fragment(),
            Tutorial3Fragment()
        )

        // 2. Pasang adapter ke ViewPager2
        val adapter = TutorialFragmentAdapter(this, fragmentsList)
        binding.tutorialViewPager.adapter = adapter

        // 3. Hubungkan titik penanda (Tahap 6) - harus SETELAH adapter dipasang
        binding.dotIndicator.attachTo(binding.tutorialViewPager)

        // 4. Tombol Lewati menutup tutorial dan kembali ke Beranda
        binding.btnLewati.setOnClickListener { finish() }
    }
}
