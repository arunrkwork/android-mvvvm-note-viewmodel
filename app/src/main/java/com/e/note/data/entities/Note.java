package com.e.note.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.e.note.utils.Const;

@Entity(
        tableName = Const.TABLE_NOTE
)
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo( name = Const.KEY_ID, typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo( name = Const.KEY_TITLE, typeAffinity = ColumnInfo.TEXT)
    public String title;
    @ColumnInfo( name = Const.KEY_DESC, typeAffinity = ColumnInfo.TEXT)
    public String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }
}
