package com.ozhan.mustafa.howl.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ozhan.mustafa.howl.R;
import com.ozhan.mustafa.howl.core.users.get.all.GetUsersContract;
import com.ozhan.mustafa.howl.core.users.get.all.GetUsersPresenter;
import com.ozhan.mustafa.howl.models.User;
import com.ozhan.mustafa.howl.ui.adapters.UserListingRecyclerAdapter;
import com.ozhan.mustafa.howl.utils.ItemClickSupport;

import java.util.List;

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:07 PM.
 */

public class UsersFragment extends Fragment implements GetUsersContract.View, SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewAllUserListing;

    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;

    private GetUsersPresenter mGetUsersPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_users, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerViewAllUserListing = (RecyclerView) view.findViewById(R.id.recycler_view_all_user_listing);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mGetUsersPresenter = new GetUsersPresenter(this);
        getUsers();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        ItemClickSupport.Companion.addTo(mRecyclerViewAllUserListing)
                .setOnItemClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getUsers();
    }

    private void getUsers() {
        mGetUsersPresenter.getAllUsers();
    }

    @Override
    public void onItemClicked(@NonNull RecyclerView recyclerView, final int position, @NonNull View v) {
        String value;

        try {
            value = mUserListingRecyclerAdapter.getUser(position).getNameAndSurname();
        } catch (Exception e) {
            value = mUserListingRecyclerAdapter.getUser(position).getEmail();
        }
//        ChatActivity.startActivity(getActivity(),
//                value,
//                mUserListingRecyclerAdapter.getUser(position).uid,
//                mUserListingRecyclerAdapter.getUser(position).firebaseToken);

        new AlertDialog.Builder(getActivity())
                .setTitle("Friend Request")
                .setMessage("Do you want to add ' " + value + " '")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendFriendRequest(position);

                    }


                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(R.drawable.add_friend)
                .show();


    }

    private void sendFriendRequest(int position) {


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
        database.orderByChild("email").equalTo(mUserListingRecyclerAdapter.getUser(position).getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               //arkadaslik istegi arkadas olunan adama yollanmasin todo  DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friends");

                dataSnapshot.getRef().child("friendRequest").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("pending");
                Toast.makeText(getActivity(), "Your Request Sended !", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mUserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
        mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
        mUserListingRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetChatUsersSuccess(List<User> users) {

    }

    @Override
    public void onGetChatUsersFailure(String message) {

    }








}
