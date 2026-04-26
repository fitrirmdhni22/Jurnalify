package com.example.jurnalify;

import android.content.Context;
import java.util.List;

public class JournalRepository {

    private final JournalDao journalDao;

    public JournalRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        journalDao = db.journalDao();
    }

    public void addEntry(JournalEntry entry) {
        journalDao.insert(entry);
    }

    public void updateEntry(JournalEntry entry) {
        journalDao.update(entry);
    }

    public void removeEntry(JournalEntry entry) {
        journalDao.delete(entry);
    }

    public List<JournalEntry> getEntries() {
        return journalDao.getAllEntries();
    }

    public JournalEntry getEntryById(int id) {
        return journalDao.getEntryById(id);
    }
}
