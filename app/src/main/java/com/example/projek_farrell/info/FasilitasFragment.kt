package com.example.projek_farrell.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projek_farrell.databinding.FragmentInfoListBinding

// Tab 2: Fasilitas Kota, ditampilkan sebagai grid (2 kolom)
class FasilitasFragment : Fragment() {

    private var _binding: FragmentInfoListBinding? = null
    private val binding get() = _binding!!

    private val fasilitasList = listOf(
        InfoModel("Masjid Agung An-Nur", "Ibadah • Sukajadi", "https://picsum.photos/seed/masjid/400/300"),
        InfoModel("Perpustakaan Soeman HS", "Pendidikan • Sudirman", "https://picsum.photos/seed/perpus/400/300"),
        InfoModel("RTH Tunjuk Ajar Integritas", "Taman • Diponegoro", "https://picsum.photos/seed/taman/400/300"),
        InfoModel("Mal Pelayanan Publik", "Administrasi • Sudirman", "https://picsum.photos/seed/mpp/400/300"),
        InfoModel("RSUD Arifin Achmad", "Kesehatan • Diponegoro", "https://picsum.photos/seed/rsud/400/300"),
        InfoModel("Stadion Utama Riau", "Olahraga • Tampan", "https://picsum.photos/seed/stadion/400/300"),
        InfoModel("Halte Bus Kota", "Transportasi • Sudirman", "https://picsum.photos/seed/halte/400/300"),
        InfoModel("Taman Alam Mayang", "Rekreasi • Tenayan Raya", "https://picsum.photos/seed/mayang/400/300"),
        InfoModel("Kantor Wali Kota", "Pemerintahan • Tenayan Raya", "https://picsum.photos/seed/walikota/400/300"),
        InfoModel("Puskesmas Rumbai", "Kesehatan • Rumbai", "https://picsum.photos/seed/puskesmas/400/300")
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

        val fasilitasAdapter = InfoAdapter(fasilitasList) { item ->
            Toast.makeText(requireContext(), "${item.judul} (${item.keterangan})", Toast.LENGTH_SHORT).show()
        }

        binding.rvInfo.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)   // grid 2 kolom
            adapter = fasilitasAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
