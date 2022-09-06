package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserPostDetailActivity extends AppCompatActivity implements ConstantVariable, Constant{

    private static final String TAG = "UserPostDetailActivity";

    Display display ;
    Resources resources;
    MyPreference prefs;

    CustomTextView momordad, boyorgirl, month;
    CustomTextView title;
    CustomButton update;
    int baby_month = 0, baby_year = 0;
    String momordad_result = "";
    String boyorgirl_result = "";

    LinearLayout  title_layout, back_layout;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        display         = getWindowManager().getDefaultDisplay();
        prefs           = new MyPreference(UserPostDetailActivity.this);
        Context context = LocaleHelper.setLocale(UserPostDetailActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();


        new MyFlurry(UserPostDetailActivity.this);


        setContentView(R.layout.group_chat_detail);

        Log.e(TAG, "onCreate:  "  );


        month               = (CustomTextView) findViewById(R.id.date);
        momordad            = (CustomTextView) findViewById(R.id.momordad);
        boyorgirl           = (CustomTextView) findViewById(R.id.boyorgirl);
        update              = (CustomButton) findViewById(R.id.button);
        title_layout        = (LinearLayout) findViewById(R.id.title_layout);
        back_layout         = (LinearLayout) findViewById(R.id.back_layout);
        title               = (CustomTextView) findViewById(R.id.question_detail_name);

        back_layout.getLayoutParams().width     = (display.getWidth() * 3 ) /20;
        title_layout.getLayoutParams().width    = (display.getWidth() * 17 ) / 20;

        title.setText(resources.getString(R.string.user_detail_title));

        if(prefs.getIntPreferences(SP_USER_MONOTH) == 0 || prefs.getIntPreferences(SP_USER_YEAR) == 0){
            month.setText(resources.getString(R.string.choose_month_hint));
            momordad.setText(resources.getString(R.string.group_chat_detail_momordad));
            boyorgirl.setText(resources.getString(R.string.group_chat_detail_boyorgirl));


        }else{
            baby_month  = prefs.getIntPreferences(SP_USER_MONOTH);
            baby_year   = prefs.getIntPreferences(SP_USER_YEAR);
            momordad_result = prefs.getStringPreferences(SP_USER_MOMORDAD);
            boyorgirl_result = prefs.getStringPreferences(SP_USER_BOYORGIRL);
            month.setText(prefs.getIntPreferences(SP_USER_MONOTH)+" / "+prefs.getIntPreferences(SP_USER_YEAR));
            month.setTextColor(resources.getColor(R.color.black));
            boyorgirl.setTextColor(resources.getColor(R.color.black));
            momordad.setTextColor(resources.getColor(R.color.black));
            if(prefs.getStringPreferences(SP_USER_BOYORGIRL).equals(BOY)){
                boyorgirl.setText(resources.getString(R.string.group_chat_detail_boy));
            }else{
                boyorgirl.setText(resources.getString(R.string.group_chat_detail_girl));
            }

            if(prefs.getStringPreferences(SP_USER_MOMORDAD).equals(MOTHER)){
                momordad.setText(resources.getString(R.string.group_chat_detail_mom));
            }else if(prefs.getStringPreferences(SP_USER_MOMORDAD).equals(FATHER)){
                momordad.setText(resources.getString(R.string.group_chat_detail_dad));
            }else{
                momordad.setText(resources.getString(R.string.group_chat_detail_other_show));
            }
        }

        update.setText(resources.getString(R.string.group_chat_name_update));
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(UserPostDetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.number_picker);
                dialog.setCancelable(true);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);
                dialog.setCanceledOnTouchOutside(true);

                NumberPicker month_picker = (NumberPicker) dialog.findViewById(R.id.month_picker);
                NumberPicker year_picker  = (NumberPicker) dialog.findViewById(R.id.year_picker);
                CustomTextView title      = (CustomTextView) dialog.findViewById(R.id.title);
                CustomButton confirm        = (CustomButton) dialog.findViewById(R.id.confirm);

                title.setText(resources.getString(R.string.baby_birth_date));

                baby_month = Calendar.getInstance().get(Calendar.MONTH);

                month_picker.setMaxValue(12);
                month_picker.setMinValue(1);
                month_picker.setValue(baby_month+1);
                month_picker.setWrapSelectorWheel(false);
                month_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                        baby_month = i1;
                    }

                });
                baby_year  = Calendar.getInstance().get(Calendar.YEAR);
                year_picker.setMaxValue(baby_year );
                year_picker.setMinValue(baby_year - 20);
                year_picker.setWrapSelectorWheel(false);
                year_picker.setValue(baby_year);
                year_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        baby_year = i1;
                    }
                });

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(calculateKidAge(baby_month, baby_year)){

                            month.setText(baby_month+" / "+baby_year);
                            month.setTextColor(resources.getColor(R.color.black));
                            dialog.dismiss();
                        }else{

                            ToastHelper.showToast(UserPostDetailActivity.this, resources.getString(R.string.kid_age_error));

                        }
                    }
                });

                dialog.show();


            }
        });


        boyorgirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                try {

                    FlurryAgent.logEvent("Click Boy or Girl Change");
                } catch (Exception e) {
                }

                final Dialog dialog = new Dialog(UserPostDetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_boyorgirl);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = display.getWidth();
                window.setAttributes(wlp);

                CustomTextView title     = (CustomTextView) dialog.findViewById(R.id.title);
                CustomTextView boy       = (CustomTextView) dialog.findViewById(R.id.boy);
                CustomTextView girl      = (CustomTextView) dialog.findViewById(R.id.girl);
                RadioButton rboy         = (RadioButton) dialog.findViewById(R.id.radioboy);
                RadioButton rgirl        = (RadioButton) dialog.findViewById(R.id.radiogirl);
                LinearLayout boylayout   = (LinearLayout) dialog.findViewById(R.id.boy_layout);
                LinearLayout girllayout  = (LinearLayout) dialog.findViewById(R.id.girl_layout);
                if(prefs.getStringPreferences(SP_USER_BOYORGIRL).equals("unknown_gender")){
                    rboy.setChecked(true);
                }else if(prefs.getStringPreferences(SP_USER_BOYORGIRL).equals(BOY)){
                    rboy.setChecked(true);
                }else{
                    rgirl.setChecked(true);
                }

                title.setText(resources.getString(R.string.group_chat_detail_boyorgirl));
                boy.setText(resources.getString(R.string.group_chat_detail_boy));
                girl.setText(resources.getString(R.string.group_chat_detail_girl));

                rboy.setOnClickListener(new dismissBoyGirlDialog(dialog,
                        resources.getString(R.string.group_chat_detail_boy),BOY));
                rgirl.setOnClickListener(new dismissBoyGirlDialog(dialog,
                        resources.getString(R.string.group_chat_detail_girl),GIRL));
                girllayout.setOnClickListener(new dismissBoyGirlDialog(dialog,
                        resources.getString(R.string.group_chat_detail_girl),GIRL));
                boylayout.setOnClickListener(new dismissBoyGirlDialog(dialog,
                        resources.getString(R.string.group_chat_detail_boy),BOY));

                dialog.show();
            }
        });



        momordad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    FlurryAgent.logEvent("Click Mom or Dad Change");
                } catch (Exception e) {
                }

                final Dialog dialog = new Dialog(UserPostDetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_monordad);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = display.getWidth();
                window.setAttributes(wlp);

                CustomTextView title     = (CustomTextView) dialog.findViewById(R.id.title);
                CustomTextView boy       = (CustomTextView) dialog.findViewById(R.id.boy);
                CustomTextView girl      = (CustomTextView) dialog.findViewById(R.id.girl);
                CustomTextView other     = (CustomTextView) dialog.findViewById(R.id.other);
                RadioButton rdad         = (RadioButton) dialog.findViewById(R.id.radioboy);
                RadioButton rmom        = (RadioButton) dialog.findViewById(R.id.radiogirl);
                RadioButton rother       = (RadioButton) dialog.findViewById(R.id.radioother);
                LinearLayout dadlayout   = (LinearLayout) dialog.findViewById(R.id.boy_layout);
                LinearLayout momlayout  = (LinearLayout) dialog.findViewById(R.id.girl_layout);
                LinearLayout otherlayout  = (LinearLayout) dialog.findViewById(R.id.other_layout);

                if(prefs.getStringPreferences(SP_USER_MOMORDAD).equals("unknown_parent_status")){
                    rdad.setChecked(true);
                }else if(prefs.getStringPreferences(SP_USER_MOMORDAD).equals(MOTHER)){
                    rmom.setChecked(true);
                }else if(prefs.getStringPreferences(SP_USER_MOMORDAD).equals(FATHER)){
                    rdad.setChecked(true);
                }else{
                   rother.setChecked(true);
                }

                title.setText(resources.getString(R.string.group_chat_detail_momordad));
                boy.setText(resources.getString(R.string.group_chat_detail_dad));
                girl.setText(resources.getString(R.string.group_chat_detail_mom));
                other.setText(resources.getString(R.string.group_chat_detail_other));

                rdad.setOnClickListener(new dismissMomDadDialog(dialog,resources.getString(R.string.group_chat_detail_dad), FATHER));
                rmom.setOnClickListener(new dismissMomDadDialog(dialog, resources.getString(R.string.group_chat_detail_mom), MOTHER));
                rother.setOnClickListener(new dismissMomDadDialog(dialog,resources.getString(R.string.group_chat_detail_other),OTHER));
                momlayout.setOnClickListener(new dismissMomDadDialog(dialog, resources.getString(R.string.group_chat_detail_mom), MOTHER));
                dadlayout.setOnClickListener(new dismissMomDadDialog(dialog,resources.getString(R.string.group_chat_detail_dad), FATHER));
                otherlayout.setOnClickListener(new dismissMomDadDialog(dialog,resources.getString(R.string.group_chat_detail_other),OTHER));
                dialog.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(momordad_result.equals("") || momordad.getText() == null || momordad.getText().toString().trim().length() == 0){

                    ToastHelper.showToast(UserPostDetailActivity.this, resources.getString(R.string.group_chat_detail_momordad_error));

                }else if(boyorgirl_result.equals("") || boyorgirl.getText() == null || boyorgirl.getText().toString().trim().length() == 0){

                    ToastHelper.showToast(UserPostDetailActivity.this, resources.getString(R.string.group_chat_detail_boyorgirl_error));

                }else if(baby_month == 0 || baby_year == 0 || month.getText() == null || month.getText().toString().trim().length() == 0){

                    ToastHelper.showToast(UserPostDetailActivity.this, resources.getString(R.string.group_chat_detail_month_error));

                }else{
                    sendUserInformation(momordad_result, boyorgirl_result,baby_month, baby_year);

                }
            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(momordad.getText() == null || momordad.getText().toString().trim().length() == 0
                        ||boyorgirl.getText() == null || boyorgirl.getText().toString().trim().length() == 0
                        || month.getText() == null || month.getText().toString().trim().length() == 0
                        ||baby_year == 0|| baby_month == 0){

                    finish();
                }else{
                    checkInformation();
                }

            }
        });

    }

    public void checkInformation(){
        if(momordad_result.equals("") || momordad.getText() == null || momordad.getText().toString().trim().length() == 0){
        }else if(boyorgirl_result.equals("") || boyorgirl.getText() == null || boyorgirl.getText().toString().trim().length() == 0){
        }else if(baby_month == 0 || baby_year == 0 || month.getText() == null || month.getText().toString().trim().length() == 0){
        }else{
            sendUserInformation(momordad_result, boyorgirl_result,baby_month, baby_year);

        }
    }

    @Override
    public void onBackPressed() {

        if(momordad.getText() == null || momordad.getText().toString().trim().length() == 0
                ||boyorgirl.getText() == null || boyorgirl.getText().toString().trim().length() == 0
                || month.getText() == null || month.getText().toString().trim().length() == 0
                ||baby_year == 0|| baby_month == 0){
            finish();
        }else{
            checkInformation();
        }

    }

    private boolean calculateKidAge(int month, int year){


        boolean boo = true;

        int kid_year    = 0;
        int cur_month   = Calendar.getInstance().get(Calendar.MONTH)+1;
        int cur_year    = Calendar.getInstance().get(Calendar.YEAR);

        int increment = 0;
        if(month > cur_month){
            increment = 1;

        }

        kid_year = cur_year - year - increment;

       if(kid_year < 0)
           boo = false;

        return boo;

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void sendUserInformation(final String momordad, final String boyorgirl,
                                     final int month, final int year)
    {

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("momordad",  momordad);
            uploadMessage.put("boyorgirl",  boyorgirl);
            uploadMessage.put("month",  month);
            uploadMessage.put("year",  year);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, String.format(constantUpdateUserInfo, prefs.getIntPreferences(SP_MEMBER_ID)), uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {


                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "edit_user_profile");
                            FlurryAgent.logEvent("Update User Information", mix);
                        } catch (Exception e) {
                        }

                        try {
                            int status =  response.getInt("status");
                            if(status == 1)
                            prefs.saveStringPreferences(SP_USER_MOMORDAD, momordad);
                            prefs.saveStringPreferences(SP_USER_BOYORGIRL, boyorgirl);
                            prefs.saveIntPerferences(SP_USER_MONOTH, month);
                            prefs.saveIntPerferences(SP_USER_YEAR, year);


                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e(TAG, "onResponse:  "  + e.getMessage() );
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                ToastHelper.showToast(UserPostDetailActivity.this, resources.getString(R.string.no_internet_error));


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


    public  class dismissMomDadDialog implements View.OnClickListener{
        Dialog dialog;
        String result;
        String status;

        public dismissMomDadDialog(Dialog dialog, String result, String status) {
            this.result = result;
            this.dialog = dialog;
            this.status = status;
        }

        @Override
        public void onClick(View v) {

            dialog.dismiss();
            momordad.setText(result);
            momordad.setTextColor(resources.getColor(R.color.black));
            momordad_result = status;


        }
    }


    public  class dismissBoyGirlDialog implements View.OnClickListener{
        Dialog dialog;
        String result;
        String status;

        public dismissBoyGirlDialog(Dialog dialog, String result, String status) {
            this.result = result;
            this.dialog = dialog;
            this.status = status;
        }

        @Override
        public void onClick(View v) {

            dialog.dismiss();
            boyorgirl.setText(result);
            boyorgirl.setTextColor(resources.getColor(R.color.black));
            boyorgirl_result = status;


        }
    }

}
