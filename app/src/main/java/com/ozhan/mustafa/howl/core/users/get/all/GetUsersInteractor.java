package com.ozhan.mustafa.howl.core.users.get.all;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ozhan.mustafa.howl.events.PushNotificationEvent;
import com.ozhan.mustafa.howl.models.User;
import com.ozhan.mustafa.howl.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:00 PM.
 */

public class GetUsersInteractor implements GetUsersContract.Interactor {
    private static final String TAG = "GetUsersInteractor";

    private GetUsersContract.OnGetAllUsersListener mOnGetAllUsersListener;

    public GetUsersInteractor(GetUsersContract.OnGetAllUsersListener onGetAllUsersListener) {
        this.mOnGetAllUsersListener = onGetAllUsersListener;
    }



    @Override
    public void getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                List<User> users = new ArrayList<>();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    User user = dataSnapshotChild.getValue(User.class);
                    if (!TextUtils.equals(user.uid, FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        users.add(user);
                    }
                }
                mOnGetAllUsersListener.onGetAllUsersSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage());
            }
        });
    }

    @Override
    public void getChatUsersFromFirebase() {

    }

    @Override
    public void getConversationsFromFirebase() {

        final List<User> users=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friends").orderByValue().equalTo(true).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {




                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(dataSnapshot.getKey().toString());

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //   User user =new User();
                        users.add(dataSnapshot.getValue(User.class));
                        mOnGetAllUsersListener.onGetAllUsersSuccess(users);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        //q mOnGetAllUsersListener.onGetAllUsersFailure("iceri");


                    }
                });


                // mOnGetAllUsersListener.onGetAllUsersSuccess(users);




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        mOnGetAllUsersListener.onGetAllUsersFailure("asdasd");




    }

    @Override
    public void getNotificationsFromFirebase(final String uid) {
        final List<User> users=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("friendRequest").orderByValue().equalTo("pending").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {




                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(dataSnapshot.getKey().toString());

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    //   User user =new User();

                        users.add(dataSnapshot.getValue(User.class));
                        mOnGetAllUsersListener.onGetAllUsersSuccess(users);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                       //q mOnGetAllUsersListener.onGetAllUsersFailure("iceri");


                    }
                });


               // mOnGetAllUsersListener.onGetAllUsersSuccess(users);




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        mOnGetAllUsersListener.onGetAllUsersFailure("asdasd");


    }

}