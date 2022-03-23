package com.example.course_keeper_capstone.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        //private final TextView userItemView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.userItemView = itemView.findViewById();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final User current = mUsers.get(position);
                }
            });
        }
    }

    private List<User> mUsers;
}
