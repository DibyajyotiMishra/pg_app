package com.rightsteptechnologies.ezyliv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView profilename,profileemail,profilenumber,profilePGname,updateProfile,updatePassword;
    private FirebaseDatabase firebaseDatabase;
    String name;
    String Uid;
    private static final String TAG = "ProfileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilename = findViewById(R.id.ProfileUserid);
        profileemail = findViewById(R.id.profileEmail);
        profilenumber= findViewById(R.id.profileMobileNumber);
        profilePGname= findViewById(R.id.ProfilePGname);
        updateProfile= findViewById(R.id.updateProfile);
        updatePassword= findViewById(R.id.UpdatePassword);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference(Objects.requireNonNull(firebaseAuth.getUid()));
        Uid= firebaseAuth.getUid();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                UserProfile userprofile = dataSnapshot.getValue(UserProfile.class);
                profilename.setText(userprofile.getUserName());
                profileemail.setText(userprofile.getUserEmail());
                profilenumber.setText(userprofile.getUserNumber());
                profilePGname.setText(userprofile.getUserPGname());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        databaseReference.addValueEventListener(postListener);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,UpdateProfileActivity.class));
                ProfileActivity.this.finish();
            }
        });
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,UpdatePasswordActivity.class));
                ProfileActivity.this.finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
        ProfileActivity.this.finish();
        super.onBackPressed();
    }


}

