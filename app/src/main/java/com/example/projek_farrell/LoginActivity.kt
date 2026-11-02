package com.example.projek_farrell

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projek_farrell.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            val usernameInput = binding.etUsername.text.toString().trim()
            val passwordInput = binding.etPassword.text.toString().trim()

            // 1. Validasi input tidak boleh kosong
            if (usernameInput.isEmpty()) {
                binding.etUsername.error = "Username tidak boleh kosong!"
                Toast.makeText(this, "Silakan masukkan username!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordInput.isEmpty()) {
                binding.etPassword.error = "Password tidak boleh kosong!"
                Toast.makeText(this, "Silakan masukkan password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Cek akun demo
            if (usernameInput == "farrell" && passwordInput == "12345") {

                // 3. Simpan sesi login ke SharedPreferences
                val sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("USERNAME", usernameInput)
                editor.apply()

                // Tampilkan hasil inputan menggunakan Toast
                Toast.makeText(
                    this,
                    "Login Berhasil!\nUsername: $usernameInput\nPassword: $passwordInput",
                    Toast.LENGTH_LONG
                ).show()

                // 4. Intent berpindah dari Login ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("EXTRA_USERNAME", usernameInput)
                startActivity(intent)
                finish() // Tutup LoginActivity agar tidak bisa di-back
            } else {
                // Username atau password salah
                Toast.makeText(
                    this,
                    "Login Gagal!\nUsername: $usernameInput\nPassword: $passwordInput\n(Coba: farrell / 12345)",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
