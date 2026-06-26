package com.example.jurnalify;

import android.content.Context;
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
import com.example.jurnalify.BuildConfig;

public class AiService {

    private final GeminiApi api;
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

        String prompt = "Kamu adalah asisten AI dari aplikasi Jurnalify. Tugasmu adalah menganalisis perasaan dari jurnal harian.\n\n" +
                "SOP: \n" +
                "1. Jika teks di bawah ini adalah curhatan atau cerita hari ini, lakukan analisis.\n" +
                "2. Jika teks di bawah ini TIDAK RELEVAN, berikan respon 'Luar Konteks'.\n\n" +
                "Teks User: \"" + teksJurnal + "\"\n\n" +
                "Format jawaban (HANYA berikan teks sesuai format ini tanpa markdown):\n" +
                "Mood: [Isi Mood + Emoji]\n" +
                "Akurasi: [Persentase keyakinanmu 0-100%]\n" +
                "Pesan: [Berikan 1-2 kalimat motivasi singkat yang hangat]";

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
                    if (callback != null) callback.onError("Gagal: Error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GeminiResponse> call, Throwable t) {
                if (callback != null) callback.onError("Koneksi gagal.");
            }
        });
    }

    private void parseResult(String fullText, MoodCallback callback) {
        try {
            String cleanText = fullText.replaceAll("\\*", "").trim();
            
            String mood = "Netral";
            String akurasi = "0%";
            String pesan = cleanText;

            Pattern moodPattern = Pattern.compile("Mood:\\s*([^\\n\\r]+)", Pattern.CASE_INSENSITIVE);
            Pattern akurasiPattern = Pattern.compile("Akurasi:\\s*([^\\n\\r]+)", Pattern.CASE_INSENSITIVE);
            Pattern pesanPattern = Pattern.compile("Pesan:\\s*([\\s\\S]+)", Pattern.CASE_INSENSITIVE);

            Matcher moodMatcher = moodPattern.matcher(cleanText);
            if (moodMatcher.find()) mood = moodMatcher.group(1).trim();

            Matcher akurasiMatcher = akurasiPattern.matcher(cleanText);
            if (akurasiMatcher.find()) akurasi = akurasiMatcher.group(1).trim();

            Matcher pesanMatcher = pesanPattern.matcher(cleanText);
            if (pesanMatcher.find()) pesan = pesanMatcher.group(1).trim();

            if (callback != null) callback.onSuccess(mood, akurasi, pesan);
        } catch (Exception e) {
            if (callback != null) callback.onSuccess("Selesai", "100%", fullText);
        }
    }

    public interface MoodCallback {
        void onSuccess(String mood, String accuracy, String message);
        void onError(String error);
    }

    public interface GeminiApi {
        @POST("v1beta/models/gemini-3.1-flash-lite:generateContent")
        Call<GeminiResponse> generateContent(@Query("key") String apiKey, @Body GeminiRequest request);
    }

    public static class GeminiRequest {
        @SerializedName("contents") public List<Content> contents;
        public GeminiRequest(String text) {
            Part part = new Part(); part.text = text;
            Content content = new Content(); content.parts = Collections.singletonList(part);
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
