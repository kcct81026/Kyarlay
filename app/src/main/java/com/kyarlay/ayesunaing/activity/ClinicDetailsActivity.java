package com.kyarlay.ayesunaing.activity;

import android.content.Context;
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
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Clicnic;
import com.kyarlay.ayesunaing.object.ClinicDetails;
import com.kyarlay.ayesunaing.object.Images;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.PackageObject;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClinicDetailsActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "ClinicDetailsActivity";
    
    // widgets
    LinearLayout back_layout;
    Boolean loading=true;
    RecyclerView.LayoutManager manager;

    MyPreference prefs;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    MediaAdapter mediaAdapter;
    DatabaseAdapter databaseAdapter;
    RecyclerView recyclerView ;

    Display display ;
    Resources resources;
    AppCompatActivity activity;
    String tag = "";
    ProgressBar progressBar;

    int id;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_click);

        activity =  ClinicDetailsActivity.this;
        display = activity.getWindowManager().getDefaultDisplay();

        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        databaseAdapter = new DatabaseAdapter(activity);
        mediaAdapter = new MediaAdapter((AppCompatActivity) activity, mainCatDetails);
        new MyFlurry(ClinicDetailsActivity.this);

        id = getIntent().getIntExtra("id",0);

        Log.e(TAG, "onCreate: " );

        Map<String, String> mix = new HashMap<String, String>();
        mix.put("view_directory", prefs.getStringPreferences(ConstantVariable.SP_DIRECTORY_CLICK));
        mix.put("id", String.valueOf(id));
        FlurryAgent.logEvent("", mix);

        back_layout = findViewById(R.id.back_layout);
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar        = (ProgressBar) findViewById(R.id.progress_bar);


        prefs.saveIntPerferences(ConstantVariable.SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mediaAdapter);


        if (prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);

            mainPageItem();

        }else{
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_NO_ITEM);
            mainCatDetails.add(noitem);

        }

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    private void mainPageItem()
    {
        String url = "";

        if (prefs.getStringPreferences(ConstantVariable.SP_DIRECTORY_CLICK).equals("clinic_directory"))
            url = constantclinic;
        else if (prefs.getStringPreferences(ConstantVariable.SP_DIRECTORY_CLICK).equals("schools"))
            url = constantschools;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,url + "/" + id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        progressBar.setVisibility(View.GONE);

                        if(response.length() > 0) {
                            if (mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)) {
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }


                            try {

                                Gson gson = new Gson();
                                ClinicDetails main = gson.fromJson(response.toString(), ClinicDetails.class);

                                Clicnic clicnic = new Clicnic() ;
                                clicnic.setPhone(main.getPhone());
                                clicnic.setAddress(main.getAddress());
                                clicnic.setName(main.getName());
                                clicnic.setDesc(main.getDesc());
                                clicnic.setPreview_photo_url("");
                                clicnic.setPostType(CLINIC_ITEM);
                                mainCatDetails.add(clicnic);

                                if (main.getImages().size() > 0){
                                    List<MainItem> sliderAds = new ArrayList<>();

                                    for (int j = 0; j < main.getImages().size(); j++) {
                                        Images images = main.getImages().get(j);
                                        MainItem item = new MainItem();
                                        item.setId(images.getId());
                                        item.setPreview_url(images.getUrl());
                                        item.setDimen(images.getDimen());
                                        item.setPost_type("image");

                                        sliderAds.add(item);
                                    }

                                    MainObject mainObject = new MainObject();
                                    mainObject.setPostType(SLIDER);
                                    mainObject.setItems(sliderAds);


                                    mainCatDetails.add(mainObject);

                                }


                                if (main.getPackageObjectList().size() > 0){
                                    for (int i = 0 ; i < main.getPackageObjectList().size(); i++){
                                        PackageObject images = main.getPackageObjectList().get(i);
                                        images.setPostType(PACKAGE_ITEM);
                                        mainCatDetails.add(images);


                                    }
                                }


                                Reading pro2 = new Reading();
                                pro2.setPostType(CART_DETAIL_NO_ITEM);
                                mainCatDetails.add(pro2);

                                mediaAdapter.notifyItemInserted(mainCatDetails.size());


                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }

                        }}

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "onErrorResponse: error "  + error.getMessage() );

                            ToastHelper.showToast(ClinicDetailsActivity.this, resources.getString(R.string.search_error));

                        }
                    });


        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }







}
