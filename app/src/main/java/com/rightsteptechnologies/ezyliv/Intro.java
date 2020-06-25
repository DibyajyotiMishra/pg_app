package com.rightsteptechnologies.ezyliv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Intro extends AppCompatActivity {
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        myWebView = (WebView) findViewById(R.id.webview_intro);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        myWebView.loadUrl("https://ezyliv.in/");
        myWebView.setWebViewClient(new WebViewClient() {
            public void onreceiveError(WebView view, int errorcode, String desc, String failing_url) {
                myWebView.loadUrl("file:///android_assets/calender1.html");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intro.this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.login:
                startActivity(new Intent(Intro.this,LoginActivity.class));
                Intro.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
