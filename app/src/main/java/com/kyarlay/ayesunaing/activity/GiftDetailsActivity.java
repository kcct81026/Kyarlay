package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.GiftObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GiftDetailsActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "GiftDetailsActivity";

    // widgets
    private LinearLayout title_layout;
    private CustomTextView title, txtGiftTitile,txtGiftValid, txtGiftPoint, txtGiftDetailTitle, txtGiftAcces;
    private CustomTextView txtGiftDetailBody,txtGiftContactTitle,txtGiftContactBody,txtGiftPurchase;
    private NetworkImageView imgGift;
    private CustomTextView txtBar;
    private ProgressBar pFull, pFill;

    // vars
    MyPreference prefs;
    Resources resources;
    Display display;

    GiftObject giftObject ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gift_details);

        Log.e(TAG, "onCreate: " );
        //new MyFlurry(GiftDetailsActivity.this);

        prefs  = new MyPreference(GiftDetailsActivity.this);
        Context context1 = LocaleHelper.setLocale(GiftDetailsActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context1.getResources();
        display = getWindowManager().getDefaultDisplay();

        giftObject = getIntent().getParcelableExtra("giftObject");

        title_layout = findViewById(R.id.title_layout);
        title = findViewById(R.id.title);
        imgGift = findViewById(R.id.imgGift);
        txtGiftTitile = findViewById(R.id.txtGiftTitile);
        txtGiftValid = findViewById(R.id.txtGiftValid);
        txtGiftPoint = findViewById(R.id.txtGiftPoint);
        txtGiftDetailTitle = findViewById(R.id.txtGiftDetailTitle);
        txtGiftDetailBody = findViewById(R.id.txtGiftDetailBody);
        txtGiftContactTitle = findViewById(R.id.txtGiftContactTitle);
        txtGiftContactBody = findViewById(R.id.txtGiftContactBody);
        txtGiftPurchase = findViewById(R.id.txtGiftPurchase);
        txtGiftAcces = findViewById(R.id.txtGiftAcces);

        txtBar = findViewById(R.id.barText);
        pFill = findViewById(R.id.barFill);
        pFull = findViewById(R.id.barFull);


        txtGiftTitile.setTypeface(txtGiftTitile.getTypeface(), Typeface.BOLD);
        txtGiftValid.setTypeface(txtGiftValid.getTypeface(), Typeface.BOLD);
        txtGiftDetailTitle.setTypeface(txtGiftDetailTitle.getTypeface(), Typeface.BOLD);
        txtGiftContactTitle.setTypeface(txtGiftContactTitle.getTypeface(), Typeface.BOLD);
        txtGiftContactBody.setTypeface(txtGiftContactBody.getTypeface(), Typeface.BOLD);

        if (giftObject.getPhoto_url() != null  && giftObject.getPhoto_url().length() > 0){
            imgGift.setImageUrl(giftObject.getPhoto_url(), AppController.getInstance().getImageLoader());
        }

        imgGift.getLayoutParams().height  = (display.getWidth() * giftObject.getDimen()) / 100;
        imgGift.getLayoutParams().width   = display.getWidth();

        if (giftObject.getAccess().equals("everyone")){
            txtGiftAcces.setVisibility(View.GONE);
        }
        else if(giftObject.getAccess().equals("member_only")){
            txtGiftAcces.setVisibility(View.VISIBLE);
            txtGiftAcces.setText("* Vip Member Only");
            txtGiftAcces.setTypeface(txtGiftAcces .getTypeface(), Typeface.BOLD);
        }
        txtGiftTitile.setText(giftObject.getTitle());
        title.setText(giftObject.getTitle());
        String[] release = giftObject.getRelease_at().split(" ");
        String[] end_at = giftObject.getEnd_at().split(" ");
        txtGiftValid.setText(String.format(resources.getString(R.string.valid) , release[0], end_at[0]));
        txtGiftPoint.setText(giftObject.getPrice() + " Points");
        txtGiftDetailTitle.setText(resources.getString(R.string.details));
        txtGiftContactTitle.setText(resources.getString(R.string.contact_info));
        txtGiftDetailBody.setText(giftObject.getBody());
        txtGiftContactBody.setText(giftObject.getPhone());

        txtGiftPurchase.setText(resources.getString(R.string.purchase));

        ViewGroup.LayoutParams layoutParams = pFull.getLayoutParams();
        int percentWidth  = (int)(layoutParams.width / giftObject.getAvailable_count());

        int percent = (int) ((100 * giftObject.getRedeemed_count()) / giftObject.getAvailable_count());
        txtBar.setText(giftObject.getRedeemed_count() + " sold");
        if (giftObject.getRedeemed_count() == 0) {
            pFill.setVisibility(View.GONE);
            txtBar.setText(giftObject.getAvailable_count() +  " available");
        }
        else if (percent <= 20){
            percentWidth = (int)(layoutParams.width  * 20 /100);
        }
        else
            percentWidth = layoutParams.width * percent / 100;


        ViewGroup.LayoutParams param = pFill.getLayoutParams();
        param.width = percentWidth;
        pFill.setLayoutParams(param);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Drawable leftDrawable = AppCompatResources
                    .getDrawable(GiftDetailsActivity.this, R.drawable.call);
            txtGiftContactBody.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
        }


        title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtGiftContactBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + giftObject.getPhone()));
                startActivity(callIntent);
            }
        });

        txtGiftPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getStringPreferences(SP_USER_TOKEN) == null
                        || prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){

                    Intent intent   = new Intent(GiftDetailsActivity.this, ActivityLogin.class);
                    startActivity(intent);

                }
                else {
                    int makePurchase = 0;
                    if (giftObject.getAccess().equals("member_only")) {
                        if (prefs.getIntPreferences(SP_VIP_ID) == 0) {
                            makePurchase = 0;

                            ToastHelper.showToast(GiftDetailsActivity.this, resources.getString(R.string.fail_purchase));

                        } else {
                            makePurchase = 1;
                        }
                    } else if (giftObject.getAccess().equals("everyone")) {
                        makePurchase = 1;
                    }

                    if (makePurchase == 1) {
                        if (giftObject.getAvailable_count() > 0) {

                            final Dialog dialog = new Dialog(GiftDetailsActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_activate_member);
                            dialog.setCanceledOnTouchOutside(true);

                            Window window = dialog.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.CENTER;
                            wlp.width = getWindowManager().getDefaultDisplay().getWidth();
                            window.setAttributes(wlp);

                            CustomButton feedback = (CustomButton) dialog.findViewById(R.id.feedback);
                            final CustomEditText editText = (CustomEditText) dialog.findViewById(R.id.edittext);
                            final CustomTextView txtTitle = (CustomTextView) dialog.findViewById(R.id.dialog_activate_member);

                            txtTitle.setText(giftObject.getPrompt_message());

                            feedback.setText(resources.getString(R.string.purchase));


                            feedback.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (prefs.isNetworkAvailable()) {
                                        if (editText.getText().toString().trim().length() > 0) {
                                            makePurchaseGift(editText.getText().toString(), dialog, giftObject.getId());
                                        } else {

                                            ToastHelper.showToast(GiftDetailsActivity.this, resources.getString(R.string.enter_code));

                                        }
                                    }
                                }

                            });
                            dialog.show();

                        } else {

                            ToastHelper.showToast(GiftDetailsActivity.this, resources.getString(R.string.fully_reddem));

                        }
                    }
                }


            }
        });
    }
    private void makePurchaseGift(String pin, final Dialog dialog, int gObjId)
    {
        JSONObject uploadOrder = new JSONObject();
        try {
            uploadOrder.put("gift_id",    gObjId);
            uploadOrder.put("pin",         pin);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, String.format(constantClaimGift, prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID)), uploadOrder,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();

                        try{
                            String text = response.getString("text");

                            ToastHelper.showToast(GiftDetailsActivity.this, text);



                            if (response.getInt("status") == 1){
                                prefs.saveIntPerferences(SP_CHANGE, 1);
                                if (prefs.isNetworkAvailable())
                                    getCustomerInfo();


                            }
                            else{
                                prefs.saveIntPerferences(SP_CHANGE, 0);
                            }
                        }catch (Exception e){
                            Log.e(TAG, "onResponse: exception : "  + e.getMessage() );
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

                Log.e(TAG, "onErrorResponse: error "  + error.getMessage() );
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

                    finish();

                } catch (JSONException e) {
                    Log.e(TAG, "onResponse:  "  + e.getMessage() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
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
