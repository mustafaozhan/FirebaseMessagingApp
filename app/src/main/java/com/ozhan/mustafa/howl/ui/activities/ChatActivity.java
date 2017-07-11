package com.ozhan.mustafa.howl.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ozhan.mustafa.howl.HowlMainApp;
import com.ozhan.mustafa.howl.R;
import com.ozhan.mustafa.howl.ui.fragments.ChatFragment;
import com.ozhan.mustafa.howl.utils.Constants;

public class ChatActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    public static void startActivity(Context context, String receiver, String receiverUid, String firebaseToken) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constants.INSTANCE.getARG_RECEIVER(), receiver);
        intent.putExtra(Constants.INSTANCE.getARG_RECEIVER_UID(), receiverUid);
        intent.putExtra(Constants.INSTANCE.getARG_FIREBASE_TOKEN(), firebaseToken);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        bindViews();
        init();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void init() {
        // set the toolbar
        setSupportActionBar(mToolbar);

        // set toolbar title
        mToolbar.setTitle(getIntent().getExtras().getString(Constants.INSTANCE.getARG_RECEIVER()));



      //
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(getIntent().getExtras().get(Constants.INSTANCE.getARG_RECEIVER_UID()).toString())
                .child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                mToolbar.setSubtitle(dataSnapshot.getValue().toString());}catch (Exception e){
                //no status
            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        // set the register screen fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_chat,
                ChatFragment.newInstance(getIntent().getExtras().getString(Constants.INSTANCE.getARG_RECEIVER()),
                        getIntent().getExtras().getString(Constants.INSTANCE.getARG_RECEIVER_UID()),
                        getIntent().getExtras().getString(Constants.INSTANCE.getARG_FIREBASE_TOKEN())),
                ChatFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HowlMainApp.setChatActivityOpen(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HowlMainApp.setChatActivityOpen(false);
    }
}

