package com.ozhan.mustafa.howl.models

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:03 PM.
 */

class User(var uid: String,
           var email: String,
           var firebaseToken: String,
           var nameAndSurname: String? = null,
           var status: String? = null)
