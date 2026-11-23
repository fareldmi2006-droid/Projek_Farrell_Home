package com.example.projek_farrell

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projek_farrell.databinding.FragmentHomeBinding
import com.example.projek_farrell.info.InfoKotaActivity
import com.example.projek_farrell.laporan.RiwayatLaporanActivity

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
        // Layanan & Peta pindah ke menu bawah yang sesuai
        binding.menuLayanan.setOnClickListener {
            (activity as? MainActivity)?.bukaMenu(R.id.nav_layanan)
        }
        binding.menuPeta.setOnClickListener {
            (activity as? MainActivity)?.bukaMenu(R.id.nav_peta)
        }
        // Transportasi & Kesehatan menampilkan detail layanan
        binding.menuTransportasi.setOnClickListener {
            LayananHelper.tampilkan(requireContext(), "transportasi")
        }
        binding.menuKesehatan.setOnClickListener {
            LayananHelper.tampilkan(requireContext(), "kesehatan")
        }
        // Darurat: konfirmasi lalu buka aplikasi telepon ke 112
        binding.menuDarurat.setOnClickListener {
            LayananHelper.darurat(requireContext())
        }

        // 2. Intent dari MainActivity (HomeFragment) ke DetailActivity
        binding.btnDetail.setOnClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EXTRA_JUDUL", "NUCEOS Smart City")
            startActivity(intent)
        }

        // 3. Kartu berita membuka halaman Berita & Forum (Pertemuan 10)
        binding.cardBerita.setOnClickListener {
            startActivity(Intent(requireContext(), InfoKotaActivity::class.java))
        }

        // 4. "Lihat semua" membuka halaman Berita & Forum
        binding.tvLihatSemua.setOnClickListener {
            startActivity(Intent(requireContext(), InfoKotaActivity::class.java))
        }

        // 5. Kartu Aktivitas Terbaru membuka Riwayat Laporan
        binding.cardAktivitas.setOnClickListener {
            startActivity(Intent(requireContext(), RiwayatLaporanActivity::class.java))
        }

        return binding.root
    }
}
