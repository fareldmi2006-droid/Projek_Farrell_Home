# Projek_Farrell — NUCEOS Smart City (UTS)

Farrell Fathurrahman · NIM 2455301067 · Kotlin + XML + View Binding

**Akun demo:** `farrell` / `12345`

## Alur aplikasi
```
SplashActivity ──(belum login)──► LoginActivity ──► MainActivity ──► DetailActivity
      └──────────(sudah login)───────────────────────┘     │
                                                           ├─ HomeFragment    (Beranda)
                                                           ├─ LayananFragment (Layanan Publik)
                                                           └─ ProfilFragment  (Profil Warga)
```

## Ketentuan tugas → letaknya
| Ketentuan | File |
|---|---|
| View Binding | `app/build.gradle.kts` → `viewBinding = true`, dipakai di semua Activity & Fragment |
| Login + Toast hasil input | `LoginActivity.kt` + `activity_login.xml` |
| CardView di Login | `activity_login.xml` → `cardLogin` |
| Intent Login → Main | `LoginActivity.kt` (langkah 4) |
| Intent Main → Detail | `HomeFragment.kt` → `btnDetail` |
| DetailActivity + gambar | `DetailActivity.kt` + `activity_detail.xml` |
| AlertDialog Logout (Ya/Tidak) | `MainActivity.kt` (langkah 3) |
| 3 Fragment | `HomeFragment.kt`, `LayananFragment.kt`, `ProfilFragment.kt` |
| Bottom Navigation + efek sentuh | `activity_main.xml` (`itemRippleColor`, `nav_item_color`), `res/menu/bottom_nav_menu.xml` |
| Splash + SharedPreferences | `SplashActivity.kt`, sesi `USER_SESSION` dihapus saat Logout |

## Cara menjalankan
Buka folder `Projek_Farrell` (yang berisi `settings.gradle.kts`), tunggu Gradle Sync, lalu Run.
