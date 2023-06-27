package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityProfileCompleted extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "ActivityProfileCom";

    CustomTextView txtTitle,txtText2,txtText3,txtContinue;

    MyPreference prefs;
    Resources resources;
    AppCompatActivity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_finish_profile);

        activity = ActivityProfileCompleted.this;
        prefs   = new MyPreference(ActivityProfileCompleted.this);
        if(prefs.getStringPreferences(LANGUAGE) == ""){
            prefs.saveStringPreferences(LANGUAGE, LANGUAGE_MYANMAR);
        }
        Context context = LocaleHelper.setLocale(ActivityProfileCompleted.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
       // new MyFlurry(ActivityProfileCompleted.this);

        txtTitle = findViewById(R.id.txtTitle);
        txtText2 = findViewById(R.id.txtText2);
        txtText3 = findViewById(R.id.txtText3);
        txtContinue = findViewById(R.id.txtContinue);

        txtContinue.setText(resources.getString(R.string.added_to_cart_cancel));
        txtTitle.setText(resources.getString(R.string.get_point_text_one));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtText2.setText(Html.fromHtml(
                    resources.getString(R.string.get_point_text_two)  , Html.FROM_HTML_MODE_COMPACT));

            txtText3.setText(Html.fromHtml(
                    resources.getString(R.string.get_point_text_three)  , Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtText2.setText(Html.fromHtml(resources.getString(R.string.get_point_text_two)));
            txtText3.setText(Html.fromHtml(resources.getString(R.string.get_point_text_three)));
        }




        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCustomerInfo();


            }
        });


    }
    public void getCustomerInfo(){
        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantGetCustomerInfo, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {



                try {
                    prefs.saveStringPreferences(SP_USER_PHONE, response.getString("phone"));
                    prefs.saveStringPreferences(SP_USER_NAME, response.getString("name"));
                    prefs.saveStringPreferences(SP_USER_VIP_ACCOUNT, response.getString("vip"));
                    prefs.saveIntPerferences(SP_USER_MONOTH, response.getInt("birth_month"));
                    prefs.saveIntPerferences(SP_USER_YEAR, response.getInt("birth_year"));
                    prefs.saveStringPreferences(SP_USER_BOYORGIRL, response.getString("kid_gender"));
                    prefs.saveIntPerferences(SP_MEMBER_ID, response.getInt("id"));
                    if(response.getString(("profile_url")) != null){
                        prefs.saveStringPreferences(SP_USER_PROFILEIMAGE, response.getString("profile_url"));
                    }else
                        prefs.saveStringPreferences(SP_USER_PROFILEIMAGE, "");

                    prefs.saveIntPerferences(SP_USER_POINT, response.getInt("gift_points"));

                    if (response.getString("vip").trim().length() == 0) {
                        prefs.saveIntPerferences(SP_VIP_ID, 0);
                    } else {
                        prefs.saveIntPerferences(SP_VIP_ID, 1);

                    }


                    if (prefs.getIntPreferences(SP_CHANGE) == 1){
                        prefs.saveIntPerferences(SP_CHANGE, 0);
                        prefs.saveIntPerferences(ConstantVariable.COUNT_DOWN, 0);
                        Intent intent = new Intent(ActivityProfileCompleted.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else if (prefs.getBooleanPreference(LOGIN_SAVECART)){
                        prefs.saveBooleanPreference(LOGIN_SAVECART, false);
                        Intent intent = new Intent(ActivityProfileCompleted.this,ShoppingCartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                    else
                        finish();





                } catch (Exception e) {
                    Log.e(TAG, "onResponse: "  + e.getMessage() );

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


}
