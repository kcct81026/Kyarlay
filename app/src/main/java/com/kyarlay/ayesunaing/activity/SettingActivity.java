package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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
import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AgeConfig;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import me.myatminsoe.mdetect.MDetect;

public class SettingActivity extends AppCompatActivity implements ConstantVariable, Constant, ConstantsDB {


    private static final String TAG = "SettingActivity";

    Resources resources;
    MyPreference prefs;
    Display display;

    CustomTextView title;
    LinearLayout language_layout, feedback_layout, about_layout, user_layout, logout_layout,
            member_sign_in, contact_layout, old_ordered_layout, point_layout, edit_profile_layout,
            activate_layout, title_layout;
    NetworkImageView profile_image;
    CustomTextView  language, feedback, about, phone, name, help, address, logout, member_text,
            old_ordered, member, contact, edit_profile_text, current_point, activate_text ;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    UniversalAdapter adapter;
    DatabaseAdapter databaseAdapter;
    Boolean loading = true;
    
    AppCompatActivity activity;
    TextView txtVersion;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_profile);

        Log.e(TAG, "onCreate: " );

        activity = (AppCompatActivity) SettingActivity.this; 
        prefs            = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        databaseAdapter = new DatabaseAdapter(activity);
        display = activity.getWindowManager().getDefaultDisplay();


        //new MyFlurry(activity);

       /* try {

            Map<String, String> mix = new HashMap<String, String>();
            //FlurryAgent.logEvent("View Profile Page", mix);
        } catch (Exception e) {
        }*/
        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);
        title            = (CustomTextView) findViewById(R.id.title);
        title_layout            =  findViewById(R.id.title_layout);
        title.setText(resources.getString(R.string.action_settings)+"");

        help            =   findViewById(R.id.help);
        language_layout =   findViewById(R.id.language_layout);
        feedback_layout =   findViewById(R.id.feedback_layout);
        about_layout    =   findViewById(R.id.aboutus_layout);
        user_layout     =   findViewById(R.id.user_layout);
        logout_layout   =   findViewById(R.id.logout_layout);
        edit_profile_layout =   findViewById(R.id.edit_profile_layout);
        profile_image   =  findViewById(R.id.profile_image);
        txtVersion   =  findViewById(R.id.txtVersion);

        member          =   findViewById(R.id.member_sign_in_text);
        member_sign_in  =   findViewById(R.id.member_sign_in);
        language        =   findViewById(R.id.language);
        feedback        =   findViewById(R.id.feedback);
        about           =   findViewById(R.id.aboutus);
        phone           =   findViewById(R.id.phone_text);
        name            =   findViewById(R.id.name_text);
        address         =   findViewById(R.id.address);
        logout          =   findViewById(R.id.logout);
        member_text     =   findViewById(R.id.member_text);
        old_ordered     =   findViewById(R.id.old_ordered);
        contact         =   findViewById(R.id.contact);
        contact_layout  =   findViewById(R.id.contact_layout);
        old_ordered_layout=   findViewById(R.id.old_ordered_layout);
        point_layout   =   findViewById(R.id.point_layout);
        current_point   =  findViewById(R.id.current_point);
        edit_profile_text   =  findViewById(R.id.edit_profile_text);
        activate_layout     =  findViewById(R.id.activate_layout);
        activate_text       =  findViewById(R.id.activate_text);

        edit_profile_text.setText(resources.getString(R.string.edit_profile));
        edit_profile_layout.setVisibility(View.GONE);
        current_point.setText(resources.getString(R.string.current_point));
        old_ordered_layout.setVisibility(View.GONE);

        profile_image.getLayoutParams().width   = (display.getWidth() * 2 ) / 5;
        profile_image.getLayoutParams().height   = (display.getWidth() * 2 ) / 5;

        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            txtVersion.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: "  + e.getMessage() );
        }





        contact.setText(resources.getString(R.string.to_contact));
        contact_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        title1.setText(resources.getString(R.string.call_phone_dialog));
                        for(int i = 0; i < phoneArray.size(); i ++){
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
                                  /*  try {

                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source","campain");
                                        mix.put("call_id",phoneString);
                                        //FlurryAgent.logEvent("Call Call Center", mix);
                                    } catch (Exception e) {
                                    }*/
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

        edit_profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.saveIntPerferences(SP_CHANGE, 1);
                Intent intent   = new Intent(activity, EditProfileActivity.class);
                startActivity(intent);

            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   = new Intent(activity, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefs.getIntPreferences(SP_CHANGE) == 1){
                    Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                    finish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        language.setText(resources.getString(R.string.language));
        feedback.setText(resources.getString(R.string.feedback));
        about.setText(resources.getString(R.string.about_us));
        logout.setText(resources.getString(R.string.logout));


        if(prefs.getStringPreferences(SP_USER_TOKEN).equals("") &&
                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){
            user_layout.setVisibility(View.GONE);
            logout_layout.setVisibility(View.GONE);

            member.setText(resources.getString(R.string.member_sign_in));

            member_sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent   = new Intent(activity, ActivityLogin.class);
                    activity.startActivity(intent);



                }
            });

        }else {
            user_layout.setVisibility(View.VISIBLE);
            logout_layout.setVisibility(View.VISIBLE);


            try{
                if (prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals("null")){
                    profile_image.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
                }
                else if (!prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals(""))
                    profile_image.setImageUrl(prefs.getStringPreferences(SP_USER_PROFILEIMAGE), AppController.getInstance().getImageLoader());
                else
                    profile_image.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);


            }catch (Exception e){
                profile_image.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
                Log.e(TAG, "onResume:  "   +e.getMessage() );
            }

            old_ordered.setText(resources.getString(R.string.order_old));


            if(prefs.getStringPreferences(SP_USER_NAME) != "") {
                name.setVisibility(View.VISIBLE);
                String age;

                if( prefs.getStringPreferences(SP_USER_MOMORDAD).equals("unknown_parent_status" ) ||  prefs.getStringPreferences(SP_USER_MOMORDAD).equals("other" )){
                    age  = resources.getString(R.string.family_member);
                }else{

                    AgeConfig ageConfig = new AgeConfig(activity);
                    age = ageConfig.calculateKidAge(prefs.getIntPreferences(SP_USER_MONOTH), prefs.getIntPreferences(SP_USER_YEAR),
                            prefs.getStringPreferences(SP_USER_BOYORGIRL), prefs.getStringPreferences(SP_USER_MOMORDAD));

                    Log.e(TAG, "onResume: "  + age );
                }

                name.setText(prefs.getStringPreferences(SP_USER_NAME)+" \n"+age);
            }else
                name.setVisibility(View.GONE);

            if(prefs.getStringPreferences(SP_USER_PHONE) != "") {
                phone.setVisibility(View.VISIBLE);
                phone.setText(prefs.getStringPreferences(SP_USER_PHONE));
            }else
                phone.setVisibility(View.GONE);



            if(prefs.getStringPreferences(SP_USER_ADDRESS) != "") {
                address.setVisibility(View.VISIBLE);
                address.setText(prefs.getStringPreferences(SP_USER_ADDRESS));
            }else
                address.setVisibility(View.GONE);

            if (prefs.getIntPreferences(SP_VIP_ID) == 0){
                member_text.setVisibility(View.GONE);
                activate_layout.setVisibility(View.GONE);
                activate_text.setText(resources.getString(R.string.member_activate));
            }
            else{
                activate_text.setText(resources.getString(R.string.connect_with_memberid));
                member_text.setVisibility(View.VISIBLE);
                member_text.setText(prefs.getStringPreferences(SP_USER_VIP_ACCOUNT));

                activate_layout.setVisibility(View.GONE);
            }

        }

        activate_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "click activate dialog");
                    //FlurryAgent.logEvent("Member Activate", mix);
                } catch (Exception e) {
                }
