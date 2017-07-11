package com.ozhan.mustafa.howl.events

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:01 PM.
 */

class PushNotificationEvent {
    var title: String? = null
    var message: String? = null
    var username: String? = null
    var uid: String? = null
    var fcmToken: String? = null

    constructor() {}

    constructor(title: String, message: String, username: String, uid: String, fcmToken: String) {
        this.title = title
        this.message = message
        this.username = username
        this.uid = uid
        this.fcmToken = fcmToken
    }
}
