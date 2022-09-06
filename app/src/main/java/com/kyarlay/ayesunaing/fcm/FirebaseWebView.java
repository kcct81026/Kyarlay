package com.kyarlay.ayesunaing.fcm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.MainActivity;
import com.kyarlay.ayesunaing.data.MyFlurry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ayesu on 6/5/17.
 */

public class FirebaseWebView extends AppCompatActivity {


    WebView web;
    String url ="https://www.momolay.com/privacy";
    ProgressBar progressBar;
    LinearLayout back_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);


        new MyFlurry(FirebaseWebView.this);
        try {
            Map<String, String> mix = new HashMap<String, String>();
            mix.put("source", "WebView_link");
            FlurryAgent.logEvent("Incoming Pushnotification Click", mix);
        } catch (Exception e) {}
        back_layout = (LinearLayout) findViewById(R.id.back_layout);
        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(FirebaseWebView.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        web=(WebView) findViewById(R.id.web);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        web.loadUrl(url);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.setWebViewClient(new MyBrowser(url));

/*
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.arrowleft);*/

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(FirebaseWebView.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private class MyBrowser extends WebViewClient {
        String url;
        public MyBrowser(String url)
        {
            this.url=url;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }


}