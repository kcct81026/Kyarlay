package com.kyarlay.ayesunaing.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.flurry.android.FlurryAgent;
import com.google.firebase.perf.metrics.Trace;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityLogin;
import com.kyarlay.ayesunaing.activity.NotificationAcitivity;
import com.kyarlay.ayesunaing.activity.SettingActivity;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.PointInfo;
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

public class FragmentProfile extends Fragment implements ConstantVariable, Constant, ConstantsDB {

    private static final String TAG = "FragmentProfile";

    // widget
    CustomTextView title;
    LinearLayout titleLayout, settingLayout;
    LinearLayout noti_layout;
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

    String[] typesArray = {"order", "redeem", "signup", "posting", "invite"};



    private Trace trace;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_profile, container, false);

        activity        = (AppCompatActivity) getActivity();

        prefs            = new MyPreference(getActivity());
        Context context = LocaleHelper.setLocale(getActivity(), prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        databaseAdapter = new DatabaseAdapter(getActivity());
        display = getActivity().getWindowManager().getDefaultDisplay();

       // new MyFlurry(getActivity());

        try {


            //FlurryAgent.logEvent("View Profile Page");
        } catch (Exception e) {
        }

        Log.e(TAG, "onCreateView: "  );


        title = rootview.findViewById(R.id.title);
        titleLayout = rootview.findViewById(R.id.title_layout);
        noti_layout = rootview.findViewById(R.id.noti_layout);
        settingLayout = rootview.findViewById(R.id.settingLayout);
        recyclerView = rootview.findViewById(R.id.recycler_view);
        progressBar = rootview.findViewById(R.id.progressBar1);

        titleLayout.getLayoutParams().width = (display.getWidth() * 13 ) / 20;
        noti_layout.getLayoutParams().width = (display.getWidth() * 3 ) / 20;
        settingLayout.getLayoutParams().width = (display.getWidth() * 3) / 20;
        title.setText(resources.getString(R.string.menu_setting)+"");

        manager = new LinearLayoutManager(activity);
        universalAdapter = new UniversalAdapter( activity, universalPosts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        prefs.saveIntPerferences(SP_CHANGE, 0);

        universalPosts.clear();



        if (prefs.getStringPreferences(SP_USER_TOKEN).equals("") &&
                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0) {

            PointInfo pointInfoUse = new PointInfo();
            pointInfoUse.setName("login");
            pointInfoUse.setPostType(USER_LOGIN);
            universalPosts.add(pointInfoUse);
            progressBar.setVisibility(View.VISIBLE);
            getPointInfo();

        } else {

            if (prefs.isNetworkAvailable()){
                progressBar.setVisibility(View.VISIBLE);
                getCustomerInfo();
            }else{

                ToastHelper.showToast( activity, resources.getString(R.string.no_internet_error));

            }

        }



        noti_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                        prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0) {
                    try {

                        //FlurryAgent.logEvent("Click Notification");
                    } catch (Exception e) {
                    }


                    Intent intent   = new Intent(activity, NotificationAcitivity.class);
                    intent.putExtra("post_type","");
                    activity.startActivity(intent);
                }else{
                    prefs.saveIntPerferences(SP_CHANGE, 1 );
                    Intent intent = new Intent(activity, ActivityLogin.class);
                    startActivity(intent);
                }
            }
        });

        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.saveIntPerferences(SP_CHANGE, 0 );
                Intent intent =  new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });



        return rootview;
    }



    private void getPointInfo()
    {

        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(constantPointInfo + prefs.getIntPreferences(SP_MEMBER_ID),

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);

                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            Type listType = new TypeToken<List<PointInfo>>() {
                            }.getType();
                            List<PointInfo> pointInfoList = mGson.fromJson(response.toString(), listType);
                            ArrayList<PointInfo> earnList = new ArrayList<>();
                            ArrayList<PointInfo> useList = new ArrayList<>();


                            for (int i = 0; i < pointInfoList.size(); i++) {
                                PointInfo pointInfo = pointInfoList.get(i);


                                for (int k=0; k < typesArray.length ; k++){
                                    if (typesArray[k].equals(pointInfo.getName())){

                                        if (pointInfo.getType().equals("use")){
                                            pointInfo.setPostType(USER_LOGIN);
                                            useList.add(pointInfo);
                                        }


                                        else if (pointInfo.getType().equals("earn")){
                                            pointInfo.setPostType(USER_LOGIN);
                                            earnList.add(pointInfo);
                                        }

                                    }
                                }
                            }

                            PointInfo pointInfoUse = new PointInfo();
                            pointInfoUse.setType("use");
                            pointInfoUse.setPostType(POINT_TITLE);
                            universalPosts.add(pointInfoUse);

                            for (int p = 0; p < useList.size() ; p++){
                                universalPosts.add(useList.get(p));
                            }







                            PointInfo pointInfoEarn = new PointInfo();
                            pointInfoEarn.setType("earn");
                            pointInfoEarn.setPostType(POINT_TITLE);
                            universalPosts.add(pointInfoEarn);





                            for (int j = 0; j < earnList.size() ; j++){
                                universalPosts.add(earnList.get(j));
                            }

                            if(!prefs.getStringPreferences(SP_USER_TOKEN).equals("") &&
                                    prefs.getStringPreferences(SP_USER_TOKEN).trim().length() != 0){
                                PointInfo pointMember = new PointInfo();
                                pointMember.setPostType(USER_LOGIN);
                                pointMember.setName("connect_member");
                                universalPosts.add(pointMember);
                            }






                            PointInfo pointInfoUse2 = new PointInfo();
                            pointInfoUse2.setType("space");
                            pointInfoUse2.setPostType(POINT_TITLE);
                            universalPosts.add(pointInfoUse2);

                            universalAdapter.notifyDataSetChanged();




                        }catch (Exception e){
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }




    public void getCustomerInfo(){
        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantGetCustomerInfo, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {



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
                    universalPost.setPostType(USER_PROFILE);
                    universalPosts.add(universalPost);

                    UniversalPost universalPost1 = new UniversalPost();
                    universalPost1.setPostType(ORDER_DELIVERY_STATUS);
                    universalPosts.add(universalPost1);


                    universalAdapter.notifyDataSetChanged();

                    getPointInfo();

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