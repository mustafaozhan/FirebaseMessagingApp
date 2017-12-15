package com.ozhan.mustafa.howl.core.login

import android.app.Activity

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:55 PM.
 */

interface LoginContract {
    interface View {
        fun onLoginSuccess(message: String)

        fun onLoginFailure(message: String)
    }

    interface Presenter {
        fun login(activity: Activity, email: String, password: String)
    }

    interface Interactor {
        fun performFirebaseLogin(activity: Activity, email: String, password: String)
    }

    interface OnLoginListener {
        fun onSuccess(message: String)

        fun onFailure(message: String)
    }
}
