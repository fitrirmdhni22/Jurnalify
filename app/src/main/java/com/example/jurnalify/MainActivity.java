package com.example.jurnalify;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private JournalRepository repository;
    private String currentSearchQuery = "";
    private static final int NOTIFICATION_PERMISSION_CODE = 101;

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

        // Set Dynamic Greeting & Notifikasi
        updateGreeting();
        checkNotificationPermission();
        scheduleReminder();
        updateStreak();

        EditText editSearch = findViewById(R.id.editSearch);
        if (editSearch != null) {
            editSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    currentSearchQuery = s.toString();
                    refreshJournalList();
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        ExtendedFloatingActionButton fabAddNote = findViewById(R.id.fabAddNote);
        if (fabAddNote != null) {
            fabAddNote.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, AddJournalActivity.class);
                startActivity(intent);
            });
        }

        refreshJournalList();
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) 
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, 
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
            }
        }
    }

    private void scheduleReminder() {
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        
        // Atur waktu pengingat: Jam 20:00 (8 malam)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Jika jam 8 malam sudah lewat, setel untuk besok
        if (Calendar.getInstance().getTimeInMillis() > calendar.getTimeInMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }

    private void updateGreeting() {
        TextView textWelcome = findViewById(R.id.textWelcome);
        if (textWelcome == null) return;

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String greeting;

        if (hour >= 5 && hour < 11) {
            greeting = "Selamat pagi, Bestie! ☀️";
        } else if (hour >= 11 && hour < 15) {
            greeting = "Selamat siang, Bestie! ☁️";
        } else if (hour >= 15 && hour < 18) {
            greeting = "Selamat sore, Bestie! ⛅";
        } else {
            greeting = "Selamat malam, Bestie! 🌙";
        }

        textWelcome.setText(greeting);
    }

    private void updateStreak() {
        TextView textStreak = findViewById(R.id.textStreak);
        if (textStreak != null) {
            int streak = repository.calculateStreak();
            textStreak.setText("🔥 " + streak + " Hari");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateGreeting();
        updateStreak();
        refreshJournalList();
    }

    private void refreshJournalList() {
        LinearLayout listContainer = findViewById(R.id.journalListContainer);
        TextView textEmpty = findViewById(R.id.textEmptyState);

        if (listContainer == null) return;

        listContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        
        List<JournalEntry> entries;
        if (currentSearchQuery.isEmpty()) {
            entries = repository.getEntries();
        } else {
            entries = repository.searchEntries(currentSearchQuery);
        }

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
                    textMoodAI.setText("Mood: " + entry.moodAI + " ☁️");
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
                                updateStreak();
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
            if (!currentSearchQuery.isEmpty() && entries.isEmpty()) {
                textEmpty.setText("Jurnal tidak ditemukan.");
            } else {
                textEmpty.setText("Belum ada catatan hari ini.");
            }
        }
    }
}
