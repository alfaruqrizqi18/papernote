package com.alpha.papernote.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alpha.papernote.R;
import com.alpha.papernote.models.NotesModel;

import java.util.List;

public class MainActivityNotesAdapter extends RecyclerView.Adapter<MainActivityNotesAdapter.ViewHolder> {

    Context context;
    private List<NotesModel> notesModels;

    public MainActivityNotesAdapter(Context context, List<NotesModel> notesModels) {
        this.notesModels = notesModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MainActivityNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_papernote, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityNotesAdapter.ViewHolder viewHolder, int i) {
        final NotesModel notes = notesModels.get(i);
        if (notes.getTitle().isEmpty()) {
            viewHolder.title.setVisibility(View.GONE);
        } else {
            viewHolder.title.setVisibility(View.VISIBLE);
            viewHolder.title.setText(notes.getTitle());
        }

        if (notes.getContent().isEmpty()) {
            viewHolder.papernote_content.setVisibility(View.GONE);
        } else {
            viewHolder.papernote_content.setVisibility(View.VISIBLE);
            if (notes.getContent().length() > 50) {
                viewHolder.papernote_content.setText(notes.getContent().toString().substring(0, 50));
            } else {
                viewHolder.papernote_content.setText(notes.getContent());
            }
        }
        viewHolder.cardView.setCardBackgroundColor(Color.parseColor(notes.getColor()));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Title : " + notes.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title, papernote_content;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.title);
            papernote_content = itemView.findViewById(R.id.papernote_content);
        }
    }
}
