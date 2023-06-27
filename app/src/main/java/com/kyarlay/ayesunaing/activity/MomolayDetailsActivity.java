package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.perf.metrics.Trace;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.EditTextBackPressed;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.KyarlayAds;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.Reading_Post;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ayesu on 12/22/17.
 */

public class MomolayDetailsActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "MomolayDetailsActivity";

    // vars
    Reading reading;
    String status = "";
    String pageUrl = "";
    MediaAdapter mediaAdapter;
    ArrayList<UniversalPost> universalListPost = new ArrayList<>();
    MyPreference prefs;
    Resources resources;
    Display display;
    FirebaseAnalytics firebaseAnalytics;

    Boolean loading = true;
    RecyclerView.LayoutManager manager;

    // widgets
    RecyclerView recyclerView;
    LinearLayout  back_layout;
    EditTextBackPressed message_text;
    ImageView send, imgComment, imgRemove, imgCamera;
    RelativeLayout relativeComment;
    ProgressBar progressBar;

    CardView cardView;
    LinearLayout edit_layout, linearSend, linearCamera;

    boolean backpressed ;

    LinearLayout header;
    CustomTextView header_title, view_more;

    static Util util;
    static TransferUtility transferUtility;

    private Trace trace;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment_details_activity);

        Log.e(TAG, "onCreate: " );

        firebaseAnalytics   = FirebaseAnalytics.getInstance(MomolayDetailsActivity.this);
        prefs   = new MyPreference(MomolayDetailsActivity.this);
        Context context = LocaleHelper.setLocale(MomolayDetailsActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();
        display          = getWindowManager().getDefaultDisplay();


        util = new Util();
        transferUtility = util.getTransferUtility(this);


        progressBar         = (ProgressBar) findViewById(R.id.progressBar1);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);

        recyclerView    = (RecyclerView) findViewById(R.id.recycler_view);
        message_text = findViewById(R.id.message_text);
        send = findViewById(R.id.send);
        cardView = findViewById(R.id.product_detail_adapter_footer_layout);
        edit_layout = findViewById(R.id.edit_layout);
        header              = (LinearLayout) findViewById(R.id.header);
        header_title        = (CustomTextView) findViewById(R.id.header_title);
        view_more           = (CustomTextView) findViewById(R.id.view_more);

        relativeComment = findViewById(R.id.relativeComment);
        imgComment = findViewById(R.id.imgComment);
        imgRemove = findViewById(R.id.imgCommentRemove);
        imgCamera = findViewById(R.id.imgCamera);
        linearCamera = findViewById(R.id.linearCamera);
        linearSend = findViewById(R.id.linearSend);


        linearCamera.setVisibility(View.GONE);
        relativeComment.setVisibility(View.GONE);

        recyclerView.setPadding(0,0,0,0);

       // new MyFlurry(MomolayDetailsActivity.this);


        cardView.setVisibility(View.GONE);

        backpressed = false;

        reading = new Reading();
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        reading.setId(bundle.getInt("id"));
        reading.setSort_by(status);

        prefs.saveIntPerferences(SP_PAGE_READ_COMMENT, SP_DEFAULT);


        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        mediaAdapter        = new MediaAdapter(MomolayDetailsActivity.this, universalListPost);
        recyclerView.setAdapter(mediaAdapter);

        try {


            Map<String, String> mix = new HashMap<String, String>();
            mix.put("type", "read_post");
            mix.put("source", "post_detail");
            mix.put("post_id",String.valueOf( reading.getId()));
            mix.put("status", status);
            //FlurryAgent.logEvent("View Post", mix);
        } catch (Exception e) {
        }


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });



        header.setVisibility(View.GONE);
        if(prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);
            //richTextDetail(String.format(constantMomolayDetails, reading.getId() + ""));
            callAds(String.format(constantMomolayDetails, reading.getId() + ""));

        }else{
            progressBar.setVisibility(View.GONE);


            ToastHelper.showToast(MomolayDetailsActivity.this, resources.getString(R.string.no_internet_error));

        }



    }

    private void callAds(String str){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantKyarlayAds + "rectangular" , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if(universalListPost.size() != 0 && universalListPost.get(universalListPost.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalListPost.remove(universalListPost.size() - 1);
                }
                Log.e(TAG, "onResponse: ************ "  + response.toString() );
                KyarlayAds main = new KyarlayAds();

                if (response.length() > 0){
                    Gson gson = new Gson();
                    main = gson.fromJson(response.toString(), KyarlayAds.class);
                }
                else{
                    main.setId(-810);
                }

                richTextDetail(main, str);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                KyarlayAds main = new KyarlayAds();
                main.setId(-810);
                richTextDetail(main, str);

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }



    public void richTextDetail(KyarlayAds kyarlayAds, String richtext_url) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                richtext_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if(response.length() > 0) {
                    universalListPost.clear();
                    progressBar.setVisibility(View.GONE);

                    try {
                        Gson gson = new Gson();
                        Reading main = gson.fromJson(response.toString(), Reading.class);
                        if(main.getPostType().equals(USER_POST)){
                            main.setPostType(USER_POST_READING_DETAILS);
                        }else{
                            main.setPostType(READING_DETAIL);
                        }



                        try{
                            if (main.getPage_img_url().trim().length() <= 0){
                                main.setPage_img_url(pageUrl);
                            }

                        }catch (Exception e){
                            Log.e(TAG, "onResponse: reading page profile exception "  + e.getMessage() );
                        }

                        universalListPost.add(main);

                        for(int i = 0; i < main.getPosts().size(); i ++){
                            Reading_Post post = new Reading_Post();
                            post = main.getPosts().get(i);
                            post.setPostType(post.getPost_type());
                            universalListPost.add(post);



                            if (i == 0){
                                if (kyarlayAds.getId() != -810){

                                    kyarlayAds.setPostType(ADS);
                                    universalListPost.add(kyarlayAds);

                                }
                            }

                            if (i == 2){
                                if (kyarlayAds.getId() != -810){

                                    kyarlayAds.setPostType(ADS);
                                    universalListPost.add(kyarlayAds);

                                }
                            }

                            if (i == 4){
                                if (kyarlayAds.getId() != -810){

                                    kyarlayAds.setPostType(ADS);
                                    universalListPost.add(kyarlayAds);

                                }
                            }


                        }




                        mediaAdapter.notifyDataSetChanged();

                        try {


                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("post_id",String.valueOf( reading.getId()));
                            //FlurryAgent.logEvent("View Comment Page", mix);
                        } catch (Exception e) {
                        }


                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: "  + e.getMessage() );
                        e.printStackTrace();

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });

        AppController.getInstance().addToRequestQueue(request);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }






}

