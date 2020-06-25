package com.rightsteptechnologies.ezyliv;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WithdrawActivityReview extends AppCompatActivity {
    private WebView myWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_withdraw_review);
        getSupportActionBar().hide();
        myWebView = (WebView) findViewById(R.id.webview_review);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        myWebView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdu34ji4cJzmt9UVmkLQHbxvZLgtx8TqT5LUPaXdwRSjiQmUw/viewform?usp=sf_link");
        myWebView.setWebViewClient(new WebViewClient() {
            public void onreceiveError(WebView view, int errorcode, String desc, String failing_url) {
                myWebView.loadUrl("file:///android_assets/calender1.html");
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WithdrawActivityReview.this,Intro.class));
        WithdrawActivityReview.this.finish();
        super.onBackPressed();
    }

}
