package com.ozhan.mustafa.howl.core.registration

import android.app.Activity

import com.google.firebase.auth.FirebaseUser

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:57 PM.
 */

interface RegisterContract {
    interface View {
        fun onRegistrationSuccess(firebaseUser: FirebaseUser)

        fun onRegistrationFailure(message: String)
    }

    interface Presenter {
        fun register(activity: Activity, email: String, password: String)
    }

    interface Interactor {
        fun performFirebaseRegistration(activity: Activity, email: String, password: String)
    }

    interface OnRegistrationListener {
        fun onSuccess(firebaseUser: FirebaseUser)

        fun onFailure(message: String)
    }
}
