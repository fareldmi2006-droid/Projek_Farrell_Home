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

    companion object {
        // Tab yang dibuka pertama kali: 0 = Berita, 1 = Forum Warga, 2 = Pengumuman
        const val EXTRA_TAB = "EXTRA_TAB"
    }

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
        supportActionBar?.title = "Berita & Forum"
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
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_info)
                }
                1 -> {
                    tab.text = "Forum Warga"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_person)
                }
                2 -> {
                    tab.text = "Pengumuman"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_bell)
                    // Badge angka: jumlah pengumuman baru
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = PengumumanFragment.pengumumanList.size
                }
            }
        }.attach()

        // 3. Buka tab tertentu kalau diminta (contoh: ikon lonceng -> Pengumuman)
        binding.viewPager.setCurrentItem(intent.getIntExtra(EXTRA_TAB, 0), false)
    }
}
