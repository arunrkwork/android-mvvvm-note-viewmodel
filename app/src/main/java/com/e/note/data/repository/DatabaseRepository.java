package com.e.note.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.note.data.db.AppDatabase;
import com.e.note.data.entities.Note;
import com.e.note.data.dao.NoteDao;

import java.util.List;

public class DatabaseRepository {

    private static final String TAG = "DatabaseRepository";
    private NoteDao mNoteDao;

    public MutableLiveData<Long> lastInserId = new MutableLiveData<>();
    public MutableLiveData<Integer> updatedId = new MutableLiveData<>();
    public MutableLiveData<Boolean> progress = new MutableLiveData<>();

    private LiveData<List<Note>> mAllNotes;
    private static DatabaseRepository instance;

    private DatabaseRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);

        mNoteDao = database.getNoteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    public static DatabaseRepository getInstance(Application application) {
        if (instance == null) {
            instance = new DatabaseRepository(application);
        }
        return instance;
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    public void insertNote(Note note) {
        new InsertNoteAsyncTask(mNoteDao).execute(note);
    }

    private class InsertNoteAsyncTask extends AsyncTask<Note, String, Long> {

        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            Log.d(TAG, "InsertNoteAsyncTask: ");
            this.noteDao = noteDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            progress.postValue(true);
        }

        @Override
        protected Long doInBackground(Note... notes) {
            Log.d(TAG, "doInBackground: ");
            long id = noteDao.insertNote(notes[0]);
            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            super.onPostExecute(id);
            Log.d(TAG, "onPostExecute: ");
            progress.postValue(false);
            lastInserId.postValue(id);
        }
    }

    public void updateNote(Note note) {
        new UpdateNoteAsyncTask(mNoteDao).execute(note);
    }

    private class UpdateNoteAsyncTask extends AsyncTask<Note, String, Integer> {

        private NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            Log.d(TAG, "UpdateNoteAsyncTask: ");
            this.noteDao = noteDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            progress.postValue(true);
        }

        @Override
        protected Integer doInBackground(Note... notes) {
            Log.d(TAG, "doInBackground: ");
            int id = noteDao.updateNote(notes[0]);
            return id;
        }

        @Override
        protected void onPostExecute(Integer id) {
            super.onPostExecute(id);
            Log.d(TAG, "onPostExecute: ");
            progress.postValue(false);
            updatedId.postValue(id);
        }
    }


    public void deleteNote(Note note) {
        new DeleteNoteAsyncTask(mNoteDao).execute(note);
    }

    private class DeleteNoteAsyncTask extends AsyncTask<Note, String, Integer> {

        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            Log.d(TAG, "DeleteNoteAsyncTask: ");
            this.noteDao = noteDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            progress.postValue(true);
        }

        @Override
        protected Integer doInBackground(Note... notes) {
            Log.d(TAG, "doInBackground: ");
            int id = noteDao.deleteNote(notes[0]);
            return id;
        }

        @Override
        protected void onPostExecute(Integer id) {
            super.onPostExecute(id);
            Log.d(TAG, "onPostExecute: ");
            progress.postValue(false);
            updatedId.postValue(id);
        }
    }

    public MutableLiveData<Integer> getUpdatedId() {
        return updatedId;
    }

    public MutableLiveData<Long> getLastInserId() {
        return lastInserId;
    }

    public MutableLiveData<Boolean> getProgress() {
        return progress;
    }
}
