## 👩‍💻 Informasi Mahasiswa

- **Nama**           : Fitri Ramadhani  
- **NIM**            : 312410085  
- **Kelas**          : I241A 
- **Mata Kuliah**    : Pemrograman Mobile 2 (UAS)  
- **Dosen Pengampu** : Donny Maulana, S.Kom., M.M.S.I.
- **Link ClickUp**   :
  - [Gantt Chart](https://sharing.clickup.com/90181792800/g/h/2kzm1x10-598/fa7b2d413661937)
  - [Board Task](https://sharing.clickup.com/90181792800/b/h/6-901812785911-2/4779a73b928d11a)
- **Link Youtube (Demo/UX)**: https://youtube.com/shorts/kQpGxnvBd_E?si=_nnGA51YZ9bsjfUK

# 📓 Jurnalify - Personal Journal with AI Mood Analyzer & Streak System

**Jurnalify** adalah aplikasi catatan harian modern berbasis Android yang mengintegrasikan kecerdasan buatan (**AI**) untuk membantu pengguna memahami suasana hati mereka. Dikembangkan untuk memenuhi tugas akhir (UAS), aplikasi ini mengedepankan pengalaman pengguna yang personal, aman (Offline-First), dan motivatif.

---

## ✨ Fitur Unggulan (Final Version)

- **🧠 AI Mood Analysis:** Integrasi **Google Gemini AI** untuk analisis sentimen teks secara real-time.
- **🔥 Gamified Streak System:** Algoritma penghitung konsistensi menulis harian untuk meningkatkan *user retention*.
- **🏠 Room Database Persistence:** Arsitektur database lokal yang efisien dan cepat.
- **📍 Location-Aware Greeting:** Personalisasi antarmuka berdasarkan lokasi geografis pengguna.
- **🔔 Smart Reminder:** Sistem notifikasi harian menggunakan `AlarmManager`.

---

## 🛠️ Tech Stack & Architecture

- **Bahasa:** Java (Android SDK)
- **Database:** Room Persistence Library (SQLite)
- **API:** Google Gemini AI API & Google Play Services Location
- **Library:** Retrofit 2, OkHttp 3, Material Design Components
- **Pola Desain:** Repository Pattern & Data Access Object (DAO)

---

## 📸 Antarmuka Pengguna & Penjelasan Fungsional

Berikut adalah detail antarmuka pengguna Jurnalify beserta penjelasan teknisnya:

### 1. Splash & Authentication Flow
Layar pembuka dengan branding Jurnalify. Alur ini mencakup pengecekan izin lokasi untuk fitur personalisasi sapaan.
> <img width="300" alt="Splash Screen" src="https://github.com/user-attachments/assets/8239fe93-c2cf-4765-b635-3139500134a6" />

### 2. Location Onboarding & Geofencing
Aplikasi meminta izin lokasi untuk menentukan identitas negara pengguna. Jika diizinkan, aplikasi akan menampilkan bendera negara dan sapaan yang relevan di layar awal.
> <img width="300" alt="Izin Lokasi" src="https://github.com/user-attachments/assets/95fab7e4-2ee2-43bc-a899-b053bfdb038c" />
> <img width="300" alt="Location Flag" src="https://github.com/user-attachments/assets/20c1ef44-ae46-4a08-8937-1768ba7626e4" />

### 3. Dashboard Utama (Smart Dashboard)
Pusat navigasi aplikasi yang memiliki beberapa komponen cerdas:
- **Streak Counter (🔥):** Menampilkan jumlah hari berturut-turut pengguna menulis jurnal. Logika ini menghitung selisih waktu antar entri di database.
- **Dynamic Header:** Warna dan sapaan berubah otomatis sesuai waktu (Pagi/Siang/Sore/Malam).
- **Search Logic:** Pencarian jurnal secara instan menggunakan query `LIKE` pada database Room.
> <img width="300" alt="Dashboard" src="https://github.com/user-attachments/assets/2ddfd8ff-1edb-4a44-9c5b-89aa4e56e4cd" />

### 4. Write & Edit Journal
Formulir input yang bersih. Saat pengguna menyimpan jurnal, aplikasi secara otomatis mencatat `timestamp` untuk keperluan perhitungan streak.
- **Add Journal:** Fokus pada kemudahan pengetikan.
- **Edit Journal:** Memungkinkan pembaruan data lama tanpa merusak urutan streak.
> <img width="300" alt="Add Journal" src="https://github.com/user-attachments/assets/6c7a2d8e-1b0e-49e5-a348-0d3342bfc2c1" />
> <img width="300" alt="Edit Journal" src="https://github.com/user-attachments/assets/f4571aee-c542-4ce6-9cc3-4423c78e506f" />

### 5. Detail View & AI Insight
Layar paling krusial di mana pengguna melakukan refleksi.
- **Text Justification:** Implementasi tata letak teks rata kiri-kanan agar jurnal terlihat profesional.
- **AI Analysis:** Menghubungkan teks jurnal ke Gemini AI untuk mendapatkan analisis suasana hati dan pesan motivasi yang bersifat suportif.
> <img width="300" alt="Detail Journal" src="https://github.com/user-attachments/assets/4b10aa24-005e-4890-a45c-1ea2644c4c96" />
> <img width="300" alt="AI Analysis" src="https://github.com/user-attachments/assets/fef78a18-9db8-4eac-98d1-16955d06e1d5" />

### 6. Data Management
Fitur untuk menghapus jurnal yang tidak diinginkan dengan dialog konfirmasi untuk mencegah penghapusan yang tidak disengaja.
> <img width="300" alt="Remove Journal" src="https://github.com/user-attachments/assets/d101b97c-73f3-4d47-8bbb-f7c5e14db4a8" />

---

## 🏗️ Instalasi & Pengembangan

1.  **Clone:** `git clone https://github.com/username/jurnalify.git`
2.  **API Key:** Dapatkan kunci API Gemini dari Google AI Studio dan masukkan ke `AiService.java`.
3.  **Sync Gradle:** Pastikan koneksi internet stabil untuk mengunduh dependensi Room dan Retrofit.
4.  **Run:** Gunakan Android Studio (rekomendasi versi Ladybug ke atas).

---

**Dibuat dengan ❤️ oleh Fitri Ramadhani untuk kemajuan kesehatan mental melalui teknologi.**
