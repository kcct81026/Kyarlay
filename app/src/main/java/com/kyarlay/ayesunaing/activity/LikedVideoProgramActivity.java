package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
//import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikedVideoProgramActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "LikedVideoProgramActivi";

    RecyclerView recyclerView;
    UniversalAdapter universalAdapter;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    RecyclerView.LayoutManager manager;
    DatabaseAdapter databaseAdapter;

    MyPreference prefs;
    CustomTextView no_list;
    Resources resources;

    LinearLayout title_layout, back_layout;
    RelativeLayout cart_layout;
    CircularTextView cart_text;
    CustomTextView title;

    Display display;
    Boolean loading = true;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fcm_list_layout);

       // new MyFlurry(LikedVideoProgramActivity.this);


        Log.e(TAG, "onCreate: " );

        try {


            Map<String, String> mix = new HashMap<String, String>();
            //FlurryAgent.logEvent("View Product Wishlist", mix);
        } catch (Exception e) {}

        prefs  = new MyPreference(LikedVideoProgramActivity.this);
        Context context1 = LocaleHelper.setLocale(LikedVideoProgramActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context1.getResources();
        databaseAdapter = new DatabaseAdapter(LikedVideoProgramActivity.this);

        display = getWindowManager().getDefaultDisplay();
        no_list  = (CustomTextView) findViewById(R.id.no_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        title            = (CustomTextView) findViewById(R.id.title);


        cart_layout.setVisibility(View.GONE);

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "wishlist");
                    //FlurryAgent.logEvent("Click Shopping Cart", mix);

                } catch (Exception e) {
                }

                Intent intent = new Intent(LikedVideoProgramActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 14) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LikedVideoProgramActivity.this.finish();
            }
        });
        title.setText(resources.getString(R.string.liked_video)+"");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){
            productCount();
            postCount();

            cart_text.setStrokeWidth(1);
            cart_text.setStrokeColor("#000000");
            cart_text.setSolidColor("#ffffff");
            int count = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);

            if (count == 0) {
                cart_text.setVisibility(View.GONE);
            } else {
                cart_text.setVisibility(View.VISIBLE);
                cart_text.setText(count + "");
            }
            manager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(manager);
            universalAdapter = new UniversalAdapter(LikedVideoProgramActivity.this, mainCatDetails);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(universalAdapter);

            getLikeVideoProgram();
        }else{
            Intent intent   = new Intent(LikedVideoProgramActivity.this, ActivityLogin.class);
            startActivity(intent);
            finish();
        }


    }


    public void productCount()
    {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,  constantProductCount, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            prefs.saveIntPerferences(SP_PRODUCT_LIKED_COUNT ,response.getInt("count"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " +e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

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

        AppController.getInstance().addToRequestQueue(jsonObjReq,"sign_in");
    }


    private void postCount()
    {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,  constantPostCount,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            prefs.saveIntPerferences(SP_POST_LIKED_COUNT ,response.getInt("count"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " +e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

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

        AppController.getInstance().addToRequestQueue(jsonObjReq,"sign_in");
    }



    private void getLikeVideoProgram()

    {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(constantVideoLikes,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        mainCatDetails.clear();
                        if(response.length() > 0) {

                            no_list.setVisibility(View.GONE);
                            loading = false;
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Reading>>() {
                                }.getType();
                                List<Reading> categoryList = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < categoryList.size(); i++) {
                                    Reading pro = new Reading();
                                    pro = categoryList.get(i);
                                    mainCatDetails.add(pro);


                                    databaseAdapter.insertProductLike(pro.getId(), "like");
                                }

                                universalAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " +e.getMessage() );
                            }


                        }else{
                            loading = true;
                            recyclerView.setVisibility(View.GONE);
                            Context context = LocaleHelper.setLocale(LikedVideoProgramActivity.this, prefs.getStringPreferences(LANGUAGE));
                            Resources resources = context.getResources();
                            no_list.setText(resources.getString(R.string.no_list));
                            no_list.setTextSize(12);

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = true;
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                    Product pro1 = new Product();
                    pro1.setPostType(CART_DETAIL_NO_ITEM);
                    mainCatDetails.add(pro1);
                    universalAdapter.notifyDataSetChanged();
                }


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


        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LikedVideoProgramActivity.this.finish();

    }


}