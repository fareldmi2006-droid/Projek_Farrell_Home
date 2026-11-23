package com.example.projek_farrell.info

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Menentukan Fragment untuk setiap tab (poster 07: Berita, Forum Warga, Pengumuman)
class InfoTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Jumlah tab
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BeritaFragment()
            1 -> ForumFragment()
            2 -> PengumumanFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}
