package com.example.projek_farrell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projek_farrell.databinding.FragmentProfilBinding
import androidx.lifecycle.lifecycleScope
import com.example.projek_farrell.data.AppDatabase
import com.example.projek_farrell.laporan.RiwayatLaporanActivity
import kotlinx.coroutines.launch

class ProfilFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi ViewBinding di Fragment
        binding = FragmentProfilBinding.inflate(inflater, container, false)

        // 1. Ambil username dari Intent milik MainActivity
        val username = requireActivity().intent.getStringExtra("EXTRA_USERNAME") ?: "farrell"
        binding.tvUsername.text = username
        binding.tvAvatar.text = username.take(1).uppercase()

        // 2. Klik angka Laporan untuk membuka Riwayat (jumlahnya diisi di onResume)
        binding.statLaporan.setOnClickListener {
            startActivity(Intent(requireContext(), RiwayatLaporanActivity::class.java))
        }

        // 3. Menu Akun & Preferensi menampilkan Toast saat disentuh
        binding.menuDataDiri.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Data diri", Toast.LENGTH_SHORT).show()
        }
        binding.menuAlamat.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Alamat tersimpan", Toast.LENGTH_SHORT).show()
        }
        binding.menuNotifikasi.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Notifikasi", Toast.LENGTH_SHORT).show()
        }
        binding.menuBantuan.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Pusat bantuan", Toast.LENGTH_SHORT).show()
        }
        binding.menuPengaturan.setOnClickListener {
            Toast.makeText(requireContext(), "Menu Pengaturan", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    // Jumlah laporan dibaca dari Room setiap halaman Profil tampil
    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            val jumlah = AppDatabase.getInstance(requireContext()).laporanDao().count()
            binding.tvJumlahLaporan.text = String.format("%02d", jumlah)
        }
    }
}
