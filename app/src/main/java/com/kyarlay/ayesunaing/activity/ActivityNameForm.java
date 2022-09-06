package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
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

import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ActivityNameForm extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "ActivityNameForm";

    LinearLayout back_layout,callLayout;
    CustomTextView toolTitle, txtTitle,txtNameTitle,txtContinue,to_contact;
    CustomEditText editName;

    MyPreference prefs;
    Resources resources;
    AppCompatActivity activity;
    String phone;
    int hasAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_name_form);
        Log.e(TAG, "onCreate: "  );

        activity = ActivityNameForm.this;
        prefs   = new MyPreference(ActivityNameForm.this);
        if(prefs.getStringPreferences(LANGUAGE) == ""){
            prefs.saveStringPreferences(LANGUAGE, LANGUAGE_MYANMAR);
        }
        Context context = LocaleHelper.setLocale(ActivityNameForm.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        new MyFlurry(ActivityNameForm.this);


        hasAccount = getIntent().getIntExtra("check_phone",0);
        phone = getIntent().getStringExtra("phone");

        back_layout = findViewById(R.id.back_layout);
        toolTitle = findViewById(R.id.toolTitle);
        txtTitle = findViewById(R.id.txtTitle);
        editName = findViewById(R.id.editName);
        txtNameTitle = findViewById(R.id.txtNameTitle);
        txtContinue = findViewById(R.id.txtContinue);
        callLayout = findViewById(R.id.callLayout);
        to_contact = findViewById(R.id.to_contact);

        editName.setBackgroundResource(R.drawable.border_grey);
        GradientDrawable drawable = (GradientDrawable) editName.getBackground();
        drawable.setColor(activity.getResources().getColor(R.color.custom_grey));

        toolTitle.setText("Profile Name");
        txtNameTitle.setText("Name");
        txtTitle.setText(resources.getString(R.string.fill_name));
        to_contact.setText(resources.getString(R.string.to_contact));
        txtContinue.setText(resources.getString(R.string.phone_number_continue));

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editName.getText().toString().length() == 0)
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.grey_solid_button));
                else
                    txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().trim().length() > 0){
                    //myAccountLogin();
                    Intent intent = new Intent(ActivityNameForm.this, ActivityProfileForm.class);
                    intent.putExtra("name", editName.getText().toString());
                    startActivity(intent);
                    finish();

                    
                }else{
                    ToastHelper.showToast(activity, resources.getString(R.string.error_name));
                }
            }
        });
    }

}
