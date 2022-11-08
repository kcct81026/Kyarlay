package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.MainPaymentObj;
import com.kyarlay.ayesunaing.object.PaymentObject;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalStepAdapter;
import com.rakshakhegde.stepperindicator.StepperIndicator;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityStepTwoCart extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "ActivityStepTwoCart";

    MyPreference prefs;
    AppCompatActivity activity;
    Resources resources;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    UniversalStepAdapter universalAdapter;
    RecyclerView.LayoutManager manager;
    DatabaseAdapter databaseAdapter;

    LinearLayout back_layout;
    CustomTextView title, txtContinue;
    RecyclerView recyclerView;
    StepperIndicator stepper_indicator;
    RelativeLayout relativeCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_step_one_cart);

        activity = ActivityStepTwoCart.this;
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();
        databaseAdapter = new DatabaseAdapter(activity);


        back_layout = findViewById(R.id.back_layout);
        title = findViewById(R.id.title);
        txtContinue = findViewById(R.id.txtContinue);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        stepper_indicator =  findViewById(R.id.stepper_indicator);
        relativeCart =  findViewById(R.id.relativeCart);


        manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);

        universalAdapter = new UniversalStepAdapter(activity, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        stepper_indicator.setCurrentStep(1);
        title.setText("Payment");
        txtContinue.setText(resources.getString(R.string.phone_number_continue));


        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtContinue.setEnabled(false);

                if(!prefs.isContains(TEMP_CHOOSE_PAYMENT_ID)){
                    ToastHelper.showToast(activity,resources.getString(R.string.choose_payment));
                    txtContinue.setEnabled(true);
                }else{
                    txtContinue.setEnabled(true);
                    Intent intent = new Intent(activity, ActivityStepThreeCart.class);
                    startActivity(intent);


                }

            }
        });

        Product noitem = new Product();
        noitem.setPostType(CART_DETAIL_FOOTER);
        mainCatDetails.add(noitem);
        universalAdapter.notifyDataSetChanged();
        getPaymentList();


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void clickNoify(){

        if (mainCatDetails.size()> 0){
            universalAdapter.notifyDataSetChanged();
        }




    }

    private void getPaymentList()
    {


        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest( constantPaymentList + "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e(TAG, "onResponse: ---------------- 810 "  + response.toString() );


                        if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            mainCatDetails.remove(mainCatDetails.size() - 1);
                        }

                        if(response.length() > 0) {


                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            Type listType = new TypeToken<List<PaymentObject>>() {}.getType();
                            final List<PaymentObject> paymentObjectList = mGson.fromJson(response.toString(), listType);



                            if (paymentObjectList.size() > 0){


                                if(!prefs.isContains(TEMP_CHOOSE_PAYMENT_ID)){
                                    prefs.saveIntPerferences(TEMP_CHOOSE_PAYMENT_ID, paymentObjectList.get(0).getId() );
                                    prefs.saveFloatPerferences(TEMP_COMMISSION_RATE,  paymentObjectList.get(0).getCommission());
                                    prefs.saveStringPreferences(TEMP_COMMISSION_IMG,  paymentObjectList.get(0).getImg_url());
                                    prefs.saveStringPreferences(TEMP_COMMISSION_NAME,  paymentObjectList.get(0).getName());
                                    prefs.saveStringPreferences(TEMP_CHOOSE_PAYMENT_TAG,  paymentObjectList.get(0).getTag());
                                    prefs.saveStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NAME,  paymentObjectList.get(0).getAcc_name());
                                    prefs.saveStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NUM,  paymentObjectList.get(0).getAcc_num());


                                }


                                MainPaymentObj mainPaymentObj = new MainPaymentObj();
                                mainPaymentObj.setId(0);
                                mainPaymentObj.setPaymentObjectList(paymentObjectList);
                                mainPaymentObj.setPostType(STEP_TWO_MAIN);
                                mainCatDetails.add(mainPaymentObj);
                                universalAdapter.notifyItemInserted(mainCatDetails.size());


                                relativeCart.setVisibility(View.VISIBLE);

                            }

                        }



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastHelper.showToast(ActivityStepTwoCart.this, resources.getString(R.string.search_error));
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                }

                Product noitem = new Product();
                noitem.setPostType(REFRESH_FOOTER);
                mainCatDetails.add(noitem);


                universalAdapter.notifyDataSetChanged();
            }
        }){

            /*
             *
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
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }

    public void clickRefresh(){

        mainCatDetails.clear();
        Product noitem = new Product();
        noitem.setPostType(CART_DETAIL_FOOTER);
        mainCatDetails.add(noitem);
        universalAdapter.notifyDataSetChanged();
        getPaymentList();

    }

}
