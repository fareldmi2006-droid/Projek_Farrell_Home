package com.example.projek_farrell

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projek_farrell.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi ViewBinding di Fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 0. Sapaan di banner: ambil username dari SharedPreferences (sesi login)
        val sharedPreferences = requireContext().getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", "Warga")
        binding.tvSapaan.text = "Halo, $username!"

        // 1. Menu Akses Cepat (GridLayout)
        // Pengaduan membuka Form Pengaduan (Pertemuan 9)
        binding.menuPengaduan.setOnClickListener {
            startActivity(Intent(requireContext(), PengaduanActivity::class.java))
        }
        binding.menuLayanan.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Layanan", Toast.LENGTH_SHORT).show()
        }
        binding.menuPeta.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Peta Kota", Toast.LENGTH_SHORT).show()
        }
        binding.menuTransportasi.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Transportasi", Toast.LENGTH_SHORT).show()
        }
        binding.menuKesehatan.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Kesehatan", Toast.LENGTH_SHORT).show()
        }
        binding.menuDarurat.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Darurat: hubungi 112", Toast.LENGTH_SHORT).show()
        }

        // 2. Intent dari MainActivity (HomeFragment) ke DetailActivity
        binding.btnDetail.setOnClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EXTRA_JUDUL", "NUCEOS Smart City")
            startActivity(intent)
        }

        // 3. Kartu berita
        binding.cardBerita.setOnClickListener {
            Toast.makeText(requireContext(), "Peresmian jalan Kota Pekanbaru", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}
