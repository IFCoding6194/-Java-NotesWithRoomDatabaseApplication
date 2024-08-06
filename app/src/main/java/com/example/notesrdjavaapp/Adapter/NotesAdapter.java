package com.example.notesrdjavaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesrdjavaapp.Models.Notes;
import com.example.notesrdjavaapp.Models.NotesClickLisener;
import com.example.notesrdjavaapp.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private Context context;
    private List<Notes> list;
    private NotesClickLisener lisener;

    public NotesAdapter(Context context, List<Notes> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(R.layout.notes_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        Notes notes = list.get(position);
        holder.title.setText(notes.getTitle());
        holder.des.setText(notes.getDes());

        holder.item_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lisener != null){
                    lisener.onClick(notes);
                }
            }
        });
    }

    public void filterList(List<Notes> filterList){
        list = filterList;
        notifyDataSetChanged();
    }

    public void setNotesClickListener(NotesClickLisener lisener){
        this.lisener = lisener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, des;
        private CardView item_cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            des = itemView.findViewById(R.id.description_tv);
            item_cv = itemView.findViewById(R.id.item_cv);
        }
    }
}

