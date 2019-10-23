package com.e.note;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
