# NUCEOS Smart City (Projek_Farrell_Home)

Farrell Fathurrahman · NIM 2455301067 · 3 TI A
Pemrograman Mobile, Politeknik Caltex Riau

Aplikasi Android (Kotlin + XML + View Binding) untuk warga Kota Pekanbaru. Lewat aplikasi ini warga bisa melihat info kota, membuat laporan pengaduan, dan mengakses layanan publik.

**Akun demo:** `farrell` / `12345`

---

## 1. Cara menjalankan

1. Buka Android Studio, pilih **File → Open**, lalu pilih folder project ini (yang berisi `settings.gradle.kts`).
2. Tunggu **Gradle Sync** selesai. Kalau muncul tawaran *upgrade Android Gradle Plugin*, tolak saja. Project ini sengaja memakai AGP 8.7.3 dan compileSdk 35.
3. Pilih emulator. **API 34** lebih ringan daripada API 36/37.
4. Klik **Run ▶** (bukan ikon petir *Apply Changes*), lalu login dengan akun demo.

Kalau emulator hang (jam tidak berubah, Toast tidak hilang), buka **Device Manager → ⋮ → Cold Boot Now**.

---

## 2. Alur aplikasi

```
SplashActivity ──(belum login)──► LoginActivity ──► MainActivity
      └─────────(sudah login)────────────────────────┘
                                                      │
   Bar atas: ⓘ Info ──► TutorialActivity (3 halaman geser)
             ⎋ Logout ─► AlertDialog Ya/Tidak
                                                      │
   Bottom Nav ├─ HomeFragment (Beranda)
              │    ├─ Banner sapaan (bg_header_wave)
              │    ├─ GridLayout Akses Cepat (6 menu)
              │    │    └─ Pengaduan ──► PengaduanActivity
              │    │                        └─ Kirim ──► RiwayatLaporanActivity
              │    └─ Projek Saya ──► DetailActivity
              ├─ LayananFragment (Layanan Publik)
              └─ ProfilFragment (Profil Warga)
                   └─ angka "Laporan" ──► RiwayatLaporanActivity
```

---

## 3. Struktur folder

```
app/src/main/
├── AndroidManifest.xml              daftar semua Activity
├── java/com/example/projek_farrell/
│   ├── SplashActivity.kt            cek sesi login (SharedPreferences)
│   ├── LoginActivity.kt             login + simpan sesi
│   ├── MainActivity.kt              wadah Fragment, Bottom Nav, ikon Info, Logout
│   ├── HomeFragment.kt              Beranda
│   ├── LayananFragment.kt           Layanan Publik
│   ├── ProfilFragment.kt            Profil Warga
│   ├── DetailActivity.kt            detail projek
│   ├── PengaduanActivity.kt         Form Pengaduan (P9)
│   ├── tutorial/                    Tutorial Screen (P9)
│   │   ├── TutorialActivity.kt
│   │   ├── TutorialFragmentAdapter.kt
│   │   └── Tutorial1/2/3Fragment.kt
│   └── laporan/                     Riwayat Laporan (P7)
│       ├── LaporanModel.kt          bentuk data 1 laporan
│       ├── LaporanData.kt           daftar laporan yang dipakai bersama
│       ├── LaporanAdapter.kt        Custom Adapter
│       └── RiwayatLaporanActivity.kt
└── res/
    ├── layout/                      semua tampilan XML
    │   ├── activity_pengaduan.xml, activity_tutorial.xml, activity_riwayat_laporan.xml
    │   ├── fragment_tutorial1/2/3.xml
    │   └── item_laporan.xml         tampilan 1 baris laporan
    ├── drawable/
    │   ├── bg_header_wave.xml       bentuk gelombang (vector)
    │   └── bg_rounded_container.xml kotak bersudut membulat
    └── values/colors.xml            palet warna NUCEOS
```

