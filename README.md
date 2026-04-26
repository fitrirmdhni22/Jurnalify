## 👩‍💻 Informasi Mahasiswa

- **Nama**           : Fitri Ramadhani  
- **NIM**            : 312410085  
- **Kelas**          : I241A 
- **Mata Kuliah**    : Pemrograman Mobile 2 (UTS)  
- **Dosen Pengampu** : Donny Maulana, S.Kom., M.M.S.I.
- **Link ClickUp**   :
  - https://sharing.clickup.com/90181792800/g/h/2kzm1x10-598/fa7b2d413661937 (Gantt Chart)
  - https://sharing.clickup.com/90181792800/b/h/6-901812785911-2/4779a73b928d11a (Board)
- **Link Youtube (UX)**: https://youtube.com/shorts/kQpGxnvBd_E?si=_nnGA51YZ9bsjfUK

# 📓 Jurnalify - Personal Journal with AI Mood Analyzer

**Jurnalify** adalah aplikasi catatan harian modern berbasis Android yang mengintegrasikan kecerdasan buatan (**AI**) untuk membantu pengguna memahami suasana hati mereka. Dengan pendekatan *Offline-First*, Jurnalify memastikan setiap kenangan tersimpan aman di perangkat pengguna.

---

## ✨ Fitur Unggulan

- **🧠 AI Mood Analysis:** Menganalisis teks jurnal secara otomatis menggunakan **Google Gemini AI** untuk menentukan mood dan memberikan pesan motivasi yang hangat.
- **🏠 Offline Storage:** Menggunakan **Room Database** untuk memastikan data dapat diakses kapan saja tanpa ketergantungan pada koneksi internet.
- **📱 Modern & Clean UI:** Antarmuka yang intuitif dengan sentuhan Material Design 3.
- **✍️ Rapi & Nyaman Dibaca:** Implementasi *Text Justify* (rata kiri-kanan) pada isi jurnal dan respon AI agar tampilan lebih profesional.

---

## 🛠️ Tech Stack (Teknologi yang Digunakan)

Aplikasi ini dibangun menggunakan standar pengembangan Android modern:
- **Language:** Java (Android SDK)
- **Database:** Room Database (SQLite Abstraction)
- **Networking:** Retrofit 2 & OkHttp 3 (untuk integrasi AI)
- **AI Engine:** Google Gemini AI (Gemini 3 Flash Preview)
- **Architecture:** DAO (Data Access Object) Pattern
- **UI Components:** Material CardView, ConstraintLayout, ScrollView
- **Location Services:** Google Play Services Location (untuk deteksi negara pengguna).

---

## 📸 Antarmuka Pengguna (UI Overview)

Di bawah ini adalah penjelasan detail mengenai fungsionalitas dan tampilan antarmuka Jurnalify:

### 1. Splash Screen
Layar pembuka dengan logo Jurnalify yang memberikan kesan pertama yang elegan dan profesional saat aplikasi dijalankan.
> <img width="720" height="1488" alt="image" src="https://github.com/user-attachments/assets/8239fe93-c2cf-4765-b635-3139500134a6" />

### 2. Location Permission & Greeting Screen
Fitur spesial yang memberikan sentuhan personal kepada pengguna berdasarkan lokasi geografis mereka.
- Location Permission: Layar permintaan izin lokasi yang transparan, menjelaskan bahwa akses lokasi digunakan untuk menampilkan bendera negara pengguna.
- Smart Greeting: Menggunakan Google Play Services Location untuk mendeteksi lokasi secara real-time dan menampilkan bendera negara serta sapaan selamat datang yang sesuai (misal: "Negara: Indonesia 🇮🇩").
- Interactive Onboarding: Memberikan pengalaman transisi yang mulus sebelum pengguna masuk ke daftar jurnal utama.

