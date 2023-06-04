package com.kyarlay.ayesunaing.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTypefaceSpan;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.fragment.FragmentMall;
import com.kyarlay.ayesunaing.fragment.FragmentMedia;
import com.kyarlay.ayesunaing.fragment.FragmentReadingPager;
import com.kyarlay.ayesunaing.fragment.FragmentSubCategory;
import com.kyarlay.ayesunaing.in_app.Constants;
import com.kyarlay.ayesunaing.in_app.InAppUpdateManager;
import com.kyarlay.ayesunaing.in_app.InAppUpdateStatus;
import com.kyarlay.ayesunaing.object.CustomerProductList;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.ShopLocation;
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
import java.util.Random;

import me.myatminsoe.mdetect.MDetect;

import static com.kyarlay.ayesunaing.data.ConstantsDB.TABLE_PICKUP_SHOP;

/*import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.FreshchatUser;*/


/**
 * Created by ayesu on 11/22/17.
 */

public class MainActivity extends AppCompatActivity implements Constant, ConstantVariable, InAppUpdateManager.InAppUpdateHandler {

    private static final String TAG = "MainActivity";

    FrameLayout content_frame;
    DatabaseAdapter databaseAdapter;




    Resources resources;
    MyPreference prefs;
    BottomNavigationView navigation;

    CircularTextView text_cart;


    FragmentMall fragmentOne;
    FragmentMedia fragmentMedia;
    FragmentSubCategory fragmentCategory;


    private InAppUpdateManager inAppUpdateManager;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = new MyPreference(MainActivity.this);

        Log.e(TAG, "onCreate: " );


        inAppUpdateManager = InAppUpdateManager.Builder(this, REQ_CODE_VERSION_UPDATE)
                .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
                .mode(Constants.UpdateMode.FLEXIBLE)
                // default is false. If is set to true you,
                // have to manage the user confirmation when
                // you detect the InstallStatus.DOWNLOADED status,
                .useCustomNotification(true)
                .handler(this);
        inAppUpdateManager.checkForAppUpdate();

        new MyFlurry(MainActivity.this);
        Intent intent = getIntent();
        final Bundle bundle=intent.getExtras();
        if(bundle != null ){

            try {


                Map<String, String> mix = new HashMap<String, String>();
                mix.put("source", "MainActivity");
                FlurryAgent.logEvent("Incoming Pushnotification Click", mix);

            } catch (Exception e) {}
        }



