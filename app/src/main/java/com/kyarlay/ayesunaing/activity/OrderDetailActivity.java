package com.kyarlay.ayesunaing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kbzbank.payment.sdk.callback.CallbackResultActivity;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Order;
import com.kyarlay.ayesunaing.object.OrderDetailsObj;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayesunaing on 9/27/16.
 */
public class OrderDetailActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "OrderDetailActivity";

    Order order;
    ArrayList<UniversalPost> universalList = new ArrayList<>();
    RecyclerView recyclerView;
    CustomTextView   title;

    LinearLayout back_layout;
    MyPreference prefs;
    UniversalAdapter adapter;
    Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        Log.e(TAG, "onCreate: " );


       // new MyFlurry(OrderDetailActivity.this);
        display = getWindowManager().getDefaultDisplay();


        prefs       = new MyPreference(OrderDetailActivity.this);
        recyclerView     = (RecyclerView) findViewById(R.id.recycler);
        title            = (CustomTextView) findViewById(R.id.title);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);

        title.getLayoutParams().width    = (display.getWidth() * 17 ) / 20;
        back_layout.getLayoutParams().width    = (display.getWidth() * 3 ) / 20;

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        order = bundle.getParcelable("order");
        order.setPostType(ORDER_DETAIL_TOP);
        universalList.add(order);



        prefs.saveIntPerferences(OLD_ORDER_DIFFER , 0);
        prefs.saveBooleanPreference(PAYMENTHISTORYDONE, false);

        orderDetailList(order.getOrder_id());

        title.setText(getResources().getString(R.string.order_actionbar_title) + " " +order.getVoucher_number());
        LinearLayoutManager manager = new LinearLayoutManager(OrderDetailActivity.this);
        recyclerView.setLayoutManager(manager);
        adapter = new UniversalAdapter(OrderDetailActivity.this, universalList);
        recyclerView.setAdapter(adapter);

        Log.e(TAG, "onCreate: *********** "  + order.toString() );


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }

    public void orderDetailList(int order_id){






        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantOrderedDetail+order_id + "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {



                    if(response.length() > 0) {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            Type listType = new TypeToken<List<Product>>() {
                            }.getType();
                            List<Product> productList = mGson.fromJson(response.toString(), listType);

                            int total = 0;

                            for(int i = productList.size() - 1 ; i >= 0 ; i--){
                                Product order1 = productList.get(i);
                                order1.setPostType(ORDER_DETAIL);
                               // universalList.add(order1);

                                total = total + (productList.get(i).getPrice()  * productList.get(i).getLikes());

                                productList.set(i, order1);
                            }

                            prefs.saveIntPerferences(OLD_ORDER_DIFFER, (total + (int)order.getPayment_fee() ));

                            OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                            orderDetailsObj.setOrder(order);
                            orderDetailsObj.setProductList(productList);
                            orderDetailsObj.setViewAll(false);

                            orderDetailsObj.setPostType(ORDER_DETAILS_FOOTER);
                            universalList.add(orderDetailsObj);

                            //order.setPostType(ORDER_DETAILS_FOOTER);
                          //  universalList.add(order);

                            adapter.notifyDataSetChanged();



                        }catch (Exception e){
                            Log.e(TAG, "onResponse: " + e.getMessage() );
                        }
                    }
                }

            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){            /**
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
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void getProduct(int productID){
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantProduct+productID + "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> productList = mGson.fromJson(response.toString(), listType);

                                for(int i = 0; i < productList.size(); i++){
                                    Product product = new Product();
                                    product   = productList.get(i);

                                    try {

                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("product_id",String.valueOf(product.getId()));
                                        //FlurryAgent.logEvent("Click Product", mix);

                                    } catch (Exception e) {
                                    }

                                    i = productList.size();
                                    Intent intent = new Intent(OrderDetailActivity.this, ProductActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("product", product);
                                    intent.putExtras(bundle);
                                    startActivity(intent);


                                }
                                adapter.notifyDataSetChanged();



                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }){            /**
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
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(prefs.getBooleanPreference(PAYMENTHISTORYDONE)){
            Log.e(TAG, "onResume: ------------------------------------------------------- "   );
            prefs.saveBooleanPreference(PAYMENTDONE, false);
            prefs.saveBooleanPreference(PAYMENTAGAIN, true);
            prefs.saveBooleanPreference(PAYMENTHISTORYDONE, false);

            Intent intent = new Intent(OrderDetailActivity.this, CallbackResultActivity.class);
            startActivity(intent);
        }
    }
}
