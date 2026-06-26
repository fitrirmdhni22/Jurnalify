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
> <img width="720" height="1487" alt="image" src="https://github.com/user-attachments/assets/bc832122-9ba9-49cf-9646-57628d683394" />

### 2. Location Onboarding & Geofencing
Aplikasi meminta izin lokasi untuk menentukan identitas negara pengguna. Jika diizinkan, aplikasi akan menampilkan bendera negara dan sapaan yang relevan di layar awal.
> <img width="300" alt="Izin Lokasi" src="https://github.com/user-attachments/assets/95fab7e4-2ee2-43bc-a899-b053bfdb038c" />
> <img width="300" alt="Location Flag" src="https://github.com/user-attachments/assets/20c1ef44-ae46-4a08-8937-1768ba7626e4" />

### 3. Dashboard Utama (Smart Dashboard)
Pusat navigasi aplikasi yang memiliki beberapa komponen cerdas:
- **Streak Counter (🔥):** Menampilkan jumlah hari berturut-turut pengguna menulis jurnal. Logika ini menghitung selisih waktu antar entri di database.
- **Dynamic Header:** Warna dan sapaan berubah otomatis sesuai waktu (Pagi/Siang/Sore/Malam).
- **Search Logic:** Pencarian jurnal secara instan menggunakan query `LIKE` pada database Room.
> <img width="720" height="1475" alt="image" src="https://github.com/user-attachments/assets/1d2db3ef-ff6c-4f46-a675-711a85b65862" />

### 4. Write & Edit Journal
Formulir input yang bersih. Saat pengguna menyimpan jurnal, aplikasi secara otomatis mencatat `timestamp` untuk keperluan perhitungan streak.
- **Add Journal:** Fokus pada kemudahan pengetikan.
- **Edit Journal:** Memungkinkan pembaruan data lama tanpa merusak urutan streak.
> <img width="720" height="1481" alt="image" src="https://github.com/user-attachments/assets/694d3fd5-b269-41e4-92ad-aaebec527e7a" />

> <img width="698" height="1600" alt="image" src="https://github.com/user-attachments/assets/07e5716a-77e5-41bc-8f3a-238f6b0b816f" />

### 5. Detail View & AI Insight
Layar paling krusial di mana pengguna melakukan refleksi.
- **Text Justification:** Implementasi tata letak teks rata kiri-kanan agar jurnal terlihat profesional.
- **AI Analysis:** Menghubungkan teks jurnal ke Gemini AI untuk mendapatkan analisis suasana hati dan pesan motivasi yang bersifat suportif.
> <img width="720" height="1472" alt="image" src="https://github.com/user-attachments/assets/61162145-90ce-4cb6-968a-614513036685" />

> <img width="720" height="1486" alt="image" src="https://github.com/user-attachments/assets/76eca543-c628-4d6e-a246-8e6b795b0779" />

### 6. Data Management
Fitur untuk menghapus jurnal yang tidak diinginkan dengan dialog konfirmasi untuk mencegah penghapusan yang tidak disengaja.
> <img width="720" height="1480" alt="image" src="https://github.com/user-attachments/assets/1137da78-c34e-4739-8fec-e12504b61672" />

---

## 🏗️ Instalasi & Pengembangan

1.  **Clone:** `git clone https://github.com/username/jurnalify.git`
2.  **API Key:** Dapatkan kunci API Gemini dari Google AI Studio dan masukkan ke `AiService.java`.
3.  **Sync Gradle:** Pastikan koneksi internet stabil untuk mengunduh dependensi Room dan Retrofit.
4.  **Run:** Gunakan Android Studio (rekomendasi versi Ladybug ke atas).

---

**Dibuat dengan ❤️ oleh Fitri Ramadhani untuk kemajuan kesehatan mental melalui teknologi.**
