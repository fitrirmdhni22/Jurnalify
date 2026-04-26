package com.example.jurnalify;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "journal_entries")
public class JournalEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String title;
    public String content;
    public String month;
    public String day;
    public String dateText;
    public String preview;
    public String moodAI;
    public String pesanAI;
    public String location;

    public JournalEntry() {
    }

    @Ignore
    public JournalEntry(String title, String content, String month, String day, String dateText, String preview, String moodAI, String pesanAI, String location) {
        this.title = title;
        this.content = content;
        this.month = month;
        this.day = day;
        this.dateText = dateText;
        this.preview = preview;
        this.moodAI = moodAI;
        this.pesanAI = pesanAI;
        this.location = location;
    }
}
