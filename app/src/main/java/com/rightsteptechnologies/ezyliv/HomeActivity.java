package com.rightsteptechnologies.ezyliv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private ImageView profile,complaint,delete_account,logout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profile=(ImageView)findViewById(R.id.profile);
        complaint=(ImageView)findViewById(R.id.complaints);
        firebaseAuth=FirebaseAuth.getInstance();
        delete_account=(ImageView)findViewById(R.id.withdraw_account);
        logout=(ImageView)findViewById(R.id.logout);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
                HomeActivity.this.finish();
            }
        });
        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ComplaintsActivity.class));
                HomeActivity.this.finish();
            }
        });
        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,DeleteAccountActivity.class));
                HomeActivity.this.finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                HomeActivity.this.finish();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });

    }
}
