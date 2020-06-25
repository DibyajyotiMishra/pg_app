package com.rightsteptechnologies.ezyliv;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText name,email,phone,password,pgname;
    private TextView login;
    private Button register;
    String username,useremail,userphone,userpassword,userpgname;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        name= (EditText) findViewById(R.id.tv_name);
        email= (EditText) findViewById(R.id.tv_email);
        phone= (EditText) findViewById(R.id.tv_phone);
        pgname= (EditText) findViewById(R.id.tv_pgname);
        password= (EditText) findViewById(R.id.tv_password);
        login = (TextView) findViewById(R.id.textView);
        register = (Button) findViewById(R.id.button);
        firebaseAuth= FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    useremail = email.getText().toString().trim();
                    userpassword= password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerification();
                            }
                            else Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
    private boolean validate(){
        boolean result = false;
        username = name.getText().toString();
        useremail = email.getText().toString();
        userphone = phone.getText().toString();
        userpassword= password.getText().toString();
        userpgname = pgname.getText().toString();
        if ((username.isEmpty())|| (useremail.isEmpty())|| (userpassword.isEmpty()) || (userphone.isEmpty())|| (userpgname.isEmpty()))
        {

            Toast.makeText(RegisterActivity.this,"enter valid details",Toast.LENGTH_LONG).show();
            result = false;

        }
        else result= true;
        return result;
    }

    private  void sendEmailVerification()
    {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(RegisterActivity.this,"Registration Successful! Please verify your Email to proceed!",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Registration Unsuccessful! Please try again!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference= firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(username,useremail,userphone,userpgname);
        databaseReference.setValue(userProfile);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        RegisterActivity.this.finish();
        super.onBackPressed();
    }

}
