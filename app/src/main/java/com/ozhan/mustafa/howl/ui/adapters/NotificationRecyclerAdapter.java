package com.ozhan.mustafa.howl.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ozhan.mustafa.howl.R;
import com.ozhan.mustafa.howl.models.User;
import com.ozhan.mustafa.howl.ui.activities.ProfileActivity;

import java.util.List;

/**
 * Created by Mustafa Özhan on 5/2/17 at 9:01 PM.
 */

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder> {
    private List<User> mUsers;
    private Context context;

    public NotificationRecyclerAdapter(List<User> users) {
        this.mUsers = users;
    }

    public void add(User user) {
        mUsers.add(user);
        notifyItemInserted(mUsers.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_friend_request, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final User user = mUsers.get(position);
        holder.mBttnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitNotificationAnswer("accepted", mUsers.get(position).uid);
                mUsers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUsers.size());


            }
        });
        holder.mBttnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitNotificationAnswer("ignored", mUsers.get(position).uid);
                mUsers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUsers.size());
            }
        });
        holder.mBttnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitNotificationAnswer("blocked", mUsers.get(position).uid);
                mUsers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUsers.size());

            }
        });


        String alphabet;

        //  holder.txtLastMessage.setText(user.email);
        try {
            holder.txtUsername.setText(user.nameAndSurname);
            alphabet = user.nameAndSurname.substring(0, 1);
        } catch (Exception e) {
            holder.txtUsername.setText(user.email);
            alphabet = user.email.substring(0, 1);
        }

        //  holder.txtUserAlphabet.setText(alphabet);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(context.getResources().getString(R.string.storage_link));
        storageRef.child("profilePictures/" + user.email + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                try {
                    Glide
                            .with(holder.imgViewUser.getContext())
                            .load("" + uri.toString())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                            .into(holder.imgViewUser);
                } catch (Exception ignored) {

                }
            }
        });
        holder.imgViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imgViewUser.getContext(), ProfileActivity.class);
                Bundle b = new Bundle();
                b.putString("key", user.email); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                holder.imgViewUser.getContext().startActivity(intent);

            }
        });


    }

    private void SubmitNotificationAnswer(String answer, String uid) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.child("friendRequest").child(uid).setValue(answer);
        DatabaseReference otherOne = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("friends");
        if (answer.equals("accepted")) {

            mDatabase.child("friends").child(uid).setValue(true);
            otherOne.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);


        } else {
            mDatabase.child("friends").child(uid).setValue(false);
            otherOne.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(false);
        }


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

        private TextView txtUsername, txtRequest;
        private ImageView imgViewUser;
        private Button mBttnAccept, mBttnIgnore, mBttnBlock;

        ViewHolder(View itemView) {
            super(itemView);
            imgViewUser = (ImageView) itemView.findViewById(R.id.user_photo);
            txtUsername = (TextView) itemView.findViewById(R.id.text_view_username);
            mBttnAccept = (Button) itemView.findViewById(R.id.button_accept);
            mBttnIgnore = (Button) itemView.findViewById(R.id.button_ignore);
            mBttnBlock = (Button) itemView.findViewById(R.id.button_block);
            txtRequest = (TextView) itemView.findViewById(R.id.text_view_friend_request);
        }
    }


}
