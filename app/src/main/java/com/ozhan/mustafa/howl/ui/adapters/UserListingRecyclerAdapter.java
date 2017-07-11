package com.ozhan.mustafa.howl.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ozhan.mustafa.howl.R;
import com.ozhan.mustafa.howl.models.User;
import com.ozhan.mustafa.howl.ui.activities.ProfileActivity;

import java.util.List;

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:06 PM.
 */


public class UserListingRecyclerAdapter extends RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder> {
    private List<User> mUsers;


    public UserListingRecyclerAdapter(List<User> users) {
        this.mUsers = users;
    }

    public void add(User user) {
        mUsers.add(user);
        notifyItemInserted(mUsers.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_user_listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final User user = mUsers.get(position);

        String alphabet;

        //  holder.txtLastMessage.setText(user.email);
        try {
            holder.txtUsername.setText(user.getNameAndSurname());
            alphabet = user.getNameAndSurname().substring(0, 1);
        } catch (Exception e) {
            holder.txtUsername.setText(user.getEmail());
            alphabet = user.getEmail().substring(0, 1);
        }

        //  holder.txtUserAlphabet.setText(alphabet);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(String.valueOf(R.string.storage_link));
        storageRef.child("profilePictures/" + user.getEmail() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                try {
                    Glide
                            .with(holder.imgViewUser.getContext())
                            .load("" + uri.toString())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                            .into(holder.imgViewUser);
                } catch (Exception e) {

                }
            }
        });
        holder.imgViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imgViewUser.getContext(), ProfileActivity.class);
                Bundle b = new Bundle();
                b.putString("key", user.getEmail()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                holder.imgViewUser.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        }
        return 0;
    }

    public User getUser(int position) {
        return mUsers.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtUsername, txtLastMessage;
        private ImageView imgViewUser;

        ViewHolder(View itemView) {
            super(itemView);
            imgViewUser = (ImageView) itemView.findViewById(R.id.user_photo);
            txtUsername = (TextView) itemView.findViewById(R.id.text_view_username);
            //txtLastMessage=(TextView)itemView.findViewById(R.id.text_view_last_message);
        }
    }


}
