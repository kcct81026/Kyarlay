package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.SafeMainObj;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SafeDetailsActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "SafeDetailsActivity";

    private LinearLayout back_layout, title_layout;
    private RelativeLayout like_layot;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView img;

    private UniversalAdapter adapter;
    private ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    private AppCompatActivity activity;
    private MyPreference prefs;
    private Resources resources;
    private Display display;


    private int safeID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_all_names);

        Log.e(TAG, "onCreate: " );


        new MyFlurry(SafeDetailsActivity.this);

        safeID = getIntent().getIntExtra("safe_id" , 0);


        try {


            Map<String, String> mix = new HashMap<String, String>();
            mix.put("sub_id", String.valueOf(safeID));
            FlurryAgent.logEvent("IsItSafe Sub Category Page", mix);
        } catch (Exception e) {
        }



        activity = SafeDetailsActivity.this;
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        display = getWindowManager().getDefaultDisplay();

        back_layout = findViewById(R.id.back_layout);
        recyclerView    = findViewById(R.id.recycler_view);
        progressBar    = findViewById(R.id.progressBar1);
        like_layot = findViewById(R.id.like_layot);
        title_layout = findViewById(R.id.title_layout);
        img = findViewById(R.id.img);



        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 14) / 20;
        img.getLayoutParams().width    = ( display.getWidth() * 3) / 20;

        img.setVisibility(View.GONE);
        like_layot.setVisibility(View.GONE);

        adapter = new UniversalAdapter(activity, universalPosts);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!prefs.getBooleanPreference(SP_ADS_LOADED)){
                    CountDownTimer countDownTimer = new CountDownTimer(5*60 *1000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {


                            prefs.saveBooleanPreference(SP_ADS_LOADED, true);
                            prefs.saveIntPerferences(ADS_COUNT_DOWN_TIME, (int)(millisUntilFinished / 1000) );



                        }

                        @Override
                        public void onFinish() {

                            prefs.saveBooleanPreference(SP_ADS_LOADED, false);

                        }
                    };

                    countDownTimer.start();
                    Intent intent = new Intent(SafeDetailsActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }
            }
        });

        if (prefs.isNetworkAvailable()){
            getSafetyCategories();
        }
    }

    @Override
    public void onBackPressed() {
        if (!prefs.getBooleanPreference(SP_ADS_LOADED)){
            CountDownTimer countDownTimer = new CountDownTimer(5*60 *1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {


                    prefs.saveBooleanPreference(SP_ADS_LOADED, true);
                    prefs.saveIntPerferences(ADS_COUNT_DOWN_TIME, (int)(millisUntilFinished / 1000) );



                }

                @Override
                public void onFinish() {

                    prefs.saveBooleanPreference(SP_ADS_LOADED, false);

                }
            };

            countDownTimer.start();
            Intent intent = new Intent(SafeDetailsActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }

    private void getSafetyCategories()
    {

        Log.e(TAG, "getSafetyCategories: " + constantSafeCategory + safeID +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) );

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,constantSafeCategory + safeID +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        progressBar.setVisibility(View.GONE);

                        if(response.length() > 0) {

                            try {

                                Gson gson = new Gson();
                                SafeMainObj main = gson.fromJson(response.toString(), SafeMainObj.class);
                                main.setPostType(SAFE_SUB_MAIN);
                                universalPosts.add(main);
                                adapter.notifyItemInserted(universalPosts.size());

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }

                        }}

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error "  + error.getMessage() );

            }
        });


        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}
