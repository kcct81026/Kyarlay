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
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityLogin;
import com.kyarlay.ayesunaing.activity.UserPostUploadActivity;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FragmentNew extends Fragment implements Constant, ConstantVariable {

    private static final String TAG = "FragmentNew";

    ProgressBar progressBar;
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

    FloatingActionButton group_chat;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_reading_recycler, container, false);

        mainCatDetails.clear();
        activity        = (AppCompatActivity) getActivity();

        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();



       // new MyFlurry(getActivity());


        display         = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity);
        recyclerView        = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        progressBar         = (ProgressBar) rootView.findViewById(R.id.progressBar1);

        group_chat          = rootView.findViewById(R.id.action_button);



        Log.e(TAG, "onCreateView: "  );

       // group_chat.setText(resources.getString(R.string.wirte_post));





        if (prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);
            group_chat.setVisibility(View.VISIBLE);


            getBrands();
        }else{
            progressBar.setVisibility(View.GONE);
            group_chat.setVisibility(View.GONE);


            ToastHelper.showToast( activity, resources.getString(R.string.no_internet_error));

        }


        prefs.saveIntPerferences(SP_PAGE_REDING_NUM, SP_DEFAULT);


        manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                if(image != null) {
                    image.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                if(image != null) {
                    image.setVisibility(View.GONE);
                }
            }
        });

        mediaAdapter = new MediaAdapter( activity, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mediaAdapter);

        recyclerView.setBackgroundColor(activity.getResources().getColor(R.color.background));

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean scrollUp = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE && scrollUp) {
                    group_chat.setVisibility(View.VISIBLE);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {
                    loading = true;
                    prefs.saveIntPerferences(SP_PAGE_REDING_NUM, prefs.getIntPreferences(SP_PAGE_REDING_NUM) + SP_DEFAULT);
                    mainCategory();
                    //callAds();
                }

            }
        });



        group_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getStringPreferences(SP_USER_TOKEN) == null
                        || prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){

                    prefs.saveIntPerferences(SP_READING_LOGIN, 1);
                    Intent intent   = new Intent(activity, ActivityLogin.class);
                    activity.startActivity(intent);

                }else {
                    prefs.saveIntPerferences(SP_CHANGE, 1);
                    Intent intent = new Intent(activity, UserPostUploadActivity.class);
                    activity.startActivity(intent);
                   // activity.overridePendingTransition(R.anim.slide_up, R.anim.slide_bottom);
                }

            }
        });



        return rootView;
    }



    public void clickRefresh(){

        int position = -1;
        for (int i = 0 ; i < mainCatDetails.size(); i ++){
            if (mainCatDetails.get(i).getPostType().equals(REFRESH_FOOTER)){
                position = i;
            }
        }

        if (position != -1){
            mainCatDetails.remove(position);
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_FOOTER);
            mainCatDetails.add(noitem);
            mediaAdapter.notifyItemInserted(mainCatDetails.size());
            mainCategory();
           // callAds();
        }

    }



    @Override
    public void onResume() {
        super.onResume();

        if(prefs.getIntPreferences(SP_POST_LIKE_COUNT) != 0){
            int id = prefs.getIntPreferences(SP_POST_LIKE_COUNT_ID);
            int index = -1;
            for(int i = 0; i < mainCatDetails.size(); i++){
                Reading reading = (Reading) mainCatDetails.get(i);
                if(id == reading.getId()){
                    index = i;
                    i = mainCatDetails.size();
                }
            }
            if(index != -1){
                mediaAdapter.notifyItemChanged(index);
            }
            prefs.remove(SP_POST_LIKE_COUNT);
            prefs.remove(SP_POST_LIKE_COUNT_ID);
        }

        if(prefs.getIntPreferences(SP_USER_POST_ID) != 0){
            try{
                Reading reading = new Reading();
                reading.setId(prefs.getIntPreferences(SP_USER_POST_ID));
                reading.setLikes(prefs.getIntPreferences(SP_USER_POST_LIKE));
                reading.setComment_coount(prefs.getIntPreferences(SP_USER_POST_COMMENT));
                reading.setRelease_at(prefs.getStringPreferences(SP_USER_POST_RELEASE_AT));
                reading.setBody(prefs.getStringPreferences(SP_USER_POST_BODY));
                reading.setBirth_month(prefs.getIntPreferences(SP_USER_MONOTH));
                reading.setBirth_year(prefs.getIntPreferences(SP_USER_YEAR));
                reading.setCategory_type(prefs.getStringPreferences(SP_USER_BOYORGIRL));
                reading.setSort_by( prefs.getStringPreferences(SP_USER_MOMORDAD));
                reading.setPage_name(prefs.getStringPreferences(SP_USER_NAME));
                reading.setDimen(prefs.getIntPreferences(SP_USER_POST_IMAGE_DIMEN));
                reading.setPhoto_url(prefs.getStringPreferences(SP_USER_POST_IMAGE_URL));
                reading.setCustomer_id(prefs.getIntPreferences(SP_MEMBER_ID));
                reading.setPage_img_url(prefs.getStringPreferences(SP_USER_PROFILEIMAGE));
                reading.setPage_name(prefs.getStringPreferences(SP_USER_NAME));


                reading.setCustomer_type(prefs.getStringPreferences(SP_MEMBER_TYPE));
                reading.setPostType(USER_POST);
                mainCatDetails.add(2, reading);
                mediaAdapter.notifyItemInserted(2);

                prefs.remove(SP_USER_POST_ID);
                prefs.remove(SP_USER_POST_LIKE);
                prefs.remove(SP_USER_POST_COMMENT);
                prefs.remove(SP_USER_POST_RELEASE_AT);
                prefs.remove(SP_USER_POST_BODY);
                manager.scrollToPosition(2);

            }catch (Exception e){
                Log.e(TAG, "onResume: "  + e.getMessage() );
            }

        }


        removeitem();

    }

    public void removeitem(){
        if(mainCatDetails.size() > 1) {

            for(int i = 0; i < mainCatDetails.size() ; i++){
                UniversalPost post = new UniversalPost();
                post = mainCatDetails.get(i);
                if (post.getPostType().equals(signup)) {
                    mainCatDetails.remove(i);
                    mediaAdapter.notifyItemRemoved(i);
                }else if(post.getPostType().equals(posting)) {
                    mainCatDetails.remove(i);
                    mediaAdapter.notifyItemRemoved(i);
                }else if(post.getPostType().equals(feedback)) {
                    mainCatDetails.remove(i);
                    mediaAdapter.notifyItemRemoved(i);
                }else if(post.getPostType().equals(invite)){
                    mainCatDetails.remove(i);
                    mediaAdapter.notifyItemRemoved(i);
                }

            }

        }
    }


    private void getBrands(){



        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantKnowledgeSponsor,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        getBonouosBanner();
                        if(response.length() > 0) {

                            progressBar.setVisibility(View.GONE);

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<MainObject>>() {
                                }.getType();
                                List<MainObject> lists = mGson.fromJson(response.toString(), listType);
                                for (int i = 0 ; i < lists.size(); i ++){
                                    MainObject obj = lists.get(i);
                                    obj.setPostType(BRAND_BANNER);
                                    if(obj.getItems().size() > 0)
                                        mainCatDetails.add(obj);

                                }

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "   + e.getMessage() );
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getBonouosBanner();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }



    public void getBonouosBanner(){



        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                String.format(constantGetPoint,  prefs.getIntPreferences(ConstantVariable.SP_CURRENT_VERSION) + "", prefs.getStringPreferences(SP_USER_PHONE) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) , "posting"), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                mainCategory();
                //callAds();
                loading = false;
                if(response.length() > 0){

                    try {
                        if(response.getInt("status") != 0){
                            Reading reading  = new Reading();
                            reading.setPreview_image_url(response.getString("img_url"));
                            reading.setId(response.getInt("status"));
                            reading.setPostType(response.getString("type"));
                            reading.setDimen(response.getInt("img_dimen"));

                            mainCatDetails.add(reading);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //callAds();
                mainCategory();
                loading = false;


            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }


    private void mainCategory( )
    {
        int page    = prefs.getIntPreferences(SP_PAGE_REDING_NUM);


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantRadingDetail+"&page="+page
                +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION) +"&post_type=reading"  + "&sort=recent",
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);

                        if(response.length() > 0) {
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
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


                                 /*   if(mainCatDetails.size() != 0  && (mainCatDetails.size()  % 6 )== 0){

                                        if (kyarlayAds.getId() != -810){

                                            kyarlayAds.setPostType(ADS);
                                            mainCatDetails.add(kyarlayAds);

                                        }



                                    }*/



                                }

                                Reading pro = new Reading();
                                pro.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(pro);
                                mediaAdapter.notifyItemInserted(mainCatDetails.size());
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }else{
                            loading = true;
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(noitem);


                            mediaAdapter.notifyItemInserted(mainCatDetails.size());

                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                }


                Product noitem = new Product();
                noitem.setPostType(REFRESH_FOOTER);
                mainCatDetails.add(noitem);

                mediaAdapter.notifyItemInserted(mainCatDetails.size());

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /*private void callAds(){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantKyarlayAds + "rectangular" , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                }

                KyarlayAds main = new KyarlayAds();

                if (response.length() > 0){
                    Gson gson = new Gson();
                    main = gson.fromJson(response.toString(), KyarlayAds.class);
                }
                else{
                    main.setId(-810);
                }

                mainCategory(main);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                KyarlayAds main = new KyarlayAds();
                main.setId(-810);
                mainCategory(main);

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }*/



}


