package com.example.jurnalify;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddJournalActivity extends AppCompatActivity {

    private EditText inputTitle;
    private EditText inputContent;
    private JournalRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        repository = new JournalRepository(this);

        inputTitle = findViewById(R.id.inputJournalTitle);
        inputContent = findViewById(R.id.inputJournalContent);
        Button buttonSave = findViewById(R.id.buttonSaveEntry);
        ImageButton buttonBack = findViewById(R.id.buttonBack);

        if (buttonSave != null) {
            buttonSave.setOnClickListener(v -> saveJournalLocally());
        }

        if (buttonBack != null) {
            buttonBack.setOnClickListener(v -> finish());
        }
    }

    private void saveJournalLocally() {
        if (inputTitle == null || inputContent == null) return;

        String title = inputTitle.getText().toString().trim();
        String content = inputContent.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "Judul dan isi tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        Date now = new Date();
        String month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(now);
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(now);
        String dateText = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(now);
        String preview = content.length() > 30 ? content.substring(0, 30) + "..." : content;

        // Kembali menggunakan pemanggilan simpel
        JournalEntry entry = new JournalEntry(
                title, 
                content, 
                month, 
                day, 
                dateText, 
                preview, 
                "Belum dicek", 
                "-", 
                "Lokasi tidak aktif"
        );

        repository.addEntry(entry);

        Toast.makeText(this, "Berhasil disimpan!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
