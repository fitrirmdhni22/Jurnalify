package com.example.jurnalify;

import android.content.Context;
import java.util.Calendar;
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

    public List<JournalEntry> searchEntries(String query) {
        return journalDao.searchEntries("%" + query + "%");
    }

    public JournalEntry getEntryById(int id) {
        return journalDao.getEntryById(id);
    }

    public int calculateStreak() {
        List<JournalEntry> entries = journalDao.getAllEntries();
        if (entries.isEmpty()) return 0;

        // Sort by timestamp descending (just in case DAO order is different)
        // Actually, DAO is ORDER BY id DESC, but timestamp is better for logic.
        // We'll assume the entries are somewhat ordered.
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long todayStart = cal.getTimeInMillis();
        
        cal.add(Calendar.DAY_OF_YEAR, -1);
        long yesterdayStart = cal.getTimeInMillis();

        int streak = 0;
        long currentCheckDate = todayStart;
        boolean foundToday = false;
        boolean foundYesterday = false;

        // Check if there's an entry today or yesterday to start/continue streak
        for (JournalEntry entry : entries) {
            long entryDate = getStartOfDay(entry.timestamp);
            if (entryDate == todayStart) foundToday = true;
            if (entryDate == yesterdayStart) foundYesterday = true;
        }

        if (!foundToday && !foundYesterday) return 0;

        // Start checking backwards from the most recent day that has an entry
        if (!foundToday) {
            currentCheckDate = yesterdayStart;
        }

        while (true) {
            boolean found = false;
            for (JournalEntry entry : entries) {
                if (getStartOfDay(entry.timestamp) == currentCheckDate) {
                    found = true;
                    break;
                }
            }

            if (found) {
                streak++;
                // Move to previous day
                cal.setTimeInMillis(currentCheckDate);
                cal.add(Calendar.DAY_OF_YEAR, -1);
                currentCheckDate = cal.getTimeInMillis();
            } else {
                break;
            }
        }

        return streak;
    }

    private long getStartOfDay(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
