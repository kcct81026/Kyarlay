package com.kbzbank.payment.sdk.callback;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kbzbank.payment.KBZPay;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.MainActivity;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;

public class CallbackResultActivity extends AppCompatActivity implements ConstantVariable{


    private static final String TAG = "CallbackResultActivity";


    CustomTextView txtTitle, txtStatus, txtContinue,txtPaymentTitle,txtAccountNumberTittle,
            txtAccountNumber,txtCopy,txtAccountNameTittle,txtAccountName,txtPayNow;

    MyPreference prefs;
    AppCompatActivity activity;
    Resources resources;
    LinearLayout layoutPaymnet;

    int result = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        result = intent.getIntExtra(KBZPay.EXTRA_RESULT, 0);
        setContentView(R.layout.layout_checkout_success);


        Log.e(TAG, "onCreate: " );


        activity = CallbackResultActivity.this;
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
        txtPayNow = findViewById(R.id.txtPayNow);


        txtContinue.setText(resources.getString(R.string.added_to_cart_cancel));
        txtTitle.setText(resources.getString(R.string.order_status));
        txtPayNow.setText(resources.getString(R.string.pay_now));


        txtPaymentTitle.setText(String.format(resources.getString(R.string.payment_info),prefs.getStringPreferences(ConstantVariable.TEMP_COMMISSION_NAME)));
        txtAccountNumber.setText(prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NUM ));
        txtCopy.setText(resources.getString(R.string.copy));
        txtAccountNameTittle.setText(resources.getString(R.string.acc_name));
        txtAccountNumberTittle.setText(resources.getString(R.string.acc_num));
        txtAccountName.setText( prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NAME));

        txtPaymentTitle.setTypeface(txtPaymentTitle.getTypeface(), Typeface.BOLD);
        txtAccountNumber.setTypeface(txtAccountNumber.getTypeface(), Typeface.BOLD);
        txtAccountName.setTypeface(txtPaymentTitle.getTypeface(), Typeface.BOLD);
        txtStatus.setTypeface(txtStatus.getTypeface(), Typeface.BOLD);


        layoutPaymnet.setVisibility(View.GONE);

        if (prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NAME).length() == 0){
            txtAccountName.setText("");
        }
        if (prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NUM).length() == 0){
            txtAccountNumber.setText("");
        }


        if (result == KBZPay. COMPLETED) {
            Log.e("KBZPay", "pay success! ");
            txtStatus.setText(resources.getString(R.string.kpay_success));
        } else {
            String failMsg = intent.getStringExtra(KBZPay.EXTRA_FAIL_MSG);
            Log.e("KBZPay", "pay fail, fail reason = " + failMsg);
            txtStatus.setText(resources.getString(R.string.kpay_fail));
        }




        txtPayNow.setVisibility(View.GONE);

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefs.getBooleanPreference(PAYMENTAGAIN)){
                    activity.finish();
                }
                else{
                    prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                    activity.finish();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (prefs.getBooleanPreference(PAYMENTAGAIN)){
            activity.finish();
        }
        else{
            prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.finish();
        }

    }
}
