package com.example.facebook.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts_table")
public class PostModelData {
    String title;
    String body;
    int userId;
    @PrimaryKey(autoGenerate = true)
    int id;

    public PostModelData(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }
}
