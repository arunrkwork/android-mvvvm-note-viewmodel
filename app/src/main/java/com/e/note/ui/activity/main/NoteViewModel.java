package com.e.note.ui.activity.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.note.data.repository.DatabaseRepository;
import com.e.note.data.entities.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private DatabaseRepository mDatabaseRepository;
    private LiveData<List<Note>> allNotes;
    public MutableLiveData<Long> lastInsertId;
    public MutableLiveData<Integer> updatedId;
    public MutableLiveData<Boolean> progress;

    public NoteViewModel(Application application) {
        super(application);
        mDatabaseRepository = DatabaseRepository.getInstance(application);
        allNotes = mDatabaseRepository.getAllNotes();
        progress = mDatabaseRepository.getProgress();
        lastInsertId = mDatabaseRepository.getLastInserId();
        updatedId = mDatabaseRepository.getUpdatedId();
    }

    public LiveData<List<Note>> getLiveData() {
        return allNotes;
    }

    public void insertNote(Note note) {
        mDatabaseRepository.insertNote(note);
    }

    public void udpateNote(Note note) {
        mDatabaseRepository.updateNote(note);
    }


    public MutableLiveData<Boolean> getProgress() {
        return progress;
    }

    public MutableLiveData<Long> getLastInsertId() {
        return lastInsertId;
    }

    public MutableLiveData<Integer> getUpdatedId() {
        return updatedId;
    }
}
