package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
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

public class LoginFillActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "LoginFillActivity";

    LinearLayout  back_layout, call_layout, name_layout,  linearParentStatus ;
    LinearLayout boy_layout, girl_layout, other_layout;
    CustomTextView boy, girl, other, txt_parent_status;
    RadioButton boy_radio, girl_radio, other_radio;

    CustomTextView to_contact, txt_title, txt_enter, txt_phone;
    CustomEditText ed_input;
    CustomButton btn_login;
    ProgressBar progress_bar;

    LinearLayout boy_child_layout, girl_child_layout, linearChildStatus;
    CustomTextView boy_child, girl_child,  txt_child_status;
    RadioButton boy_child_radio, girl_child_radio;

    LinearLayout linearCalendar;
    CustomTextView txtBdTitle, kid_birth_date;

    ImageView profileImage;

    Resources resources;

    Display display;
    MyPreference prefs;

    int hasAccount;
    String phone;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fill_form);

        Log.e(TAG, "onCreate: " );

        prefs   = new MyPreference(LoginFillActivity.this);
        display = getWindowManager().getDefaultDisplay();
        if(prefs.getStringPreferences(LANGUAGE) == ""){
            prefs.saveStringPreferences(LANGUAGE, LANGUAGE_MYANMAR);
        }
        Context context = LocaleHelper.setLocale(LoginFillActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();


        new MyFlurry(LoginFillActivity.this);


        back_layout = findViewById(R.id.back_layout);
        call_layout = findViewById(R.id.callLayout);
        name_layout = findViewById(R.id.linearName);
        to_contact = findViewById(R.id.to_contact);
        txt_title = findViewById(R.id.txt_title);
        txt_enter = findViewById(R.id.txt_enter);
        txt_phone = findViewById(R.id.txt_phone);

        ed_input = findViewById(R.id.ed_input);
        btn_login = findViewById(R.id.btn_login);
        progress_bar = findViewById(R.id.progress_bar);
        profileImage = findViewById(R.id.userProfile);

        linearParentStatus = findViewById(R.id.linearParentStatus);
        boy_layout  =findViewById(R.id.boy_layout);
        girl_layout  =findViewById(R.id.girl_layout);
        other_layout  =findViewById(R.id.other_layout);
        girl  =findViewById(R.id.girl);
        boy  =findViewById(R.id.boy);
        other  =findViewById(R.id.other);
        txt_parent_status  =findViewById(R.id.txt_parent_status);
        boy_radio = findViewById(R.id.radioboy);
        girl_radio = findViewById(R.id.radiogirl);
        other_radio = findViewById(R.id.radioother);

        linearChildStatus = findViewById(R.id.linearChildStatus);
        boy_child_layout  =findViewById(R.id.boy_child_layout);
        girl_child_layout  =findViewById(R.id.girl_child_layout);
        girl_child  =findViewById(R.id.girl_child);
        boy_child  =findViewById(R.id.boy_child);
        txt_child_status  =findViewById(R.id.txt_child_status);
        boy_child_radio = findViewById(R.id.radioboy_child);
        girl_child_radio = findViewById(R.id.radiogirl_child);

        linearCalendar = findViewById(R.id.linearCalendar);
        txtBdTitle = findViewById(R.id.txt_bd_title);
        kid_birth_date = findViewById(R.id.txt_bd);

        hasAccount = getIntent().getIntExtra("check_phone",0);
        phone = getIntent().getStringExtra("phone");

        profileImage.setVisibility(View.GONE);
        linearCalendar.setVisibility(View.GONE);
        linearChildStatus.setVisibility(View.GONE);
        linearParentStatus.setVisibility(View.GONE);
        txt_title.setText(resources.getString(R.string.fill_name));
        btn_login.setText(resources.getString(R.string.signup));


        back_layout.getLayoutParams().width = (display.getWidth() * 3 ) / 20;
        call_layout.getLayoutParams().width = (display.getWidth() * 17) / 20;
        call_layout.setVisibility(View.GONE);

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        txt_enter.setText(resources.getString(R.string.name_hint));
        txt_parent_status.setText(resources.getString(R.string.group_chat_detail_momordad));
        boy.setText(resources.getString(R.string.group_chat_detail_dad));
        girl.setText(resources.getString(R.string.group_chat_detail_mom));
        other.setText(resources.getString(R.string.family_member));



        txt_child_status.setText(resources.getString(R.string.group_chat_detail_boyorgirl));
        boy_child.setText(resources.getString(R.string.group_chat_detail_boy));
        girl_child.setText(resources.getString(R.string.group_chat_detail_girl));


        boy_layout.setOnClickListener(new ParentClickListener());
        girl_layout.setOnClickListener(new ParentClickListener());
        other_layout.setOnClickListener(new ParentClickListener());
        boy_child_layout.setOnClickListener(new ParentClickListener());
        girl_child_layout.setOnClickListener(new ParentClickListener());


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_input.getText().toString().trim().length() == 0){

                    ToastHelper.showToast(LoginFillActivity.this, resources.getString(R.string.group_chat_detail_name_error));

                }
                else{
                    progress_bar.setVisibility(View.VISIBLE);
                    myAccountLogin();
                }
            }
        });

    }
    private void myAccountLogin(){

        JSONObject uploadMessage = new JSONObject();
        try {

            uploadMessage.put("name",  ed_input.getText().toString());




        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, String.format(constantUpdateUserInfo, prefs.getIntPreferences(SP_MEMBER_ID)), uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progress_bar.setVisibility(View.GONE);

                        try{
                            if (response.getInt("status" ) == 1){
                                prefs.saveStringPreferences(SP_USER_NAME, ed_input.getText().toString());


                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("user_phone", prefs.getStringPreferences(SP_USER_PHONE));
                                    mix.put("name", prefs.getStringPreferences(SP_USER_NAME));
                                    FlurryAgent.logEvent("Signup", mix);
                                } catch (Exception e) {
                                }


                                if (prefs.getIntPreferences(SP_CHANGE) == 1){
                                    prefs.saveIntPerferences(SP_CHANGE, 0);
                                    Intent intent = new Intent(LoginFillActivity.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else if (prefs.getBooleanPreference(LOGIN_SAVECART)){
                                    prefs.saveBooleanPreference(LOGIN_SAVECART, false);
                                    Intent intent = new Intent(LoginFillActivity.this,ShoppingCartActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                                else
                                    finish();
                            }

                        }catch (Exception e){
                            Log.e(TAG, "onResponse: " + e.getMessage() );

                        }



                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progress_bar.setVisibility(View.GONE);

                ToastHelper.showToast(LoginFillActivity.this, resources.getString(R.string.no_internet_error));


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



    public  class ParentClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.boy_layout :
                    boy_radio.setChecked(true);
                    girl_radio.setChecked(false);
                    other_radio.setChecked(false);
                    linearChildStatus.setVisibility(View.VISIBLE);
                    break;
                case R.id.girl_layout :
                    boy_radio.setChecked(false);
                    girl_radio.setChecked(true);
                    other_radio.setChecked(false);
                    linearChildStatus.setVisibility(View.VISIBLE);
                    break;

                case R.id.other_layout :
                    boy_radio.setChecked(false);
                    girl_radio.setChecked(false);
                    other_radio.setChecked(true);
                    linearChildStatus.setVisibility(View.GONE);

                    break;

                case R.id.boy_child_layout :
                    boy_child_radio.setChecked(true);
                    girl_child_radio.setChecked(false);

                    break;

                case R.id.girl_child_layout :
                    boy_child_radio.setChecked(false);
                    girl_child_radio.setChecked(true);

                    break;

            }
        }
    }
}
