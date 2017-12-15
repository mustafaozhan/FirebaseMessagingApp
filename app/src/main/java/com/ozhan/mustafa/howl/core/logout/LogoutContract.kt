package com.ozhan.mustafa.howl.core.logout

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:56 PM.
 */

interface LogoutContract {
    interface View {
        fun onLogoutSuccess(message: String)

        fun onLogoutFailure(message: String)
    }

    interface Presenter {
        fun logout()
    }

    interface Interactor {
        fun performFirebaseLogout()
    }

    interface OnLogoutListener {
        fun onSuccess(message: String)

        fun onFailure(message: String)
    }
}
