package com.kyarlay.ayesunaing.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.object.KyarlayAds;

import org.json.JSONObject;

public class CallAdsInterstitial extends AppCompatActivity implements Constant {

    private static final String TAG = "CallAdsInterstitial";
    ImageLoader imageLoader;
    RelativeLayout ads_layout;
    NetworkImageView imgAds;
    Display display;
    ImageView imgClose;
    RelativeLayout main_ads_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ads_interstitial);

        imageLoader  = AppController.getInstance().getImageLoader();
        display = getWindowManager().getDefaultDisplay();

        ads_layout = findViewById(R.id.ads_layout);
        imgAds = findViewById(R.id.imgAds);
        imgClose = findViewById(R.id.imgClose);
        main_ads_layout = findViewById(R.id.main_ads_layout);



        callAdsInterstitial();

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void callAdsInterstitial(){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantKyarlayAds + "interstitial" , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {




                if (response.length() > 0){
                    ads_layout.setVisibility(View.VISIBLE);
                    Gson gson = new Gson();
                    KyarlayAds main = gson.fromJson(response.toString(), KyarlayAds.class);


                    imgAds.getLayoutParams().height = ( display.getWidth() * main.getImage_dimen() ) / 100;
                    ads_layout.getLayoutParams().height = ( display.getWidth() * main.getImage_dimen() ) / 100;
                    imgAds.setImageUrl(main.getImage_url(), imageLoader);

                    ads_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                    Request.Method.GET, String.format(constantAdsClick, main.getId()+""), null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            try{

                                                if (response.getInt("status")  == 1){

                                                    if (main.getType().equals("link")){
                                                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(main.getTarget_url()));
                                                        startActivity(myIntent);
                                                    }
                                                    else if(main.getType().equals("image")){
                                                        try {
                                                            Intent intent = new Intent(CallAdsInterstitial.this , AndroidLoadImageFromAdsUrl.class);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("url", main.getImage_url().toString());
                                                            intent.putExtras(bundle);
                                                            startActivity(intent);
                                                        } catch (Exception e) {
                                                            Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                                                        }
                                                    }
                                                }

                                            }catch (Exception e){

                                            }


                                        }
                                    },  new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );


                                }
                            });
                            AppController.getInstance().addToRequestQueue(jsonObjReq, "VersionDownload");


                        }
                    });


                }
                else{
                    finish();
                }






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                finish();

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }
}
