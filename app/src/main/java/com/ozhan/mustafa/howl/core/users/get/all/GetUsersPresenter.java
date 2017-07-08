package com.ozhan.mustafa.howl.core.users.get.all;

import com.ozhan.mustafa.howl.models.User;

import java.util.List;

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:00 PM.
 */

public class GetUsersPresenter implements GetUsersContract.Presenter, GetUsersContract.OnGetAllUsersListener {
    private GetUsersContract.View mView;
    private GetUsersInteractor mGetUsersInteractor;

    public GetUsersPresenter(GetUsersContract.View view) {
        this.mView = view;
        mGetUsersInteractor = new GetUsersInteractor(this);
    }

    @Override
    public void getAllUsers() {
        mGetUsersInteractor.getAllUsersFromFirebase();
    }
    @Override
    public void getAllNotifications(String uid){
        mGetUsersInteractor.getNotificationsFromFirebase(uid);
    }
    public void getAllConversations(){

        mGetUsersInteractor.getConversationsFromFirebase();
    }

    @Override
    public void getChatUsers() {
        mGetUsersInteractor.getChatUsersFromFirebase();
    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mView.onGetAllUsersSuccess(users);
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mView.onGetAllUsersFailure(message);
    }

}
