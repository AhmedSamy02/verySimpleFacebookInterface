package com.example.facebook.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.facebook.model.PostModelData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface PostsDao {
    @Insert
    Completable insertPost(PostModelData postModel);
    @Insert
    Completable insertPost(List<PostModelData> postModelData);
    @Query("select * from posts_table")
    Single<List<PostModelData>>getPosts();
}
