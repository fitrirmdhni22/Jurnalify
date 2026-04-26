package com.example.jurnalify;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JournalEditActivity extends AppCompatActivity {

    public static final String EXTRA_INDEX = "extra_index";

    private int entryId = -1;
    private JournalRepository repository;
    private JournalEntry currentEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_edit);

        // Inisialisasi Repository
        repository = new JournalRepository(this);

        ImageButton buttonBack = findViewById(R.id.buttonBackEdit);
        EditText editTitle = findViewById(R.id.editJournalTitle);
        EditText editContent = findViewById(R.id.editJournalContent);
        Button buttonApply = findViewById(R.id.buttonApplyUpdate);
        Button buttonCancel = findViewById(R.id.buttonCancelUpdate);

        // Ambil ID dari Intent
        entryId = getIntent().getIntExtra(EXTRA_INDEX, -1);
        currentEntry = repository.getEntryById(entryId);

        if (currentEntry != null) {
            if (editTitle != null) editTitle.setText(currentEntry.title);
            if (editContent != null) editContent.setText(currentEntry.content);
        } else {
            Toast.makeText(this, "Data jurnal tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (buttonBack != null) {
            buttonBack.setOnClickListener(v -> finish());
        }

        if (buttonCancel != null) {
            buttonCancel.setOnClickListener(v -> finish());
        }

        if (buttonApply != null) {
            buttonApply.setOnClickListener(v -> {
                if (editTitle == null || editContent == null) return;

                String newTitle = editTitle.getText().toString().trim();
                String newContent = editContent.getText().toString().trim();

                if (TextUtils.isEmpty(newTitle) || TextUtils.isEmpty(newContent)) {
                    Toast.makeText(JournalEditActivity.this, "Judul dan isi tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update objek entry
                currentEntry.title = newTitle;
                currentEntry.content = newContent;
                currentEntry.preview = newContent.length() > 30 ? newContent.substring(0, 30) + "..." : newContent;

                // Simpan perubahan ke Database
                repository.updateEntry(currentEntry);

                Toast.makeText(JournalEditActivity.this, "Jurnal diperbarui", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}