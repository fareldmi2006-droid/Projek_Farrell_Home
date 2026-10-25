package com.example.projek_farrell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projek_farrell.databinding.FragmentLayananBinding

class LayananFragment : Fragment() {

    private lateinit var binding: FragmentLayananBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi ViewBinding di Fragment
        binding = FragmentLayananBinding.inflate(inflater, container, false)

        // 1. Tombol cari: tampilkan kata kunci dengan Toast
        binding.btnCari.setOnClickListener {
            val kataKunci = binding.etCari.text.toString().trim()
            if (kataKunci.isEmpty()) {
                Toast.makeText(requireContext(), "Ketik nama layanan dulu", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Mencari layanan: $kataKunci", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Setiap kartu layanan menampilkan Toast saat disentuh
        binding.cardAdministrasi.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan Administrasi: KTP, KK, Akta", Toast.LENGTH_SHORT).show()
        }
        binding.cardKesehatan.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan Kesehatan: Puskesmas & RS", Toast.LENGTH_SHORT).show()
        }
        binding.cardLingkungan.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan Lingkungan: Sampah & kebersihan", Toast.LENGTH_SHORT).show()
        }
        binding.cardTransportasi.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan Transportasi: Rute & info rute", Toast.LENGTH_SHORT).show()
        }
        binding.cardUtilitas.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan Utilitas: Air & Listrik", Toast.LENGTH_SHORT).show()
        }
        binding.cardInformasi.setOnClickListener {
            Toast.makeText(requireContext(), "Informasi Kota: Pengumuman resmi", Toast.LENGTH_SHORT).show()
        }

        // 3. Tombol Hubungi Kami
        binding.btnHubungi.setOnClickListener {
            Toast.makeText(requireContext(), "Menghubungi pusat layanan warga...", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}
