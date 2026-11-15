package com.example.projek_farrell.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projek_farrell.databinding.FragmentInfoListBinding

// Tab 1: Berita Kota, ditampilkan sebagai daftar (1 kolom)
class BeritaFragment : Fragment() {

    private var _binding: FragmentInfoListBinding? = null
    private val binding get() = _binding!!

    private val beritaList = listOf(
        InfoModel("Peresmian jalan baru di Kota Pekanbaru", "Pemerintahan • Hari ini", "https://picsum.photos/seed/jalan/600/300"),
        InfoModel("Jadwal vaksinasi gratis di puskesmas", "Kesehatan • Kemarin", "https://picsum.photos/seed/vaksin/600/300"),
        InfoModel("Bus kota tambah rute ke Rumbai", "Transportasi • 2 hari lalu", "https://picsum.photos/seed/buskota/600/300"),
        InfoModel("Gotong royong bersihkan drainase", "Lingkungan • 3 hari lalu", "https://picsum.photos/seed/drainase/600/300"),
        InfoModel("Pembuatan KTP bisa lewat Mal Pelayanan Publik", "Administrasi • 4 hari lalu", "https://picsum.photos/seed/ktp/600/300"),
        InfoModel("Festival kuliner Melayu di akhir pekan", "Acara • 5 hari lalu", "https://picsum.photos/seed/kuliner/600/300"),
        InfoModel("Pemadaman listrik terjadwal di Tampan", "Utilitas • 6 hari lalu", "https://picsum.photos/seed/listrik/600/300"),
        InfoModel("Lomba kebersihan antar kelurahan dibuka", "Lingkungan • 1 minggu lalu", "https://picsum.photos/seed/lomba/600/300")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beritaAdapter = InfoAdapter(beritaList) { item ->
            Toast.makeText(requireContext(), "Membuka berita: ${item.judul}", Toast.LENGTH_SHORT).show()
        }

        binding.rvInfo.apply {
            layoutManager = LinearLayoutManager(requireContext())   // daftar 1 kolom
            adapter = beritaAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
