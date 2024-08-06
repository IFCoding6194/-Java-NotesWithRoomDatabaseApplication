package com.example.notesrdjavaapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notesrdjavaapp.Adapter.NotesAdapter;
import com.example.notesrdjavaapp.Database.RoomDB;
import com.example.notesrdjavaapp.Models.Notes;
import com.example.notesrdjavaapp.Models.NotesClickLisener;
import com.example.notesrdjavaapp.R;
import com.example.notesrdjavaapp.databinding.ActivityNotesHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class NotesHomeActivity extends AppCompatActivity implements NotesClickLisener {
    private ActivityNotesHomeBinding binding;
    private NotesAdapter notesAdapter;
    private RoomDB database;
    private List<Notes> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = RoomDB.getInstance(this);
        notesList.addAll(database.mainDAO().getAll());
        setNotesRC();

        // Set Status Bar Color
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.light_yellow_santhanam));

        binding.addNotesImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesHomeActivity.this, CreateEDNotesActivity.class);
                startActivity(intent);
            }
        });

        binding.searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filter(String s){
        List<Notes> filterList = new ArrayList<>();
        for (Notes singleNotes : notesList){
            if (singleNotes.getTitle().toLowerCase().contains(s.toLowerCase()) ||
            singleNotes.getDes().toLowerCase().contains(s.toLowerCase())){
                filterList.add(singleNotes);
            }
        }
        notesAdapter.filterList(filterList);
    }

    private  void setNotesRC(){
        binding.notesRcv.setHasFixedSize(true);
        binding.notesRcv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesAdapter = new NotesAdapter(this, notesList);
        notesAdapter.setNotesClickListener(this);
        binding.notesRcv.setAdapter(notesAdapter);
    }

    @Override
    public void onClick(Notes notes) {
        Intent intent = new Intent(NotesHomeActivity.this, CreateEDNotesActivity.class);
        intent.putExtra("oldNotes",notes);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        notesList.clear();
        notesList.addAll(database.mainDAO().getAll());
        setNotesRC();
        notesAdapter.notifyDataSetChanged();
    }
}