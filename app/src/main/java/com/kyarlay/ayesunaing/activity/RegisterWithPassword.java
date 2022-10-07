package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CompoundButton;
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
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.FreshchatUser;
import com.kyarlay.ayesunaing.BuildConfig;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomSwitchView;
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

public class RegisterWithPassword extends AppCompatActivity implements Constant, ConstantVariable {
    private static final String TAG = "RegisterWithPassword";

    // widget
    LinearLayout back_layout;
    ProgressBar progressBar;
    CustomTextView title,titlePhone,titleName,titlePassword,titleConfirm;
    CustomEditText phone  ,name,password,confirm_password;
    CustomButton button;
    CustomSwitchView switch_password;
    // vars
    Resources resources;

    Display display;
    MyPreference prefs;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        Log.e(TAG, "onCreate: "  );


        prefs   = new MyPreference(RegisterWithPassword.this);
        display = getWindowManager().getDefaultDisplay();

        Context context = LocaleHelper.setLocale(RegisterWithPassword.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        new MyFlurry(RegisterWithPassword.this);

        back_layout = findViewById(R.id.back_layout);
        back_layout.getLayoutParams().width = (display.getWidth() * 3 ) / 20;
        progressBar     = findViewById(R.id.progress_bar);

        title = findViewById(R.id.title);
        titlePhone = findViewById(R.id.titlePhone);
        phone = findViewById(R.id.txtPhone);
        titleName = findViewById(R.id.titleName);
        name = findViewById(R.id.txtName);
        titlePassword = findViewById(R.id.titlePassword);
        password = findViewById(R.id.txtPassword);
        titleConfirm = findViewById(R.id.titleConfirm);
        confirm_password = findViewById(R.id.txtConfirm);
        button = findViewById(R.id.button);

        title.setText(resources.getString(R.string.signup));
        titlePhone.setText(resources.getString(R.string.phone_number_hint));
        titleName.setText(resources.getString(R.string.name_hint));
        titlePassword.setText(resources.getString(R.string.password_hint));
        titleConfirm.setText(resources.getString(R.string.confirm_password_hint));
        button.setText(resources.getString(R.string.signup));

        switch_password = (CustomSwitchView) findViewById(R.id.switch_password);
        switch_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(prefs.getIntPreferences("password_show") == 0) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    prefs.saveIntPerferences("password_show", 1);
                }
                else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    prefs.saveIntPerferences("password_show", 0);
                }
            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name != null && name.getText().toString().trim().length() > 0 &&
                        phone != null && phone.getText().toString().trim().length() > 0 &&
                password != null && password.getText().toString().trim().length() > 0 &&
                        confirm_password  != null && confirm_password.getText().toString().trim().length() > 0 ){

                    if(password.getText().toString().equals(confirm_password.getText().toString())){
                        if(password.getText().toString().trim().length() > 5) {
                            progressBar.setVisibility(View.VISIBLE);
                            myAccountLogin(phone.getText().toString(), password.getText().toString(), name.getText().toString());

                        }

                        else{

                            ToastHelper.showToast(RegisterWithPassword.this, resources.getString(R.string.password_length_wrong));


                        }
                    }else {

                        ToastHelper.showToast(RegisterWithPassword.this, resources.getString(R.string.wrong_password));

                    }


                }else{

                    ToastHelper.showToast(RegisterWithPassword.this, resources.getString(R.string.error_password_name));

                }

            }
        });
    }

    public void checkPhoneNumber(final String phoneNumber){
        String url  = "https://www.kyarlay.com/api/customers/has_account?phone="+phoneNumber;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            try {

                                Map<String, String> mix = new HashMap<String, String>();
                                FlurryAgent.logEvent("Complete Phone Entry Page", mix);

                            } catch (Exception e) {
                            }

                            int has_account = response.getInt("has_account");



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse:  " + e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {}
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


    private void myAccountLogin(final String phone,
                                final String password, final  String name){


        JSONObject uploadOrder = new JSONObject();
        try {
            uploadOrder.put("name",          name);
            uploadOrder.put("phone",           phone);
            uploadOrder.put("password",    password);
            uploadOrder.put("image",    "");
            uploadOrder.put("password_confirmation",    password);
            uploadOrder.put("fcm_token",    prefs.getStringPreferences(SP_FCM_TOEKN));
            uploadOrder.put("manufacturer", prefs.getStringPreferences(SP_DEVICE_MANUFACTURER));
            uploadOrder.put("model", prefs.getStringPreferences(SP_DEVICE_MODEL));
            uploadOrder.put("device_id", prefs.getStringPreferences(SP_DEVICE_ID));
            uploadOrder.put("imei", prefs.getStringPreferences(SP_DEVICE_IMEI));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        prefs.saveStringPreferences(SP_USER_PASSWORD, password);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantGetCustomerDetail, uploadOrder,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);
                        prefs.remove(SP_USER_SIGNUP);
                        try {
                            if(response.getInt("member_id") == 0){

                                ToastHelper.showToast(RegisterWithPassword.this, "This phone number is already registerd!");

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
                                    FlurryAgent.logEvent("Signup", mix);

                                } catch (Exception e) {
                                }

                                try{
                                    FreshchatConfig freshchatConfig=new FreshchatConfig(BuildConfig.FRESH_CAHT_ID,BuildConfig.FRESH_CHAT_KEY);
                                    Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);
                                    Freshchat.getInstance(getApplicationContext()).identifyUser(String.valueOf(prefs.getIntPreferences(SP_MEMBER_ID)), prefs.getStringPreferences(SP_USER_FRESH_CHAT_ID));
                                    FreshchatUser freshUser=Freshchat.getInstance(getApplicationContext()).getUser();
                                    freshUser.setFirstName(prefs.getStringPreferences(SP_USER_NAME+""));
                                    freshUser.setEmail(prefs.getStringPreferences(SP_USER_TOKEN));
                                    freshUser.setPhone("+95", prefs.getStringPreferences(SP_USER_PHONE));

                                    Freshchat.getInstance(getApplicationContext()).setUser(freshUser);

                                    Freshchat.getInstance(RegisterWithPassword.this).setPushRegistrationToken(prefs.getStringPreferences(SP_FCM_TOEKN));

                                }catch (Exception e){

                                }
                                if (prefs.getIntPreferences(SP_CHANGE) == 1){
                                    prefs.saveIntPerferences(SP_CHANGE, 0);
                                    prefs.saveIntPerferences(ConstantVariable.COUNT_DOWN, 0);
                                    Intent intent = new Intent(RegisterWithPassword.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else if (prefs.getBooleanPreference(LOGIN_SAVECART)){
                                    prefs.saveBooleanPreference(LOGIN_SAVECART, false);
                                    Intent intent = new Intent(RegisterWithPassword.this,ShoppingCartActivity.class);
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
}
