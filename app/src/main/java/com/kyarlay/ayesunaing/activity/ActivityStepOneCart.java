package com.kyarlay.ayesunaing.activity;

import android.content.Context;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.StoreHelper;
import com.kyarlay.ayesunaing.object.Addresses;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.ShopLocation;
import com.kyarlay.ayesunaing.object.TownShipObject;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalStepAdapter;
import com.rakshakhegde.stepperindicator.StepperIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityStepOneCart extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "ActivityStepOneCart";

    MyPreference prefs;
    AppCompatActivity activity;
    Resources resources;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    List<Delivery> deliveryList = new ArrayList<>();
    DatabaseAdapter databaseAdapter;
    UniversalStepAdapter universalAdapter;
    RecyclerView.LayoutManager manager;

    LinearLayout back_layout;
    RelativeLayout relativeCart;
    CustomTextView title, txtContinue;
    RecyclerView recyclerView;
    StepperIndicator stepper_indicator;
    List<Addresses> addList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_step_one_cart);

        activity = ActivityStepOneCart.this;
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        databaseAdapter = new DatabaseAdapter(activity);


        deliveryList = databaseAdapter.getDelivery();

        back_layout = findViewById(R.id.back_layout);
        title = findViewById(R.id.title);
        txtContinue = findViewById(R.id.txtContinue);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        stepper_indicator = findViewById(R.id.stepper_indicator);
        relativeCart = findViewById(R.id.relativeCart);

        manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);

        universalAdapter = new UniversalStepAdapter(activity, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);


        prefs.saveIntPerferences(TEMP_CHOOSE_DAY, -1);
        prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, -1);
        prefs.saveIntPerferences(TEMP_DELI_ID, -1);

        //prefs.saveIntPerferences(TEMP_FAKE_ADDRESS_ID, -1);
        prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, 0);
        prefs.saveIntPerferences(DELIVERY_ID, 0);
        prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
        prefs.saveStringPreferences(TEMP_REMARK_TEXT, "");
        prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, false);
        prefs.saveBooleanPreference(TEMP_SAME_DAY, false);
        prefs.saveBooleanPreference(TEMP_ADD_ADDRESS, false);
        prefs.saveBooleanPreference(DELIVERY_STORE_ID_MINUS, false);
        prefs.saveBooleanPreference(CAN_CHOOSE_TIMESLOT, false);


        stepper_indicator.setCurrentStep(0);
        title.setText("Address");
        txtContinue.setText(resources.getString(R.string.phone_number_continue));



        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        if (prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP))
            choosePickUp();
        else
            settingList();


    }


    private void settingList(){

        mainCatDetails.clear();
        prefs.saveIntPerferences(TEMP_DELI_ID, -1);

        List<Addresses> addressesList = databaseAdapter.getCustomerAddressList();
        TownShipObject universalPost = new TownShipObject();
        universalPost.setAddressesList(addressesList);
        universalPost.setPostType(STEP_ONE_TOP);
        mainCatDetails.add(universalPost);

        int checkID = -1;

        if (addressesList.size() > 0){
            for (int i = 0; i < addressesList.size(); i++) {

                Addresses addresses = addressesList.get(i);

                if (prefs.getIntPreferences(TEMP_FAKE_ADDRESS_ID) == -1 && i ==0 ) {
                    addresses.setSelected(true);
                    StoreHelper.saveDeliverPreferences(activity, addresses.getDelivery_id());
                    checkID = StoreHelper.getStoreIdbyTownShipId(activity, addresses.getTownShipID());

                }
                else if (prefs.getIntPreferences(TEMP_FAKE_ADDRESS_ID) == addresses.getId()){
                    addresses.setSelected(true);
                    StoreHelper.saveDeliverPreferences(activity, addresses.getDelivery_id());
                    checkID = StoreHelper.getStoreIdbyTownShipId(activity, addresses.getTownShipID());
                }
                else {
                    addresses.setSelected(false);

                }


                addresses.setCheckable(true);
                addresses.setPostType(STEP_ONE_ITEM);

                mainCatDetails.add(addresses);

            }
        }
        else{
            checkID = prefs.getIntPreferences(TEMP_PICK_UP_ID);
        }

        Log.e(TAG, "settingList: ------------------- " + checkID );




        if (checkID != -1) {
            checkDelivery(checkID);
            prefs.saveBooleanPreference(DELIVERY_STORE_ID_MINUS, true);
        }
        else{
            prefs.saveBooleanPreference(DELIVERY_STORE_ID_MINUS, false);
            prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, true);
            prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
            prefs.saveBooleanPreference(TEMP_SAME_DAY, false);

            UniversalPost universalShop = new UniversalPost();
            universalShop.setPostType(STEP_ONE_BOTTOMS);
            mainCatDetails.add(universalShop);

            universalAdapter.notifyDataSetChanged();

        }



    }

    public void choosePickUp(){

        prefs.saveIntPerferences(TEMP_DELI_ID, -1);

        if (prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP)){
            mainCatDetails.clear();

            TownShipObject universalPost = new TownShipObject();
            universalPost.setPostType(STEP_ONE_TOP);
            mainCatDetails.add(universalPost);


            int checkID = 0;
            ArrayList<ShopLocation> shopLocationArrayList = databaseAdapter.getOneShop(prefs.getIntPreferences(TEMP_PICK_UP_ID));
            if (shopLocationArrayList.size() > 0){
                ShopLocation shopLocation = shopLocationArrayList.get(0);
                shopLocation.setPostType(PICK_UP_SHOP_ITEM);
                mainCatDetails.add(shopLocation);

                checkID = shopLocation.getId();

                Log.e(TAG, "choosePickUp: 333333 "  + shopLocation.getId() );


                int index = -1;
                for (int i=0; i< deliveryList.size();i++){




                    if (deliveryList.get(i).getName().equals("Showroom Pick-up")) {
                        index = deliveryList.get(i).getId();

                        Log.e(TAG, "choosePickUp -----------------*** -----  " + index);

                    }

                }

                if (index != -1){
                    prefs.saveIntPerferences(DELIVERY_ID, index);
                }
                else{
                    prefs.saveIntPerferences(DELIVERY_ID, shopLocation.getId());
                }


                prefs.saveIntPerferences(DELIVERY_PRICE, 0);
                prefs.saveIntPerferences(DELIVERY_FREE_AMOUNT, 0);

                Log.e(TAG, "choosePickUp:  ++++++++++++++++  "  + prefs.getIntPreferences(DELIVERY_ID));
            }



            ArrayList<Addresses> addressesList = databaseAdapter.getCustomerAddressList();
            for (int i = 0; i < addressesList.size(); i++) {
                Addresses addresses = addressesList.get(i);


                addresses.setSelected(false);
                addresses.setCheckable(true);
                addresses.setPostType(STEP_ONE_ITEM);

                mainCatDetails.add(addresses);
            }


            if (checkID != -1) {
                checkDelivery(checkID);
                prefs.saveBooleanPreference(DELIVERY_STORE_ID_MINUS, true);
            }
            else{
                prefs.saveBooleanPreference(DELIVERY_STORE_ID_MINUS, false);
                prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, true);
                prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
                prefs.saveBooleanPreference(TEMP_SAME_DAY, false);

                UniversalPost universalShop = new UniversalPost();
                universalShop.setPostType(STEP_ONE_BOTTOMS);
                mainCatDetails.add(universalShop);

                universalAdapter.notifyDataSetChanged();

            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        //prefs.saveBooleanPreference(TEMP_ADD_ADDRESS, true);

        if (prefs.getBooleanPreference(TEMP_ADD_ADDRESS)) {

            prefs.saveBooleanPreference(TEMP_ADD_ADDRESS, false);

            prefs.saveIntPerferences(TEMP_CHOOSE_DAY, -1);
            prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, -1);
            prefs.saveIntPerferences(TEMP_DELI_ID, -1);
            prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, 0);

            //prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
            prefs.saveStringPreferences(TEMP_REMARK_TEXT, "");
            //prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, false);



            if (prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP))
                choosePickUp();
            else {
                settingList();
                Log.e(TAG, "onResume: "  + "this is setting list " );
            }
        }




    }

    public void clickRefresh(){


        prefs.saveIntPerferences(TEMP_CHOOSE_DAY, -1);
        prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, -1);
        prefs.saveIntPerferences(TEMP_DELI_ID, -1);
        prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, 0);
        prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
        prefs.saveBooleanPreference(TEMP_ADD_ADDRESS, false);
        prefs.saveStringPreferences(TEMP_REMARK_TEXT, "");
        prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, false);
        prefs.saveBooleanPreference(TEMP_SAME_DAY, false);



        settingList();
    }

    private void checkDelivery(int deliveryId){


        JSONObject uploadOrder = new JSONObject();
        try {
            uploadOrder.put("store_location_id",    deliveryId);
            uploadOrder.put("products",    databaseAdapter.getCheckOrderToServer());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "uploadOrderAddress: "  + uploadOrder.toString() );



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantCheckDelivery  , uploadOrder,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: AAAAA 81026 "  + response.toString() );
                        try{
                            if (response.getInt("same_day") == 1){
                                prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, true);
                                prefs.saveBooleanPreference(TEMP_IS_TOMORROW, true);
                                prefs.saveBooleanPreference(TEMP_SAME_DAY, true);
                            }
                            else{
                                prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, true);
                                prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
                                prefs.saveBooleanPreference(TEMP_SAME_DAY, false);
                            }


                            if (response.getInt("time_slot") == 1){
                                prefs.saveBooleanPreference(CAN_CHOOSE_TIMESLOT,true);
                            }
                            else
                                prefs.saveBooleanPreference(CAN_CHOOSE_TIMESLOT,false);

                            UniversalPost universalShop = new UniversalPost();
                            universalShop.setPostType(STEP_ONE_BOTTOMS);
                            mainCatDetails.add(universalShop);

                            universalAdapter.notifyDataSetChanged();

                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            /* *
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"Order123");
    }
}
