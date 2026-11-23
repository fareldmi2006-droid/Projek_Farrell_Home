package com.example.projek_farrell.peta

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projek_farrell.databinding.FragmentPetaBinding

// Poster 03: Peta Kota. Cari fasilitas publik, lalu buka rutenya di Google Maps
class PetaFragment : Fragment() {

    private var _binding: FragmentPetaBinding? = null
    private val binding get() = _binding!!

    // Data contoh fasilitas publik di Pekanbaru
    private val semuaLokasi = listOf(
        LokasiModel("Kantor Wali Kota Pekanbaru", "Tenayan Raya, Pekanbaru", "Pemerintahan"),
        LokasiModel("Mal Pelayanan Publik Pekanbaru", "Pekanbaru", "Administrasi"),
        LokasiModel("RSUD Arifin Achmad", "Jl. Diponegoro, Pekanbaru", "Kesehatan"),
        LokasiModel("Perpustakaan Soeman HS", "Jl. Jenderal Sudirman, Pekanbaru", "Pendidikan"),
        LokasiModel("Masjid Raya An-Nur", "Jl. Hang Tuah, Pekanbaru", "Ibadah"),
        LokasiModel("Taman Alam Mayang", "Tenayan Raya, Pekanbaru", "Rekreasi"),
        LokasiModel("Stadion Utama Riau", "Tampan, Pekanbaru", "Olahraga"),
        LokasiModel("Puskesmas Rumbai", "Rumbai, Pekanbaru", "Kesehatan")
    )
    private val tampil = mutableListOf<LokasiModel>()
    private lateinit var adapter: LokasiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. RecyclerView daftar lokasi
        adapter = LokasiAdapter(tampil) { lokasi -> bukaMaps(lokasi.nama) }
        binding.rvLokasi.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLokasi.adapter = adapter
        saring("")

        // 2. Cari lokasi: daftar langsung tersaring setiap huruf diketik
        binding.etCariLokasi.doAfterTextChanged { teks ->
            saring(teks.toString().trim())
        }

        // 3. Ketuk peta: buka Google Maps di Pekanbaru
        binding.cardPeta.setOnClickListener { bukaMaps("Pekanbaru") }
    }

    private fun saring(kataKunci: String) {
        val hasil = semuaLokasi.filter {
            it.nama.contains(kataKunci, ignoreCase = true) ||
                it.alamat.contains(kataKunci, ignoreCase = true) ||
                it.kategori.contains(kataKunci, ignoreCase = true)
        }
        tampil.clear()
        tampil.addAll(hasil)
        adapter.notifyDataSetChanged()
        binding.tvJumlahLokasi.text = "Fasilitas publik (${hasil.size})"
        binding.tvKosongLokasi.visibility = if (hasil.isEmpty()) View.VISIBLE else View.GONE
    }

    // Intent implisit: minta aplikasi peta (Google Maps) mencari tempat ini
    private fun bukaMaps(tempat: String) {
        val uri = Uri.parse("geo:0,0?q=" + Uri.encode("$tempat, Pekanbaru"))
        try {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Aplikasi peta tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
