package com.ozhan.mustafa.howl.core.users.get.all

import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View

import com.ozhan.mustafa.howl.models.User

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:59 PM.
 */

interface GetUsersContract {
    interface View {
        fun onItemClicked(recyclerView: RecyclerView, position: Int, v: android.view.View)

        fun onGetAllUsersSuccess(users: List<User>)

        fun onGetAllUsersFailure(message: String)

        fun onGetChatUsersSuccess(users: List<User>)

        fun onGetChatUsersFailure(message: String)


    }

    interface Presenter {
        fun getAllUsers()

        fun getChatUsers()
    }

    interface Interactor {
        fun getAllUsersFromFirebase()

        fun getChatUsersFromFirebase()


        fun getConversationsFromFirebase()

        fun getNotificationsFromFirebase(uid: String)
    }

    interface OnGetAllUsersListener {
        fun getAllNotifications(uid: String)

        fun onGetAllUsersSuccess(users: List<User>)

        fun onGetAllUsersFailure(message: String)
    }

    interface OnGetChatUsersListener {
        fun onGetChatUsersSuccess(users: List<User>)

        fun onGetChatUsersFailure(message: String)
    }
}

