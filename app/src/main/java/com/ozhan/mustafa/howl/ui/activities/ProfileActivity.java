package com.ozhan.mustafa.howl.ui.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ozhan.mustafa.howl.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTxtViewNameAndSurname,mTxtViewStatus;
    private ImageView mImgViewProfilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Bundle b = getIntent().getExtras();
        String value = null; // or other values
        if(b != null)
            value = b.getString("key");

        mImgViewProfilePicture=(ImageView)findViewById(R.id.imageViewPicture);
        mTxtViewNameAndSurname=(TextView)findViewById(R.id.text_view_nameAndSurname);
        mTxtViewStatus=(TextView)findViewById(R.id.status);

      //  mImgViewProfilePicture.setOnClickListener(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(String.valueOf(R.string.storage_link));
        storageRef.child("profilePictures/" + value + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                try {
                    Glide
                            .with(mImgViewProfilePicture.getContext())
                            .load("" + uri.toString())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                            .centerCrop()
                            .crossFade()
                            .into(mImgViewProfilePicture);
                } catch (Exception e) {

                }
            }
        });





        FirebaseDatabase.getInstance().getReference().child("users").orderByChild("email").equalTo(value).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    try {
                        mTxtViewNameAndSurname.setText(dataSnapshot.child("nameAndSurname").getValue().toString());
                    } catch (Exception e) {
                       mTxtViewNameAndSurname.setText("No Name and Surname.");
                    }
                    try {
                        mTxtViewStatus.setText(dataSnapshot.child("status").getValue().toString());
                    } catch (Exception e) {
                        mTxtViewStatus.setText("No status.");
                    }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if (snapshot.getValue() != null) {
//                    try {
//                        mETxtUsername.setText(snapshot.child("nameAndSurname").getValue().toString());
//                    } catch (Exception e) {
//                        Log.d(getTag(), "No username defined");
//                    }
//                    try {
//                        mETxtStatus.setText(snapshot.child("status").getValue().toString());
//                    } catch (Exception e) {
//                        Log.d(getTag(), "No status defined");
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });


    }

    @Override
    public void onClick(View v) {

    }

}
