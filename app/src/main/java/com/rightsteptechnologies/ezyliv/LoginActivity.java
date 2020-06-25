package com.rightsteptechnologies.ezyliv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText email,password;
    private TextView register,forgot_password;
    private Button login;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        email= (EditText) findViewById(R.id.password);
        password= (EditText) findViewById(R.id.tv_password);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        register= findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        firebaseAuth= FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();
        if(user!= null){
            finish();
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            LoginActivity.this.finish();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                LoginActivity.this.finish();
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Forgot_PasswordActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate(email.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this,Intro.class));
        LoginActivity.this.finish();
        super.onBackPressed();
    }

    private void validate(String useremail, String userpassword){
        if (useremail!= null || userpassword!=null) {
            firebaseAuth.signInWithEmailAndPassword(useremail, userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkEmailVerification();
                    } else
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else Toast.makeText(LoginActivity.this,"Input Credentials should be entered* to continue!",Toast.LENGTH_LONG).show();
        }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Boolean email_flag= firebaseUser.isEmailVerified();
        if (email_flag){
            LoginActivity.this.finish();
            Toast.makeText(LoginActivity.this, "Welcome to EZYLIV! Happy Living!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));

        }
        else
        {
            Toast.makeText(LoginActivity.this,"Please Verify your email address to proceed",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}