# Izin Lokasi
> <img width="720" height="1493" alt="image" src="https://github.com/user-attachments/assets/95fab7e4-2ee2-43bc-a899-b053bfdb038c" />

> <img width="720" height="1490" alt="image" src="https://github.com/user-attachments/assets/80dd8615-2f3a-4148-9786-04d7fe699c17" />


# Bendera Negara
> <img width="720" height="1566" alt="image" src="https://github.com/user-attachments/assets/20c1ef44-ae46-4a08-8937-1768ba7626e4" />

### 3. Dashboard (Main Screen)
Pusat dari segala aktivitas pengguna.
- **RecyclerView List:** Menampilkan daftar jurnal secara dinamis dengan ringkasan informasi (Judul, Tanggal, Preview isi).
- **Mood Indicator:** Menampilkan hasil analisis AI langsung pada kartu jurnal di dashboard.
- **Empty State:** Tampilan visual yang ramah jika pengguna belum memiliki catatan.
- **Floating Action Button (FAB):** Akses cepat satu klik untuk menulis jurnal baru.
> <img width="720" height="1494" alt="image" src="https://github.com/user-attachments/assets/2ddfd8ff-1edb-4a44-9c5b-89aa4e56e4cd" />

### 4. Add & Edit Journal
Ruang kreatif bagi pengguna untuk mencurahkan isi hati.
- **Clean Input:** Form input judul dan konten yang minimalis tanpa distraksi.
- **Real-time Persistence:** Integrasi dengan Room Database memastikan setiap kata yang disimpan langsung masuk ke penyimpanan lokal.
# Add Journal
> <img width="266" height="475" alt="image" src="https://github.com/user-attachments/assets/d31b944e-63ba-4a9e-b766-169af6846633" />

# Edit Journal
> <img width="720" height="1485" alt="image" src="https://github.com/user-attachments/assets/f4571aee-c542-4ce6-9cc3-4423c78e506f" />

### 5. Detail Journal & AI Analysis
Layar utama untuk refleksi diri.
- **Justified Content:** Teks jurnal ditampilkan dengan rata kiri-kanan agar nyaman dibaca seperti buku.
- **AI Mood Analyzer Section:** 
    - Tombol **"Analisis Perasaan"** yang akan memanggil Gemini AI.
    - **Sahabat Personality:** Respon AI diprogram dengan kepribadian "Sahabat Karib" yang ceria, penuh semangat, dan menggunakan banyak emoji lucu (✨, 💖, 🌸).
    - **Detailed Motivation:** Pesan motivasi yang dipersonalisasi berdasarkan konten jurnal pengguna.
# Detail Journal
> <img width="720" height="1549" alt="image" src="https://github.com/user-attachments/assets/4b10aa24-005e-4890-a45c-1ea2644c4c96" />

# AI Analysis
> <img width="720" height="1576" alt="image" src="https://github.com/user-attachments/assets/92e42997-2a3d-4f73-84e0-ef7be3fedb05" />

### 6. Remove Journal
> <img width="720" height="1479" alt="image" src="https://github.com/user-attachments/assets/d101b97c-73f3-4d47-8bbb-f7c5e14db4a8" />

---

## 🏗️ Cara Menjalankan Project

1. **Clone Repository:**
2. **Setup API Key:**
   Dapatkan API Key di [Google AI Studio](https://aistudio.google.com/) dan masukkan ke dalam file `AiService.java`:
3. **Build & Run:**
   Buka project di Android Studio, lakukan *Gradle Sync*, dan jalankan pada Emulator atau Perangkat Fisik.

---

## 📝 Catatan Database
Aplikasi ini menggunakan **SQLite (Room Database)**. Data bersifat privat dan hanya tersimpan di perangkat lokal pengguna (*Client-side storage*), sehingga tidak memerlukan server eksternal atau Firebase untuk penyimpanan data utama.

---

**Dibuat dengan ❤️ untuk membantu refleksi diri yang lebih baik.** 
