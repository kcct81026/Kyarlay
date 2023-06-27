package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.SelectableRoundedImageView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.ClickeListener;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MainGridItemClickListener;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainSuperActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "MainSuperActivity";

    ArrayList<UniversalPost> universalPostsOne = new ArrayList<>();
    ArrayList<UniversalPost> universalPostsTwo = new ArrayList<>();

    RecyclerView recyclerViewOne ;
    RecyclerView recyclerViewTwo ;
    UniversalAdapter universalAdapterOne;
    UniversalAdapter universalAdapterTwo;


    ImageLoader imageLoader;



    MyPreference prefs;
    Resources resources;
    RecyclerView.LayoutManager managerOne;
    GridLayoutManager managerTwo;
    Boolean loading = true;


    Display display;
    //int brandID, catID = 1;
    ImageView imgSearch;

    ProgressBar progress_bar;


    DatabaseAdapter databaseAdapter;
    //String fromClass = "";


    AppCompatActivity activity;
    SelectableRoundedImageView img;
    LinearLayout linear;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_two_recyclerview);

        activity  = MainSuperActivity.this;
        databaseAdapter = new DatabaseAdapter(MainSuperActivity.this);

       // new MyFlurry(MainSuperActivity.this);
        prefs = new MyPreference(MainSuperActivity.this);
        Context context = LocaleHelper.setLocale(MainSuperActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //brandID = prefs.getIntPreferences(BRAND_ID);
        imageLoader         = AppController.getInstance().getImageLoader();

      /*  fromClass = bundle.getString("fromClass");
        catID = bundle.getInt("id");*/

       // prefs.saveIntPerferences(SUPER_ID, catID);
        display = getWindowManager().getDefaultDisplay();

        Log.e(TAG, "onCreate: "  );


        imgSearch = findViewById(R.id.imgSearch);
        img = findViewById(R.id.img);
        linear = findViewById(R.id.linear);

        recyclerViewOne = (RecyclerView) findViewById(R.id.recyclerOne);
        recyclerViewTwo = (RecyclerView) findViewById(R.id.recyclerTwo);

        progress_bar = findViewById(R.id.progress_bar);




        recyclerViewOne.getLayoutParams().width     = ( display.getWidth()) / 3;
        //linear.getLayoutParams().width     = ( display.getWidth() * 3) / 4;


        universalAdapterOne= new UniversalAdapter(MainSuperActivity.this, universalPostsOne);
        managerOne = new GridLayoutManager(activity, 1, RecyclerView.VERTICAL, false);
        recyclerViewOne.setLayoutManager(managerOne);
        recyclerViewOne.setHasFixedSize(true);
        recyclerViewOne.setAdapter(universalAdapterOne);


        universalAdapterTwo= new UniversalAdapter(MainSuperActivity.this, universalPostsTwo);
        managerTwo = new GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false);
        recyclerViewTwo.setLayoutManager(managerTwo);
        recyclerViewTwo.setHasFixedSize(true);
        recyclerViewTwo.setAdapter(universalAdapterTwo);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "main");
                    //FlurryAgent.logEvent("Click Search", mix);
                } catch (Exception e) {
                    Log.e(TAG, "onClick: "  + e.getMessage() );
                }
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fate_out);
            }
        });

        recyclerViewOne.addOnItemTouchListener(new MainGridItemClickListener(activity,
                recyclerViewOne, universalPostsOne, new ClickeListener() {
            @Override
            public void onClick(View view, int position) {

                if (universalPostsOne.get(position).getPostType().equals(MASTER_LEFT_ITEM)){

                    CategoryMain categoryMain = (CategoryMain) universalPostsOne.get(position);
                    prefs.saveIntPerferences(SUPER_ID, categoryMain.getId());

                    img.setImageUrl(categoryMain.getHeader_image(), imageLoader);

                    universalPostsTwo.clear();
                    Product pro2 = new Product();
                    pro2.setPostType(CART_DETAIL_FOOTER);
                    universalPostsTwo.add(pro2);
                    universalAdapterTwo.notifyDataSetChanged();
                    mainSubCategories();
                    universalAdapterOne.notifyDataSetChanged();


                }
            }
            @Override
            public void onLongClick(View view, int position) {
            }

        }));




        if (prefs.isNetworkAvailable()){
            mainLeftCategory();

        }
        else{

            ToastHelper.showToast(MainSuperActivity.this, resources.getString(R.string.no_internet_error));

        }




    }


    private void mainLeftCategory()
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantSuperCategories  +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progress_bar.setVisibility(View.GONE);
                        recyclerViewOne.setVisibility(View.VISIBLE);



                        if(response.length() > 0) {


                            if(universalPostsOne.size() != 0 && universalPostsOne.get(universalPostsOne.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPostsOne.remove(universalPostsOne.size() - 1);
                            }


                            ArrayList<CategoryMain> categoryList = new ArrayList<>();
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<CategoryMain>>() {}.getType();
                                categoryList = mGson.fromJson(response.toString(), listType);



                                if (categoryList.size() > 0){
                                    linear.setVisibility(View.VISIBLE);


                                    for (int i=0; i < categoryList.size(); i++){
                                        CategoryMain categoryMain = categoryList.get(i);
                                        categoryMain.setPostType(MASTER_LEFT_ITEM);
                                        universalPostsOne.add(categoryMain);

                                        if (i == 0 ){
                                            img.setImageUrl(categoryMain.getHeader_image(), imageLoader);
                                            prefs.saveIntPerferences(SUPER_ID, categoryMain.getId());
                                        }

                                    }
                                }

                                universalAdapterOne.notifyItemInserted(universalPostsOne.size());

                            }catch (Exception e){

                                Log.e(TAG, "onResponse: "   + e.getMessage() );
                            }

                            mainSubCategories();


                        }




                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


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

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void mainSubCategories()
    {
        Log.e(TAG, "mainSubCategories: "  + constantSuperCategories + "/" + prefs.getIntPreferences(SUPER_ID) +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)  );

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantSuperCategories + "/" + prefs.getIntPreferences(SUPER_ID) +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {



                        progress_bar.setVisibility(View.GONE);
                        recyclerViewTwo.setVisibility(View.VISIBLE);



                        if(universalPostsTwo.size() != 0 && universalPostsTwo.get(universalPostsTwo.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPostsTwo.remove(universalPostsTwo.size() - 1);
                        }


                        if(response.length() > 0) {

                           
                            ArrayList<CategoryMain> categoryList = new ArrayList<>();
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<CategoryMain>>() {}.getType();
                                categoryList = mGson.fromJson(response.toString(), listType);



                                if (categoryList.size() > 0){


                                    for (int i=0; i < categoryList.size(); i++){
                                        CategoryMain categoryMain = categoryList.get(i);
                                        categoryMain.setPostType(SUPER_CATEGORY_ITEM);
                                        universalPostsTwo.add(categoryMain);

                                    }
                                }

                                universalAdapterTwo.notifyItemInserted(universalPostsTwo.size());


                                loading = false;


                            }catch (Exception e){

                                Log.e(TAG, "onResponse: "   + e.getMessage() );
                            }


                        }
                        else{
                            Product pro1 = new Product();
                            pro1.setPostType(CART_DETAIL_FOOTER);
                            universalPostsTwo.add(pro1);
                            universalAdapterTwo.notifyItemInserted(universalPostsTwo.size());
                        }





                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = true;


                // mainPageItem();

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

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

}
