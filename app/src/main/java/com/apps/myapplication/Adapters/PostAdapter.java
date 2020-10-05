package com.apps.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myapplication.R;
import com.apps.myapplication.Room.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    Context mContext;
    List<Post> data;

    public PostAdapter(Context mContext, List<Post> list) {
        this.mContext = mContext;
        data = list;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PostViewHolder(inflater.inflate(R.layout.post_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        final Post post = data.get(position);
        holder.user_id.setText(String.valueOf(post.getUserId()));
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public Post getItem(int position) {
        return data.get(position);
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title, user_id, body;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id = itemView.findViewById(R.id.user_Id);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
        }
    }
}
