package com.example.projek_farrell

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.projek_farrell.info.InfoKotaActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// Kumpulan info layanan publik. Dipakai bersama oleh HomeFragment dan LayananFragment
object LayananHelper {

    data class Layanan(val judul: String, val isi: String)

    val daftar = linkedMapOf(
        "administrasi" to Layanan(
            "Administrasi",
            "• Pembuatan KTP elektronik\n• Kartu Keluarga (KK)\n• Akta kelahiran\n\nBawa dokumen asli ke Mal Pelayanan Publik atau kantor Disdukcapil."
        ),
        "kesehatan" to Layanan(
            "Kesehatan",
            "• Puskesmas di setiap kecamatan\n• RSUD Arifin Achmad\n• Layanan peserta BPJS Kesehatan\n\nLokasi fasilitas kesehatan bisa dicari di menu Peta."
        ),
        "lingkungan" to Layanan(
            "Lingkungan",
            "• Pengangkutan sampah\n• Kebersihan drainase\n• Penghijauan taman kota\n\nTemukan sampah menumpuk? Laporkan lewat Buat Pengaduan."
        ),
        "transportasi" to Layanan(
            "Transportasi",
            "• Rute bus Trans Metro Pekanbaru\n• Info halte terdekat\n• Laporan jalan rusak\n\nCari halte terdekat di menu Peta."
        ),
        "utilitas" to Layanan(
            "Utilitas",
            "• Air bersih (PDAM Tirta Siak)\n• Listrik (PLN)\n• Lampu penerangan jalan\n\nLampu jalan mati? Laporkan lewat Buat Pengaduan."
        ),
        "informasi" to Layanan(
            "Informasi Kota",
            "• Pengumuman resmi pemerintah kota\n• Berita kota terbaru\n• Agenda kegiatan warga"
        )
    )

    // Menampilkan detail satu layanan dalam dialog
    fun tampilkan(context: Context, key: String) {
        val layanan = daftar[key] ?: return
        val dialog = MaterialAlertDialogBuilder(context)
            .setTitle(layanan.judul)
            .setMessage(layanan.isi)
            .setPositiveButton("Tutup", null)

        if (key == "informasi") {
            dialog.setNeutralButton("Lihat Pengumuman") { _, _ ->
                val intent = Intent(context, InfoKotaActivity::class.java)
                intent.putExtra(InfoKotaActivity.EXTRA_TAB, 2)
                context.startActivity(intent)
            }
        } else {
            dialog.setNeutralButton("Buat Pengaduan") { _, _ ->
                context.startActivity(Intent(context, PengaduanActivity::class.java))
            }
        }
        dialog.show()
    }

    // Mencari layanan dari kata kunci. Hasilnya true kalau ketemu
    fun cari(context: Context, kataKunci: String): Boolean {
        val ketemu = daftar.entries.firstOrNull { (key, layanan) ->
            key.contains(kataKunci, ignoreCase = true) ||
                layanan.judul.contains(kataKunci, ignoreCase = true) ||
                layanan.isi.contains(kataKunci, ignoreCase = true)
        }
        if (ketemu != null) tampilkan(context, ketemu.key)
        return ketemu != null
    }

    // Membuka aplikasi telepon dengan nomor yang sudah terisi (tanpa izin CALL_PHONE)
    fun telepon(context: Context, nomor: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$nomor")))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Aplikasi telepon tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    // Konfirmasi sebelum menelepon nomor darurat 112
    fun darurat(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Panggilan Darurat")
            .setMessage("Hubungi layanan darurat 112? Gunakan hanya untuk keadaan darurat.")
            .setPositiveButton("Hubungi 112") { _, _ -> telepon(context, "112") }
            .setNegativeButton("Batal", null)
            .show()
    }
}
