package com.apps.myapplication.Utils;
import com.apps.myapplication.Models.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("posts")
    Call<List<PostModel>> getPosts();
}
