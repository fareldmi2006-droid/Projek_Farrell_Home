package com.example.projek_farrell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projek_farrell.databinding.FragmentLayananBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LayananFragment : Fragment() {

    private lateinit var binding: FragmentLayananBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi ViewBinding di Fragment
        binding = FragmentLayananBinding.inflate(inflater, container, false)

        // 1. Tombol cari: cari layanan dari kata kunci, lalu tampilkan detailnya
        binding.btnCari.setOnClickListener {
            val kataKunci = binding.etCari.text.toString().trim()
            if (kataKunci.isEmpty()) {
                Toast.makeText(requireContext(), "Ketik nama layanan dulu", Toast.LENGTH_SHORT).show()
            } else if (!LayananHelper.cari(requireContext(), kataKunci)) {
                Toast.makeText(requireContext(), "Layanan \"$kataKunci\" tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Setiap kartu layanan membuka dialog detail layanan
        binding.cardAdministrasi.setOnClickListener { LayananHelper.tampilkan(requireContext(), "administrasi") }
        binding.cardKesehatan.setOnClickListener { LayananHelper.tampilkan(requireContext(), "kesehatan") }
        binding.cardLingkungan.setOnClickListener { LayananHelper.tampilkan(requireContext(), "lingkungan") }
        binding.cardTransportasi.setOnClickListener { LayananHelper.tampilkan(requireContext(), "transportasi") }
        binding.cardUtilitas.setOnClickListener { LayananHelper.tampilkan(requireContext(), "utilitas") }
        binding.cardInformasi.setOnClickListener { LayananHelper.tampilkan(requireContext(), "informasi") }

        // 3. Tombol Hubungi Kami: pilih kontak bantuan
        binding.btnHubungi.setOnClickListener {
            val pilihan = arrayOf("Telepon darurat 112", "Kirim pengaduan lewat aplikasi")
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Hubungi Kami")
                .setItems(pilihan) { _, posisi ->
                    when (posisi) {
                        0 -> LayananHelper.darurat(requireContext())
                        1 -> startActivity(Intent(requireContext(), PengaduanActivity::class.java))
                    }
                }
                .show()
        }

        return binding.root
    }
}
