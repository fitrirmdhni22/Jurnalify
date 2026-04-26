package com.example.jurnalify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private final List<JournalEntry> journalList;
    private final JournalRepository repository;

    public JournalAdapter(List<JournalEntry> journalList, Context context) {
        this.journalList = journalList;
        this.repository = new JournalRepository(context);
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Pastikan R.layout.item_journal_entry ada di res/layout/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal_entry, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        JournalEntry entry = journalList.get(position);

        // Format tanggal/bulan
        String month = (entry.month != null) ? entry.month : "JAN";
        String monthShort = month.length() >= 3 ? month.substring(0, 3).toUpperCase() : month.toUpperCase();

        holder.textMonth.setText(monthShort);
        holder.textDay.setText(entry.day);
        holder.textTitle.setText(entry.title);
        holder.textPreview.setText(entry.preview);

        // Mood AI
        if (entry.moodAI != null && !entry.moodAI.isEmpty()) {
            holder.textMoodAI.setVisibility(View.VISIBLE);
            holder.textMoodAI.setText("Mood: " + entry.moodAI + " ✨");
        } else {
            holder.textMoodAI.setVisibility(View.GONE);
        }

        // Hapus Data
        holder.buttonDelete.setOnClickListener(v -> {
            int currentPos = holder.getBindingAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                repository.removeEntry(journalList.get(currentPos));
                journalList.remove(currentPos);
                notifyItemRemoved(currentPos);
                notifyItemRangeChanged(currentPos, journalList.size());
            }
        });

        // Detail Data
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), JournalDetailActivity.class);
            // Menggunakan EXTRA_INDEX dari JournalDetailActivity
            intent.putExtra(JournalDetailActivity.EXTRA_INDEX, entry.id);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public static class JournalViewHolder extends RecyclerView.ViewHolder {
        TextView textMonth, textDay, textTitle, textPreview, textMoodAI;
        ImageButton buttonDelete;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            textMonth = itemView.findViewById(R.id.textMonth);
            textDay = itemView.findViewById(R.id.textDay);
            textTitle = itemView.findViewById(R.id.textEntryTitle);
            textPreview = itemView.findViewById(R.id.textEntryPreview);
            textMoodAI = itemView.findViewById(R.id.textMoodAI);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}