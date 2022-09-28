package com.example.facebook.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook.R;
import com.example.facebook.model.PostModel;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<PostModel> postModels = new ArrayList<>();

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext())).inflate(R.layout.post_list_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.bodyTV.setText(postModels.get(position).getBody());
        holder.titleTV.setText(postModels.get(position).getTitle());
        holder.userTV.setText(Integer.toString(postModels.get(position).getUserId()));
        holder.idTv.setText(Integer.toString(postModels.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return postModels.size();
    }

    public void setList(List<PostModel> postModel) {
        this.postModels = postModel;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, userTV, bodyTV, idTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.tvTitle);
            userTV = itemView.findViewById(R.id.tvUserID);
            bodyTV = itemView.findViewById(R.id.tvBody);
            idTv = itemView.findViewById(R.id.tvId);
        }
    }
}
