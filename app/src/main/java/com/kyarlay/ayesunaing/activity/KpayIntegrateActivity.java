/*
package com.kyarlay.ayesunaing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kbzbank.payment.KBZPay;
import com.kbzbank.payment.sdk.callback.CallbackResultActivity;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.MyPreference;

public class KpayIntegrateActivity extends AppCompatActivity implements Constant, ConstantVariable {

    TextView txtPay;
    Activity context;
    MyPreference prefs;

    private static final String TAG = "KpayIntegrateActivity";
    private int flag =0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pay);
        prefs = new MyPreference(this);

        context = this;
        txtPay = findViewById(R.id.txtPay);

        txtPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 1 ;
                KBZPay.startPay(context,
                        prefs.getStringPreferences(SP_ORDER_INFO),
                        prefs.getStringPreferences(SP_SIGN),
                        prefs.getStringPreferences(SP_SHA));

              */
/*  Intent intent = new Intent(KpayIntegrateActivity.this, CallbackResultActivity.class);
                startActivity(intent);*//*



            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(flag == 1){
            Intent intent = new Intent(KpayIntegrateActivity.this, CallbackResultActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: *************************** "  + "81026"  );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 0);
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
*/
