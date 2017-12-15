package com.ozhan.mustafa.howl.core.chat

import android.content.Context

import com.ozhan.mustafa.howl.models.Chat

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:52 PM.
 */

interface ChatContract {
    interface View {
        fun onSendMessageSuccess()

        fun onSendMessageFailure(message: String)

        fun onGetMessagesSuccess(chat: Chat)

        fun onGetMessagesFailure(message: String)
    }

    interface Presenter {
        fun sendMessage(context: Context, chat: Chat, receiverFirebaseToken: String)

        fun getMessage(senderUid: String, receiverUid: String)
    }

    interface Interactor {
        fun sendMessageToFirebaseUser(context: Context, chat: Chat, receiverFirebaseToken: String)

        fun getMessageFromFirebaseUser(senderUid: String, receiverUid: String)
    }

    interface OnSendMessageListener {
        fun onSendMessageSuccess()

        fun onSendMessageFailure(message: String)
    }

    interface OnGetMessagesListener {
        fun onGetMessagesSuccess(chat: Chat)

        fun onGetMessagesFailure(message: String)
    }
}
