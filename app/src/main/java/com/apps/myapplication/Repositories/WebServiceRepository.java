package com.apps.myapplication.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.myapplication.Models.PostModel;
import com.apps.myapplication.Room.Post;
import com.apps.myapplication.Utils.API;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServiceRepository {
    Application application;
    final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public WebServiceRepository(Application application) {
        this.application = application;
    }

    private static OkHttpClient providesOkHttpClientBuilder() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Post> webserviceResponseList = new ArrayList<>();

    public LiveData<List<Post>> providesWebService() {

        final MutableLiveData<List<Post>> data = new MutableLiveData<>();

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();


            //Defining retrofit api service
            API api = retrofit.create(API.class);
            api.getPosts().enqueue(new Callback<List<PostModel>>() {
                @Override
                public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                    Log.d("url", String.valueOf(call.request().url()));
                    Log.d("Post", "Response::::" + response.body());
                    webserviceResponseList = parseJson(response.body());
                    Log.d("webServiceResponseList", String.valueOf(webserviceResponseList.size()));
                    PostRepository postRoomDBRepository = new PostRepository(application);
                    for( Post post : webserviceResponseList) {
                        postRoomDBRepository.insert(post);
                        Log.d("Data:",post.getUserId()+"\t"+post.getBody()+"\n"+post.getTitle());
                    }
                    data.setValue(webserviceResponseList);

                }

                @Override
                public void onFailure(Call<List<PostModel>> call, Throwable t) {
//                    Log.d("url", String.valueOf(call.request().url()));
//                    Log.d("Repository", "Failed:::");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // getting data ready for saving in database
    private List<Post> parseJson(List<PostModel> response) {

        List<Post> apiResults = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {
            PostModel object = response.get(i);
            Post post = new Post();
            String title, body;
            try {
                title = object.getTitle();
                body = object.getBody();
            } catch (Exception e) {
                title = body = "Not Available";
            }
            post.setTitle(title);
            post.setBody(body);
            post.setId(object.getId());
            post.setUserId(object.getUserId());
            apiResults.add(post);
//            Log.d("Entry" + String.valueOf(i), apiResults.get(i).getName() + apiResults.get(i).getPush()
//                    + apiResults.get(i).getPull() + apiResults.get(i).getAdmin());
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;
    }
}
