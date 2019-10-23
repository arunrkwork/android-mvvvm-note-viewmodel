package com.e.note;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private DatabaseRepository mDatabaseRepository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(Application application) {
        super(application);
        mDatabaseRepository = new DatabaseRepository(application);
        allNotes = mDatabaseRepository.getAllNotes();
    }


    public LiveData<List<Note>> getLiveData() {
        return allNotes;
    }

    public void insertNote(Note note) {
        mDatabaseRepository.insertNote(note);
    }

}
