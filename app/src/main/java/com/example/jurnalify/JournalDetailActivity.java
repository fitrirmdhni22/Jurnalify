package com.example.jurnalify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JournalDetailActivity extends AppCompatActivity {

    public static final String EXTRA_INDEX = "extra_index";
    private int entryId = -1;
    private AiService aiService;
    private JournalRepository repository;
    private JournalEntry currentEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_detail);

        repository = new JournalRepository(this);
        aiService = new AiService(this);

        TextView textTitle = findViewById(R.id.textDetailTitle);
        TextView textDate = findViewById(R.id.textDetailDate);
        TextView textContent = findViewById(R.id.editDetailContent);
        TextView textMoodBadge = findViewById(R.id.textDetailMoodAI);
        ImageButton buttonBack = findViewById(R.id.buttonBackDetail);
        Button buttonUpdate = findViewById(R.id.buttonUpdateEntry);

        Button buttonAnalyze = findViewById(R.id.buttonCheckMood);
        TextView textAiResult = findViewById(R.id.textCheckMoodResult);

        entryId = getIntent().getIntExtra(EXTRA_INDEX, -1);
        currentEntry = repository.getEntryById(entryId);

        if (currentEntry != null) {
            if (textTitle != null) textTitle.setText(currentEntry.title);
            if (textDate != null) textDate.setText(currentEntry.dateText);
            if (textContent != null) textContent.setText(currentEntry.content);
            if (textMoodBadge != null) textMoodBadge.setText("Status AI: " + currentEntry.moodAI);

            if (currentEntry.pesanAI != null && !currentEntry.pesanAI.equals("-")) {
                if (textAiResult != null) {
                    textAiResult.setVisibility(View.VISIBLE);
                    textAiResult.setText(currentEntry.pesanAI);
                }
            }

            if (buttonAnalyze != null) {
                buttonAnalyze.setOnClickListener(v -> performMoodAnalysis(currentEntry.content, buttonAnalyze, textAiResult, currentEntry, textMoodBadge));
            }
        } else {
            Toast.makeText(this, "Data jurnal tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (buttonBack != null) {
            buttonBack.setOnClickListener(v -> finish());
        }

        if (buttonUpdate != null) {
            buttonUpdate.setOnClickListener(v -> {
                // Pastikan JournalEditActivity menggunakan EXTRA_INDEX sebagai ID Database juga
                Intent intent = new Intent(JournalDetailActivity.this, JournalEditActivity.class);
                intent.putExtra("extra_index", entryId); 
                startActivity(intent);
            });
        }
    }

    private void performMoodAnalysis(String content, Button btn, TextView resultView, JournalEntry entry, TextView badge) {
        btn.setEnabled(false);
        btn.setText("Menganalisis...");
        resultView.setVisibility(View.VISIBLE);
        resultView.setText("Sedang menganalisis mood...");

        aiService.analisisMood(content, new AiService.MoodCallback() {
            @Override
            public void onSuccess(String mood, String message) {
                runOnUiThread(() -> {
                    btn.setEnabled(true);
                    btn.setText("Analisis Ulang");

                    resultView.setText(message);
                    if (badge != null) badge.setText("Status AI: " + mood);

                    entry.moodAI = mood;
                    entry.pesanAI = message;
                    
                    // Simpan perubahan ke database
                    repository.updateEntry(entry);

                    Toast.makeText(JournalDetailActivity.this, "Analisis Berhasil", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    btn.setEnabled(true);
                    btn.setText("Coba Lagi");
                    resultView.setText("Gagal: " + error);
                    Log.e("JournalDetail", "Error AI: " + error);
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data jika kembali dari EditActivity
        currentEntry = repository.getEntryById(entryId);
        if (currentEntry != null) {
            TextView textTitle = findViewById(R.id.textDetailTitle);
            TextView textContent = findViewById(R.id.editDetailContent);
            if (textTitle != null) textTitle.setText(currentEntry.title);
            if (textContent != null) textContent.setText(currentEntry.content);
        }
    }
}
