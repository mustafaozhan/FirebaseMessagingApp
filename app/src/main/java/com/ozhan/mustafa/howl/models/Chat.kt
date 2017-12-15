package com.ozhan.mustafa.howl.models

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Mustafa Özhan on 3/4/17 at 1:03 PM.
 */

@IgnoreExtraProperties
class Chat(var sender: String,
           var receiver: String,
           var senderUid: String,
           var receiverUid: String,
           var message: String,
           var timestamp: Long)