*/
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_activate_member);
                dialog.setCanceledOnTouchOutside(true);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);

                CustomButton feedback = (CustomButton) dialog.findViewById(R.id.feedback);
                final CustomEditText editText =   (CustomEditText) dialog.findViewById(R.id.edittext);
                final CustomTextView txtTitle =   (CustomTextView) dialog.findViewById(R.id.dialog_activate_member);

                txtTitle.setText(resources.getString(R.string.member_activate_title));

                feedback.setText(resources.getString(R.string.member_activate));
                feedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(prefs.isNetworkAvailable()) {
                            if (editText.getText().toString().trim().length() > 0) {
                                memberActivate(editText.getText().toString(), dialog);
                            }else{

                                ToastHelper.showToast(SettingActivity.this , resources.getString(R.string.no_internet_error));

                            }
                        }
                    }

                });
                dialog.show();
            }
        });

        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_logout);

                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);

                CustomButton cancel = (CustomButton) dialog.findViewById(R.id.dialog_delete_cancel);
                CustomButton confirm = (CustomButton) dialog.findViewById(R.id.dialog_delete_confirm);
                CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
                CustomTextView text = (CustomTextView) dialog.findViewById(R.id.text);

                title.setText(resources.getString(R.string.logout));
                text.setText(resources.getString(R.string.logout_text));
                cancel.setText(resources.getString(R.string.logout_cancel));
                confirm.setText(resources.getString(R.string.logout_confirm));

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        /*try {


                            //FlurryAgent.logEvent("Click Logout");
                        } catch (Exception e) {
                        }*/

                        prefs.remove("SP_ISAPPINSTALLED");
                        prefs.remove("LANGUAGE");
                        prefs.remove("SP_USER_TOKEN");
                        prefs.remove("SP_CUSTOMER_PRODUCT_COUNT");
                        prefs.remove("SP_PHONE_NUMBER");
                        prefs.remove("SP_ADS_LOADED");
                        prefs.remove("ADS_COUNT_DOWN_TIME");
                        prefs.remove("PREFERENCES_TOOL_BAR_CART");
                        prefs.remove("SP_UNIQUE_ID_FOR_USER");
                        prefs.remove("SP_DEVICE_ID");
                        prefs.remove("SP_FCM_TOEKN");
                        prefs.remove("SP_DEVICE_IMEI");
                        prefs.remove("SP_MAINACTIVITY_CLICK");
                        prefs.remove("SP_MEMBER_ID");
                        prefs.remove("SP_VIP_ID");
                        prefs.remove("SP_USER_PHONE");
                        prefs.remove("SP_USER_POINT");
                        prefs.remove("SP_USER_PROFILEIMAGE");
                        prefs.remove("SP_TOWNSHIP_STORE_ID");




                        //prefs.clearAll();
                        databaseAdapter.deleteAllColumn(TABLE_SAVE_CART);
                        databaseAdapter.deleteAllColumn(TABLE_POST_LIKE);
                        databaseAdapter.deleteAllColumn(TABLE_PRODUCT_LIKE);
                        databaseAdapter.deleteAllColumn(TABLE_SAVE_NAMES);
                        user_layout.setVisibility(View.GONE);
                        //member_layout.setVisibility(View.VISIBLE);
                        logout_layout.setVisibility(View.GONE);
                        Intent intent = new Intent(activity, Splashscreen.class);
                        startActivity(intent);
                        activity.finishAffinity();
                    }

                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        language_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* try {

                    //FlurryAgent.logEvent("Click Language Change");
                } catch (Exception e) {
                }*/

                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_language);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = display.getWidth();
                window.setAttributes(wlp);

                CustomTextView myanmar      = (CustomTextView) dialog.findViewById(R.id.myanmar);
                CustomTextView engilsh    = (CustomTextView) dialog.findViewById(R.id.english);
                RadioButton rmyanmar         = (RadioButton) dialog.findViewById(R.id.radiomyanmar);
                RadioButton renglish       = (RadioButton) dialog.findViewById(R.id.radioenglish);
                CustomButton next           = (CustomButton) dialog.findViewById(R.id.next);
                next.setVisibility(View.GONE);

                if(prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_MYANMAR)) {
                    rmyanmar.setChecked(true);
                    renglish.setChecked(false);
                }else if(prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_ENGLISH)) {
                    rmyanmar.setChecked(false);
                    renglish.setChecked(true);
                }else  {
                    rmyanmar.setChecked(true);
                    renglish.setChecked(false);
                }

                myanmar.setText(resources.getString(R.string.setting_myanmar));
                engilsh.setText(resources.getString(R.string.setting_english));

                myanmar.setOnClickListener(new SettingActivity.dismissDialog(dialog, LANGUAGE_MYANMAR));
                engilsh.setOnClickListener(new SettingActivity.dismissDialog(dialog, LANGUAGE_ENGLISH));
                rmyanmar.setOnClickListener(new SettingActivity.dismissDialog(dialog,  LANGUAGE_MYANMAR));
                renglish.setOnClickListener(new SettingActivity.dismissDialog(dialog, LANGUAGE_ENGLISH));


                dialog.show();
            }
        });

        about_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*try {

                    //FlurryAgent.logEvent("Click About Us");
                } catch (Exception e) {
                }*/

                Intent intent = new Intent(activity, ActivityWebView.class);
                Bundle b = new Bundle();
                b.putString("url", constantAboutus);
                b.putString("title", "about");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*try {


                    //FlurryAgent.logEvent("Click Help");
                } catch (Exception e) {
                }*/

                Intent intent = new Intent(activity, ActivityWebView.class);
                Bundle b = new Bundle();
                b.putString("url", constantHelp);
                b.putString("title", "help");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        feedback_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_feedback);
                dialog.setCanceledOnTouchOutside(true);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);

                CustomButton feedback = (CustomButton) dialog.findViewById(R.id.feedback);
                CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
                final CustomEditText editText =   (CustomEditText) dialog.findViewById(R.id.edittext);
                title.setText(resources.getString(R.string.feedback));
                feedback.setText(resources.getString(R.string.feedback_button));
                feedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(prefs.isNetworkAvailable()) {
                            if (editText.getText().toString().trim().length() > 0) {
                                feedbackServer(editText.getText().toString(), dialog);
                            }else{

                                ToastHelper.showToast(SettingActivity.this , resources.getString(R.string.no_internet_error));

                            }
                        }
                    }

                });
                dialog.show();
            }
        });

        point_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PointHistoryActivity.class);
                startActivity(intent);

            }
        });


    }




    public  class dismissDialog implements View.OnClickListener{
        Dialog dialog;
        String result;

        public dismissDialog(Dialog dialog, String result) {
            this.result = result;
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {

            dialog.dismiss();
            prefs.saveStringPreferences(LANGUAGE, result);


/*
            try {


                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", prefs.getStringPreferences(LANGUAGE));
                //FlurryAgent.logEvent("Change Language", mix);
            } catch (Exception e) {
            }*/

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

            prefs.saveIntPerferences(SP_CHANGE, 1);
            activity.recreate();


        }
    }



    private void feedbackServer(String text , final Dialog dialog)
    {
        JSONObject uploadPost = new JSONObject();
        try {
            uploadPost.put("unique_id", prefs.getStringPreferences(SP_DEVICE_ID)+"_"+prefs.getStringPreferences(SP_DEVICE_IMEI));
            uploadPost.put("text", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, constantFeedBack, uploadPost,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        try {


                            ToastHelper.showToast(SettingActivity.this ,  response.getString("text"));



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse:  "  + e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

                ToastHelper.showToast(SettingActivity.this ,  resources.getString(R.string.search_error));

            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, "Confirm");
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
                    member_text.setText(prefs.getStringPreferences(SP_USER_VIP_ACCOUNT));
                    member_text.setVisibility(View.VISIBLE);
                    activate_layout.setVisibility(View.GONE);


                    if (response.getString("vip").trim().length() == 0) {

                        prefs.saveIntPerferences(SP_VIP_ID, 0);
                        member_text.setVisibility(View.GONE);
                        activate_layout.setVisibility(View.GONE);
                    } else {

                        prefs.saveIntPerferences(SP_VIP_ID, 1);
                        member_text.setVisibility(View.VISIBLE);
                        member_text.setText(prefs.getStringPreferences(SP_USER_VIP_ACCOUNT));
                        activate_layout.setVisibility(View.GONE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
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



    private void memberActivate(String text , final Dialog dialog)
    {
        JSONObject uploadPost = new JSONObject();
        try {
            uploadPost.put("member", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, constantActivateMember, uploadPost,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)  {

                        dialog.dismiss();
                        try {

                           /* try {


                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "finish activate");
                                //FlurryAgent.logEvent("Member Activate", mix);
                            } catch (Exception e) {
                            }*/




                            ToastHelper.showToast(SettingActivity.this ,  response.getString("text"));

                            if (prefs.isNetworkAvailable()){
                                prefs.saveIntPerferences(SP_CHANGE, 1);
                                getCustomerInfo();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                ToastHelper.showToast(SettingActivity.this , resources.getString(R.string.search_error));
            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, "Confirm");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (prefs.getIntPreferences(SP_CHANGE) == 1){
            Intent intent = new Intent(SettingActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
            this.finish();
        }
        else
            finish();
    }
}

