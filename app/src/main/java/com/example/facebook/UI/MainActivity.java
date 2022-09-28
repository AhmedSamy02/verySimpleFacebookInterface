package com.example.facebook.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.facebook.R;
import com.example.facebook.adapter.PostAdapter;
import com.example.facebook.model.PostModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple_500));
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getPosts();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        PostAdapter postAdapter = new PostAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);
        postViewModel.postMutableLiveData.observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                postAdapter.setList(postModels);
            }
        });
    }
}