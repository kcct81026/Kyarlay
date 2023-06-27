package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.QAObject;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AskProdcutAcitivity extends AppCompatActivity implements ConstantVariable, Constant{

    private static final String TAG = "AskProdcutAcitivity";

    MyPreference prefs;
    Resources resources;
    Display display ;


    CustomTextView title;
    LinearLayout title_layout, back_layout;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    UniversalAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ProgressBar progressBar1;
    boolean loading     = true;
    DatabaseAdapter databaseAdapter;
    String fromCalss = "";
    int product_id ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        databaseAdapter = new DatabaseAdapter(AskProdcutAcitivity.this);
        display         = getWindowManager().getDefaultDisplay();


       // new MyFlurry(AskProdcutAcitivity.this);

        try {

            Map<String, String> userParams = new HashMap<String, String>();
            //FlurryAgent.logEvent("View Q&A About Product Page", userParams);
        } catch (Exception e) {}

        prefs  = new MyPreference(AskProdcutAcitivity.this);
        Context context1 = LocaleHelper.setLocale(AskProdcutAcitivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context1.getResources();

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle != null) {
            fromCalss = bundle.getString("fromCalss");
            product_id = bundle.getInt("product_id",0);
        }


        adapter = new UniversalAdapter( AskProdcutAcitivity.this, universalPosts);
        setContentView(R.layout.noti_layout);
        title_layout        = (LinearLayout) findViewById(R.id.title_layout);
        back_layout         = (LinearLayout) findViewById(R.id.back_layout);
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);
        title               = (CustomTextView) findViewById(R.id.title);
        progressBar1        = (ProgressBar) findViewById(R.id.progressBar1);


        Log.e(TAG, "onCreate: " );



        back_layout.getLayoutParams().width    = (display.getWidth() * 3 ) / 20;
        title_layout.getLayoutParams().width    = (display.getWidth() * 16 ) / 20;
        manager = new LinearLayoutManager(AskProdcutAcitivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);
        prefs.saveStringPreferences(SP_PRODUCT_NOTIFICATION, "");

        getQuestions();

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fromCalss.equals(FROM_FIREBASE)){
                    prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
                    Intent intent = new Intent(AskProdcutAcitivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                    finish();

            }
        });

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {
                    loading = true;

                    getQuestions();
                }

            }
        });


    }

    public void clickRefresh(){

        int position = -1;
        for (int i = 0 ; i < universalPosts.size(); i ++){
            if (universalPosts.get(i).getPostType().equals(REFRESH_FOOTER)){
                position = i;
            }
        }

        if (position != -1){
            universalPosts.remove(position);
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_FOOTER);
            universalPosts.add(noitem);
            adapter.notifyItemInserted(universalPosts.size());
            getQuestions();
        }

    }


    private void getQuestions()
    {
        String url ="" ;

        if (fromCalss.equals("question")) {
            title.setText("");
            url = constantProductQuesSearchID + product_id + "&page=" + prefs.getIntPreferences(ConstantVariable.SP_PAGE_NUM_CARTDETAIL);
        }

        else if (fromCalss.equals("fromProfile")){
            title.setText(resources.getString(R.string.ask_product));
            url = constantProductQuestions + "?page="  + prefs.getIntPreferences(ConstantVariable.SP_PAGE_NUM_CARTDETAIL);
        }

        else if (fromCalss.equals(FROM_FIREBASE)){
            title.setText(resources.getString(R.string.ask_product));
            url = constantProductQuestions + "?page="  + prefs.getIntPreferences(ConstantVariable.SP_PAGE_NUM_CARTDETAIL);
        }

        url = url + "&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) ;


        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {



                        progressBar1.setVisibility(View.GONE);
                        if(response.length() > 0){
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL) + SP_DEFAULT);

                            loading = false;

                            try {

                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<QAObject>>() {
                                }.getType();
                                List<QAObject> products = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < products.size(); i++) {

                                    QAObject qaObject = new QAObject();
                                    qaObject = products.get(i);

                                    if (fromCalss.equals("question")) {
                                        qaObject.setPostType(QA_PRODUCT_ITEM);
                                    }

                                    else if (fromCalss.equals("fromProfile")){
                                        qaObject.setPostType(QA_ITEM);
                                    }
                                    else if (fromCalss.equals(FROM_FIREBASE)){
                                        qaObject.setPostType(QA_ITEM);

                                    }



                                    universalPosts.add(qaObject);

                                }



                                Product noitem = new Product();
                                noitem.setPostType(CART_DETAIL_FOOTER);
                                universalPosts.add(noitem);

                                adapter.notifyDataSetChanged();
                            }catch (Exception e) {
                                Log.e(TAG, "onResponse: Exception :  "   + e.getMessage()  );
                            }



                        }else{
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            loading = true;
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }

                progressBar1.setVisibility(View.GONE);

                Product noitem = new Product();
                noitem.setPostType(REFRESH_FOOTER);
                universalPosts.add(noitem);


                adapter.notifyItemInserted(universalPosts.size());

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

        if(fromCalss.equals(FROM_FIREBASE)){
            prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
            Intent intent = new Intent(AskProdcutAcitivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        else
            finish();


    }
}
