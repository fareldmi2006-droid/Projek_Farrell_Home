package com.example.projek_farrell.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projek_farrell.data.dao.LaporanDao
import com.example.projek_farrell.data.entity.LaporanEntity

@Database(
    entities = [LaporanEntity::class],
    version = 2,   // v2: tambah kolom lokasi
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun laporanDao(): LaporanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Singleton: database hanya dibuat sekali
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nuceos_database"
                )
                    // Struktur tabel berubah (v1 -> v2): data lama dihapus lalu tabel dibuat ulang
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}
