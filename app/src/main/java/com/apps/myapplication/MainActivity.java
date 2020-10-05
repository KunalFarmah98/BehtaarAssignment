package com.apps.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.apps.myapplication.Adapters.PostAdapter;
import com.apps.myapplication.Room.Post;
import com.apps.myapplication.ViewModels.PostViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    RecyclerView rv;
    PostViewModel postViewModel;
    ImageButton retry;
    LinearLayout no_connection;
    ProgressDialog progressDialog;
    PostAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;
    List<Post> posts;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.post_recycler);
        retry = findViewById(R.id.retry);
        no_connection = findViewById(R.id.no_connection);
        no_connection.setVisibility(View.GONE);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        posts = new ArrayList<>();
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        populateData();
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateData();
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void populateData() {
        progressDialog = ProgressDialog.show(MainActivity.this, "Loading...", "Please wait...", true);
        if (isNetworkAvailable(MainActivity.this)) {
            postViewModel.getPosts().observe(this, new Observer<List<Post>>() {
                @Override
                public void onChanged(@Nullable List<Post> resultModels) {
                    if (resultModels != null && progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    no_connection.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                    posts = resultModels;
                    mAdapter = new PostAdapter(MainActivity.this, posts);
                    rv.setAdapter(mAdapter);
                }
            });
        } else {
            postViewModel.getOfflinePosts().observe(this, new Observer<List<Post>>() {
                @Override
                public void onChanged(@Nullable List<Post> resultModels) {
                    progressDialog.dismiss();
                    if (resultModels == null || resultModels.isEmpty()) {
                        rv.setVisibility(View.GONE);
                        no_connection.setVisibility(View.VISIBLE);
                    }
                    posts = resultModels;
                    mAdapter = new PostAdapter(MainActivity.this, posts);
                    rv.setAdapter(mAdapter);
                }
            });
        }
    }
}