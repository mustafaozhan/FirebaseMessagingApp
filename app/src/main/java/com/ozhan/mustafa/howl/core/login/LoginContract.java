package com.ozhan.mustafa.howl.core.login;

import android.app.Activity;

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:55 PM.
 */

public interface LoginContract {
    interface View {
        void onLoginSuccess(String message);

        void onLoginFailure(String message);
    }

    interface Presenter {
        void login(Activity activity, String email, String password);
    }

    interface Interactor {
        void performFirebaseLogin(Activity activity, String email, String password);
    }

    interface OnLoginListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
