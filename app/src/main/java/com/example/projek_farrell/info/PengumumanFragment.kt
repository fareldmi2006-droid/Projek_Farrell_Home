package com.example.projek_farrell.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projek_farrell.databinding.FragmentInfoListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// Tab 3: Pengumuman resmi (data contoh), ditampilkan sebagai daftar
class PengumumanFragment : Fragment() {

    private var _binding: FragmentInfoListBinding? = null
    private val binding get() = _binding!!

    companion object {
        val pengumumanList = listOf(
            InfoModel("Jadwal pengangkutan sampah", "Lingkungan • Dinas Lingkungan Hidup", "https://picsum.photos/seed/sampah/600/300"),
            InfoModel("Pemeliharaan jalan di pusat kota", "Pemerintahan • Pemerintah Kota", "https://picsum.photos/seed/aspal/600/300"),
            InfoModel("Rekayasa lalu lintas akhir pekan", "Transportasi • Dinas Perhubungan", "https://picsum.photos/seed/lalulintas/600/300")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pengumumanAdapter = InfoAdapter(pengumumanList) { item ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(item.judul)
                .setMessage("Sumber: ${item.keterangan}\n\nIkuti pengumuman ini dan sebarkan ke warga sekitar.")
                .setPositiveButton("Tutup", null)
                .show()
        }

        binding.rvInfo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pengumumanAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
