package com.ozhan.mustafa.howl.fcm


import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.ozhan.mustafa.howl.utils.Constants
import com.ozhan.mustafa.howl.utils.SharedPrefUtil

/**
 * Created by Mustafa Özhan on 3/4/17 at 11:30 AM.
 */


class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken)
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        SharedPrefUtil(applicationContext).saveString(Constants.ARG_FIREBASE_TOKEN, token!!)

        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseDatabase.getInstance()
                    .reference
                    .child(Constants.ARG_USERS)
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child(Constants.ARG_FIREBASE_TOKEN)
                    .setValue(token)
        }
    }

    companion object {
        private val TAG = "MyFirebaseIIDService"
    }
}