package com.example.projek_farrell.info

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Menentukan Fragment untuk setiap tab
class InfoTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Jumlah tab
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BeritaFragment()
            1 -> FasilitasFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}
