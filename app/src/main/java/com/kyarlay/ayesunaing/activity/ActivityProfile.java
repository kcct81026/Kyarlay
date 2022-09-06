package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.ShopLocation;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityProfile extends AppCompatActivity implements Constant, ConstantVariable {
    private static final String TAG = "ActivityProfile";

    // widget
    RecyclerView recyclerView ;
    ProgressBar progressBar;

    // vars
    MyPreference prefs;
    Resources resources;
    DatabaseAdapter databaseAdapter ;
    Display display ;

    UniversalAdapter universalAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    AppCompatActivity activity;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_activity_profile);

        activity        = (AppCompatActivity) ActivityProfile.this;

        prefs            = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        databaseAdapter = new DatabaseAdapter(activity);
        display = activity.getWindowManager().getDefaultDisplay();
        new MyFlurry(activity);

        try {

            FlurryAgent.logEvent("View Profile Page");
        } catch (Exception e) {
        }

        Log.e(TAG, "onCreateView: "  );


        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar1);


        manager = new LinearLayoutManager(activity);
        universalAdapter = new UniversalAdapter( activity, universalPosts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        prefs.saveIntPerferences(SP_CHANGE, 0);

        universalPosts.clear();



        if (!prefs.getStringPreferences(SP_USER_TOKEN).equals("") &&
                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() != 0) {

            if (prefs.isNetworkAvailable()){
                progressBar.setVisibility(View.VISIBLE);
                getCustomerInfo();
            }else{

                ToastHelper.showToast(activity, resources.getString(R.string.no_internet_error));

            }
        }

    }




    private JsonArrayRequest shopList()
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantShopLocations + LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            Type listType = new TypeToken<List<ShopLocation>>() {}.getType();
                            List<ShopLocation> shopLocationList = mGson.fromJson(response.toString(), listType);

                            Product pro = new Product();
                            pro.setPostType(DISCOUNT_TITLE);
                            pro.setTitle(resources.getString(R.string.shop_location));
                            universalPosts.add(pro);

                            for (int i=0; i < shopLocationList.size(); i++){
                                shopLocationList.get(i).setPostType(SHOP_LOCATION);
                                universalPosts.add(shopLocationList.get(i));
                            }


                            universalAdapter.notifyItemInserted(universalPosts.size());

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "product_list" );
                    mix.put("status", "error" );

                    FlurryAgent.logEvent("Incoming from Deep Linking", mix);
                } catch (Exception e) {
                }


            }
        });
        return jsonObjReq;
    }

    public void getCustomerInfo(){
        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantGetCustomerInfo, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                progressBar.setVisibility(View.GONE);

                try {
                    prefs.saveStringPreferences(SP_USER_PHONE, response.getString("phone"));
                    prefs.saveStringPreferences(SP_USER_NAME, response.getString("name"));
                    prefs.saveStringPreferences(SP_USER_VIP_ACCOUNT, response.getString("vip"));
                    prefs.saveIntPerferences(SP_USER_MONOTH, response.getInt("birth_month"));
                    prefs.saveIntPerferences(SP_USER_YEAR, response.getInt("birth_year"));
                    prefs.saveStringPreferences(SP_USER_BOYORGIRL, response.getString("kid_gender"));
                    prefs.saveIntPerferences(SP_MEMBER_ID, response.getInt("id"));
                    if(response.getString(("profile_url")) != null){
                        prefs.saveStringPreferences(SP_USER_PROFILEIMAGE, response.getString("profile_url"));
                    }else
                        prefs.saveStringPreferences(SP_USER_PROFILEIMAGE, "");

                    prefs.saveIntPerferences(SP_USER_POINT, response.getInt("gift_points"));

                    if (response.getString("vip").trim().length() == 0) {
                        prefs.saveIntPerferences(SP_VIP_ID, 0);
                    } else {
                        prefs.saveIntPerferences(SP_VIP_ID, 1);

                    }




                    UniversalPost universalPost = new UniversalPost();
                    universalPost.setPostType(USER_PROFILE_NEW);
                    universalPosts.add(universalPost);

                    UniversalPost universalPost1 = new UniversalPost();
                    universalPost1.setPostType(ORDER_DELIVERY_STATUS);
                    universalPosts.add(universalPost1);


                    universalAdapter.notifyDataSetChanged();
                    JsonArrayRequest jsonArrayRequest = shopList();
                    AppController.getInstance().addToRequestQueue(jsonArrayRequest);


                } catch (Exception e) {
                    Log.e(TAG, "onResponse: "  + e.getMessage() );

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "update_profile");
    }
}
