package com.ozhan.mustafa.howl.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:03 PM.
 */

@IgnoreExtraProperties
public class User {
    public String uid;
    public String email;
    public String firebaseToken;
    public String nameAndSurname=null;
    public String status=null;

    public User() {
    }

    public User(String uid, String email, String firebaseToken) {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
    }

}
