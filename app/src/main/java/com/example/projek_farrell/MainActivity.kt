package com.example.projek_farrell

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.projek_farrell.databinding.ActivityMainBinding
import com.example.projek_farrell.info.InfoKotaActivity
import com.example.projek_farrell.peta.PetaFragment
import com.example.projek_farrell.tutorial.TutorialActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Fragment pertama yang tampil adalah HomeFragment
        replaceFragment(HomeFragment())

        // 2. Bottom Navigation: ganti Fragment & judul sesuai menu yang dipilih
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    binding.tvJudulHalaman.text = "Beranda"
                    replaceFragment(HomeFragment())
                }
                R.id.nav_peta -> {
                    binding.tvJudulHalaman.text = "Peta Kota"
                    replaceFragment(PetaFragment())
                }
                R.id.nav_lapor -> {
                    // Menu Lapor membuka form Pengaduan, menu yang aktif tidak berpindah
                    startActivity(Intent(this, PengaduanActivity::class.java))
                    return@setOnItemSelectedListener false
                }
                R.id.nav_layanan -> {
                    binding.tvJudulHalaman.text = "Layanan Publik"
                    replaceFragment(LayananFragment())
                }
                R.id.nav_profil -> {
                    binding.tvJudulHalaman.text = "Profil Warga"
                    replaceFragment(ProfilFragment())
                }
            }
            true
        }

        // 3a. Ikon lonceng membuka tab Pengumuman di Berita & Forum
        binding.btnNotif.setOnClickListener {
            val intent = Intent(this, InfoKotaActivity::class.java)
            intent.putExtra(InfoKotaActivity.EXTRA_TAB, 2)
            startActivity(intent)
        }

        // 3. Ikon Info membuka Tutorial NUCEOS (Pertemuan 9)
        binding.btnInfo.setOnClickListener {
            startActivity(Intent(this, TutorialActivity::class.java))
        }

        // 4. AlertDialog konfirmasi Logout
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    // Ya -> hapus sesi login, lalu pindah ke halaman Login
                    val sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLogin", false)
                    editor.remove("USERNAME")
                    editor.apply()

                    Toast.makeText(this, "Berhasil logout", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    // Tidak -> tetap di MainActivity
                    dialog.dismiss()
                }
                .show()
        }
    }

    // Dipanggil dari HomeFragment: pindah ke menu bawah tertentu (contoh: Peta, Layanan)
    fun bukaMenu(menuId: Int) {
        binding.bottomNav.selectedItemId = menuId
    }

    // Fungsi untuk mengganti isi FrameLayout dengan Fragment
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
