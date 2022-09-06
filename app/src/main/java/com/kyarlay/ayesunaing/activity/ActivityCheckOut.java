package com.kyarlay.ayesunaing.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;

public class ActivityCheckOut extends AppCompatActivity implements ConstantVariable {

    private static final String TAG = "ActivityCheckOut";

    CustomTextView txtTitle, txtStatus, txtContinue,txtPaymentTitle,txtAccountNumberTittle,
            txtAccountNumber,txtCopy,txtAccountNameTittle,txtAccountName;

    MyPreference prefs;
    AppCompatActivity activity;
    Resources resources;
    LinearLayout layoutPaymnet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_checkout_success);

        activity = ActivityCheckOut.this;
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        txtContinue = findViewById(R.id.txtContinue);
        txtStatus = findViewById(R.id.txtStatus);
        txtTitle = findViewById(R.id.txtTitle);
        layoutPaymnet = findViewById(R.id.layoutPaymnet);
        txtPaymentTitle = findViewById(R.id.txtPaymentTitle);
        txtAccountNumberTittle = findViewById(R.id.txtAccountNumberTittle);
        txtAccountNumber = findViewById(R.id.txtAccountNumber);
        txtCopy = findViewById(R.id.txtCopy);
        txtAccountNameTittle = findViewById(R.id.txtAccountNameTittle);
        txtAccountName = findViewById(R.id.txtAccountName);


        txtContinue.setText(resources.getString(R.string.added_to_cart_cancel));
        txtTitle.setText(resources.getString(R.string.order_status));


        txtPaymentTitle.setText(String.format(resources.getString(R.string.payment_info),prefs.getStringPreferences(ConstantVariable.TEMP_COMMISSION_NAME)));
        txtAccountNumber.setText(prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NUM ));
        txtCopy.setText(resources.getString(R.string.copy));
        txtAccountNameTittle.setText(resources.getString(R.string.acc_name));
        txtAccountNumberTittle.setText(resources.getString(R.string.acc_num));
        txtAccountName.setText( prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NAME));

        txtPaymentTitle.setTypeface(txtPaymentTitle.getTypeface(), Typeface.BOLD);
        txtAccountNumber.setTypeface(txtAccountNumber.getTypeface(), Typeface.BOLD);
        txtAccountName.setTypeface(txtPaymentTitle.getTypeface(), Typeface.BOLD);


        if (prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_TAG).equals("cod"))
            layoutPaymnet.setVisibility(View.GONE);
        else
            layoutPaymnet.setVisibility(View.VISIBLE);

        if (prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NAME).length() == 0){
            txtAccountName.setText("");
        }
        if (prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NUM).length() == 0){
            txtAccountNumber.setText("");
        }

        txtCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied", prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NUM));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ActivityCheckOut.this, "Copied", Toast.LENGTH_LONG).show();
            }
        });





        txtStatus.setText(getIntent().getExtras().getString("status"));

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
                Intent intent = new Intent(activity,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                activity.finish();
            }
        });




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
        Intent intent = new Intent(activity,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
}
