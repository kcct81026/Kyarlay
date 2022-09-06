package com.kyarlay.ayesunaing.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.MyPreference;

public class FragmentWebView extends Fragment implements Constant, ConstantVariable {
    private static final String TAG = "FragmentWebView";

    ProgressBar progressBar1;
    WebView web;
    int position = 0;
    String str = "";
    MyPreference prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            position = getArguments().getInt("position", 0);
        }



        Log.e(TAG, "onCreateView: "  + position );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.layout_fragment_webview, container, false);
        progressBar1 = rootView.findViewById(R.id.progressBar1);
        web = rootView.findViewById(R.id.web);

        prefs = new MyPreference(getActivity());


       /* if (position == 1){

            if(prefs.getStringPreferences(SP_USER_PHONE) != "") {
                str = String.format(constantCompetitionTopTen, prefs.getIntPreferences(COMPETITION_ID)) +  "phone=" +  prefs.getStringPreferences(SP_USER_PHONE) + "&mobile=1";
            }else{
                str = String.format(constantCompetitionTopTen, prefs.getIntPreferences(COMPETITION_ID))  + "mobile=1";
            }


        }
        else{
            if(prefs.getStringPreferences(SP_USER_PHONE) != "") {
                str = String.format(constantCompetitionTerms, prefs.getIntPreferences(COMPETITION_ID)) +  "phone=" +  prefs.getStringPreferences(SP_USER_PHONE) + "&mobile=1";
            }else{
                str = String.format(constantCompetitionTerms, prefs.getIntPreferences(COMPETITION_ID))  + "mobile=1";
            }
        }*/
        if(prefs.getStringPreferences(SP_USER_PHONE) != "") {
            str = String.format(constantCompetitionTerms, prefs.getIntPreferences(COMPETITION_ID)) +  "phone=" +  prefs.getStringPreferences(SP_USER_PHONE) + "&mobile=1";
        }else{
            str = String.format(constantCompetitionTerms, prefs.getIntPreferences(COMPETITION_ID))  + "mobile=1";
        }

        Log.e(TAG, "onCreateView: "  + str );

        web.loadUrl(str);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.setWebViewClient(new FragmentWebView.MyBrowser(str));
        return rootView;
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
            progressBar1.setVisibility(View.GONE);
        }
    }

}
