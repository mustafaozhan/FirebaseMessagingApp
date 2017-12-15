package com.ozhan.mustafa.howl.models

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:03 PM.
 */

@IgnoreExtraProperties
class Chat {
    lateinit var sender: String
    lateinit var receiver: String
    lateinit var senderUid: String
    lateinit var receiverUid: String
    lateinit var message: String
    var timestamp: Long = 0

    constructor() {}

    constructor(sender: String, receiver: String, senderUid: String, receiverUid: String, message: String, timestamp: Long) {
        this.sender = sender
        this.receiver = receiver
        this.senderUid = senderUid
        this.receiverUid = receiverUid
        this.message = message
        this.timestamp = timestamp
    }
}
