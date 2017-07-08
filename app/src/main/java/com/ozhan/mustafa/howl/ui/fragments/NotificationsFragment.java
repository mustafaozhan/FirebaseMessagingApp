package com.ozhan.mustafa.howl.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.ozhan.mustafa.howl.R;
import com.ozhan.mustafa.howl.core.users.get.all.GetUsersContract;
import com.ozhan.mustafa.howl.core.users.get.all.GetUsersPresenter;
import com.ozhan.mustafa.howl.models.User;
import com.ozhan.mustafa.howl.ui.adapters.NotificationRecyclerAdapter;

import java.util.List;


public class NotificationsFragment extends Fragment implements GetUsersContract.View, SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewNotifications;

    private NotificationRecyclerAdapter mNotificationsRecyclerAdapter;

    private GetUsersPresenter mGetNotificationsPresenter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_notifications, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerViewNotifications = (RecyclerView) view.findViewById(R.id.recycler_view_notifications);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mGetNotificationsPresenter = new GetUsersPresenter(this);
        getUsers();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

//        ItemClickSupport.addTo(mRecyclerViewNotifications)
//                .setOnItemClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getUsers();
    }

    private void getUsers() {
        mGetNotificationsPresenter.getAllNotifications(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
    }

//    @Override
//    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//        String value;
//
//        try {
//            value = mNotificationsRecyclerAdapter.getUser(position).nameAndSurname;
//        } catch (Exception e) {
//            value = mNotificationsRecyclerAdapter.getUser(position).email;
//        }
//        ChatActivity.startActivity(getActivity(),
//                value,
//                mNotificationsRecyclerAdapter.getUser(position).uid,
//                mNotificationsRecyclerAdapter.getUser(position).firebaseToken);
//    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(false);


            }
        });


        mNotificationsRecyclerAdapter = new NotificationRecyclerAdapter(users);

        mRecyclerViewNotifications.setAdapter(mNotificationsRecyclerAdapter);
        mNotificationsRecyclerAdapter.notifyDataSetChanged();


    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                onGetAllUsersSuccess(null);
            }
        });
        // Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
        //   mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerViewNotifications.setAdapter(null);


    }

    @Override
    public void onGetChatUsersSuccess(List<User> users) {

        mNotificationsRecyclerAdapter = new NotificationRecyclerAdapter(users);
        mRecyclerViewNotifications.setAdapter(mNotificationsRecyclerAdapter);
        mNotificationsRecyclerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onGetChatUsersFailure(String message) {


    }

    
}