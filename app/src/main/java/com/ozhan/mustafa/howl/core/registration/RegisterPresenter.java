package com.ozhan.mustafa.howl.core.registration;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:58 PM.
 */

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.OnRegistrationListener {
    private RegisterContract.View mRegisterView;
    private RegisterInteractor mRegisterInteractor;

    public RegisterPresenter(RegisterContract.View registerView) {
        this.mRegisterView = registerView;
        mRegisterInteractor = new RegisterInteractor(this);
    }




    @Override
    public void register(Activity activity, String email, String password) {
        mRegisterInteractor.performFirebaseRegistration(activity, email, password);
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        mRegisterView.onRegistrationSuccess(firebaseUser);
    }

    @Override
    public void onFailure(String message) {
        mRegisterView.onRegistrationFailure(message);
    }
}
