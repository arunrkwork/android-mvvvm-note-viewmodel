package com.e.note.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.e.note.R;
import com.e.note.data.entities.Note;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NoteActivity";
    EditText edTitle, edDescription;
    Button btnAddNote;
    Note note = null;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        gson = new GsonBuilder().create();
        if (getIntent().getExtras() != null) {
            String noteJson = getIntent().getExtras().getString("note");
            note = new Note();
            Log.d(TAG, "onCreate: " + noteJson);
            note = gson.fromJson(noteJson, Note.class);
        }

        edTitle = findViewById(R.id.edTitle);
        edDescription = findViewById(R.id.edDescription);
        btnAddNote = findViewById(R.id.btnAddNote);
        btnAddNote. setOnClickListener(this);

        if (note != null) {
            edTitle.setText(note.getTitle());
            edDescription.setText(note.getDesc());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddNote) {

            String title, description;
            title = edTitle.getText().toString().trim();
            description = edDescription.getText().toString().trim();

            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                Intent resultIntent = new Intent();
                Note newNote = new Note();
                newNote.setId(note != null ? note.getId() : 0);
                newNote.setTitle(title);
                newNote.setDesc(description);
                String json = gson.toJson(newNote);
                resultIntent.putExtra("note", json);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }
    }
}
