package com.apps.myapplication.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apps.myapplication.Repositories.PostRepository;
import com.apps.myapplication.Repositories.WebServiceRepository;
import com.apps.myapplication.Room.Post;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository postRoomDBPostsitory;
    private LiveData<List<Post>> mAllPosts;
    WebServiceRepository webServicePostsitory;
    private LiveData<List<Post>> retroObservable;

    public PostViewModel(Application application) {
        super(application);
        postRoomDBPostsitory = new PostRepository(application);
        webServicePostsitory = new WebServiceRepository(application);
    }

    public LiveData<List<Post>> getPosts() {
        retroObservable = webServicePostsitory.providesWebService();
        mAllPosts = postRoomDBPostsitory.getPosts();
        return mAllPosts;
    }

    public LiveData<List<Post>> getOfflinePosts() {
        return postRoomDBPostsitory.getPosts();
    }
}
