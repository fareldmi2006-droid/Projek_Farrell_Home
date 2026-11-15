package com.example.projek_farrell.info

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projek_farrell.R
import com.example.projek_farrell.databinding.ActivityInfoKotaBinding
import com.google.android.material.tabs.TabLayoutMediator

class InfoKotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoKotaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInfoKotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Info Kota"
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.white))
        binding.toolbar.setNavigationOnClickListener { finish() }

        // 1. Pasang adapter ke ViewPager2
        binding.viewPager.adapter = InfoTabsAdapter(this)

        // 2. Hubungkan TabLayout dan ViewPager2 + judul, ikon, badge
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Berita"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_bell)
                    // Badge angka: jumlah berita baru
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 3
                }
                1 -> {
                    tab.text = "Fasilitas"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_place)
                }
            }
        }.attach()
    }
}
