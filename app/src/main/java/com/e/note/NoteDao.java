package com.e.note;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long insertNote(Note note);

    @Update
    int updateNote(Note note);

    @Query("select * from " + Const.TABLE_NOTE)
    LiveData<List<Note>> getAllNotes();

}
