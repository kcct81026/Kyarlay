package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kyarlay.ayesunaing.R;
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

public class ActivityLogin extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "ActivityLogin";

    LinearLayout back_layout, callLayout;
    CustomTextView txtShopTitle, txtTitle, txtContinue, to_contact;
    PrefixEditText editPhone;

    MyPreference prefs;
    AppCompatActivity activity;
    FirebaseAnalytics firebaseAnalytics;
    Resources resources;
    CountDownTimer countDownTimer = null;
    long milliseconds;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login);

        Log.e(TAG, "onCreate: "   );

        activity = (AppCompatActivity) ActivityLogin.this;
        prefs = new MyPreference(activity);
        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);

        if(prefs.getStringPreferences(LANGUAGE) == ""){
            prefs.saveStringPreferences(LANGUAGE, LANGUAGE_MYANMAR);
        }

        if(prefs != null && !prefs.isContains(SP_DEVICE_IMEI) && !prefs.isContains(SP_DEVICE_ID) && !prefs.isContains(SP_DEVICE_MANUFACTURER)){
            setPhoneDate();
        }
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        new MyFlurry(activity);
        try {


            Map<String, String> mix = new HashMap<String, String>();
            FlurryAgent.logEvent("View Phone Entry Page", mix);
        } catch (Exception e) {
        }





        back_layout = findViewById(R.id.back_layout);
        txtShopTitle = findViewById(R.id.txtShopTitle);
        txtTitle = findViewById(R.id.txtTitle);
        editPhone = findViewById(R.id.editPhone);
        txtContinue = findViewById(R.id.txtContinue);
        callLayout = findViewById(R.id.callLayout);
        to_contact = findViewById(R.id.to_contact);




        txtShopTitle.setText("Shop with KyarLay App");
        txtShopTitle.setTypeface(txtShopTitle.getTypeface(), Typeface.BOLD);
        txtTitle.setText(resources.getString(R.string.login_register_title));
        txtContinue.setText(resources.getString(R.string.phone_number_continue));
        to_contact.setText(resources.getString(R.string.to_contact));


        editPhone.setBackgroundResource(R.drawable.border_grey);
        GradientDrawable drawable = (GradientDrawable) editPhone.getBackground();
        drawable.setColor(activity.getResources().getColor(R.color.custom_grey));

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.saveIntPerferences(SP_CHANGE, 0);
                finish();
            }
        });

        editPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editPhone.getText().toString().length() == 0)
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                else
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtContinue.setClickable(false);
                if(editPhone.getText().toString().trim().length() > 0) {
                    if(editPhone.getText().toString().trim().length() < 6 && editPhone.getText().toString().trim().length() > 13){

                        ToastHelper.showToast(ActivityLogin.this, resources.getString(R.string.order_dialog_phone_no_error));
                        txtContinue.setClickable(true);


                    }else {

                        String str = editPhone.getText().toString();
                        if (str.startsWith("09")){
                            checkPhoneNumber(editPhone.getText().toString());
                        }
                        else{
                            checkPhoneNumber("09" + editPhone.getText().toString());
                        }



                    }

                }else{

                    ToastHelper.showToast(ActivityLogin.this, resources.getString(R.string.error_phone_number));


                }


            }
        });
    }


    private void checkPhoneNumber(final String phoneNumber){

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("phone",  phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantRequestOtp, uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, "onResponse: "  + response.toString() );


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

                                        milliseconds = millisUntilFinished / 1000;

                                    }

                                    @Override
                                    public void onFinish() {

                                        prefs.saveIntPerferences(COUNT_DOWN, 0);

                                    }
                                };

                                countDownTimer.start();

                                Intent intent = new Intent(ActivityLogin.this, ActivityOtp.class);
                                intent.putExtra("phone", phoneNumber);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                txtContinue.setClickable(true);

                                ToastHelper.showToast(ActivityLogin.this, resources.getString(R.string.check_number));

                            }
                        }catch (Exception e){
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                txtContinue.setClickable(true);
                prefs.remove(SP_USER_SIGNUP);
                Log.e(TAG, "onErrorResponse: "   + error.getMessage() );
                ToastHelper.showToast(activity, resources.getString(R.string.search_error));
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


    public  void setPhoneDate(){
        String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        prefs.saveStringPreferences(SP_DEVICE_ID, device_id);

        prefs.saveStringPreferences(SP_DEVICE_MANUFACTURER, Build.MANUFACTURER);
        prefs.saveStringPreferences(SP_DEVICE_MODEL, Build.MODEL);
        prefs.saveStringPreferences(SP_DEVICE_IMEI, Build.SERIAL);
    }

}
