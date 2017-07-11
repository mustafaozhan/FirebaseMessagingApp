package com.ozhan.mustafa.howl.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ozhan.mustafa.howl.R;
import com.ozhan.mustafa.howl.core.users.get.all.GetUsersContract;
import com.ozhan.mustafa.howl.core.users.get.all.GetUsersPresenter;
import com.ozhan.mustafa.howl.models.User;
import com.ozhan.mustafa.howl.ui.activities.ChatActivity;
import com.ozhan.mustafa.howl.ui.adapters.ConversationListingRecyclerAdapter;
import com.ozhan.mustafa.howl.ui.adapters.NotificationRecyclerAdapter;
import com.ozhan.mustafa.howl.utils.ItemClickSupport;

import java.util.List;

public class ConversationsFragment extends Fragment implements GetUsersContract.View,ItemClickSupport.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {



    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewConversations;

    private ConversationListingRecyclerAdapter mConversationsRecyclerAdapter;

    private GetUsersPresenter mGetConversationsPresenter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_conversations, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerViewConversations = (RecyclerView) view.findViewById(R.id.recycler_view_conversation);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mGetConversationsPresenter = new GetUsersPresenter(this);
        getUsers();

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        ItemClickSupport.addTo(mRecyclerViewConversations)
                .setOnItemClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {

        getUsers();

    }

    private void getUsers() {
        mGetConversationsPresenter.getAllConversations();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        String value;

        try {
            value = mConversationsRecyclerAdapter.getUser(position).getNameAndSurname();
        } catch (Exception e) {
            value = mConversationsRecyclerAdapter.getUser(position).getEmail();
        }
        ChatActivity.startActivity(getActivity(),
                value,
                mConversationsRecyclerAdapter.getUser(position).getUid(),
                mConversationsRecyclerAdapter.getUser(position).getFirebaseToken());
    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        mConversationsRecyclerAdapter = new ConversationListingRecyclerAdapter(users);
        mRecyclerViewConversations.setAdapter(mConversationsRecyclerAdapter);
        mConversationsRecyclerAdapter.notifyDataSetChanged();





    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
//                onGetAllUsersSuccess(null); burayi yoruma almak ilk basta yenileme ekraninin cikmasini sagladi
            }
        });
        // Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
        //  mSwipeRefreshLayout.setRefreshing(false);

        mRecyclerViewConversations.setAdapter(null);







    }

    @Override
    public void onGetChatUsersSuccess(List<User> users) {
        mConversationsRecyclerAdapter = new ConversationListingRecyclerAdapter(users);
        mRecyclerViewConversations.setAdapter(mConversationsRecyclerAdapter);
        mConversationsRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onGetChatUsersFailure(String message) {

    }
}