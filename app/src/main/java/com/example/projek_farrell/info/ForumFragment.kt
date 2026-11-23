package com.example.projek_farrell.info

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projek_farrell.databinding.FragmentForumBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// Tab 2: Forum Warga (Suara Warga). Postingan baru disimpan sementara selama aplikasi berjalan
class ForumFragment : Fragment() {

    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ForumAdapter

    companion object {
        // Data contoh + postingan baru (hilang saat aplikasi ditutup)
        val forumList = mutableListOf(
            ForumModel("Rina", "Drainase di Jl. Melati sering tersumbat kalau hujan deras. Ada jadwal gotong royong?", "2 jam lalu"),
            ForumModel("Budi", "Terima kasih, lampu jalan di gang kami sudah diperbaiki setelah dilaporkan.", "Kemarin"),
            ForumModel("Sari", "Usul: tambah tempat sampah terpilah di taman kota.", "3 hari lalu")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ForumAdapter(forumList)
        binding.rvForum.layoutManager = LinearLayoutManager(requireContext())
        binding.rvForum.adapter = adapter

        // Tombol Buka Forum: tulis suara warga baru
        binding.btnTulis.setOnClickListener { tulisPostingan() }
    }

    private fun tulisPostingan() {
        val input = EditText(requireContext())
        input.hint = "Tulis pendapat atau usulanmu"
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or
            InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        input.minLines = 3
        val wadah = FrameLayout(requireContext())
        val jarak = (20 * resources.displayMetrics.density).toInt()
        wadah.setPadding(jarak, jarak / 2, jarak, 0)
        wadah.addView(input)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Suara Warga")
            .setView(wadah)
            .setPositiveButton("Kirim") { _, _ ->
                val isi = input.text.toString().trim()
                if (isi.isEmpty()) {
                    Toast.makeText(requireContext(), "Tulisan masih kosong", Toast.LENGTH_SHORT).show()
                } else {
                    val prefs = requireContext().getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
                    val nama = prefs.getString("USERNAME", "Warga") ?: "Warga"
                    forumList.add(0, ForumModel(nama, isi, "Baru saja"))
                    adapter.notifyItemInserted(0)
                    binding.rvForum.scrollToPosition(0)
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
