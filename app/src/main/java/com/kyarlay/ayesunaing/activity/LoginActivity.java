package com.kyarlay.ayesunaing.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.FreshchatUser;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.PrefixEditText;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by ayesu on 8/30/17.
 */

public class LoginActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 123;


    LinearLayout phone_layout, back_layout, call_layout;

    PrefixEditText phone;
    CustomEditText phonePass, password;
    CustomButton button;
    CustomTextView txtEnter,txtPass;
    MyPreference prefs;

    Resources resources;


    CustomTextView to_contact, title, timer, txtNewAcc;
    Display display;
    ProgressBar progressBar;

    long milliseconds;

    String stringPhone = "";
    CountDownTimer countDownTimer = null;


    int count = 0;
    boolean loginWithPassword = false;
    ImageView image;
    FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        prefs   = new MyPreference(LoginActivity.this);

        Log.e(TAG, "onCreate: " );


        display = getWindowManager().getDefaultDisplay();
        if(prefs.getStringPreferences(LANGUAGE) == ""){
            prefs.saveStringPreferences(LANGUAGE, LANGUAGE_MYANMAR);
        }
        firebaseAnalytics   = FirebaseAnalytics.getInstance(LoginActivity.this);


        if(prefs != null && !prefs.isContains(COUNT_DOWN)){
            prefs.saveIntPerferences(COUNT_DOWN,0);
        }
        if(prefs != null && !prefs.isContains(SP_DEVICE_IMEI) && !prefs.isContains(SP_DEVICE_ID) && !prefs.isContains(SP_DEVICE_MANUFACTURER)){
            setPhoneDate();
        }


        stringPhone = getIntent().getStringExtra("phone" );
        Context context = LocaleHelper.setLocale(LoginActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        new MyFlurry(LoginActivity.this);
        try {


            Map<String, String> mix = new HashMap<String, String>();
            FlurryAgent.logEvent("View Phone Entry Page", mix);
        } catch (Exception e) {
        }

        phone_layout    = (LinearLayout) findViewById(R.id.phone_layout);
        phone           = (PrefixEditText) findViewById(R.id.phone);
        phonePass       = (CustomEditText) findViewById(R.id.phone2);
        password       = (CustomEditText) findViewById(R.id.phonePassword);

        button          = (CustomButton) findViewById(R.id.button);
        back_layout     = (LinearLayout) findViewById(R.id.back_layout);
        call_layout     = (LinearLayout) findViewById(R.id.callLayout);
        to_contact      = (CustomTextView) findViewById(R.id.to_contact);
        title           = (CustomTextView) findViewById(R.id.title);
        progressBar     = findViewById(R.id.progress_bar);
        txtEnter        = findViewById(R.id.txt_enter);
        txtPass        = findViewById(R.id.txt_enterpassword);
        timer        = findViewById(R.id.timer);
        txtNewAcc        = findViewById(R.id.txtNewAcc);
        image        = findViewById(R.id.image);
        txtNewAcc.setVisibility(View.GONE);
        phonePass.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        txtPass.setVisibility(View.GONE);


        if (prefs.getIntPreferences(COUNT_DOWN) == 0){
            timer.setVisibility(View.GONE);
        }else {
            timer.setVisibility(View.VISIBLE);
            int testTime = prefs.getIntPreferences(COUNT_DOWN_TIMER);

            CountDownTimer cdt = new CountDownTimer(testTime * 1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    prefs.saveIntPerferences(COUNT_DOWN_TIMER, (int)(millisUntilFinished / 1000) );
                    timer.setText(String.format(resources.getString(R.string.otp_waiting_time) , "" + (int) (millisUntilFinished / 1000) ));
                    timer.setVisibility(View.VISIBLE);


                }

                @Override
                public void onFinish() {

                    prefs.saveIntPerferences(COUNT_DOWN, 0);
                    timer.setVisibility(View.GONE);
                }
            };

            cdt.start();
        }


        title.setText(resources.getString(R.string.login_title));
        to_contact.setText(resources.getString(R.string.to_contact));

        txtEnter.setText(resources.getString(R.string.phone_number_hint));
        txtEnter.setVisibility(View.VISIBLE);

        InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        button.setText(resources.getString(R.string.phone_number_continue));

        back_layout.getLayoutParams().width = (display.getWidth() * 3 ) / 20;
        call_layout.getLayoutParams().width = (display.getWidth() * 17) / 20;

        txtNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    FlurryAgent.logEvent("View Profile Signup Page", mix);

                } catch (Exception e) {
                }

                Bundle bundle = new Bundle();
                bundle.putString("sign_up_method","phone");
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);

                Intent intent = new Intent(LoginActivity.this, RegisterWithPassword.class);
                startActivity(intent);
                finish();
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0){

                    count++;
                    Handler h = new Handler();

                    h.postDelayed(new Runnable() {

                        @Override

                        public void run() {

                            if (count > 4){

                                phone.setVisibility(View.GONE);
                                phonePass.setVisibility(View.VISIBLE);
                                password.setVisibility(View.VISIBLE);
                                phonePass.setVisibility(View.VISIBLE);
                                txtPass.setVisibility(View.VISIBLE);

                                txtNewAcc.setVisibility(View.VISIBLE);

                                txtPass.setText(resources.getString(R.string.password_hint));

                                txtNewAcc.setText( "* " + resources.getString(R.string.new_account));

                                loginWithPassword = true;

                                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(phone.getWindowToken(), 0);

                            }
                            else{

                                count = 0;
                            }

                        }

                    }, 3000);
                }
                else {

                    count ++ ;

                }

            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefs.saveIntPerferences(SP_CHANGE, 0);
                finish();
            }
        });

        call_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (prefs.getStringPreferences(CALL_CENTER_NO).trim().length() > 0) {

                    final ArrayList<String> phoneArray = new ArrayList<String>();
                    StringTokenizer st = new StringTokenizer(prefs.getStringPreferences(CALL_CENTER_NO), ",");
                    while (st.hasMoreTokens()) {
                        phoneArray.add(st.nextToken());
                    }
                    if (phoneArray.size() > 1) {
                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_call);
                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        LinearLayout mainlayout = (LinearLayout) dialog.findViewById(R.id.layout);

                        CustomTextView title1 = (CustomTextView) dialog.findViewById(R.id.title);
                        title1.setText(resources.getString(R.string.call_phone_dialog));
                        for(int i = 0; i < phoneArray.size(); i ++){
                            final String phoneString = phoneArray.get(i);
                            LinearLayout phoneLayout = new LinearLayout(LoginActivity.this);
                            phoneLayout.setOrientation(LinearLayout.VERTICAL);
                            phoneLayout.setPadding(0, 10, 0, 0);

                            CustomTextView price = new CustomTextView(LoginActivity.this);
                            price.setTextSize(16);
                            price.setPadding(10, 10, 10, 10);
                            price.setGravity(Gravity.LEFT);
                            price.setText(phoneString);
                            phoneLayout.addView(price);

                            CustomTextView choose = new CustomTextView(LoginActivity.this);
                            choose.setBackgroundResource(R.drawable.textview_round);
                            choose.setTextSize(14);
                            choose.setText(resources.getString(R.string.choose));
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            parms.gravity = Gravity.RIGHT;
                            parms.setMargins(0, 5, 5, 10);
                            choose.setLayoutParams(parms);
                            phoneLayout.addView(choose);


                            TextView space = new TextView(LoginActivity.this);
                            space.setHeight(6);
                            space.setBackgroundResource(R.color.checked_bg);
                            phoneLayout.addView(space);
                            mainlayout.addView(phoneLayout);

                            phoneLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {

                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source","campain");
                                        mix.put("call_id",phoneString);
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
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (prefs.isNetworkAvailable()){

                    if (loginWithPassword){
                        if(password.getText().toString().trim().length() == 0){

                            ToastHelper.showToast(LoginActivity.this, resources.getString(R.string.error_password));

                        }
                        else if(phonePass.getText().toString().trim().length() == 0){

                            ToastHelper.showToast(LoginActivity.this, resources.getString(R.string.order_dialog_phone_no_error));

                        }
                        else{
                            progressBar.setVisibility(View.VISIBLE);
                            myAccountLogin(phonePass.getText().toString(), password.getText().toString(), "", "",
                                    "","", 0, 0);
                        }
                    }
                    else{

                        if(phone != null && phone.getText().toString().trim().length() > 0) {
                            if(phone.getText().toString().trim().length() < 6 && phone.getText().toString().trim().length() > 13){

                                ToastHelper.showToast(LoginActivity.this, resources.getString(R.string.order_dialog_phone_no_error));


                            }else {

                                if (prefs.getIntPreferences(COUNT_DOWN) == 0){

                                    progressBar.setVisibility(View.VISIBLE);

                                    String str = phone.getText().toString();
                                    if (str.startsWith("09")){
                                        checkPhoneNumber(phone.getText().toString());
                                    }
                                    else{
                                        checkPhoneNumber("09" + phone.getText().toString());
                                    }

                                }
                                else{

                                    timer.setVisibility(View.VISIBLE);


                                    ToastHelper.showToast(LoginActivity.this, resources.getString(R.string.otp_time));

                                }


                            }

                        }else{

                            ToastHelper.showToast(LoginActivity.this, resources.getString(R.string.error_phone_number));


                        }

                    }


                }
                else{

                    ToastHelper.showToast(LoginActivity.this, resources.getString(R.string.no_internet_error));


                }

            }
        });



    }

    private void myAccountLogin( String phone, String pass,String name, String image, String parent_type, String kid_type, int month, int year){


        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("name",          name);
            uploadMessage.put("phone",           phone);
            if(!pass.equals(""))
                uploadMessage.put("password",    pass);
            if(!image.equals(""))
                uploadMessage.put("image",    image);
            if(!parent_type.equals(""))
                uploadMessage.put("momordad",  parent_type);
            if(!kid_type.equals(""))
                uploadMessage.put("boyorgirl",  kid_type);
            if(month != 0)
                uploadMessage.put("month",  month);
            if(year != 0)
                uploadMessage.put("year",  year);
            uploadMessage.put("password_confirmation",    pass);

            uploadMessage.put("fcm_token",    prefs.getStringPreferences(SP_FCM_TOEKN));
            uploadMessage.put("manufacturer", prefs.getStringPreferences(SP_DEVICE_MANUFACTURER));
            uploadMessage.put("model", prefs.getStringPreferences(SP_DEVICE_MODEL));
            uploadMessage.put("device_id", prefs.getStringPreferences(SP_DEVICE_ID));
            uploadMessage.put("imei", prefs.getStringPreferences(SP_DEVICE_IMEI));


        } catch (JSONException e) {
            e.printStackTrace();
        }



        prefs.saveStringPreferences(SP_USER_PASSWORD, password.getText().toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantGetCustomerDetail, uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        prefs.remove(SP_USER_SIGNUP);
                        progressBar.setVisibility(View.GONE);

                        try {
                            if(response.getInt("member_id") == 0){

                                ToastHelper.showToast(LoginActivity.this, resources.getString(R.string.error_correct_password));

                            }else {

                                prefs.saveStringPreferences(SP_USER_PHONE, response.getString("phone"));
                                prefs.saveStringPreferences(SP_USER_TOKEN, response.getString("authentication_token"));
                                prefs.saveStringPreferences(SP_USER_NAME, response.getString("name"));
                                prefs.saveStringPreferences(SP_USER_VIP_ACCOUNT, response.getString("vip"));
                                prefs.saveIntPerferences(SP_MEMBER_ID, response.getInt("member_id"));
                                prefs.saveStringPreferences(SP_USER_FRESH_CHAT_ID, response.getString("freshchat_id"));
                                if(response.getString(("profile_url")) != null){
                                    prefs.saveStringPreferences(SP_USER_PROFILEIMAGE, response.getString("profile_url"));
                                }else
                                    prefs.saveStringPreferences(SP_USER_PROFILEIMAGE, "");

                                prefs.saveIntPerferences(SP_USER_POINT, response.getInt("point"));
                                prefs.saveStringPreferences(SP_USER_MOMORDAD, response.getString("parent_status"));
                                prefs.saveStringPreferences(SP_USER_BOYORGIRL, response.getString("kid_gender"));
                                prefs.saveIntPerferences(SP_USER_MONOTH, response.getInt("birth_month"));
                                prefs.saveIntPerferences(SP_USER_YEAR, response.getInt("birth_year"));

                                if (response.getString("vip").trim().length() == 0) {
                                    prefs.saveIntPerferences(SP_VIP_ID, 0);
                                } else {
                                    prefs.saveIntPerferences(SP_VIP_ID, 1);
                                }



                                try {

                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("user_phone", prefs.getStringPreferences(SP_USER_PHONE));
                                    mix.put("name", prefs.getStringPreferences(SP_USER_NAME));
                                    FlurryAgent.logEvent("Login", mix);
                                } catch (Exception e) {
                                }

                           /*     try{
                                    Map<String, Object> eventValue = new HashMap<String, Object>();
                                    eventValue.put(AFInAppEventParameterName.CONTENT_TYPE,"Login_In");
                                    eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, prefs.getIntPreferences(SP_MEMBER_ID));
                                    AppsFlyerLib.getInstance().trackEvent(getApplicationContext() , AFInAppEventType.LOGIN , eventValue);



                                }catch (Exception e){
                                    Log.e(TAG, "AppsFlyerLib Exception : "  + e.getMessage() );
                                }*/

                                try{
                                    FreshchatConfig freshchatConfig=new FreshchatConfig(SP_FRESH_CAHT_ID,SP_FRESH_CHAT_KEY);
                                    Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);
                                    Freshchat.getInstance(getApplicationContext()).identifyUser(String.valueOf(prefs.getIntPreferences(SP_MEMBER_ID)), prefs.getStringPreferences(SP_USER_FRESH_CHAT_ID));
                                    FreshchatUser freshUser=Freshchat.getInstance(getApplicationContext()).getUser();
                                    freshUser.setFirstName(prefs.getStringPreferences(SP_USER_NAME+""));
                                    freshUser.setEmail(prefs.getStringPreferences(SP_USER_TOKEN));
                                    freshUser.setPhone("+95", prefs.getStringPreferences(SP_USER_PHONE));

                                    Freshchat.getInstance(getApplicationContext()).setUser(freshUser);

                                    Freshchat.getInstance(LoginActivity.this).setPushRegistrationToken(prefs.getStringPreferences(SP_FCM_TOEKN));
                                }catch (Exception e){

                                }

                                if (prefs.getIntPreferences(SP_CHANGE) == 1){
                                    prefs.saveIntPerferences(SP_CHANGE, 0);
                                    prefs.saveIntPerferences(ConstantVariable.COUNT_DOWN, 0);
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else if (prefs.getBooleanPreference(LOGIN_SAVECART)){
                                    prefs.saveBooleanPreference(LOGIN_SAVECART, false);
                                    Intent intent = new Intent(LoginActivity.this,ShoppingCartActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    finish();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: "  + e.getMessage() );

                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                prefs.remove(SP_USER_SIGNUP);
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"sign_in");
    }

    public  void setPhoneDate(){
        String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        prefs.saveStringPreferences(SP_DEVICE_ID, device_id);

        prefs.saveStringPreferences(SP_DEVICE_MANUFACTURER, Build.MANUFACTURER);
        prefs.saveStringPreferences(SP_DEVICE_MODEL, Build.MODEL);
        prefs.saveStringPreferences(SP_DEVICE_IMEI, Build.SERIAL);
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.saveIntPerferences(SP_CHANGE, 0);
        finish();
    }

    private void checkPhoneNumber(final String phoneNumber){

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("phone",           phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantRequestOtp, uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        progressBar.setVisibility(View.GONE);
                        try{
                            try {

                                Map<String, String> mix = new HashMap<String, String>();
                                FlurryAgent.logEvent("Complete Phone Entry Page", mix);
                            } catch (Exception e) {
                            }


                            int status = response.getInt("status");
                            if (status == 1){
                                countDownTimer = new CountDownTimer(60000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {


                                        prefs.saveIntPerferences(COUNT_DOWN, 1 );
                                        prefs.saveIntPerferences(COUNT_DOWN_TIMER, (int)(millisUntilFinished / 1000) );
                                        timer.setText(String.format(resources.getString(R.string.otp_waiting_time) , "" + (int) (millisUntilFinished / 1000) ));

                                        timer.setVisibility(View.VISIBLE);
                                        milliseconds = millisUntilFinished / 1000;




                                    }

                                    @Override
                                    public void onFinish() {

                                        prefs.saveIntPerferences(COUNT_DOWN, 0);
                                        timer.setVisibility(View.GONE);

                                    }
                                };

                                countDownTimer.start();

                                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                                intent.putExtra("phone", phoneNumber);
                                startActivity(intent);
                                finish();
                            }
                            else{

                                ToastHelper.showToast(LoginActivity.this, resources.getString(R.string.check_number));

                            }
                        }catch (Exception e){
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                prefs.remove(SP_USER_SIGNUP);
                Log.e(TAG, "onErrorResponse: "   + error.getMessage() );
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"sign_in");
    }



}
