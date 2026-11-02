package com.example.projek_farrell.laporan

// Satu baris data laporan warga (seperti MessageModel di Pertemuan 7)
data class LaporanModel(
    val judul: String,
    val kategori: String,
    val pelapor: String,
    val tanggal: String,
    val status: String     // "Diproses" atau "Selesai"
)
