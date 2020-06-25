package com.rightsteptechnologies.ezyliv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_PasswordActivity extends AppCompatActivity {

    private EditText etmail;
    private Button proceed;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot__pasword);
        getSupportActionBar().hide();
        etmail= (EditText) findViewById(R.id.etEmail);
        proceed= (Button) findViewById(R.id.btn_proceed);
        firebaseAuth = FirebaseAuth.getInstance();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail= etmail.getText().toString().trim();
                if (useremail.equals("")){
                    Toast.makeText(Forgot_PasswordActivity.this,"Enter your registered email to proceed.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Forgot_PasswordActivity.this,"Password Reset Link is sent to your registered email.",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Forgot_PasswordActivity.this,LoginActivity.class));
                            }
                            else{
                                Toast.makeText(Forgot_PasswordActivity.this,"An error has occurred.Please try again!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Forgot_PasswordActivity.this,LoginActivity.class));
        Forgot_PasswordActivity.this.finish();
        super.onBackPressed();
    }
}
