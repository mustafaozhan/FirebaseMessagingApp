package com.ozhan.mustafa.howl.core.logout;

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:56 PM.
 */

public interface LogoutContract {
    interface View {
        void onLogoutSuccess(String message);

        void onLogoutFailure(String message);
    }

    interface Presenter {
        void logout();
    }

    interface Interactor {
        void performFirebaseLogout();
    }

    interface OnLogoutListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
