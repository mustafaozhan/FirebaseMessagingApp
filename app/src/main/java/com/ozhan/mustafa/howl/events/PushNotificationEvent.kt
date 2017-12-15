package com.ozhan.mustafa.howl.events

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:01 PM.
 */

class PushNotificationEvent(title: String, message: String, username: String, uid: String, fcmToken: String) {
    var title: String? = title
    var message: String? = message
    private var username: String? = username
    var uid: String? = uid
    private var fcmToken: String? = fcmToken


}
