package com.ozhan.mustafa.howl.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ozhan.mustafa.howl.R;
import com.ozhan.mustafa.howl.core.chat.ChatContract;
import com.ozhan.mustafa.howl.core.chat.ChatPresenter;
import com.ozhan.mustafa.howl.events.PushNotificationEvent;
import com.ozhan.mustafa.howl.models.Chat;
import com.ozhan.mustafa.howl.ui.adapters.ChatRecyclerAdapter;
import com.ozhan.mustafa.howl.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by Mustafa Özhan on 3/4/17 at 12:26 PM.
 */

public class ChatFragment extends Fragment implements ChatContract.View, View.OnClickListener {

    private RecyclerView mRecyclerViewChat;
    private EditText mETxtMessage;
    private ProgressDialog mProgressDialog;
    private ImageButton mIBSend;


    private ChatRecyclerAdapter mChatRecyclerAdapter;

    private ChatPresenter mChatPresenter;

    public static ChatFragment newInstance(String receiver, String receiverUid, String firebaseToken) {
        Bundle args = new Bundle();
        args.putString(Constants.INSTANCE.getARG_RECEIVER(), receiver);
        args.putString(Constants.INSTANCE.getARG_RECEIVER_UID(), receiverUid);
        args.putString(Constants.INSTANCE.getARG_FIREBASE_TOKEN(), firebaseToken);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_chat, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mRecyclerViewChat = (RecyclerView) view.findViewById(R.id.recycler_view_chat);
        mETxtMessage = (EditText) view.findViewById(R.id.edit_text_message);
        mIBSend = (ImageButton) view.findViewById(R.id.imageButtonSend);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);

        mIBSend.setOnClickListener(this);


        //   mETxtMessage.setOnEditorActionListener(this);

        mChatPresenter = new ChatPresenter(this);
        mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                getArguments().getString(Constants.INSTANCE.getARG_RECEIVER_UID()));
    }
//
//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (actionId == EditorInfo.IME_ACTION_SEND) {
//
//            sendMessage();
//            return true;
//        }
//        return false;
//    }

    private void sendMessage() {

        String message = mETxtMessage.getText().toString();
        mETxtMessage.setText("");
        if (!message.equals("")) {
            String receiver = getArguments().getString(Constants.INSTANCE.getARG_RECEIVER());
            String receiverUid = getArguments().getString(Constants.INSTANCE.getARG_RECEIVER_UID());
            String sender = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String receiverFirebaseToken = getArguments().getString(Constants.INSTANCE.getARG_FIREBASE_TOKEN());
            Chat chat = new Chat(sender,
                    receiver,
                    senderUid,
                    receiverUid,
                    message,
                    System.currentTimeMillis());
            mChatPresenter.sendMessage(getActivity().getApplicationContext(),
                    chat,
                    receiverFirebaseToken);
        }
    }

    @Override
    public void onSendMessageSuccess() {


        //  mIBSend.setBackgroundResource(R.drawable.send_button);
        // Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_SHORT).show();
        try {
            mRecyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
        } catch (Exception e) {
            //konusmalar database den silinmis ise
        }

    }

    @Override
    public void onSendMessageFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMessagesSuccess(Chat chat) {

        if (mChatRecyclerAdapter == null) {
            mChatRecyclerAdapter = new ChatRecyclerAdapter(new ArrayList<Chat>());
            mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);
        }


        mChatRecyclerAdapter.add(chat);

        mRecyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);


    }

    @Override
    public void onGetMessagesFailure(String message) {

        try {


            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // log out yaptiysa eger hata veriyo
        }
    }

    @Subscribe //ben sildim
    public void onPushNotificationEvent(PushNotificationEvent pushNotificationEvent) {
        if (mChatRecyclerAdapter == null || mChatRecyclerAdapter.getItemCount() == 0) {
            mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    pushNotificationEvent.getUid());

        }

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.imageButtonSend:
                sendMessage();
                break;
        }
    }


}