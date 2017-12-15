package com.ozhan.mustafa.howl.models

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:03 PM.
 */

@IgnoreExtraProperties
class User {
    lateinit var uid: String
    var email: String? = null
    lateinit var firebaseToken: String
    var nameAndSurname: String? = null
    var status: String? = null

    constructor() {}

    constructor(uid: String, email: String?, firebaseToken: String) {
        this.uid = uid
        this.email = email
        this.firebaseToken = firebaseToken
    }

}
