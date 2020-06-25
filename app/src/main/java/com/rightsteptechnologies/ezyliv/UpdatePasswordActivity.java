package com.rightsteptechnologies.ezyliv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {
    private EditText password,cnf_password;
    private Button reset;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        password = findViewById(R.id.editText);
        cnf_password = findViewById(R.id.editText4);
        reset = findViewById(R.id.button2);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upassword = password.getText().toString();
                String ucnfpassword = cnf_password.getText().toString();
                if (upassword.equals(ucnfpassword)) {
                    firebaseUser.updatePassword(upassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdatePasswordActivity.this, "Password is changed! Log in again to continue..", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdatePasswordActivity.this, LoginActivity.class));
                                UpdatePasswordActivity.this.finish();
                            }
                            else {
                                Toast.makeText(UpdatePasswordActivity.this,"An unexpected error has occurred! Please try again.",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdatePasswordActivity.this, ProfileActivity.class));
                                UpdatePasswordActivity.this.finish();
                            }
                        }
                    });
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdatePasswordActivity.this,ProfileActivity.class));
        UpdatePasswordActivity.this.finish();
        super.onBackPressed();
    }

}
