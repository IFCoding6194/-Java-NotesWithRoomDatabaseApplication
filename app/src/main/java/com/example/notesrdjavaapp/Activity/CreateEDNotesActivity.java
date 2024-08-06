package com.example.notesrdjavaapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notesrdjavaapp.Database.RoomDB;
import com.example.notesrdjavaapp.Models.Notes;
import com.example.notesrdjavaapp.R;
import com.example.notesrdjavaapp.databinding.ActivityCreateEdnotesBinding;

public class CreateEDNotesActivity extends AppCompatActivity {
    private ActivityCreateEdnotesBinding binding;
    private Notes notes;
    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateEdnotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = RoomDB.getInstance(this);

        // Set Status Bar Color
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.light_yellow_santhanam));

        notes = (Notes) getIntent().getSerializableExtra("oldNotes");
        if (notes != null){
            binding.titleEdt.setText(notes.getTitle());
            binding.descriptionEdt.setText(notes.getDes());
            binding.NotesTittleTv.setText("Edit & Delete Notes");
        }else{
            binding.deleteImg.setVisibility(View.GONE);
            binding.NotesTittleTv.setText("Create New Notes");
        }
        binding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotes();
            }
        });
        binding.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotes();
            }
        });
    }

    private void saveNotes(){
        String title = binding.titleEdt.getText().toString().trim();
        String des = binding.descriptionEdt.getText().toString().trim();

        if (TextUtils.isEmpty(title)){
            binding.titleEdt.setError("Enter a Title");
            binding.titleEdt.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(des)){
            binding.descriptionEdt.setError("Enter a Description");
            binding.descriptionEdt.requestFocus();
            return;
        }
        Intent intent = new Intent();
        if (notes != null){
            // Update Notes
            notes.setTitle(title);
            notes.setDes(des);
            database.mainDAO().update(notes.getID(),title,des);
            Toast.makeText(CreateEDNotesActivity.this,"Note updated Successfully!",Toast.LENGTH_SHORT).show();
        }else {
            // Create Notes
            Notes newNotes = new Notes(title, des);
            database.mainDAO().insert(newNotes);
            Toast.makeText(CreateEDNotesActivity.this,"Note Created Successfully!",Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
        finish();
    }

    private void deleteNotes(){
        if (notes != null){
            // Delete Notes
            database.mainDAO().delete(notes);
            Toast.makeText(CreateEDNotesActivity.this,"Note Deleted Successfully!",Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }
    }
}