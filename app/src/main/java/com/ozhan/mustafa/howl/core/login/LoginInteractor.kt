package com.ozhan.mustafa.howl.core.login

import android.app.Activity
import android.util.Log

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ozhan.mustafa.howl.utils.Constants
import com.ozhan.mustafa.howl.utils.SharedPrefUtil

import android.content.ContentValues.TAG

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:55 PM.
 */

class LoginInteractor internal constructor(private val mOnLoginListener: LoginContract.OnLoginListener) : LoginContract.Interactor {

    override fun performFirebaseLogin(activity: Activity, email: String, password: String) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    Log.d(TAG, "performFirebaseLogin:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (task.isSuccessful) {
                        mOnLoginListener.onSuccess(task.result.toString())
                        updateFirebaseToken(task.result.user.uid,
                                SharedPrefUtil(activity.applicationContext).getString(Constants.ARG_FIREBASE_TOKEN, ""))
                    } else {
                        mOnLoginListener.onFailure(task.exception!!.message!!)
                    }
                }
    }

    private fun updateFirebaseToken(uid: String, token: String) {
        FirebaseDatabase.getInstance()
                .reference
                .child(Constants.ARG_USERS)
                .child(uid)
                .child(Constants.ARG_FIREBASE_TOKEN)
                .setValue(token)
    }
}
