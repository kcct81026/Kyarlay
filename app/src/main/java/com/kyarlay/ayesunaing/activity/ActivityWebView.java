package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;

/**
 * Created by kcct on 9/7/16.
 */
public class ActivityWebView extends AppCompatActivity implements ConstantVariable {
    WebView web;
    String str ="https://www.momolay.com/privacy";
    ProgressBar progressBar;
    String title;
    Resources resources;
    MyPreference prefs;
    String s;

    Display display ;

    LinearLayout title_layout, back_layout;
    CustomTextView title_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        prefs  = new MyPreference(ActivityWebView.this);


        Intent intent = getIntent();
        str      = intent.getStringExtra("url");
        title    = intent.getStringExtra("title");

        display          = getWindowManager().getDefaultDisplay();
        title_textview   = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 17) / 20;

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityWebView.this.finish();
            }
        });

        web=(WebView) findViewById(R.id.web);
        web.loadUrl(str);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.setWebViewClient(new ActivityWebView.MyBrowser(str));
        Context context1 = LocaleHelper.setLocale(ActivityWebView.this, prefs.getStringPreferences(LANGUAGE));
        resources = context1.getResources();
        if(title != null && title.equals("help"))
            s = resources.getString(R.string.help);
        else if(title.equals("about"))
            s = resources.getString(R.string.about_us);
        else
            s = "";


        title_textview.setText(s);

    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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