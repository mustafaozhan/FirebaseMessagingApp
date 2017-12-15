package com.ozhan.mustafa.howl.core.users.add

import android.content.Context

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ozhan.mustafa.howl.R
import com.ozhan.mustafa.howl.models.User
import com.ozhan.mustafa.howl.utils.Constants
import com.ozhan.mustafa.howl.utils.SharedPrefUtil

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:59 PM.
 */

class AddUserInteractor(private val mOnUserDatabaseListener: AddUserContract.OnUserDatabaseListener) : AddUserContract.Interactor {

    override fun addUserToDatabase(context: Context, firebaseUser: FirebaseUser) {
        val database = FirebaseDatabase.getInstance().reference
        val user = User(firebaseUser.uid,
                firebaseUser.email,
                SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN))
        database.child(Constants.ARG_USERS)
                .child(firebaseUser.uid)
                .setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mOnUserDatabaseListener.onSuccess(context.getString(R.string.user_successfully_added))
                    } else {
                        mOnUserDatabaseListener.onFailure(context.getString(R.string.user_unable_to_add))
                    }
                }
    }
}
