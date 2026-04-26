package com.example.jurnalify;

import android.content.Context;
import android.util.Log;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class AiService {

    private final GeminiApi api;
    // Dia akan otomatis mengambil dari BuildConfig yang baru saja kita buat
    private final String apiKey = BuildConfig.GEMINI_API_KEY;

    public AiService(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://generativelanguage.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        this.api = retrofit.create(GeminiApi.class);
    }

    public void analisisMood(String teksJurnal, MoodCallback callback) {
        if (teksJurnal == null || teksJurnal.trim().isEmpty()) {
            if (callback != null) callback.onError("Teks kosong.");
            return;
        }

        // Prompt yang lebih ekspresif, hangat, dan menggunakan emoji
        String prompt = "Kamu adalah seorang sahabat karib yang sangat pengertian dan ceria. " +
                "Tugasmu adalah menganalisis mood dari teks jurnal pengguna dan memberikan tanggapan yang sangat suportif, " +
                "penuh semangat, hangat, dan dilengkapi dengan banyak emoji lucu (seperti ✨, 💖, 🌸, 🧸, 🌈).\n\n" +
                "Jurnal: \"" + teksJurnal + "\"\n\n" +
                "Format jawaban (Berikan HANYA teks sesuai format di bawah, dilarang menggunakan markdown ** atau *):\n" +
                "Mood: [Satu kata mood + emoji yang pas]\n" +
                "Pesan: [Berikan pesan motivasi yang hangat, panjang, ramah, dan penuh semangat sebagai sahabat + tambahkan banyak emoji lucu di dalamnya]";

        GeminiRequest request = new GeminiRequest(prompt);

        api.generateContent(apiKey, request).enqueue(new Callback<GeminiResponse>() {
            @Override
            public void onResponse(Call<GeminiResponse> call, Response<GeminiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().candidates != null && !response.body().candidates.isEmpty()) {
                    try {
                        String fullText = response.body().candidates.get(0).content.parts.get(0).text;
                        parseResult(fullText, callback);
                    } catch (Exception e) {
                        if (callback != null) callback.onError("Gagal baca respon AI.");
                    }
                } else {
                    if (callback != null) callback.onError("Error " + response.code() + ": Pastikan API Key dan Model benar.");
                }
            }

            @Override
            public void onFailure(Call<GeminiResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onError("Koneksi gagal: " + t.getMessage());
                }
            }
        });
    }

    private void parseResult(String fullText, MoodCallback callback) {
        try {
            // Bersihkan teks dari karakter Markdown seperti ** atau *
            String cleanText = fullText.replaceAll("\\*", "").trim();
            
            String mood = "Netral";
            String pesan = cleanText;

            // Gunakan regex agar lebih akurat mengambil data meskipun formatnya berantakan
            Pattern moodPattern = Pattern.compile("Mood:\\s*([^\\n\\r]+)", Pattern.CASE_INSENSITIVE);
            Pattern pesanPattern = Pattern.compile("Pesan:\\s*([\\s\\S]+)", Pattern.CASE_INSENSITIVE);

            Matcher moodMatcher = moodPattern.matcher(cleanText);
            if (moodMatcher.find()) {
                mood = moodMatcher.group(1).trim();
            }

            Matcher pesanMatcher = pesanPattern.matcher(cleanText);
            if (pesanMatcher.find()) {
                pesan = pesanMatcher.group(1).trim();
            } else if (cleanText.contains("\n")) {
                // Fallback jika format pesan tidak ditemukan tapi ada baris baru
                String[] lines = cleanText.split("\n");
                for (String line : lines) {
                    if (!line.toLowerCase().contains("mood:") && !line.trim().isEmpty()) {
                        pesan = line.trim();
                        break;
                    }
                }
            }

            if (callback != null) callback.onSuccess(mood, pesan);
        } catch (Exception e) {
            if (callback != null) callback.onSuccess("Selesai", fullText.replaceAll("\\*", ""));
        }
    }

    public interface MoodCallback {
        void onSuccess(String mood, String message);
        void onError(String error);
    }

    public interface GeminiApi {
        @POST("v1beta/models/gemini-3-flash-preview:generateContent")
        Call<GeminiResponse> generateContent(
                @Query("key") String apiKey,
                @Body GeminiRequest request
        );
    }

    public static class GeminiRequest {
        @SerializedName("contents") public List<Content> contents;
        public GeminiRequest(String text) {
            Part part = new Part();
            part.text = text;
            Content content = new Content();
            content.parts = Collections.singletonList(part);
            this.contents = Collections.singletonList(content);
        }
        public static class Content { @SerializedName("parts") public List<Part> parts; }
        public static class Part { @SerializedName("text") public String text; }
    }

    public static class GeminiResponse {
        @SerializedName("candidates") public List<Candidate> candidates;
        public static class Candidate { @SerializedName("content") public Content content; }
        public static class Content { @SerializedName("parts") public List<Part> parts; }
        public static class Part { @SerializedName("text") public String text; }
    }
}
