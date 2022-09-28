package com.example.facebook.data;

import com.example.facebook.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostClinet {
    private static final String BASEURL = "https://jsonplaceholder.typicode.com/";
    private PostInterface postInterface;
    private static PostClinet Instance;

    public PostClinet() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postInterface = retrofit.create(PostInterface.class);
    }

    public static PostClinet getInstance() {
        if (Instance == null)
            Instance = new PostClinet();
        return Instance;
    }

    public Call<List<PostModel>> getPost() {
        return postInterface.getPosts();
    }
}
