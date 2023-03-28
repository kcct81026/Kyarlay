package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.MainTownShip;
import com.kyarlay.ayesunaing.object.TownShip;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import me.myatminsoe.mdetect.MDetect;


/**
 * Created by ayesunaing on 3/31/17.
 */

public class Splashscreen extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "Splashscreen";


    DatabaseAdapter adapter ;
    MyPreference prefs ;
    Display display;

    int currentVersion = 1;
    int version, min_version = 0;
    String version_des = "";
    String contact = "";
    ImageView kyarlay_icon;
    Resources resources;

    ProgressBar progress_bar;
    CustomButton reload, help_button;
    LinearLayout no_internet_layout;
    CustomTextView no_internet_text;
    AppCompatActivity activity;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        activity = Splashscreen.this;
        adapter = new DatabaseAdapter(Splashscreen.this);
        prefs = new MyPreference(Splashscreen.this);
        display = getWindowManager().getDefaultDisplay();


        //dCBh-_PHStGt0OV1g3W4KH:APA91bEpINEfTGTeOztMlKSfeAOQyxg-Qxtahu8UJcjQ86azVUIu-DSjUCW9L7ZvwsO0_V-jwjDUywCsdAUs_4b7NibTYWYRmtvv1_g2tv9ObkEEDVqVAiUI-_6Bre9ciSSah8fQEnoD

        if(prefs.getBooleanPreference(SP_ISAPPINSTALLED) == false){
            removeShortcut();
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

        if (prefs.isNetworkAvailable()){
            JsonArrayRequest jsonArrayRequest = addNewAddress();
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        }



        Context contextmyanmar = LocaleHelper.setLocale( Splashscreen.this, prefs.getStringPreferences(LANGUAGE));
        resources = contextmyanmar.getResources();

        new MyFlurry(Splashscreen.this);

        if (!isTaskRoot()
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {


            finish();
            return;
        }

        try {


            FlurryAgent.logEvent("Start Kyarlay");

        } catch (Exception e) {
        }

        kyarlay_icon    =  findViewById(R.id.kyarlay_icon);
        progress_bar    =  findViewById(R.id.progress_bar);
        reload          =  findViewById(R.id.reload);
        help_button          =  findViewById(R.id.help_button);
        no_internet_layout  =  findViewById(R.id.no_internet_layout);
        no_internet_text    = findViewById(R.id.no_internet_text);

        reload.setText(resources.getString(R.string.reload));
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.setVisibility(View.VISIBLE);
                if(!prefs.getStringPreferences(SP_USER_TOKEN).equals("")) {


                    getCustomerInfo();
                }else {
                    prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_COUNT , 0);
                    checkingVersion();
                }

            }
        });

        help_button.setText(resources.getString(R.string.contact));
        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefs.getStringPreferences(CALL_CENTER_NO).trim().length() > 0) {

                    final ArrayList<String> phoneArray = new ArrayList<String>();
                    StringTokenizer st = new StringTokenizer(prefs.getStringPreferences(CALL_CENTER_NO), ",");
                    while (st.hasMoreTokens()) {
                        phoneArray.add(st.nextToken());
                    }
                    if (phoneArray.size() > 1) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_call);
                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        LinearLayout mainlayout = (LinearLayout) dialog.findViewById(R.id.layout);

                        CustomTextView title1 = (CustomTextView) dialog.findViewById(R.id.title);
                        title1.setText(resources.getString(R.string.call_phone_dialog));
                        for (int i = 0; i < phoneArray.size(); i++) {
                            final String phoneString = phoneArray.get(i);
                            LinearLayout phoneLayout = new LinearLayout(activity);
                            phoneLayout.setOrientation(LinearLayout.VERTICAL);
                            phoneLayout.setPadding(0, 10, 0, 0);


                            CustomTextView price = new CustomTextView(activity);
                            price.setTextSize(16);
                            price.setPadding(10, 10, 10, 10);
                            price.setGravity(Gravity.LEFT);
                            price.setText(phoneString);
                            phoneLayout.addView(price);


                            CustomTextView choose = new CustomTextView(activity);
                            choose.setBackgroundResource(R.drawable.textview_round);
                            choose.setTextSize(14);
                            choose.setText(resources.getString(R.string.choose));
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            parms.gravity = Gravity.RIGHT;
                            parms.setMargins(0, 5, 5, 10);
                            choose.setLayoutParams(parms);
                            phoneLayout.addView(choose);


                            TextView space = new TextView(activity);
                            space.setHeight(6);
                            space.setBackgroundResource(R.color.checked_bg);
                            phoneLayout.addView(space);
                            mainlayout.addView(phoneLayout);

                            phoneLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {

                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "campain");
                                        mix.put("call_id", phoneString);
                                        FlurryAgent.logEvent("Call Call Center", mix);
                                    } catch (Exception e) {
                                    }
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneString));
                                    startActivity(callIntent);
                                    dialog.dismiss();
                                }
                            });


                        }
                        dialog.show();
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + prefs.getStringPreferences(CALL_CENTER_NO)));
                        startActivity(callIntent);
                    }

                }
                else{
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "09765448851"));
                    startActivity(callIntent);
                }


            }
        });

        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            currentVersion = packageManager.getPackageInfo(getApplicationContext().getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate:  "   + e.getMessage() );
        }




        if(prefs.isNetworkAvailable()){
            if(!prefs.getStringPreferences(SP_USER_TOKEN).equals("")) {

                getCustomerInfo();
            }else {
                prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_COUNT , 0);
                checkingVersion();
            }
        }else {
            reload.setVisibility(View.VISIBLE);
            help_button.setVisibility(View.VISIBLE);
            no_internet_layout.setVisibility(View.VISIBLE);
            no_internet_text.setText(resources.getString(R.string.no_internet_error));
            progress_bar.setVisibility(View.GONE);

        }

        if (prefs.getBooleanPreference(SP_ADS_LOADED)){
            int testTime = prefs.getIntPreferences(ADS_COUNT_DOWN_TIME);
            CountDownTimer cdt = new CountDownTimer(testTime * 1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    prefs.saveIntPerferences(ADS_COUNT_DOWN_TIME, (int)(millisUntilFinished / 1000) );

                }

                @Override
                public void onFinish() {

                    prefs.saveBooleanPreference(SP_ADS_LOADED, false);

                }
            };

            cdt.start();
        }
        prefs.saveBooleanPreference(LOGIN_SAVECART, false);

    }



    public void checkingVersion(){


        JSONObject uploadMessage = new JSONObject();

        try {
            uploadMessage.put("customer_phone",  prefs.getStringPreferences(SP_PHONE_NUMBER));
            uploadMessage.put("currentVersion",  currentVersion);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "sendComment:  "  + e.getMessage() );
        }



        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantVersion+"?version="+currentVersion, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {





                progress_bar.setVisibility(View.INVISIBLE);
                try {
                    version     = response.getInt("version");
                    min_version = response.getInt("min_version");
                    version_des = response.getString("desc");
                    contact     = response.getString("contact");

                    prefs.saveStringPreferences(CALL_CENTER_NO, contact);
                    prefs.saveIntPerferences(SP_MEMBER_PERCENTAGE, response.getInt("discount_percentage"));
                    //prefs.saveIntPerferences(SP_MEMBER_DELIVERY_AMOUNT, response.getInt("min_free_delivery"));
                    prefs.saveIntPerferences(SP_CURRENT_VERSION, currentVersion);
                    prefs.saveIntPerferences(SP_POINT_PERCENTAGE, response.getInt("point_percentage"));
                    prefs.saveIntPerferences(SP_FEEDBACK_POINT, response.getInt("shopping_feedback_point"));

                    if(min_version > currentVersion){
                        final Dialog dialog = new Dialog(Splashscreen.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_version_outofdate);
                        dialog.setCancelable(false);

                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        CustomTextView des = (CustomTextView) dialog.findViewById(R.id.dialog_save_cart_product);
                        CustomTextView title= (CustomTextView) dialog.findViewById(R.id.title);
                        title.setText(resources.getString(R.string.new_version));
                        des.setText(version_des);
                        LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.dialog_product_yes);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.kyarlay.ayesunaing"));
                                startActivity(intent);
                            }
                        });

                        dialog.show();

                    }else if(version > currentVersion){
                        final Dialog dialog = new Dialog(Splashscreen.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_version_update);
                        dialog.setCancelable(false);

                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        CustomTextView title= (CustomTextView) dialog.findViewById(R.id.title);
                        title.setText(resources.getString(R.string.new_version));
                        CustomTextView des = (CustomTextView) dialog.findViewById(R.id.dialog_save_cart_product);
                        des.setText(version_des);
                        LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.dialog_product_yes);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        LinearLayout cart = (LinearLayout) dialog.findViewById(R.id.dialog_product_gotocart);
                        cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.kyarlay.ayesunaing"));
                                startActivity(intent);
                            }
                        });
                        dialog.show();
                    }else{
                        Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                        startActivity(intent);
                        Splashscreen.this.finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse:  "  + e.getMessage() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progress_bar.setVisibility(View.GONE);
                reload.setVisibility(View.VISIBLE);
                help_button.setVisibility(View.VISIBLE);
                no_internet_layout.setVisibility(View.VISIBLE);
                no_internet_text.setText(resources.getString(R.string.search_error));

                ToastHelper.showToast(Splashscreen.this, resources.getString(R.string.no_internet_error));



            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }



    public void getCustomerInfo(){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantGetCustomerInfo + "?" + LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.e(TAG, "onResponse: ---------------------"  + response.toString() );


                progress_bar.setVisibility(View.INVISIBLE);
                try {
                    prefs.saveIntPerferences(SP_USER_POINT, response.getInt("gift_points"));
                    prefs.saveStringPreferences(SP_USER_VIP_ACCOUNT, response.getString("vip"));

                    if (response.getString("address")!= "null" && response.getString("address").length() > 0 ){
                        prefs.saveStringPreferences(SP_USER_ADDRESS, response.getString("address"));

                    }

                    if (response.getString("vip").trim().length() == 0) {

                        prefs.saveIntPerferences(SP_VIP_ID, 0);
                    } else {

                        prefs.saveIntPerferences(SP_VIP_ID, 1);
                    }

                    prefs.saveIntPerferences(SP_KYARLAY_ACCESS, response.getInt("admin"));


                    prefs.saveIntPerferences(SP_ORDER_COUNT, response.getInt("order_count"));

                    prefs.saveIntPerferences(SP_ONE_TIME_FREE_DELIVERY, response.getInt("free_delivery"));





                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse: get customeer info " + e.getMessage() );
                }
                checkingVersion();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: get customer info "    + error.getMessage() );

                reload.setVisibility(View.VISIBLE);
                help_button.setVisibility(View.VISIBLE);
                ToastHelper.showToast(Splashscreen.this, resources.getString(R.string.no_internet_error));


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
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void addShortcut() {
        //on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(), Splashscreen.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        if(prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_MYANMAR)){
            Context context = LocaleHelper.setLocale(this, LANGUAGE_MYANMAR);
            Resources resources = context.getResources();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, resources.getString(R.string.app_name));
        }else{
            Context context = LocaleHelper.setLocale(this, LANGUAGE_ENGLISH);
            Resources resources = context.getResources();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, resources.getString(R.string.app_name));
        }

        addIntent.putExtra("duplicate",false);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(),R.drawable.ic_launcher));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

        prefs.saveBooleanPreference(SP_ISAPPINSTALLED, true);
    }

   /* private void getCompetitionList()
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantCompetition,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {




                        if(response.length() > 0) {


                            ArrayList<Competition> categoryList = new ArrayList<>();
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Competition>>() {}.getType();
                                categoryList = mGson.fromJson(response.toString(), listType);

                                if (categoryList.size() > 0){

                                    Competition competition =categoryList.get(0);
                                    prefs.saveBooleanPreference(HAS_COMPETITION , true);
                                    prefs.saveStringPreferences(COMPETITION_TITLE, competition.getTitle());
                                    prefs.saveStringPreferences(COMPETITION_DESC, competition.getDesc());
                                    prefs.saveStringPreferences(COMPETITION_IMG_URL, competition.getImg_url());
                                    prefs.saveIntPerferences(COMPETITION_DIMEN, competition.getImg_dimen());
                                    prefs.saveIntPerferences(COMPETITION_ID, competition.getId());
                                }
                                else{
                                    prefs.saveBooleanPreference(HAS_COMPETITION , false);
                                }




                            }catch (Exception e){

                                Log.e(TAG, "onResponse: "   + e.getMessage() );
                            }


                        }
                        else{
                            prefs.saveBooleanPreference(HAS_COMPETITION , false);
                        }





                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prefs.saveBooleanPreference(HAS_COMPETITION , false);

            }
        }){

            *//**
             * Passing some request headers
             * *//*
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
*/





    private void removeShortcut() {

        Intent shortcutIntent = new Intent(getApplicationContext(), Splashscreen.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        if(prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_MYANMAR)){
            Context context = LocaleHelper.setLocale(this, LANGUAGE_MYANMAR);
            Resources resources = context.getResources();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, resources.getString(R.string.app_name));
        }else{
            Context context = LocaleHelper.setLocale(this, LANGUAGE_ENGLISH);
            Resources resources = context.getResources();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, resources.getString(R.string.app_name));
        }

        addIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
        addShortcut();
    }

    private JsonArrayRequest addNewAddress(){



        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantTownShipList + LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(response.length() > 0) {
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            Type listType = new TypeToken<List<MainTownShip>>() {}.getType();
                            List<MainTownShip> mainTownShipList = mGson.fromJson(response.toString(), listType);
                            List<TownShip> townShipArrayList = new ArrayList<>();
                            List<TownShip> tempTownShipList = new ArrayList<>();
                            TownShip townShip1 = new TownShip();
                            TownShip townShip2 = new TownShip();

                            townShipArrayList.add(townShip1);
                            townShipArrayList.add(townShip2);
                            for (int i=0; i < mainTownShipList.size() ; i++){

                                MainTownShip mainTownShip = mainTownShipList.get(i);

                                if (mainTownShip.getTownShipArrayList().size() == 0){

                                    townShip1.setId(mainTownShipList.get(i).getId());
                                    townShip1.setName(mainTownShipList.get(i).getName());
                                    townShip1.setStore_location_id(-1);
                                    townShipArrayList.remove(0);
                                    townShipArrayList.add(0,townShip1);
                                }
                                else{

                                    townShip2.setId(mainTownShipList.get(i).getId());
                                    townShip2.setName(mainTownShipList.get(i).getName());
                                    townShip2.setStore_location_id(-1);
                                    townShipArrayList.remove(1);
                                    townShipArrayList.add(1,townShip2);

                                    for (int j=0; j< mainTownShip.getTownShipArrayList().size(); j++){

                                        townShipArrayList.add(j+2, mainTownShip.getTownShipArrayList().get(j));

                                    }

                                }
                            }


                            if (townShipArrayList.size() > 0){
                                DatabaseAdapter databaseAdapter = new DatabaseAdapter(Splashscreen.this);

                                databaseAdapter.insertTownship(townShipArrayList);
                            }






                        }




                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: address error "  + error.getMessage() );

                //ToastHelper.showToast(activity, resources.getString(R.string.search_error));


            }
        });
        return jsonObjReq;

    }
}