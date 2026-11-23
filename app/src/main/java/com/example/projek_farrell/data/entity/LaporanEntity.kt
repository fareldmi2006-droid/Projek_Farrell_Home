package com.example.projek_farrell.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Satu objek LaporanEntity = satu baris di tabel "laporan"
@Entity(tableName = "laporan")
data class LaporanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val judul: String,
    val kategori: String,
    val pelapor: String,
    val lokasi: String = "",
    val deskripsi: String,
    val status: String = "Diproses",
    val createdAt: Long = System.currentTimeMillis()
)
