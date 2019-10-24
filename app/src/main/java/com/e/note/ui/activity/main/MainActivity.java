package com.e.note.ui.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.e.note.data.entities.Note;
import com.e.note.ui.adapter.NoteAdapter;
import com.e.note.R;
import com.e.note.ui.activity.NoteActivity;
import com.e.note.ui.adapter.RecyclerViewClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_NOTE = 101;

    RecyclerView mRecyclerView;
    Button btnAdd;

    List<Note> list;
    private NoteViewModel viewModel;
    NoteAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new GsonBuilder().create();

        viewModel = ViewModelProviders.of(this)
                .get(NoteViewModel.class);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        list = new ArrayList<>();

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        mRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        RecyclerViewClickListener listener =  new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Note note = viewModel.getLiveData().getValue().get(position);
                update(note);
            }
        };

        adapter = new NoteAdapter(list, listener);
        mRecyclerView.setAdapter(adapter);

        viewModel.getLiveData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.d(TAG, "onChanged: id = " + notes.get(0).getId());
                adapter.addItems(notes);
            }
        });

        viewModel.getProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean progress) {
                Log.d(TAG, "progress onChanged: " + progress);
            }
        });

        viewModel.getLastInsertId().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long id) {
                Log.d(TAG, " last id onChanged: " + id);
            }
        });

        viewModel.getUpdatedId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer id) {
                Log.d(TAG, " updated id onChanged: " + id);
            }
        });



    }

    private void update(Note note) {
        Bundle bundle = new Bundle();
        String json = gson.toJson(note);
        Log.d(TAG, "update: " + json);
        bundle.putString("note", json);

        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_NOTE);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                Intent intent = new Intent(this, NoteActivity.class);
                startActivityForResult(intent, REQUEST_NOTE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (REQUEST_NOTE) : {
                if (resultCode == Activity.RESULT_OK) {
                    String json = data.getStringExtra("note");
                    Note note;
                    note = gson.fromJson(json, Note.class);

                    if (note.getId() == 0)
                        viewModel.insertNote(note);
                    else
                        viewModel.udpateNote(note);
                }
                break;
            }
        }
    }
}
