package com.e.note.ui.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.e.note.data.entities.Note;
import com.e.note.data.network.RetrofitClient;
import com.e.note.ui.adapter.NoteAdapter;
import com.e.note.R;
import com.e.note.ui.activity.NoteActivity;
import com.e.note.ui.adapter.RecyclerViewClickListener;
import com.e.note.utils.RecyclerItemTouchHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_NOTE = 101;

    RecyclerView mRecyclerView;
    Button btnAdd;

    List<Note> list;
    private NoteViewModel viewModel;
    NoteAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private CoordinatorLayout coordinatorLayout;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new GsonBuilder().create();

        viewModel = ViewModelProviders.of(this)
                .get(NoteViewModel.class);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        list = new ArrayList<>();

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        mRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);


        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
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
//                Log.d(TAG, "onChanged: id = " + notes.get(0).getId());
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
        switch (requestCode) {
            case (REQUEST_NOTE): {
                if (resultCode == Activity.RESULT_OK) {
                    String json = data.getStringExtra("note");
                    Note note;
                    note = gson.fromJson(json, Note.class);

                    if (note.getId() == 0)
                        viewModel.insertNote(note);
                    else
                        viewModel.udpateNote(note);

                  // saveToServer(note);

                }
                break;
            }
        }
    }

    private void saveToServer(Note note) {
        Call<Note> call = RetrofitClient
                .getInstance()
                .getApi()
                .createNote(note.title, note.description);

        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {

                Note s = response.body();
                Log.d(TAG, "onResponse: " + s.getMessage());

            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NoteAdapter.NoteHolder) {
            // get the removed item name to display it in snack bar
            final Note note = viewModel.getLiveData().getValue().get(viewHolder.getAdapterPosition());
            String name = note.getTitle();

            // backup of removed item for undo purpose
            final Note deletedItem = note;
            final int deletedIndex = viewHolder.getAdapterPosition();

            viewModel.deleteNote(note);
            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());


            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                    viewModel.insertNote(deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}