Pola foldernya: fitur yang punya beberapa file dikumpulkan dalam satu package (`tutorial/`, `laporan/`). File yang berdiri sendiri tetap di package utama.

---

## 4. Penjelasan fitur per materi

### Pertemuan 7: ListView + Custom Adapter → Riwayat Laporan

Ini bagian yang tampil seperti "database", yaitu daftar beberapa laporan lengkap dengan judul, kategori, tanggal, dan status.

| File | Perannya |
|---|---|
| `LaporanModel.kt` | `data class` yang menjelaskan isi satu laporan: judul, kategori, pelapor, tanggal, status. Sama seperti `MessageModel` di modul. |
| `LaporanData.kt` | `object` berisi `mutableListOf(...)` dengan 4 contoh laporan. Karena `object`, daftarnya hanya ada satu dan dipakai bersama oleh Form Pengaduan (menambah) dan Riwayat (menampilkan). |
| `item_laporan.xml` | Desain **satu baris**: ikon kategori, judul, keterangan, dan label status. |
| `LaporanAdapter.kt` | Turunan `ArrayAdapter<LaporanModel>`. Fungsi `getView()` dipanggil untuk setiap baris: buat atau pakai ulang tampilan (`convertView`), isi teks, pilih ikon sesuai kategori dengan `when`, beri warna status (emas = Diproses, teal = Selesai), lalu pasang klik yang memunculkan Snackbar. |
| `RiwayatLaporanActivity.kt` | Memasang adapter ke `ListView`. Ada filter **ChipGroup** (Semua / Diproses / Selesai) yang menyaring daftar dengan `filter { it.status == status }`, lalu memasang adapter baru. |

**Alur data saat warga mengirim laporan:**
1. Di `PengaduanActivity`, tombol **Kirim** mengecek apakah Nama dan Judul sudah diisi.
2. Kalau lengkap, dibuat `LaporanModel` baru dengan status "Diproses", lalu dimasukkan ke posisi paling atas: `LaporanData.daftarLaporan.add(0, ...)`.
3. Aplikasi pindah ke `RiwayatLaporanActivity`, dan laporan baru langsung terlihat paling atas.
4. Angka **Laporan** di Profil ikut bertambah, karena dihitung dari `LaporanData.daftarLaporan.size`.

> **Catatan jujur:** ini **belum database sungguhan**. Datanya disimpan di memori, jadi laporan baru akan hilang saat aplikasi ditutup. Supaya data tersimpan permanen, nanti perlu Room/SQLite (materi berikutnya).

### Pertemuan 9: komponen Material & tampilan

| Komponen | Letak | Penjelasan singkat |
|---|---|---|
| **TextInputLayout** | Form Pengaduan | Kolom input bergaya Material: label naik saat diketik, ikon di kiri, counter `0/50` di Judul, dan pesan merah lewat `.error` kalau kosong. |
| **ChipGroup** | Form Pengaduan & Riwayat | `singleSelection` membuat hanya satu chip yang bisa aktif. `selectionRequired` membuat chip tidak bisa dikosongkan. Listener `setOnCheckedStateChangeListener` membaca chip yang dipilih. |
| **bg_rounded_container** | Kolom No HP & Deskripsi | Shape drawable: `solid` (isi putih), `stroke` (garis tepi), `corners` (sudut membulat). |
| **bg_header_wave** | Banner Beranda | Vector drawable. `pathData` menggambar bentuk: `M` mulai, `L` garis lurus, `C` kurva gelombang, `Z` tutup. Tetap tajam di semua ukuran layar. |
| **GridLayout** | Akses Cepat Beranda | `columnCount="3"` membuat 3 kolom, dan kartu ke-4 otomatis turun ke baris kedua. Setiap kartu memakai `layout_columnWeight="1"` dan `layout_gravity="fill_horizontal"` agar lebarnya sama. |
| **ViewPager2 + FragmentStateAdapter** | Tutorial | Adapter memberi tahu jumlah halaman (`getItemCount`) dan Fragment di setiap posisi (`createFragment`). |
| **DotsIndicator** | Tutorial | Titik penanda halaman. `attachTo()` harus dipanggil **setelah** adapter dipasang. |
| **MaterialButton** | Tombol Kirim | Bisa diberi ikon (`app:icon`), sudut (`cornerRadius`), dan warna riak sentuh (`rippleColor`). |

