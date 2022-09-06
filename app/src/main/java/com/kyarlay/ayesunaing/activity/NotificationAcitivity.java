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
import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationAcitivity extends AppCompatActivity implements ConstantVariable, Constant{

    private static final String TAG = "NotificationAcitivity";

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
    String post_type = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        databaseAdapter = new DatabaseAdapter(NotificationAcitivity.this);
        display         = getWindowManager().getDefaultDisplay();

        new MyFlurry(NotificationAcitivity.this);


        try {

            FlurryAgent.logEvent("View Product Wishlist");
        } catch (Exception e) {}

        prefs  = new MyPreference(NotificationAcitivity.this);
        Context context1 = LocaleHelper.setLocale(NotificationAcitivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context1.getResources();

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle != null) {
            fromCalss = bundle.getString("from_class");

            post_type = bundle.getString("post_type");
        }
        adapter = new UniversalAdapter( NotificationAcitivity.this, universalPosts);
        setContentView(R.layout.noti_layout);
        title_layout        = (LinearLayout) findViewById(R.id.title_layout);
        back_layout         = (LinearLayout) findViewById(R.id.back_layout);
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);
        title               = (CustomTextView) findViewById(R.id.title);
        progressBar1        = (ProgressBar) findViewById(R.id.progressBar1);


        Log.e(TAG, "onCreate: "  + post_type);

        title.setText(resources.getString(R.string.notification));

        back_layout.getLayoutParams().width    = (display.getWidth() * 3 ) / 20;
        title_layout.getLayoutParams().width    = (display.getWidth() * 16 ) / 20;
        manager = new LinearLayoutManager(NotificationAcitivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);

        getNotificationList();

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromCalss != null && !fromCalss.equals("")){
                    prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
                    Intent intent = new Intent(NotificationAcitivity.this, MainActivity.class);
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
                    prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL) + SP_DEFAULT);
                    getNotificationList();
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
            getNotificationList();
        }

    }




    @Override
    public void onBackPressed() {

        if(fromCalss != null &&  !fromCalss.equals("")){
            prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
            Intent intent = new Intent(NotificationAcitivity.this, MainActivity.class);
            startActivity(intent);
        }
        super.onBackPressed();
    }


    private void getNotificationList()
    {



        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(String.format(constantNotification,prefs.getIntPreferences(SP_MEMBER_ID), prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL), post_type) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {




                        progressBar1.setVisibility(View.GONE);
                        if(response.length() > 0){
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            loading = false;
                            for(int i = 0; i < response.length(); i++){
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Reading reading = new Reading();
                                    reading.setTitle(obj.getString("title"));
                                    reading.setBody(obj.getString("body"));
                                    reading.setId(obj.getInt("post_id"));
                                    reading.setLikes(obj.getInt("likes_count"));
                                    reading.setComment_coount(obj.getInt("comments_count"));
                                    reading.setSort_by(getDateInMillis(obj.getString("updated_at")));
                                    reading.setUrl(obj.getString("post_type"));

                                    reading.setPostType(NOTIFICATION);
                                    universalPosts.add(reading);
                                    adapter.notifyDataSetChanged();

                                }catch (Exception e){
                                    Log.e(TAG, "onResponse:  "   + e.getMessage() );
                                }
                            }
                            List<MainItem> getNoti = new ArrayList<>();
                            getNoti = databaseAdapter.getAllNotification();
                            for(int i = 0; i < getNoti.size(); i++){
                                int findIndex = 0;
                                for(int j = 0 ; j < universalPosts.size(); j ++){
                                    Reading reading = (Reading) universalPosts.get(j);
                                    if(getNoti.get(i).getId() == reading.getId() ){
                                        findIndex = 1;
                                    }
                                }
                                if(findIndex == 0){
                                    databaseAdapter.deleteNotification(getNoti.get(i).getId());
                                }
                            }

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_FOOTER);
                            universalPosts.add(noitem);

                            adapter.notifyDataSetChanged();
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


                adapter.notifyDataSetChanged();

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
            }else if(days > 0  ){
                //return days+" day and "+hours+" hour and "+minutes+" min and "+seconds+" sec ago";
                return days+" day ago";
            }else if(days > 7){
                return srcDate;
            }else {
                return "";
            }


        } catch (ParseException e1) {
            e1.printStackTrace();
            Log.e(TAG, "getDateInMillis:  "  + e1.getMessage() );
            return "exception";
        }


    }

}
