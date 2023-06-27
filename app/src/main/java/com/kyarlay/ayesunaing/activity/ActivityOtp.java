package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ActivityOtp extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "ActivityOtp";

    LinearLayout back_layout,callLayout;
    CustomTextView txtShopTitle,toolTitle, txtError,  txtTitle, txtCountDown,txtContinue,to_contact,txtResend;
    EditText ed1, ed2, ed3, ed4;

    Resources resources;
    MyPreference prefs;
    String phone;
    FirebaseAnalytics firebaseAnalytics;
    CountDownTimer countDownTimer = null;
    long milliseconds;
    AppCompatActivity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_opt);

        Log.e(TAG, "onCreate: "   );


        prefs   = new MyPreference(ActivityOtp.this);
        Context context = LocaleHelper.setLocale(ActivityOtp.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        activity = ActivityOtp.this;

       // new MyFlurry(ActivityOtp.this);
        firebaseAnalytics   = FirebaseAnalytics.getInstance(ActivityOtp.this);
        try {

            //FlurryAgent.logEvent("View Otp Page");
        } catch (Exception e) {
        }
        phone = getIntent().getStringExtra("phone" );


        if(prefs != null && !prefs.isContains(COUNT_DOWN)){
            prefs.saveIntPerferences(COUNT_DOWN,0);
        }


        ed1 = findViewById(R.id.otp1);
        ed2 = findViewById(R.id.otp2);
        ed3 = findViewById(R.id.otp3);
        ed4 = findViewById(R.id.otp4);
        back_layout = findViewById(R.id.back_layout);
        toolTitle = findViewById(R.id.toolTitle);
        txtShopTitle = findViewById(R.id.txtShopTitle);
        txtTitle = findViewById(R.id.txtTitle);
        txtCountDown = findViewById(R.id.txtCountDown);
        txtContinue = findViewById(R.id.txtContinue);
        to_contact = findViewById(R.id.to_contact);
        callLayout = findViewById(R.id.callLayout);
        txtResend = findViewById(R.id.txtResend);
        txtError = findViewById(R.id.txtError);



        toolTitle.setText("Verification");
        txtResend.setText("Resend Code");
        txtShopTitle.setText("Verify your phone number");
        txtTitle.setText(resources.getString(R.string.enter_verify_code));
        txtShopTitle.setTypeface(txtShopTitle.getTypeface(), Typeface.BOLD);
        txtResend.setTypeface(txtShopTitle.getTypeface(), Typeface.BOLD);
        txtContinue.setText(resources.getString(R.string.submit));
        to_contact.setText(resources.getString(R.string.to_contact));


        if (prefs.getIntPreferences(COUNT_DOWN) == 0){
            txtCountDown.setVisibility(View.GONE);
        }else {
            txtCountDown.setVisibility(View.VISIBLE);
            int testTime = prefs.getIntPreferences(COUNT_DOWN_TIMER);

            CountDownTimer cdt = new CountDownTimer(testTime * 1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    prefs.saveIntPerferences(COUNT_DOWN_TIMER, (int)(millisUntilFinished / 1000) );
                    txtCountDown.setText((int)(millisUntilFinished / 1000) + "");
                    txtCountDown.setVisibility(View.VISIBLE);
                    txtResend.setEnabled(false);
                    //txtResend.setTextColor(Color.DKGRAY);


                }

                @Override
                public void onFinish() {

                    prefs.saveIntPerferences(COUNT_DOWN, 0);
                    txtCountDown.setVisibility(View.GONE);
                    txtResend.setEnabled(true);
                   // txtResend.setTextColor(Color.GREEN);
                }
            };

            cdt.start();
        }

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtError.setVisibility(View.GONE);
                txtResend.setEnabled(false);

                checkPhoneNumber();

            }
        });

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtContinue.setClickable(false);

                txtError.setVisibility(View.GONE);
                int check=1;
                if (ed1.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed2.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed3.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed4.getText().toString().trim().length() == 0){
                    check = 0;
                }

                if (check == 0){
                    txtContinue.setClickable(true);
                    ToastHelper.showToast(ActivityOtp.this, resources.getString(R.string.otp_code));
                }else if (check == 1){
                    if (prefs.isNetworkAvailable()){


                        sendOtpCode(ed1.getText().toString().trim() + ed2.getText().toString().trim() + ed3.getText().toString().trim() + ed4.getText().toString().trim());
                    }
                }




            }
        });


        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int check=1;
                if (ed1.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed2.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed3.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed4.getText().toString().trim().length() == 0){
                    check = 0;
                }

                if (check == 0){
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                }else if (check == 1){
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    ed2.requestFocus();
                }
                else if(s.length()==0)
                {
                    ed1.clearFocus();
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                }
            }
        });

        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int check=1;
                if (ed1.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed2.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed3.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed4.getText().toString().trim().length() == 0){
                    check = 0;
                }

                if (check == 0){
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                }else if (check == 1){
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    ed3.requestFocus();
                }
                else if(s.length()==0)
                {
                    ed1.requestFocus();
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                }
            }
        });

        ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int check=1;
                if (ed1.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed2.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed3.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed4.getText().toString().trim().length() == 0){
                    check = 0;
                }

                if (check == 0){
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                }else if (check == 1){
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    ed4.requestFocus();
                }
                else if(s.length()==0)
                {
                    ed2.requestFocus();
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                }
            }
        });

        ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int check=1;
                if (ed1.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed2.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed3.getText().toString().trim().length() == 0){
                    check = 0;
                }
                else if (ed4.getText().toString().trim().length() == 0){
                    check = 0;
                }

                if (check == 0){
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                }else if (check == 1){
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    /*InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ed4.getWindowToken(), 0);*/
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));

                }
                else if(s.length()==0)
                {
                    ed3.requestFocus();
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                }
            }
        });



        callLayout.setOnClickListener(new View.OnClickListener() {
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
                        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        LinearLayout mainlayout = (LinearLayout) dialog.findViewById(R.id.layout);

                        CustomTextView title1 = (CustomTextView) dialog.findViewById(R.id.title);
                        title1.setText(resources.getString(R.string.product_options));
                        for(int i = 0; i < phoneArray.size(); i ++) {
                            final String phoneString = phoneArray.get(i);
                            LinearLayout phoneLayout = new LinearLayout(activity);
                            phoneLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            phoneLayout.setOrientation(LinearLayout.HORIZONTAL);
                            phoneLayout.setPadding(0, 20, 0, 20);


                            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            childParam1.weight = 0.1f;
                            CustomTextView price = new CustomTextView(activity);
                            price.setTextSize(16);
                            price.setPadding(10, 10, 10, 10);
                            //price.setGravity(Gravity.LEFT);
                            price.setText(phoneString);
                            price.setLayoutParams(childParam1);


                            RadioButton radioButton = new RadioButton(activity);
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            parms.gravity = Gravity.CENTER;
                            parms.weight = 0.9f;
                            radioButton.setLayoutParams(parms);

                            radioButton.setClickable(false);

                            phoneLayout.addView(price);
                            phoneLayout.addView(radioButton);
                            phoneLayout.setWeightSum(1f);


                            TextView space = new TextView(activity);
                            space.setHeight(6);
                            space.setBackgroundResource(R.color.checked_bg);

                            mainlayout.addView(phoneLayout);
                            mainlayout.addView(space);

                            phoneLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {

                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "login");
                                        mix.put("call_id", phoneString);
                                        //FlurryAgent.logEvent("Call Call Center", mix);
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
                        txtContinue.setClickable(true);

                        /*
                        {"member_id":38211,"name":"Khin Cho","phone":"09421021465","authentication_token":"87_YECZ7xbfzjby8rvP7","freshchat_id":"13047f19-00e4-4863-b283-d37047c3c455","vip":"","birth_month":12,"birth_year":2018,"kid_gender":"boy","parent_status":"mother","customer_type":"normal_customer","profile_url":"https:\/\/s3.ap-southeast-1.amazonaws.com\/kyarlay\/public\/kyarlay_1643771453686.jpg","point":97774,"sign_up_bonus":1,"first_post_bonus":1,"address":"ၾကည့္ျမင္တိုင္ 23 ဟုမ္းလမ္း ၾကည့္ျမင္တိုင္","admin":1}
                         */

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

                                //ToastHelper.showToast(ActivityOtp.this, resources.getString(R.string.otp_error));
                                txtError.setText(resources.getString(R.string.otp_error));
                                txtError.setVisibility(View.VISIBLE);



                            }

                            else if (returnedName != null && !returnedName.isEmpty() && !returnedName.equals("null")){


                                Bundle bundle = new Bundle();
                                bundle.putString("sign_up_method","phone");
                                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);


                                Log.e(TAG, "onResponse: -----------------"  + prefs.getIntPreferences(SP_MEMBER_ID) );
                                showVerifiedDialog();



                            }
                            else{
                                prefs.saveIntPerferences(ConstantVariable.COUNT_DOWN, 0);

                                try {


                                    Map<String, String> mix = new HashMap<String, String>();
                                    //FlurryAgent.logEvent("View Profile Signup Page", mix);

                                } catch (Exception e) {
                                }


                                Bundle bundle = new Bundle();
                                bundle.putString("sign_up_method","phone");
                                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);

                                Intent intent = new Intent(ActivityOtp.this, ActivityNameForm.class);
                                intent.putExtra("phone", phone);
                                startActivity(intent);
                                finish();
                            }


                        }catch (Exception e){
                            Log.e(TAG, "onResponse: Excetion ****************************** "  + e.getMessage() );
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                txtContinue.setClickable(true);
                prefs.remove(SP_USER_SIGNUP);
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

    private void checkPhoneNumber(){

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("phone",  phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantRequestOtp, uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, "onResponse: "   + " ************  " + response.toString() );


                        try{


                            int status = response.getInt("status");
                            if (status == 1){
                                //txtResend.setTextColor(getResources().getColor(R.color.text));
                                txtResend.setEnabled(false);
                                txtResend.setTextColor(Color.DKGRAY);
                                ed1.setText("");
                                ed2.setText("");
                                ed3.setText("");
                                ed4.setText("");
                                ed1.requestFocus();

                                countDownTimer = new CountDownTimer(60000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {


                                        prefs.saveIntPerferences(COUNT_DOWN, 1 );
                                        prefs.saveIntPerferences(COUNT_DOWN_TIMER, (int)(millisUntilFinished / 1000) );
                                        //txtCountDown.setText(String.format(resources.getString(R.string.otp_waiting_time) , "" + (int) (millisUntilFinished / 1000) ));
                                        txtCountDown.setText((int)(millisUntilFinished / 1000) + "");
                                        txtCountDown.setVisibility(View.VISIBLE);
                                        milliseconds = millisUntilFinished / 1000;
                                        txtResend.setEnabled(false);
                                        //txtResend.setTextColor(Color.DKGRAY);



                                    }

                                    @Override
                                    public void onFinish() {

                                        prefs.saveIntPerferences(COUNT_DOWN, 0);
                                        txtCountDown.setVisibility(View.GONE);

                                        txtResend.setEnabled(true);
                                        //txtResend.setTextColor(Color.GREEN);

                                    }
                                };

                                countDownTimer.start();
                            }
                            else{

                                ToastHelper.showToast(ActivityOtp.this, resources.getString(R.string.check_number));

                            }
                        }catch (Exception e){
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                prefs.remove(SP_USER_SIGNUP);
                Log.e(TAG, "onErrorResponse: "   + error.getMessage() );
            }
        }) {

            /*
            Passing some request headers*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"sign_in");
    }

    private void showVerifiedDialog(){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_verified);

        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        //  wlp.width = getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);

        dialog.show();


        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub

                dialog.dismiss();

                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    //FlurryAgent.logEvent("Login", mix);

                } catch (Exception e) {
                }

                if (prefs.getIntPreferences(SP_CHANGE) == 1){
                    Log.e(TAG, "onFinish: ---------------------------- - change"  );
                    prefs.saveIntPerferences(SP_CHANGE, 0);
                    prefs.saveIntPerferences(ConstantVariable.COUNT_DOWN, 0);
                    Intent intent = new Intent(ActivityOtp.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else if (prefs.getBooleanPreference(LOGIN_SAVECART)){
                    Log.e(TAG, "onFinish: ---------------------------- - shopping"  );
                    prefs.saveBooleanPreference(LOGIN_SAVECART, false);
                    Intent intent = new Intent(ActivityOtp.this,ShoppingCartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                else {
                    Log.e(TAG, "onFinish: ---------------------------- - just finish"  );
                    prefs.saveBooleanPreference(LOGIN_MAIN, true);
                    finish();

                }



            }
        }.start();



    }



}
