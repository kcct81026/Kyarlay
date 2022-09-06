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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "OtpActivity";


    CustomTextView title, txtEnter;
    LinearLayout back_layout;
    CustomEditText ed1;
    CustomButton btn;
    ProgressBar progress_bar;

    Resources resources;
    MyPreference prefs;
    String phone;
    Display display;

    FirebaseAnalytics firebaseAnalytics;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_otp);

        Log.e(TAG, "onCreate: " );


        prefs   = new MyPreference(OtpActivity.this);
        Context context = LocaleHelper.setLocale(OtpActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        new MyFlurry(OtpActivity.this);
        firebaseAnalytics   = FirebaseAnalytics.getInstance(OtpActivity.this);


        try {

            FlurryAgent.logEvent("View Otp Page");
        } catch (Exception e) {
        }

        phone = getIntent().getStringExtra("phone" );

        progress_bar = findViewById(R.id.progress_bar);
        back_layout = findViewById(R.id.back_layout);
        title = findViewById(R.id.txt_title);
        txtEnter = findViewById(R.id.txt_enter);
        ed1 = findViewById(R.id.otp1);
        display = getWindowManager().getDefaultDisplay();


        btn = findViewById(R.id.button);

        back_layout.getLayoutParams().width = (display.getWidth() * 3 ) / 20;

        title.setText(resources.getString(R.string.verification_code));
        txtEnter.setText(resources.getString(R.string.enter_verify_code));
        btn.setText(resources.getString(R.string.submit));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().isEmpty() ){

                    ToastHelper.showToast(OtpActivity.this, resources.getString(R.string.otp_code));


                }else{


                    if (prefs.isNetworkAvailable()){
                        progress_bar.setVisibility(View.VISIBLE);


                        sendOtpCode(ed1.getText().toString());
                    }
                }
            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.saveIntPerferences(SP_CHANGE, 0);
                finish();
            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    private void sendOtpCode(String otp){

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("phone",      phone);
            uploadMessage.put("otp",           otp);



        } catch (JSONException e) {
            e.printStackTrace();
        }





        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantCheckOtp, uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, "onResponse: "  + response.toString() );

                        progress_bar.setVisibility(View.GONE);

                        try{

                            prefs.saveIntPerferences(SP_MEMBER_ID, response.getInt("member_id"));
                            prefs.saveStringPreferences(SP_USER_NAME, response.getString("name"));
                            prefs.saveStringPreferences(SP_USER_PHONE, response.getString("phone"));
                            prefs.saveStringPreferences(SP_USER_TOKEN, response.getString("authentication_token"));
                            prefs.saveStringPreferences(SP_USER_VIP_ACCOUNT, response.getString("vip"));
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

                            String returnedName = response.getString("name");


                            if (response.getString("vip").trim().length() == 0) {
                                prefs.saveIntPerferences(SP_VIP_ID, 0);
                            } else {
                                prefs.saveIntPerferences(SP_VIP_ID, 1);
                            }


                            prefs.saveIntPerferences(SP_KYARLAY_ACCESS, response.getInt("admin"));

                            if (response.getString("phone").equals("")){

                                ToastHelper.showToast(OtpActivity.this, resources.getString(R.string.otp_error));

                            }

                            else if (returnedName != null && !returnedName.isEmpty() && !returnedName.equals("null")){


                                Bundle bundle = new Bundle();
                                bundle.putString("sign_up_method","phone");
                                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);


                                 try {

                                        Map<String, String> mix = new HashMap<String, String>();
                                        FlurryAgent.logEvent("Login", mix);

                                    } catch (Exception e) {
                                    }


                                if (prefs.getIntPreferences(SP_CHANGE) == 1){
                                    Log.e(TAG, "onResponse: ----------------------"  + "change" );
                                    prefs.saveIntPerferences(SP_CHANGE, 0);
                                    prefs.saveIntPerferences(ConstantVariable.COUNT_DOWN, 0);
                                    Intent intent = new Intent(OtpActivity.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else if (prefs.getBooleanPreference(LOGIN_SAVECART)){
                                    Log.e(TAG, "onResponse: ----------------------"  + "shopping cart" );
                                    prefs.saveBooleanPreference(LOGIN_SAVECART, false);
                                    Intent intent = new Intent(OtpActivity.this,ShoppingCartActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                                else {
                                    Log.e(TAG, "onResponse: ----------------------"  + "just finish" );
                                    finish();

                                }
                            }
                            else{
                                prefs.saveIntPerferences(ConstantVariable.COUNT_DOWN, 0);

                                try {


                                    Map<String, String> mix = new HashMap<String, String>();
                                    FlurryAgent.logEvent("View Profile Signup Page", mix);

                                } catch (Exception e) {
                                }

                              /*  try{
                                    Map<String, Object> eventValue = new HashMap<String, Object>();
                                    eventValue.put(AFInAppEventParameterName.CONTENT_TYPE,"Sign_Up");
                                    eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, prefs.getIntPreferences(SP_MEMBER_ID));
                                    AppsFlyerLib.getInstance().trackEvent(getApplicationContext() , AFInAppEventType.LOGIN , eventValue);



                                }catch (Exception e){
                                    Log.e(TAG, "AppsFlyerLib Exception : "  + e.getMessage() );
                                }*/


                                Bundle bundle = new Bundle();
                                bundle.putString("sign_up_method","phone");
                                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);

                                Intent intent = new Intent(OtpActivity.this, LoginFillActivity.class);
                                intent.putExtra("phone", phone);
                                startActivity(intent);
                                finish();
                            }


                        }catch (Exception e){
                            Log.e(TAG, "onResponse: Excetion "  + e.getMessage() );
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                prefs.remove(SP_USER_SIGNUP);
                progress_bar.setVisibility(View.GONE);
                prefs.saveIntPerferences(ConstantVariable.COUNT_DOWN, 0);
                Log.e(TAG, "onResponse: VolleyError Excetion "  + error.getMessage() );
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



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.saveIntPerferences(SP_CHANGE, 0);
        finish();

    }
}
