package com.rightsteptechnologies.ezyliv;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ComplaintsActivity extends AppCompatActivity {

    private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        myWebView = (WebView)findViewById(R.id.webview_complaint);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        myWebView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLScHfcnj8F-PgSKWB3a1cWgTjR4H1T8S_CslH0p5Q6amdizb4A/viewform?usp=sf_link");
        myWebView.setWebViewClient(new WebViewClient(){
            public void onreceiveError(WebView view,int errorcode,String desc,String failing_url){
                myWebView.loadUrl("file:///android_assets/calender1.html");
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ComplaintsActivity.this,HomeActivity.class));
       ComplaintsActivity.this.finish();
        super.onBackPressed();
    }


}
