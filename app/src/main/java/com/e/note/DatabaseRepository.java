package com.e.note;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DatabaseRepository {

    private static final String TAG = "DatabaseRepository";
    private NoteDao mNoteDao;

    private LiveData<List<Note>> mAllNotes;

    public DatabaseRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);

        mNoteDao = database.getNoteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    public void insertNote(Note note) {
        new insertNoteAsyncTask(mNoteDao).execute(note);
    }

    private static class insertNoteAsyncTask extends AsyncTask<Note, String, String> {

        private NoteDao noteDao;

        public insertNoteAsyncTask(NoteDao noteDao) {
            Log.d(TAG, "insertNoteAsyncTask: ");
            this.noteDao = noteDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
        }

        @Override
        protected String doInBackground(Note... notes) {
            Log.d(TAG, "doInBackground: ");
            long id = noteDao.insertNote(notes[0]);
            Log.d(TAG, "doInBackground: " + id);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: ");
        }
    }

}