### Materi sebelumnya yang tetap dipakai
- **View Binding**: semua Activity dan Fragment memakai `XxxBinding.inflate(...)`.
- **Intent**: perpindahan antar-Activity, contohnya `startActivity(Intent(this, TujuanActivity::class.java))`.
- **SharedPreferences** (`USER_SESSION`): menyimpan status login dan username. Sapaan "Halo, farrell!" di Beranda juga mengambil data dari sini.
- **AlertDialog**: konfirmasi Logout (Ya / Tidak).
- **Fragment + Bottom Navigation**: `replaceFragment()` mengganti isi `fragment_container`.

---

## 5. Library tambahan (build.gradle.kts)

```kotlin
implementation("androidx.gridlayout:gridlayout:1.0.0")
implementation("androidx.viewpager2:viewpager2:1.1.0")
implementation("com.tbuonomo:dotsindicator:4.3")
```

Di modul tertulis DotsIndicator **5.1.0**, tetapi versi itu membawa library Jetpack Compose yang membutuhkan compileSdk 36 dan AGP 8.9. Project ini masih memakai compileSdk 35, jadi dipakai versi **4.3** yang fungsinya sama. Di paling bawah `build.gradle.kts` juga ada blok `resolutionStrategy { force(...) }` untuk mengunci versi `androidx.core` dan `androidx.activity` agar tidak ikut naik ke versi yang terlalu baru.

---

## 6. Persiapan bedah kode (pertanyaan yang sering muncul)

**Apa beda ArrayAdapter biasa dan Custom Adapter?**
ArrayAdapter biasa hanya bisa menampilkan satu teks per baris (`simple_list_item_1`). Custom Adapter memakai layout sendiri (`item_laporan.xml`) dan mengisi beberapa view sekaligus di `getView()`.

**Untuk apa `convertView`?**
ListView memakai ulang baris yang sudah keluar dari layar. Kalau `convertView` tidak `null`, tampilannya cukup di-`bind` ulang, tidak perlu di-inflate dari awal, sehingga scroll lebih ringan.

**Kenapa `LaporanData` memakai `object`?**
Supaya hanya ada satu daftar laporan di seluruh aplikasi. Form Pengaduan menambah data ke daftar yang sama dengan yang dibaca oleh Riwayat dan Profil.

**Kenapa tutorial memakai `listOf<Fragment>(...)` dengan tipe ditulis jelas?**
Supaya Kotlin tidak salah menebak tipe isi list. Tanpa tipe yang jelas sempat muncul error *Argument type mismatch*.

**Kenapa ikon ⓘ berupa ImageButton, bukan MenuProvider seperti di modul?**
MainActivity NUCEOS tidak memakai Toolbar. Judul dan tombol Logout dibuat sendiri, jadi ikon Info dibuat dengan cara yang sama agar konsisten.

**Kenapa `error = null` dipanggil dulu sebelum validasi?**
Untuk menghapus pesan merah dari percobaan sebelumnya, supaya yang tampil hanya error yang masih berlaku.

---

## 7. Ide pengembangan berikutnya
- Simpan laporan permanen dengan **Room/SQLite**.
- Halaman detail laporan saat baris di Riwayat diklik (kirim data lewat `intent.putExtra`).
- Tutorial yang hanya tampil sekali setelah login pertama (simpan penanda di SharedPreferences).
- Kolom pencarian di tab Layanan yang benar-benar menyaring kartu layanan.
