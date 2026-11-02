package com.example.projek_farrell.laporan

// Tempat menyimpan daftar laporan selama aplikasi berjalan.
// "object" artinya hanya ada satu daftar yang dipakai bersama oleh
// PengaduanActivity (menambah) dan RiwayatLaporanActivity (menampilkan).
// Catatan: data hilang saat aplikasi ditutup karena belum memakai database.
object LaporanData {

    val daftarLaporan = mutableListOf(
        LaporanModel("Lampu jalan mati", "Lampu Jalan", "farrell", "28 Okt 2026", "Diproses"),
        LaporanModel("Jalan berlubang di Jl. Sudirman", "Jalan Rusak", "farrell", "20 Okt 2026", "Selesai"),
        LaporanModel("Sampah menumpuk di pasar", "Sampah", "farrell", "12 Okt 2026", "Selesai"),
        LaporanModel("Drainase tersumbat saat hujan", "Banjir", "farrell", "5 Okt 2026", "Diproses")
    )
}
