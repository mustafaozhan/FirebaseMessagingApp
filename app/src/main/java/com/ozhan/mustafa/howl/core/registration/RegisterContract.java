package com.ozhan.mustafa.howl.core.registration;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:57 PM.
 */

public interface RegisterContract {
    interface View {
        void onRegistrationSuccess(FirebaseUser firebaseUser);

        void onRegistrationFailure(String message);
    }

    interface Presenter {
        void register(Activity activity, String email, String password);
    }

    interface Interactor {
        void performFirebaseRegistration(Activity activity, String email, String password);
    }

    interface OnRegistrationListener {
        void onSuccess(FirebaseUser firebaseUser);

        void onFailure(String message);
    }
}
