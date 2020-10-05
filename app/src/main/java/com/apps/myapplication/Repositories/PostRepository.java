package com.apps.myapplication.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.apps.myapplication.Room.PostDao;
import com.apps.myapplication.Room.Post;
import com.apps.myapplication.Room.PostRoomDataBase;

import java.util.List;

public class PostRepository {
    private PostDao postDao;
    private LiveData<List<Post>> posts;

    public PostRepository(Application application) {
        PostRoomDataBase db = PostRoomDataBase.getInstance(application);
        postDao = db.PostDao();
        posts = postDao.getAllPosts();
    }

    public void insert(Post post) {
        new InsertPostAsyncTask(postDao).execute(post);
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public static class InsertPostAsyncTask extends AsyncTask<Post, Void, Void> {
        private PostDao postDao;

        private InsertPostAsyncTask(PostDao postDao) {
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            postDao.insert(posts[0]);
            return null;
        }
    }

    public void insertPosts(List<Post> postlist) {
        new insertAsyncTask(postDao).execute(postlist);
    }

    private static class insertAsyncTask extends AsyncTask<List<Post>, Void, Void> {

        private PostDao mAsyncTaskDao;

        insertAsyncTask(PostDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Post>... params) {
            mAsyncTaskDao.insertPosts(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        new deleteAllAsyncTask(postDao).execute();

    }

    private static class deleteAllAsyncTask extends AsyncTask<List<Post>, Void, Void> {

        private PostDao mAsyncTaskDao;

        deleteAllAsyncTask(PostDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Post>... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
