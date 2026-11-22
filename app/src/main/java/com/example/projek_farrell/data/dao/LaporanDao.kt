package com.example.projek_farrell.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.projek_farrell.data.entity.LaporanEntity

// DAO: daftar operasi terhadap tabel laporan
@Dao
interface LaporanDao {
    // READ: semua laporan, terbaru di atas
    @Query("SELECT * FROM laporan ORDER BY createdAt DESC")
    suspend fun getAll(): List<LaporanEntity>

    // READ: jumlah laporan (untuk angka di Profil)
    @Query("SELECT COUNT(*) FROM laporan")
    suspend fun count(): Int

    // CREATE
    @Insert
    suspend fun insert(laporan: LaporanEntity)

    // DELETE
    @Delete
    suspend fun delete(laporan: LaporanEntity)
}