        if(prefs != null && !prefs.isContains(LANGUAGE)){
            if (MDetect.INSTANCE.isUnicode()) {
                prefs.saveStringPreferences(LANGUAGE, LANGUAGE_UNI);
                prefs.saveStringPreferences(SP_LANGUAGE, "uni");
            }
            else {
                prefs.saveStringPreferences(LANGUAGE, LANGUAGE_MYANMAR);
                prefs.saveStringPreferences(SP_LANGUAGE, "zawgyi");
            }
        }
        else{
            if (!prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_ENGLISH)){

                if (MDetect.INSTANCE.isUnicode()) {
                    prefs.saveStringPreferences(LANGUAGE, LANGUAGE_UNI);
                    prefs.saveStringPreferences(SP_LANGUAGE, "uni");
                }
                else {
                    prefs.saveStringPreferences(LANGUAGE, LANGUAGE_MYANMAR);
                    prefs.saveStringPreferences(SP_LANGUAGE, "zawgyi");
                }
            }
            else{
                prefs.saveStringPreferences(SP_LANGUAGE, "en");
            }
        }

        prefs.saveBooleanPreference(SP_CLICK_OTHER, false);


        Context contextmyanmar = LocaleHelper.setLocale( MainActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = contextmyanmar.getResources();
        databaseAdapter = new DatabaseAdapter(MainActivity.this);

        try {


            Map<String, String> mix = new HashMap<String, String>();
            FlurryAgent.logEvent("View Main Page", mix);

        } catch (Exception e) {
        }




        try{



            final InstallReferrerClient referrerClient;

            referrerClient = InstallReferrerClient.newBuilder(this).build();
            referrerClient.startConnection(new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    switch (responseCode) {
                        case InstallReferrerClient.InstallReferrerResponse.OK:

                            ReferrerDetails response = null;
                            try {
                                response = referrerClient.getInstallReferrer();
                                String referrerUrl = response.getInstallReferrer();
                                long referrerClickTime = response.getReferrerClickTimestampSeconds();
                                long appInstallTime = response.getInstallBeginTimestampSeconds();
                                boolean instantExperienceLaunched = response.getGooglePlayInstantParam();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }


                            // Connection established.
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                            // API not available on the current Play Store app.
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                            // Connection couldn't be established.
                            break;
                    }
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                }
            });
        }catch (Exception e){
            Log.e(TAG, "onCreate: InstallReferrerClient"  + e.getMessage() );
        }



        if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){

            productCount();
            postCount();
            myAccountLogin();



        }


        deliveryList();

        if(FirebaseInstanceId.getInstance().getToken()!= null &&
                FirebaseInstanceId.getInstance().getToken().trim().length() > 0 &&
                !prefs.getStringPreferences(SP_USER_TOKEN).equals(""))
            updateFCMID(FirebaseInstanceId.getInstance().getToken());

        if(prefs != null && !prefs.isContains(PREFERENCES_TOOL_BAR_CART)){
            prefs.saveIntPerferences(PREFERENCES_TOOL_BAR_CART, 0);
        }

        if(prefs != null && !prefs.isContains(SP_UNIQUE_ID_FOR_USER)){
            getUniqueId();
        }

        if(prefs != null && !prefs.isContains(SP_MAINACTIVITY_CLICK)){
            prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK, 0);
        }
        content_frame   = (FrameLayout) findViewById(R.id.content_frame);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        text_cart = findViewById(R.id.text_cart);
        navigation.clearAnimation();
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        int [] array = {R.string.title_mall, R.string.category, R.string.text_empty,R.string.title_feed,   R.string.knowledge };


        for (int i=0; i < navigation.getMenu().size(); i++){
            if(i != 2 )
                applyFontToMenuItem(navigation.getMenu().getItem(i), array[i]);
        }
        checkMenu();

        JsonArrayRequest jsonArrayRequest = shopList();
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    public void checkMenu(){
        if(prefs.getIntPreferences(SP_MAINACTIVITY_CLICK) == 0){

            navigation.setSelectedItemId(R.id.navigation_mall);
            navigation.getMenu().findItem(R.id.navigation_mall).setChecked(true);


        }
        else if(prefs.getIntPreferences(SP_MAINACTIVITY_CLICK) == 1){
            navigation.setSelectedItemId(R.id.navigation_video);
            navigation.getMenu().findItem(R.id.navigation_video).setChecked(true);



        }
        else if(prefs.getIntPreferences(SP_MAINACTIVITY_CLICK) == 2){
            navigation.setSelectedItemId(R.id.navigation_notification);
            navigation.getMenu().findItem(R.id.navigation_notification).setChecked(true);


        }
        else if(prefs.getIntPreferences(SP_MAINACTIVITY_CLICK) == 3){

            navigation.setSelectedItemId(R.id.navigation_category);
            navigation.getMenu().findItem(R.id.navigation_category).setChecked(true);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode != Activity.RESULT_OK) {
                // If the update is cancelled by the user,
                // you can request to start the update again.


                //  inAppUpdateManager.checkForAppUpdate();


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    public void updateFCMID(String token){
        JSONObject updateFcm = new JSONObject();
        try {
            updateFcm.put("fcm_token",  token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,  String.format(constantUpdateFcmID, prefs.getIntPreferences(SP_MEMBER_ID)), updateFcm,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

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

    private void applyFontToMenuItem(MenuItem mi, int title) {

        Typeface font;
        if (MDetect.INSTANCE.isUnicode()){
            font = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
        }
        else{
            font = AppController.typefaceManager.getCompanion().getZAWGYITWO();
        }
        SpannableString mNewTitle = new SpannableString(resources.getString(title));
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onBackPressed() {


        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_confrim);

        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);

        CustomButton cancel = (CustomButton) dialog.findViewById(R.id.dialog_delete_cancel);
        CustomButton confirm = (CustomButton) dialog.findViewById(R.id.dialog_delete_confirm);
        CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
        CustomTextView text = (CustomTextView) dialog.findViewById(R.id.text);
        RecyclerView recycler_view = dialog.findViewById(R.id.recycler_view);





        title.setText(resources.getString(R.string.exit_from_app));
        confirm.setText(resources.getString(R.string.logout_confirm));


        final ArrayList<UniversalPost> arrayList = databaseAdapter.getOrder();

        if (arrayList.size() > 0){

            text.setText(String.format(resources.getString(R.string.order_before_exit), resources.getString(R.string.go_to_basket)));

            int index = 0;

            if (arrayList.size() > 3){
                index = 3;
            }
            else
                index = arrayList.size();

            cancel.setText(resources.getString(R.string.go_to_basket));



            ArrayList<UniversalPost> mainGrids = new ArrayList<>();
            for (int i = 0 ; i < index; i++){
                UniversalPost universalPost = arrayList.get(i);
                universalPost.setPostType(SHOW_IMAGE);
                mainGrids.add(universalPost);
            }

            UniversalAdapter gridAdapter = new UniversalAdapter(this, mainGrids);
            @SuppressLint("WrongConstant") RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this , 3, GridLayoutManager.VERTICAL, false);
            recycler_view.setLayoutManager(layoutManager);
            recycler_view.setAdapter(gridAdapter);


        }
        else{
            recycler_view.setVisibility(View.GONE);
            text.setText(resources.getString(R.string.exit_confrim));
            cancel.setText(resources.getString(R.string.logout_cancel));

        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (arrayList.size() > 0){

                    if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                        Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                        startActivity(intent);
                    }
                    else{
                        prefs.saveBooleanPreference(LOGIN_SAVECART, true);
                        Intent intent   = new Intent(MainActivity.this, ActivityLogin.class);
                        startActivity(intent);
                    }


                }

            }
        });



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK, 0);

                try {

                    FlurryAgent.logEvent("App Close");
                    finishAffinity();
                } catch (Exception e) {
                }




            }

        });



        dialog.show();



    }



    public void bounceCount(){

        if(fragmentOne != null) {
            //fragmentOne.bounceCount();

            int count_wish1  = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);
            Log.e(TAG, "bounceCount: ------------------------- 810 count "  + count_wish1  );
            text_cart.setText(count_wish1 + "");

            if (count_wish1 == 0)
                text_cart.setVisibility(View.GONE);
            else
                text_cart.setVisibility(View.VISIBLE);


            Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.bounce_animation);
            AnimatorSet animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext()
                    , R.animator.flip_animation);

            animationSet.setTarget(text_cart);
            text_cart.startAnimation(bounce);
            animationSet.start();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("RestrictedApi")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {




            switch (item.getItemId()) {
                case R.id.navigation_mall:

                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("item", "navigation_mall");
                        FlurryAgent.logEvent("Click Bottom Navigation Item", mix);


                    } catch (Exception e) {
                    }


                    fragmentOne = new FragmentMall();
                    prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentOne).commitAllowingStateLoss();

                    return true;

                case R.id.navigation_video:

                    try {
                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("item", "media");
                        FlurryAgent.logEvent("Click Bottom Navigation Item", mix);

                    } catch (Exception e) {
                    }


                    prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK, 1);

                    fragmentMedia = new FragmentMedia();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentMedia).commitAllowingStateLoss();


                    return true;

                case R.id.navigation_shopping :

                    try {
                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("item", "shopping_cart");
                        FlurryAgent.logEvent("Click Bottom Navigation Item", mix);

                    } catch (Exception e) {
                    }

                    if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                            prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){
                        Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                        startActivity(intent);
                    }



                    return false;

                case R.id.navigation_category:
                   /* try {
                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("item", "navigation_category");
                        FlurryAgent.logEvent("Click Bottom Navigation Item", mix);

                    } catch (Exception e) {
                    }*/

                    prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK, 3);

                    fragmentCategory = new FragmentSubCategory();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentCategory).commitAllowingStateLoss();




                    return true;

                case R.id.navigation_notification:


                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("item", "navigation_media");
                        FlurryAgent.logEvent("Click Bottom Navigation Item", mix);


                    } catch (Exception e) {
                    }

                  /*  middleItemView.removeView(middleLogo);
                    cart_badge.setImageDrawable(MainActivity.this.getResources().getDrawable(R.mipmap.ic_kyarlay_shape));
                    middleItemView.addView(middleLogo);*/

                    //fragmentReading = new FragmentReading();
                    FragmentReadingPager fragmentTest = new FragmentReadingPager();
                    Bundle bundle = new Bundle();
                    bundle.putInt("position",0);
                    fragmentTest.setArguments(bundle);
                    prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 2);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentTest).commitAllowingStateLoss();



                   /* try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("item", "cs_chat");
                        FlurryAgent.logEvent("Click Bottom Navigation Item", mix);

                    } catch (Exception e) {
                    }



                    if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                            prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){
                        prefs.saveIntPerferences(SP_FRESH_CHAT_UNREAD_COUNT, 0);



                        FreshchatNotificationConfig notificationConfig = new FreshchatNotificationConfig()
                                .setNotificationSoundEnabled(true)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setLargeIcon(R.drawable.ic_launcher)
                                .launchActivityOnFinish(MainActivity.class.getName())
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                        Freshchat.getInstance(MainActivity.this).setNotificationConfig(notificationConfig);


                        FreshchatConfig freshchatConfig=new FreshchatConfig(SP_FRESH_CAHT_ID,SP_FRESH_CHAT_KEY);
                        Freshchat.getInstance(MainActivity.this).init(freshchatConfig);
                        Freshchat.getInstance(MainActivity.this).identifyUser(String.valueOf(prefs.getIntPreferences(SP_MEMBER_ID)), prefs.getStringPreferences(SP_USER_FRESH_CHAT_ID));
                        FreshchatUser freshUser=Freshchat.getInstance(MainActivity.this).getUser();
                        freshUser.setFirstName(prefs.getStringPreferences(SP_USER_NAME+""));
                        freshUser.setEmail(prefs.getStringPreferences(SP_USER_TOKEN));
                        freshUser.setPhone("+95", prefs.getStringPreferences(SP_USER_PHONE));
                        Freshchat.getInstance(MainActivity.this).setUser(freshUser);
                        Freshchat.showConversations(MainActivity.this);

                    }else{
                        Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                        startActivity(intent);
                    }*/
                    return true;





            }
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        if(!prefs.getStringPreferences(SP_USER_TOKEN).equals("")) {

            getCustomerProdcutList();
        }else{
            text_cart.setVisibility(View.GONE);
            prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_COUNT , 0);


        }




        /*int count_wish1  = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);
        text_cart.setText(count_wish1 + "");

        if (count_wish1 == 0)
            text_cart.setVisibility(View.GONE);
        else
            text_cart.setVisibility(View.VISIBLE);*/


    }


    private void updateFreshChatId(String restoreId)
    {


        JSONObject updateFreshID = new JSONObject();
        try {
            updateFreshID.put("freshchat_id",  restoreId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,  String.format(constantUpdateFreshChat, prefs.getIntPreferences(SP_MEMBER_ID)), updateFreshID,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.e(TAG, "onResponse: updateFreshChatId " + response.toString() );
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
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                            e.printStackTrace();
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
                            Log.e(TAG, "onResponse:  "  + e.getMessage() );
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



    public void getUniqueId() {

        String uniqueId = "";
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        uniqueId = sb.toString();
        prefs.saveStringPreferences(SP_UNIQUE_ID_FOR_USER, uniqueId);
    }


    private void deliveryList()
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantDeliveryList + "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() > 0) {

                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            ArrayList<Delivery> arrayList = new ArrayList<>();
                            Type listType = new TypeToken<List<Delivery>>() {}.getType();
                            List<Delivery> deliveries = mGson.fromJson(response.toString(), listType);
                            for(int i = 0; i < deliveries.size(); i++) {

                                arrayList.add(deliveries.get(i));

                            }
                            databaseAdapter.insertDelivery(arrayList);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void getCustomerProdcutList()
    {

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantGetCurrentOrderFromServer, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {


                    try {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Type listType = new TypeToken<CustomerProductList>() {
                        }.getType();
                        CustomerProductList customerProductList = mGson.fromJson(response.toString(), listType);
                        if (customerProductList.getProuduts().size() == 0){

                            prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_COUNT , 0);
                        }
                        else{
                            int count = 0;
                            for (int i=0; i< customerProductList.getProuduts().size() ; i++){
                                count = count + customerProductList.getProuduts().get(i).getQuantity();
                            }
                            prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_COUNT , count);
                        }

                        int count_wish1  = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);
                        text_cart.setText(count_wish1 + "");

                        if (count_wish1 == 0)
                            text_cart.setVisibility(View.GONE);
                        else
                            text_cart.setVisibility(View.VISIBLE);

                        prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_DISCOUNT, customerProductList.getProductCuromerInfo().getDisCountPrice());
                        prefs.saveIntPerferences(SP_CUSTOMER_MEMBER_DISCOUNT, customerProductList.getProductCuromerInfo().getMemberDiscount());
                        prefs.saveFloatPerferences(SP_CUSTOMER_FLASH_DISCOUNT, customerProductList.getProductCuromerInfo().getFlashDiscount());
                        prefs.saveFloatPerferences(SP_CUSTOMER_TOTAL, customerProductList.getProductCuromerInfo().getTotalPrice());

                    }catch (Exception e){
                        Log.e(TAG, "onResponse: errror   8102     "  + e.getMessage() );
                    }


                } catch (Exception e) {
                    Log.e(TAG, "onResponse: Exception  -----  8102 "   + e.getMessage() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: Exception  -----  8102 "   + error.getMessage() );
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



    private void myAccountLogin()
    {

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantGetCustomerInfo +   "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {



                    prefs.saveStringPreferences(SP_USER_PHONE, response.getString("phone"));
                    prefs.saveStringPreferences(SP_USER_NAME, response.getString("name"));
                    prefs.saveStringPreferences(SP_USER_VIP_ACCOUNT, response.getString("vip"));
                    prefs.saveStringPreferences(SP_MEMBER_TYPE, response.getString("customer_type"));
                    prefs.saveIntPerferences(SP_MEMBER_ID, response.getInt("id"));

                    if (response.getString("address")!= "null" && response.getString("address").length() > 0 ){
                        prefs.saveStringPreferences(SP_USER_ADDRESS, response.getString("address"));

                    }




                    if (response.getString("vip").trim().length() == 0) {
                        prefs.saveIntPerferences(SP_VIP_ID, 0);
                    } else {
                        prefs.saveIntPerferences(SP_VIP_ID, 1);
                    }


                    prefs.saveIntPerferences(SP_ORDER_COUNT, response.getInt("order_count"));

/*

                    FreshchatConfig freshchatConfig=new FreshchatConfig(BuildConfig.FRESH_CAHT_ID,BuildConfig.FRESH_CHAT_KEY);
                    Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);
                    Freshchat.getInstance(getApplicationContext()).identifyUser(String.valueOf(prefs.getIntPreferences(SP_MEMBER_ID)), prefs.getStringPreferences(SP_USER_FRESH_CHAT_ID));
                    FreshchatUser freshUser=Freshchat.getInstance(getApplicationContext()).getUser()    ;
                    freshUser.setFirstName(prefs.getStringPreferences(SP_USER_NAME+""));
                    freshUser.setEmail(prefs.getStringPreferences(SP_USER_TOKEN));
                    freshUser.setPhone("+95", prefs.getStringPreferences(SP_USER_PHONE));
                    Freshchat.getInstance(getApplicationContext()).setUser(freshUser);

                    if(freshUser.getRestoreId() != null &&
                            freshUser.getRestoreId().trim().length() != 0) {
                        updateFreshChatId(freshUser.getRestoreId());
                    }
*/




                } catch (Exception e) {
                    Log.e(TAG, "onResponse: Exception  "   + e.getMessage() );
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
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "update_profile");
    }





    @Override
    public void onInAppUpdateError(int code, Throwable error) {

    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {
        if (status.isDownloaded()) {


            new AlertDialog.Builder(this)
                    .setTitle("Install Kyarlay Lastest Version?")
                    .setMessage("An update has just been downloaded.")
                    .setPositiveButton("Install", (dialog, which) -> {
                        // Triggers the completion of the update of the app for the flexible flow.
                        inAppUpdateManager.completeUpdate();
                    })
                    .setNegativeButton("Later", null)
                    .show();
        }
    }

    private JsonArrayRequest shopList()
    {


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantShopLocations + LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {




                        if(response.length() > 0) {

                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            Type listType = new TypeToken<List<ShopLocation>>() {}.getType();
                            List<ShopLocation> shopLocationList = mGson.fromJson(response.toString(), listType);

                            databaseAdapter.deleteAllColumn(TABLE_PICKUP_SHOP);

                            if (shopLocationList.size() > 0){

                                databaseAdapter.insertPickUpShop(shopLocationList);
                            }
                        }
                        else{
                            databaseAdapter.deleteAllColumn(TABLE_PICKUP_SHOP);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse:   "  + error.getMessage() );


            }
        });
        return jsonObjReq;
    }

}

