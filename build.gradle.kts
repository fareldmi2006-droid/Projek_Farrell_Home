plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // Pertemuan 12: KSP untuk Room (versi harus cocok dengan Kotlin 2.0.21)
    id("com.google.devtools.ksp") version "2.0.21-1.0.28" apply false
}
