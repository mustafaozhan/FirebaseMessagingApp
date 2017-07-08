package com.ozhan.mustafa.howl.core.users.get.all;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.ozhan.mustafa.howl.models.User;

import java.util.List;

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:59 PM.
 */

public interface GetUsersContract {
    interface View {
        void onItemClicked(RecyclerView recyclerView, int position, android.view.View v);

        void onGetAllUsersSuccess(List<User> users);

        void onGetAllUsersFailure(String message);

        void onGetChatUsersSuccess(List<User> users);

        void onGetChatUsersFailure(String message);


    }

    interface Presenter {
        void getAllUsers();

        void getChatUsers();
    }

    interface Interactor {
        void getAllUsersFromFirebase();

        void getChatUsersFromFirebase();


        void getConversationsFromFirebase();

        void getNotificationsFromFirebase(String uid);
    }

    interface OnGetAllUsersListener {
        void getAllNotifications(String uid);

        void onGetAllUsersSuccess(List<User> users);

        void onGetAllUsersFailure(String message);
    }

    interface OnGetChatUsersListener {
        void onGetChatUsersSuccess(List<User> users);

        void onGetChatUsersFailure(String message);
    }
}

