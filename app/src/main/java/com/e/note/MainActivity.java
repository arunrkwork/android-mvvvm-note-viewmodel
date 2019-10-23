package com.e.note;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    RecyclerView mRecyclerView;
    EditText edNote;
    Button btnAdd;

    List<Note> list;
    private NoteViewModel viewModel;
    NoteAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this)
                .get(NoteViewModel.class);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        list = new ArrayList<>();

        edNote = findViewById(R.id.edNote);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        mRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new NoteAdapter(list);
        mRecyclerView.setAdapter(adapter);
        if (viewModel.getLiveData() == null)
            Log.d(TAG, "onCreate: viewmodel");
        else Log.d(TAG, "onCreate: note not null");

        viewModel.getLiveData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.addItems(notes);
            }
        });
//
//        viewModel.getLiveData().observe(this, new Observer<List<Note>>() {
//            @Override
//            public void onChanged(@Nullable List<Note> notes) {
//              //  adapter.addItems(notes);
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                addNote();
                break;
        }
    }

    private void addNote() {
        String message = edNote.getText().toString().trim();

        if (TextUtils.isEmpty(message)) {

        } else {
            Note note = new Note();
            note.setTitle(message);
            note.setDesc("Description");
            viewModel.insertNote(note);
            edNote.setText("");
        }


    }
}
