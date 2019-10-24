package com.e.note.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.e.note.data.dao.NoteDao;
import com.e.note.data.entities.Note;

@Database(entities = { Note.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "notes.db";
    private static volatile AppDatabase mInstance;

    public abstract NoteDao getNoteDao();

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                mInstance = create(context);
            }
        }
        return mInstance;
    }

    private static AppDatabase create(Context context) {

        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME
        ).build();
    }
}
