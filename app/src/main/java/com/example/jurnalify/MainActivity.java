package com.example.jurnalify;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private JournalRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        repository = new JournalRepository(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ExtendedFloatingActionButton fabAddNote = findViewById(R.id.fabAddNote);
        if (fabAddNote != null) {
            fabAddNote.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, AddJournalActivity.class);
                startActivity(intent);
            });
        }

        refreshJournalList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshJournalList();
    }

    private void refreshJournalList() {
        LinearLayout listContainer = findViewById(R.id.journalListContainer);
        TextView textEmpty = findViewById(R.id.textEmptyState);

        if (listContainer == null) return;

        listContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        
        List<JournalEntry> entries = repository.getEntries();

        for (int i = 0; i < entries.size(); i++) {
            JournalEntry entry = entries.get(i);
            View itemView = inflater.inflate(R.layout.item_journal_entry, listContainer, false);

            TextView textMonth = itemView.findViewById(R.id.textMonth);
            TextView textDay = itemView.findViewById(R.id.textDay);
            TextView textTitle = itemView.findViewById(R.id.textEntryTitle);
            TextView textPreview = itemView.findViewById(R.id.textEntryPreview);
            TextView textMoodAI = itemView.findViewById(R.id.textMoodAI);
            ImageButton buttonDelete = itemView.findViewById(R.id.buttonDelete);

            if (textMonth != null) {
                String m = entry.month != null ? entry.month : "JAN";
                textMonth.setText(m.length() >= 3 ? m.substring(0, 3).toUpperCase() : m.toUpperCase());
            }
            if (textDay != null) textDay.setText(entry.day);
            if (textTitle != null) textTitle.setText(entry.title);
            if (textPreview != null) textPreview.setText(entry.preview);

            if (textMoodAI != null) {
                if (entry.moodAI != null && !entry.moodAI.isEmpty()) {
                    textMoodAI.setVisibility(View.VISIBLE);
                    textMoodAI.setText("Mood: " + entry.moodAI + " ✨");
                } else {
                    textMoodAI.setVisibility(View.GONE);
                }
            }

            if (buttonDelete != null) {
                buttonDelete.setOnClickListener(v -> {
                    new AlertDialog.Builder(this)
                            .setTitle("Hapus Jurnal")
                            .setMessage("Hapus jurnal ini?")
                            .setPositiveButton("Hapus", (dialog, which) -> {
                                repository.removeEntry(entry);
                                refreshJournalList();
                            })
                            .setNegativeButton("Batal", null)
                            .show();
                });
            }

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, JournalDetailActivity.class);
                intent.putExtra(JournalDetailActivity.EXTRA_INDEX, entry.id); // Kirim ID Database
                startActivity(intent);
            });

            listContainer.addView(itemView);
        }

        if (textEmpty != null) {
            textEmpty.setVisibility(entries.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }
}
