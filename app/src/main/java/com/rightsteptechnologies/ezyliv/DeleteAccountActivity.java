package com.rightsteptechnologies.ezyliv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccountActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Button accept,deny;

    private static final String TAG = "DeleteAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");
            } else {
                requestPermission();
            }
        }
        accept  = (Button) findViewById(R.id.accept);
        deny  = (Button) findViewById(R.id.deny);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
                String msg="I wish to delete my account..Please check the Withdraw form for my details.";
                String no= "9040040027";
                if(checkPermission()) {
                    SmsManager sms=SmsManager.getDefault();
                    sms.sendTextMessage(no, null, msg, null, null);
                }else {
                    Toast.makeText(DeleteAccountActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(DeleteAccountActivity.this,"Please tell us what made you take up this decision.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),DeleteAccountActivity.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                startActivity(new Intent(DeleteAccountActivity.this,WithdrawActivityReview.class));
            }
        });
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeleteAccountActivity.this,"Redirecting to your profile",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DeleteAccountActivity.this,HomeActivity.class));
                DeleteAccountActivity.this.finish();
            }
        });
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(DeleteAccountActivity.this, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(DeleteAccountActivity.this,
                            "Permission accepted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(DeleteAccountActivity.this,
                            "Permission denied.. Allow app to send message to proceed!", Toast.LENGTH_LONG).show();
                    Button accept = (Button) findViewById(R.id.accept);
                    accept.setEnabled(false);

                }
                break;
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(DeleteAccountActivity.this,HomeActivity.class));
        DeleteAccountActivity.this.finish();
        super.onBackPressed();
    }

}
