package com.e.note.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.e.note.utils.Const;
import com.e.note.data.entities.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long insertNote(Note note);

    @Update
    int updateNote(Note note);

    @Query("select * from " + Const.TABLE_NOTE + " order by " + Const.KEY_ID + " desc")
    LiveData<List<Note>> getAllNotes();

}
