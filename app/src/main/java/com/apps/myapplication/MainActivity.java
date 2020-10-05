package com.apps.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.apps.myapplication.Adapters.PostAdapter;
import com.apps.myapplication.Room.Post;
import com.apps.myapplication.ViewModels.PostViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LOG";
    RecyclerView rv;
    PostViewModel repoViewModel;
    ProgressDialog progressDialog;
    PostAdapter mAdapter;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.post_recycler);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        posts = new ArrayList<>();
        repoViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        progressDialog = ProgressDialog.show(this, "Loading...", "Please wait...", true);
        repoViewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> resultModels) {
                if (resultModels!=null && progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                posts = resultModels;
                mAdapter = new PostAdapter(MainActivity.this, posts);
                rv.setAdapter(mAdapter);
            }
        });
    }
}