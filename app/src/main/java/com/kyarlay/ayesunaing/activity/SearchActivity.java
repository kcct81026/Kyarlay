package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayesu on 6/22/17.
 */

public class SearchActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "SearchActivity";

    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    UniversalAdapter universalAdapter ;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    CustomEditText search;
    MyPreference prefs;
    Resources resources;
    DatabaseAdapter databaseAdapter;
    ImageView  search_icon;
    String scanResult;
    int REQUEST_RPS = 1;

    LinearLayout back, scanner;
    RelativeLayout search_layout;
    Display display ;


    FirebaseAnalytics firebaseAnalytics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Log.e(TAG, "onCreate: " );

        firebaseAnalytics   = FirebaseAnalytics.getInstance(SearchActivity.this);

        new MyFlurry(SearchActivity.this);
        try {


            Map<String, String> mix = new HashMap<String, String>();
            FlurryAgent.logEvent("View Search", mix);

        } catch (Exception e) {
        }
        display = getWindowManager().getDefaultDisplay();
        prefs   = new MyPreference(SearchActivity.this);
        Context context = LocaleHelper.setLocale(SearchActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        databaseAdapter = new DatabaseAdapter(SearchActivity.this);
        getSupportActionBar().hide();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        back            = (LinearLayout) findViewById(R.id.back_layout);
        search          = (CustomEditText) findViewById(R.id.search);
        scanner         = (LinearLayout) findViewById(R.id.scanner_layout);
        search_icon     = (ImageView) findViewById(R.id.search_icon);
        recyclerView    = (RecyclerView) findViewById(R.id.recycler_view);
        search_layout   = (RelativeLayout) findViewById(R.id.search_layout);;


        back.getLayoutParams().width     = ( display.getWidth() * 2) / 20;
        search_layout.getLayoutParams().width    = ( display.getWidth() * 18) / 20;
        //scanner.getLayoutParams().width     = ( display.getWidth() * 3) / 20;


        universalAdapter = new UniversalAdapter(SearchActivity.this, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);
        manager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(manager);

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*

                try {


                    FlurryAgent.logEvent("Click Barcode Scanner");

                } catch (Exception e) {
                }
            if (ContextCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                startScan();
            } else {
                ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_RPS);
                if(ContextCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    startScan();
                }
            }

*/

            }
        });

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("search_item",search.getText().toString());
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);

                if(search.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class );
                    Bundle bun = new Bundle();
                    bun.putString("url",constantSearch + prefs.getIntPreferences(SP_CURRENT_VERSION) +
                            "&" + LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE) + "&query=");
                    bun.putString("search", search.getText().toString());
                    intent.putExtras(bun);
                    startActivity(intent);

                }else{

                    ToastHelper.showToast(SearchActivity.this , resources.getString(R.string.search_hint));

                }

            }
        });

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                            if(search.getText().toString().trim().length() > 0) {

                                try {


                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "text");
                                    FlurryAgent.logEvent("Search", mix);

                                } catch (Exception e) {
                                }


                                Bundle bundle = new Bundle();
                                bundle.putString("search_item",search.getText().toString());
                                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);

                                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class );
                                Bundle bun = new Bundle();
                                bun.putString("search", search.getText().toString());
                                intent.putExtras(bun);
                                startActivity(intent);

                            }else{

                                ToastHelper.showToast(SearchActivity.this , resources.getString(R.string.search_hint));

                            }


                            return true;
                        }
                    }
                }
                 return false;

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void getTopSearch(){
        {
            final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantTopSearch,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {

                            MainObject trending = new MainObject();
                            trending.setPostType(TRENDING);
                            trending.setTitle("Popular Search");
                            List<MainItem> list = new ArrayList<>();

                            try {
                            for (int i = 0; i < response.length(); i++){
                                JSONObject obj = new JSONObject();
                                obj = response.getJSONObject(i);
                                MainItem item = new MainItem();
                                item.setTitle(obj.getString("name"));
                                item.setPostType(TRENDING_ITEM);
                                list.add(item);
                            }
                            } catch (JSONException e) {
                                Log.e(TAG, "onResponse:  "  + e.getMessage() );
                                e.printStackTrace();
                            }


                            trending.setItems(list);
                            mainCatDetails.add(trending);

                           universalAdapter.notifyDataSetChanged();
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);


        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mainCatDetails.clear();
        MainObject searched = new MainObject();
        searched.setPostType(SEARCHED);
        searched.setTitle("Your Old Search");
        searched.setItems(databaseAdapter.getMianItemSearch());
        mainCatDetails.add(searched);

        getTopSearch();


        universalAdapter = new UniversalAdapter(SearchActivity.this, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);
        manager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(manager);

        search.setText("");


    }


   /* private void startScan() {
        Intent openCameraIntent = new Intent(SearchActivity.this, CaptureActivity.class);
        openCameraIntent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(openCameraIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            scanResult = bundle.getString("result");
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
            search.requestFocus();



            try {


                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "barcode");
                mix.put("code", scanResult);
                FlurryAgent.logEvent("Search", mix);
            } catch (Exception e) {
            }


            Bundle fbundle = new Bundle();
            fbundle.putString("search_item",search.getText().toString());
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, fbundle);

            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class );
            Bundle bun = new Bundle();
            bun.putString("url",constantSearch+prefs.getIntPreferences(SP_CURRENT_VERSION)+
                    "&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&query=");
            bun.putString("search", scanResult);
            intent.putExtras(bun);
            startActivity(intent);

        }else{

            Toast.makeText(getApplicationContext(), "Scan Result  ERror ", Toast.LENGTH_LONG).show();
        }
    }*/



}
