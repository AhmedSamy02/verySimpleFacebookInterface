package com.example.facebook.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Window;

import com.example.facebook.Database.PostsDatabase;
import com.example.facebook.R;
import com.example.facebook.adapter.PostAdapter;
import com.example.facebook.model.PostModel;
import com.example.facebook.model.PostModelData;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        PostsDatabase postsDatabase = PostsDatabase.getInstance(this);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple_500));
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getPosts();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        PostAdapter postAdapter = new PostAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);
        if (isNetworkConnected()) {
            postViewModel.postMutableLiveData.observe(this, new Observer<List<PostModel>>() {
                @Override
                public void onChanged(List<PostModel> postModels) {
                    for (int i = 0; i < (Math.min(postModels.size(), 50)); i++) {
                        postsDatabase.postsDao().insertPost(new PostModelData(postModels.get(i).getTitle(),
                                        postModels.get(i).getBody(),
                                        postModels.get(i).getUserId()))
                                .subscribeOn(Schedulers.computation())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                    }

                    postAdapter.setList(postModels);
                }
            });
        } else {
            postsDatabase.postsDao().getPosts()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<PostModelData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        List<PostModel> postModels = new ArrayList<>();

                        @Override
                        public void onSuccess(List<PostModelData> postModelData) {
                            for (PostModelData p : postModelData) {
                                postModels.add(new PostModel(p.getTitle(), p.getBody(), p.getUserId(), p.getId()));
                            }
                            postAdapter.setList(postModels);
                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}