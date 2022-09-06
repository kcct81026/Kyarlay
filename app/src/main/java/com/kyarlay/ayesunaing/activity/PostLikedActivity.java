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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Comment;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostLikedActivity extends AppCompatActivity implements Constant, ConstantVariable{

    private static final String TAG = "PostLikedActivity";

    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    MediaAdapter mediaAdapter;
    RecyclerView recyclerView;
    Boolean loading = true;
    int postId      = 0;
    MyPreference prefs;
    Resources resources;
    RecyclerView.LayoutManager manager;
    LinearLayout back_layout, title_layout;
    ProgressBar progressBar;
    CustomTextView title;
    Display display;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_wishlist);

        prefs   = new MyPreference(PostLikedActivity.this);
        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);
        Context context = LocaleHelper.setLocale(PostLikedActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();
        display = getWindowManager().getDefaultDisplay();

        Log.e(TAG, "onCreate:  "  );

        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar         = (ProgressBar) findViewById(R.id.progressBar1);
        back_layout         = (LinearLayout) findViewById(R.id.back_layout);
        title_layout        = (LinearLayout) findViewById(R.id.title_layout);
        title               = (CustomTextView) findViewById(R.id.title);
        title.setText(resources.getString(R.string.post_liked_person));
        Intent intent = getIntent();
        postId = intent.getIntExtra("post_id", 0);



        title_layout.getLayoutParams().width    = (display.getWidth() * 17 ) / 20;
        back_layout.getLayoutParams().width    = (display.getWidth() * 3 ) / 20;
        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        manager = new LinearLayoutManager(PostLikedActivity.this);
        recyclerView.setLayoutManager(manager);



        mediaAdapter = new MediaAdapter(PostLikedActivity.this, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mediaAdapter);
        getLikes();

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
                    prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL) + SP_DEFAULT);
                    getLikes();
                }

            }
        });




    }

    private void getLikes()
    {


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantPostLiked+postId+"&page="+prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);
                        if(response.length() > 0){
                            //universalListComment.clear();
                            loading = false;
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Comment>>() {
                                }.getType();
                                List<Comment> categoryList = mGson.fromJson(response.toString(), listType);

                                for (int i = 0 ; i < categoryList.size(); i ++){
                                    Comment comment = categoryList.get(i);
                                    comment.setCreated_at(getDateInMillis(comment.getCreated_at()));
                                    comment.setPostType(READING_LIKED);
                                    mainCatDetails.add( comment);
                                    mediaAdapter.notifyItemInserted(mainCatDetails.size());
                                }


                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }else {

                            loading = true;
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(noitem);
                            mediaAdapter.notifyItemInserted(mainCatDetails.size());
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                ToastHelper.showToast(PostLikedActivity.this, resources.getString(R.string.search_error));



            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    public String getDateInMillis(String srcDate) throws ParseException {
        Calendar cal =  Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            cal.setTime(sdf.parse(srcDate));
            Calendar calendar = Calendar.getInstance();
            long now = calendar.getTimeInMillis();
            long time = cal.getTimeInMillis();

            long diff = now - time;

            int seconds = (int) (diff / 1000) % 60 ;
            int minutes = (int) ((diff / (1000*60)) % 60);
            int hours   = (int) ((diff / (1000*60*60)) % 24);
            int days = (int) (diff / (1000*60*60*24));

            if(days == 0 && hours ==0 && minutes ==0 && seconds > 0){
                return  seconds +" sec ago";
            }else if(days == 0 && hours ==0 && minutes > 0){
                return  minutes+" min ago";
            }else if(days == 0 && hours > 0){
                return hours+" hour ago";
            }else if(days > 0 ){
                //return days+" day and "+hours+" hour and "+minutes+" min and "+seconds+" sec ago";
                return days+" day ago";
            }else if(days > 7){
                return srcDate;
            }else {
                return "";
            }


        } catch (ParseException e1) {
            e1.printStackTrace();
            Log.e(TAG, "getDateInMillis: "  + e1.getMessage() );
            return "exception";
        }
    }

}
