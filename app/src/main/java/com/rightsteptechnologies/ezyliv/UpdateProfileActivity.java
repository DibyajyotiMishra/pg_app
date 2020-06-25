package com.rightsteptechnologies.ezyliv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText userName,userPhone;
    private Button btn;
    private TextView email,pgname;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private static final String TAG = "UpdateProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        userName= findViewById(R.id.editText2);
        userPhone=findViewById(R.id.editText3);
        email= findViewById(R.id.textView5);
        pgname= findViewById(R.id.textView8);
        btn= findViewById(R.id.button3);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(Objects.requireNonNull(firebaseAuth.getUid()));

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                UserProfile userprofile = dataSnapshot.getValue(UserProfile.class);
                userName.setText(userprofile.getUserName());
                email.setText(userprofile.getUserEmail());
                userPhone.setText(userprofile.getUserNumber());
                pgname.setText(userprofile.getUserPGname());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        databaseReference.addValueEventListener(postListener);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname= userName.getText().toString();
                String uphone= userPhone.getText().toString();
                String uemail= email.getText().toString();
                String upgname= pgname.getText().toString();
                if ((uname!= null) || (uphone!= null)) {
                    UserProfile userProfile = new UserProfile(uname, uemail, uphone, upgname);
                    databaseReference.setValue(userProfile);
                    Toast.makeText(UpdateProfileActivity.this,"Changes saved",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));
                    UpdateProfileActivity.this.finish();
                }
                else{
                    startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));
                    UpdateProfileActivity.this.finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateProfileActivity.this,ProfileActivity.class));
        UpdateProfileActivity.this.finish();
        super.onBackPressed();
    }
}
