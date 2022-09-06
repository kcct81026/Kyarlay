package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.InviteHistory;
import com.kyarlay.ayesunaing.object.Product;
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

public class InviteFriendActivity extends AppCompatActivity implements ConstantVariable, Constant, ConstantsDB {

    private static final String TAG = "InviteFriendActivity";

    // widget
    CustomTextView title;
    LinearLayout back_layout;
    RecyclerView recyclerView ;
    ProgressBar progressBar;
    CustomTextView btnInvite;

    // vars
    MyPreference prefs;
    Resources resources;
    DatabaseAdapter databaseAdapter ;
    Display display ;

    UniversalAdapter universalAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    AppCompatActivity activity;
    Boolean loading=true;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friend_invite);

        Log.e(TAG, "onCreate: " );

        activity        = (AppCompatActivity) InviteFriendActivity.this;
        prefs            = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        databaseAdapter = new DatabaseAdapter(activity);
        display = activity.getWindowManager().getDefaultDisplay();

        new MyFlurry(InviteFriendActivity.this);
        prefs.saveIntPerferences(SP_PAGE_REDING_NUM, SP_DEFAULT);



        try {

            Map<String, String> mix = new HashMap<String, String>();
            mix.put("type", "day_calendar");
            FlurryAgent.logEvent("", mix);

        } catch (Exception e) {
        }


        title = findViewById(R.id.title);
        btnInvite = findViewById(R.id.btnInvite);
        back_layout = findViewById(R.id.back_layout);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar1);


        title.setText(resources.getString(R.string.invite)+"");

        manager = new LinearLayoutManager(activity);
        universalAdapter = new UniversalAdapter( activity, universalPosts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnInvite.setText(resources.getString(R.string.invite));
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("click", "invite_button");
                    FlurryAgent.logEvent("InviteFriend Button Click", mix);

                } catch (Exception e) {
                }
                sendFriendInvite();
            }
        });



        progressBar.setVisibility(View.VISIBLE);
        getFriendInviteList();
        sendFriendInvite();

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean scrollUp = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE && scrollUp) {

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
                    progressBar.setVisibility(View.VISIBLE);
                    getFriendInviteList();
                }

            }
        });




    }

    public void sendFriendInvite(){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_invite_friend);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);

        final CustomEditText name = dialog.findViewById(R.id.invite_name);
        name.setHint(resources.getString(R.string.invite_name));
        final CustomEditText phone = dialog.findViewById(R.id.invite_phone);
        phone.setHint(resources.getString(R.string.invite_phone));
        CustomButton invite =  dialog.findViewById(R.id.invite);
        invite.setText(resources.getString(R.string.invite_button));
        CustomTextView title    = dialog.findViewById(R.id.title);
        title.setText(resources.getString(R.string.invite_title));
        CustomTextView textView = dialog.findViewById(R.id.text);
        textView.setText(resources.getString(R.string.invite_text));


        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString() != null && phone.getText().toString() != null &&
                        name.getText().toString().trim().length() > 0 && phone.getText().toString().trim().length() > 0) {

                    JSONObject invite = new JSONObject();
                    try {
                        invite.put("phone", phone.getText().toString());
                        invite.put("name", name.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    final Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.dialog_loading);
                    dialog1.setCancelable(true);
                    Window window = dialog1.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;
                    wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();

                    final LinearLayout linearLoading = dialog1.findViewById(R.id.linearloading);
                    final CustomTextView dialog_loading_title = dialog1.findViewById(R.id.dialog_loading_title);
                    dialog_loading_title.setText(resources.getString(R.string.invite));
                    linearLoading.setVisibility(View.GONE);


                    window.setAttributes(wlp);
                    CustomTextView text = dialog1.findViewById(R.id.dialog_loading_text);
                    text.setVisibility(View.GONE);
                    dialog1.show();


                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                            Request.Method.POST, String.format(constantInviteFriend, prefs.getIntPreferences(SP_MEMBER_ID)) +  "?language=" + prefs.getStringPreferences(SP_LANGUAGE), invite,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    Log.e(TAG, "onResponse:  "  + response.toString() );



                                    try {
                                        dialog.dismiss();
                                        dialog1.dismiss();



                                        try {


                                            Map<String, String> mix = new HashMap<String, String>();

                                            FlurryAgent.logEvent("InviteFriend Click Done", mix);

                                        } catch (Exception e) {
                                        }

                                        final Dialog dialog2 = new Dialog(activity);
                                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog2.setContentView(R.layout.dialog_loading);
                                        dialog2.setCancelable(true);
                                        Window window = dialog2.getWindow();
                                        WindowManager.LayoutParams wlp = window.getAttributes();
                                        wlp.gravity = Gravity.CENTER;
                                        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                                        window.setAttributes(wlp);

                                        final LinearLayout linearLoading = dialog2.findViewById(R.id.linearloading);
                                        final CustomTextView dialog_loading_title = dialog2.findViewById(R.id.dialog_loading_title);
                                        dialog_loading_title.setText(resources.getString(R.string.invite_status));
                                        linearLoading.setVisibility(View.GONE);

                                        ProgressBar progressBar3 = dialog2.findViewById(R.id.progressBar1);
                                        progressBar3.setVisibility(View.GONE);
                                        CustomTextView text = dialog2.findViewById(R.id.dialog_loading_text);
                                        text.setVisibility(View.VISIBLE);
                                        text.setText(response.getString("text"));
                                        dialog2.show();


                                        progressBar.setVisibility(View.VISIBLE);
                                        universalPosts.clear();

                                        prefs.saveIntPerferences(SP_PAGE_REDING_NUM, SP_DEFAULT);


                                        getFriendInviteList();



                                    } catch (JSONException e) {
                                        Log.e(TAG, "onResponse: " + e.getMessage() );
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            dialog1.dismiss();

                            ToastHelper.showToast(InviteFriendActivity.this, resources.getString(R.string.search_error));

                        }
                    }) {

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                            headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                            return headers;
                        }
                    };

                    AppController.getInstance().addToRequestQueue(jsonObjReq, "sign_in");
                }else{

                    ToastHelper.showToast(InviteFriendActivity.this, resources.getString(R.string.fill_all_feild));
                }
            }
        });

        dialog.show();
    }


    private void getFriendInviteList()
    {


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantInviteList  +prefs.getIntPreferences(ConstantVariable.SP_PAGE_REDING_NUM),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        progressBar.setVisibility(View.GONE);
                        if (response.length() > 0){
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            loading = false;



                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<InviteHistory>>() {
                                }.getType();
                                List<InviteHistory> lists = mGson.fromJson(response.toString(), listType);
                                for (int i = 0 ; i < lists.size(); i ++){
                                    InviteHistory obj = lists.get(i);
                                    obj.setPostType(INVITE_HISTORY);
                                    universalPosts.add(obj);

                                }
                                universalAdapter.notifyDataSetChanged();

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  +e.getMessage() );
                            }
                        }
                        else{

                            loading = true;
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);


                            universalAdapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onErrorResponse: errror "   + error.getClass() );
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
