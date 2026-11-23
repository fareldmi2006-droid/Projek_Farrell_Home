package com.example.projek_farrell

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.projek_farrell.data.AppDatabase
import com.example.projek_farrell.databinding.FragmentProfilBinding
import com.example.projek_farrell.laporan.RiwayatLaporanActivity
import com.example.projek_farrell.tutorial.TutorialActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class ProfilFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    // SharedPreferences yang sama dengan Login (sesi pengguna)
    private val prefs by lazy {
        requireContext().getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi ViewBinding di Fragment
        binding = FragmentProfilBinding.inflate(inflater, container, false)

        // 1. Nama pengguna: dari SharedPreferences, cadangannya dari Intent MainActivity
        tampilkanNama()

        // 2. Klik angka Laporan untuk membuka Riwayat (jumlahnya diisi di onResume)
        binding.statLaporan.setOnClickListener {
            startActivity(Intent(requireContext(), RiwayatLaporanActivity::class.java))
        }

        // 3. Menu Akun & Preferensi
        binding.menuDataDiri.setOnClickListener { ubahDataDiri() }
        binding.menuAlamat.setOnClickListener { ubahAlamat() }
        binding.menuNotifikasi.setOnClickListener { aturNotifikasi() }
        binding.menuBantuan.setOnClickListener { tampilkanBantuan() }
        binding.menuPengaturan.setOnClickListener { bukaPengaturan() }

        return binding.root
    }

    // Statistik dibaca ulang setiap halaman Profil tampil
    override fun onResume() {
        super.onResume()
        muatStatistik()
    }

    private fun muatStatistik() {
        viewLifecycleOwner.lifecycleScope.launch {
            val jumlah = AppDatabase.getInstance(requireContext()).laporanDao().count()
            binding.tvJumlahLaporan.text = String.format("%02d", jumlah)
            binding.tvJumlahLayanan.text = String.format("%02d", LayananHelper.daftar.size)
            // Poin warga: setiap laporan bernilai 10 poin
            binding.tvPoin.text = (jumlah * 10).toString()
        }
    }

    private fun tampilkanNama() {
        val cadangan = requireActivity().intent.getStringExtra("EXTRA_USERNAME") ?: "Warga"
        val nama = prefs.getString("USERNAME", cadangan) ?: cadangan
        binding.tvUsername.text = nama
        binding.tvAvatar.text = nama.take(1).uppercase()
    }

    // Membuat kolom isian untuk dipasang di dalam dialog
    private fun buatInput(isiAwal: String, petunjuk: String): Pair<FrameLayout, EditText> {
        val input = EditText(requireContext())
        input.setText(isiAwal)
        input.hint = petunjuk
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
        val wadah = FrameLayout(requireContext())
        val jarak = (20 * resources.displayMetrics.density).toInt()
        wadah.setPadding(jarak, jarak / 2, jarak, 0)
        wadah.addView(input)
        return Pair(wadah, input)
    }

    // Data diri: ubah nama tampilan, disimpan di SharedPreferences
    private fun ubahDataDiri() {
        val (wadah, input) = buatInput(binding.tvUsername.text.toString(), "Nama tampilan")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Data diri")
            .setView(wadah)
            .setPositiveButton("Simpan") { _, _ ->
                val nama = input.text.toString().trim()
                if (nama.isEmpty()) {
                    Toast.makeText(requireContext(), "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                } else {
                    prefs.edit().putString("USERNAME", nama).apply()
                    tampilkanNama()
                    Toast.makeText(requireContext(), "Data diri disimpan", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // Alamat tersimpan: dipakai sebagai lokasi favorit
    private fun ubahAlamat() {
        val (wadah, input) = buatInput(prefs.getString("ALAMAT", "") ?: "", "Contoh: Jl. Melati No. 5")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Alamat tersimpan")
            .setView(wadah)
            .setPositiveButton("Simpan") { _, _ ->
                prefs.edit().putString("ALAMAT", input.text.toString().trim()).apply()
                Toast.makeText(requireContext(), "Alamat disimpan", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // Notifikasi: pilihan disimpan di SharedPreferences
    private fun aturNotifikasi() {
        val jenis = arrayOf("Status laporan", "Berita kota", "Pengumuman resmi")
        val aktif = BooleanArray(jenis.size) { i -> prefs.getBoolean("NOTIF_$i", true) }
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Notifikasi")
            .setMultiChoiceItems(jenis, aktif) { _, posisi, dicentang -> aktif[posisi] = dicentang }
            .setPositiveButton("Simpan") { _, _ ->
                val editor = prefs.edit()
                aktif.forEachIndexed { i, nilai -> editor.putBoolean("NOTIF_$i", nilai) }
                editor.apply()
                Toast.makeText(requireContext(), "Pengaturan notifikasi disimpan", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // Pusat bantuan: FAQ singkat
    private fun tampilkanBantuan() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pusat bantuan")
            .setMessage(
                "Bagaimana cara melapor?\nKetuk menu Lapor, isi form, lalu Kirim.\n\n" +
                    "Di mana melihat status laporan?\nProfil → angka Laporan, lalu ketuk laporannya.\n\n" +
                    "Apakah laporan tersimpan?\nYa, laporan disimpan di HP dengan Room Database."
            )
            .setPositiveButton("Mengerti", null)
            .setNeutralButton("Lihat tutorial") { _, _ ->
                startActivity(Intent(requireContext(), TutorialActivity::class.java))
            }
            .show()
    }

    // Pengaturan: tutorial, hapus semua laporan, tentang aplikasi
    private fun bukaPengaturan() {
        val pilihan = arrayOf("Lihat tutorial lagi", "Hapus semua laporan", "Tentang NUCEOS")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pengaturan")
            .setItems(pilihan) { _, posisi ->
                when (posisi) {
                    0 -> startActivity(Intent(requireContext(), TutorialActivity::class.java))
                    1 -> konfirmasiHapusSemua()
                    2 -> MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Tentang NUCEOS")
                        .setMessage("NUCEOS Smart City\nSmart Services. Better Living.\n\nDibuat oleh Farrell Fathurrahman (2455301067) untuk mata kuliah Mobile Programming.")
                        .setPositiveButton("Tutup", null)
                        .show()
                }
            }
            .show()
    }

    private fun konfirmasiHapusSemua() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hapus semua laporan")
            .setMessage("Semua laporan di HP ini akan dihapus. Lanjutkan?")
            .setPositiveButton("Hapus") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    AppDatabase.getInstance(requireContext()).laporanDao().deleteAll()
                    Toast.makeText(requireContext(), "Semua laporan dihapus", Toast.LENGTH_SHORT).show()
                    muatStatistik()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
