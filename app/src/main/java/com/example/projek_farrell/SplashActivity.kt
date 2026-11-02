package com.example.projek_farrell

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projek_farrell.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    // Durasi tampil Splash Screen (2 detik)
    private val splashDuration = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi ViewBinding
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            // 1. Ambil data sesi dari SharedPreferences
            val sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
            val isLogin = sharedPreferences.getBoolean("isLogin", false)
            val savedUsername = sharedPreferences.getString("USERNAME", "farrell")

            // 2. Tentukan tujuan Intent berdasarkan status isLogin
            if (isLogin) {
                // Sudah login -> langsung ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("EXTRA_USERNAME", savedUsername)
                startActivity(intent)
            } else {
                // Belum login -> ke halaman Login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            // 3. Tutup SplashActivity agar tidak bisa kembali dengan tombol back
            finish()
        }, splashDuration)
    }
}
