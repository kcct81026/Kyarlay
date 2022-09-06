package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;

public class CompetitionActivity extends AppCompatActivity implements ConstantVariable {

    private static final String TAG = "CompetitionActivity";
    CustomTextView txtSearch;
    Resources resources;
    MyPreference prefs;
    int fromdeep = 0;
    LinearLayout back_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_competition);
        txtSearch          = (CustomTextView) findViewById(R.id.txtSearch);
        back_layout          =  findViewById(R.id.back_layout);

        if (getIntent() != null)
            fromdeep = getIntent().getIntExtra("fromdeep", 0);


        prefs           = new MyPreference(CompetitionActivity.this);
        Context context = LocaleHelper.setLocale(CompetitionActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        txtSearch.setText(resources.getString(R.string.search_photo_id));

        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(CompetitionActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_activate_member);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                dialog.setCanceledOnTouchOutside(true);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);

                CustomButton feedback = (CustomButton) dialog.findViewById(R.id.feedback);
                final CustomEditText editText =   (CustomEditText) dialog.findViewById(R.id.edittext);
                final CustomTextView txtTitle =   (CustomTextView) dialog.findViewById(R.id.dialog_activate_member);


                txtTitle.setText(resources.getString(R.string.competition_search_title));
                feedback.setText(resources.getString(R.string.search_text));
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);

/*                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                editText.requestFocus();
                getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);*/


                feedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(prefs.isNetworkAvailable()) {
                            if (editText.getText().toString().trim().length() > 0) {
                               // memberActivate(editText.getText().toString(), dialog);
                                dialog.dismiss();
                                Intent intent = new Intent(CompetitionActivity.this, SearchPhotoActivity.class);
                                intent.putExtra("id", editText.getText().toString());
                                startActivity(intent);



                            }else{

                                ToastHelper.showToast(CompetitionActivity.this, resources.getString(R.string.no_internet_error));


                            }
                        }
                    }

                });
                dialog.show();

            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromdeep == 1){
                    Intent backIntent = new Intent(CompetitionActivity.this, MainActivity.class);
                    startActivity(backIntent);
                    finish();
                }
                else
                    finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fromdeep == 1){
            Intent backIntent = new Intent(CompetitionActivity.this, MainActivity.class);
            startActivity(backIntent);
            finish();
        }
        else
            finish();
    }
}
