package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Addresses;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalStepAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ayesunaing on 9/6/16.
 */
public class ShoppingCartActivity extends AppCompatActivity implements ConstantVariable, ConstantsDB, Constant, OnMenuItemClickListener, View.OnClickListener{
    private static final String TAG = "ShoppingCartActivity";

    RecyclerView recyclerView;
    UniversalStepAdapter adapter;
    ArrayList<UniversalPost> universalList = new ArrayList<>();
    ArrayList<UniversalPost> cartList = new ArrayList<>();
    DatabaseAdapter databaseAdapter;
    CustomTextView payabelText, payableAmount, delivery_fee, to_show_order, delivery_text;
    Display display;

    DecimalFormat formatter = new DecimalFormat("#,###,###");
    MyPreference prefs;
    ArrayList<Delivery> delList = new ArrayList<>();

    Animation animShow, animHide;
    Resources resources;
    ImageView delivery_image;
    ProgressDialog progressDialog;

    CustomTextView title, txtCheckOut, txtEmpty, txtSolution,txtTotal, txtTotalTitle;
    LinearLayout title_layout, back_layout;
    AppCompatActivity activity;
    FirebaseAnalytics firebaseAnalytics;
    RelativeLayout relativeCart;
    LinearLayout linearEmpty, linearTotal;

    TextView viewHidden;
    LinearLayout linearTownship;
    CustomTextView txtTownship,txtTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        display          = getWindowManager().getDefaultDisplay();
        prefs            = new MyPreference(this);
        prefs.saveIntPerferences(CART_DETAIL_SAVE, 100);
        Context context = LocaleHelper.setLocale(ShoppingCartActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        activity = (AppCompatActivity) ShoppingCartActivity.this;
        progressDialog = new ProgressDialog(activity);

        Log.e(TAG, "onCreate:   " );

        prefs.saveBooleanPreference(ALREADY_USE_POINT, false);
        prefs.saveIntPerferences(CHECK_USE_POINT, 0);
        prefs.saveIntPerferences(SP_TEMP_TOTAL, 0);

        new MyFlurry(ShoppingCartActivity.this);

        try {

            FlurryAgent.logEvent("View Shopping Cart");
        } catch (Exception e) {}
        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);

        setContentView(R.layout.shopping_cart);
        recyclerView     = (RecyclerView) findViewById(R.id.recycler_view);
        payableAmount    = (CustomTextView) findViewById(R.id.cart_detail_payable_amount);
        payabelText      = (CustomTextView) findViewById(R.id.cart_detail_payable_text);
        delivery_fee     = (CustomTextView) findViewById(R.id.delivery_fee);
        to_show_order    = (CustomTextView) findViewById(R.id.show_order) ;
        txtEmpty    = (CustomTextView) findViewById(R.id.txtEmpty) ;
        txtSolution    = (CustomTextView) findViewById(R.id.txtSolution) ;
        txtTotal    = (CustomTextView) findViewById(R.id.txtTotal) ;
        txtTotalTitle    = (CustomTextView) findViewById(R.id.txtTotalTitle) ;
        delivery_text    = (CustomTextView) findViewById(R.id.delivery_text);
        delivery_image   = (ImageView) findViewById(R.id.delivery_image);
        relativeCart   = (RelativeLayout) findViewById(R.id.relativeCart);
        linearEmpty   =  findViewById(R.id.linearEmpty);
        linearTotal   =  findViewById(R.id.linearTotal);

        viewHidden = findViewById(R.id.viewHidden);
        linearTownship = findViewById(R.id.linearTownship);
        txtTownship = findViewById(R.id.txtTownship);
        txtTitle = findViewById(R.id.txtTitle);

        databaseAdapter  = new DatabaseAdapter(ShoppingCartActivity.this);
        animShow = AnimationUtils.loadAnimation(ShoppingCartActivity.this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(ShoppingCartActivity.this, R.anim.view_hide);

        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        txtCheckOut     = (CustomTextView) findViewById(R.id.txtCheckOut);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 16) / 20;

        title.setText(resources.getString(R.string.card_detail_title));
        txtTotalTitle.setText(resources.getString(R.string.price));

        txtSolution.setText(resources.getString(R.string.empty_order));
        txtEmpty.setText(resources.getString(R.string.solution_order));

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCartActivity.this.finish();
            }
        });


        prefs.saveIntPerferences(DELIVERY_ID,0);
        prefs.saveIntPerferences(DELIVERY_FREE_AMOUNT,0);
        prefs.saveIntPerferences(DELIVERY_PRICE,0);




        final RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);

       cartList = databaseAdapter.getOrder();

        if (cartList.size() > 0){
            Product dis = new Product();
            dis.setPostType(DISCOUNT_TITLE);
            dis.setTitle(resources.getString(R.string.more_order_noti));
            universalList.add(dis);
            universalList.addAll(cartList);

            Product pro = new Product();
            pro         = databaseAdapter.getShoppingCartFooter();
            pro.setPostType(SHOPPING_CART_SUMMARY);
            universalList.add(pro);
            adapter = new UniversalStepAdapter(ShoppingCartActivity.this, universalList);
            recyclerView.setAdapter(adapter);

            linearEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            txtCheckOut.setText(resources.getString(R.string.order));


            txtTotal.setText(formatter.format(pro.getPrice())
                    +" "+resources.getString(R.string.currency));
            txtTotal.setVisibility(View.VISIBLE);
            linearTotal.setVisibility(View.VISIBLE);

        }
        else{

            txtTotal.setVisibility(View.GONE);
            linearTotal.setVisibility(View.GONE);

            txtCheckOut.setText(resources.getString(R.string.added_to_cart_cancel));
            //txtCheckOut.setText("Go Shopping");
            linearEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }







        delList = databaseAdapter.getDelivery();

        txtCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCheckOut.setEnabled(false);

                if (cartList.size() > 0){
                    prefs.remove(TEMP_CHOOSE_PAYMENT_ID);
                    prefs.remove(TEMP_CHOOSE_PAYMENT_TAG);
                    prefs.remove(TEMP_CHOOSE_PAYMENT_ACC_NUM);
                    prefs.remove(TEMP_CHOOSE_PAYMENT_ACC_NAME);
                    prefs.remove(TEMP_CHOOSE_PAYMENT_TAG);
                    prefs.remove(TEMP_COMMISSION_RATE);
                    prefs.remove(TEMP_ADDRESS_ID);

                    if (databaseAdapter.getPickupShops().size() > 0){
                        prefs.saveBooleanPreference(PICK_UP_ON_OFF,true);
                    }
                    else{
                        prefs.saveBooleanPreference(PICK_UP_ON_OFF,false);
                    }

                    prefs.saveBooleanPreference(TEMP_CHOOSE_PICK_UP, false);
                    prefs.saveBooleanPreference(TEMP_NORMAL, false);
                    prefs.saveIntPerferences(TEMP_FAKE_ADDRESS_ID,-1);
                    prefs.saveIntPerferences(TEMP_NORMAL_PRICE,0);
                    prefs.saveStringPreferences(TEMP_NORMAL_DAY,"");



                    ArrayList<Addresses> addressesList = databaseAdapter.getCustomerAddressList();


                    if (addressesList.size() > 0) {


                        prefs.saveBooleanPreference(NO_ADDRESS, false);

                        for (int j=0; j< addressesList.size(); j++){
                            if (addressesList.get(j).getTownShipID() == prefs.getIntPreferences(SP_TOWNSHIP_ID)){
                                prefs.saveIntPerferences(TEMP_FAKE_ADDRESS_ID,addressesList.get(j).getId() );

                            }
                        }

                        if (prefs.getIntPreferences(TEMP_FAKE_ADDRESS_ID) == -1 ){
                            if (prefs.getStringPreferences(SP_USER_ADDRESS).trim().length() > 0){
                                String[] strings = prefs.getStringPreferences(SP_USER_ADDRESS).split(" ");
                                if (strings.length > 0){
                                    for (int j=0; j< addressesList.size(); j++){
                                        if (strings[0].equals(addressesList.get(j).getTownship_name())){
                                            prefs.saveIntPerferences(TEMP_FAKE_ADDRESS_ID,addressesList.get(j).getId() );
                                        }
                                    }
                                }
                            }
                        }

                        Intent intent = new Intent(ShoppingCartActivity.this, ActivityStepOneCart.class);
                        startActivity(intent);

                    }
                    else{
                        //progressDialog.dismiss();
                        Intent intent = new Intent(ShoppingCartActivity.this, ActivityEditAddress.class);
                        prefs.saveBooleanPreference(NO_ADDRESS, true);
                        startActivity(intent);

                    }

                }
                else{
                    finish();


                }

            }
        });



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scrollUp = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {


                super.onScrollStateChanged(recyclerView, newState);

                relativeCart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dx == 0 && dy == 0){
                    relativeCart.setVisibility(View.GONE);
                }
                else{
                    relativeCart.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    public void changeProduct(){

        cartList.clear();
        cartList = databaseAdapter.getOrder();

        if (cartList.size() > 0){

            universalList.clear();
            Product dis = new Product();
            dis.setPostType(DISCOUNT_TITLE);
            dis.setTitle(resources.getString(R.string.more_order_noti));
            universalList.add(dis);
            universalList.addAll(cartList);

            Product pro = new Product();
            pro         = databaseAdapter.getShoppingCartFooter();
            pro.setPostType(SHOPPING_CART_SUMMARY);
            universalList.add(pro);

            linearEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            txtCheckOut.setText(resources.getString(R.string.order));



            txtTotal.setText(formatter.format(pro.getPrice() - prefs.getIntPreferences(CHECK_USE_POINT))
                    +" "+resources.getString(R.string.currency));
            txtTotal.setVisibility(View.VISIBLE);
            linearTotal.setVisibility(View.VISIBLE);



            adapter.notifyDataSetChanged();
        }
        else{
            txtTotal.setVisibility(View.GONE);
            linearTotal.setVisibility(View.GONE);

            txtCheckOut.setText("Go Shopping");
            linearEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }


    }






    public void updatePayment(){

        universalList.remove(universalList.size()-1);
        adapter.notifyItemRemoved(universalList.size()-1);



        Product pro = new Product();
        pro.setPostType(SHOPPING_CART_SUMMARY);
        universalList.add(pro);

        Product fakeProduct = databaseAdapter.getShoppingCartFooter();
        if (prefs.getBooleanPreference(ALREADY_USE_POINT)){
            int toShowPrice = 0;
            int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

            if(((fakeProduct.getPoint_usage() * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
            }
            else{
                toShowPrice = (fakeProduct.getPoint_usage() * percent ) / 100  ;
            }

            prefs.saveIntPerferences(CHECK_USE_POINT, toShowPrice);


        }
        else{
            prefs.saveIntPerferences(CHECK_USE_POINT, 0);
        }


        adapter.notifyItemInserted(universalList.size());



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtCheckOut.setEnabled(true);
        invalidateOptionsMenu();
        //setUpToolbar();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                prefs.saveIntPerferences(CART_DETAIL_SAVE, 100);
                ShoppingCartActivity.this.finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.saveIntPerferences(CART_DETAIL_SAVE, 100);
        ShoppingCartActivity.this.finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        return false;
    }

    @Override
    public void onClick(View view) {
        try {


            FlurryAgent.logEvent("View Delivery Method Dialog");
        } catch (Exception e) {
        }
        final Dialog dialog = new Dialog(ShoppingCartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delivery);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);

        CustomTextView title    = (CustomTextView) dialog.findViewById(R.id.title);
        LinearLayout mainlayout = (LinearLayout) dialog.findViewById(R.id.layout);
        title.setText(resources.getString(R.string.dialog_delivery_title));

        if(delList.size() > 0){
            for(int i = 0; i < delList.size(); i++){

                Delivery delivery = delList.get(i);

                LinearLayout deliveyLayout = new LinearLayout(ShoppingCartActivity.this);
                deliveyLayout.setId(delivery.getId());
                deliveyLayout.setOrientation(LinearLayout.VERTICAL);
                deliveyLayout.setPadding(0, 10, 0, 0);
                if(prefs.getIntPreferences(DELIVERY_ID) ==  delivery.getId())
                    deliveyLayout.setBackgroundResource(R.color.checked_bg);

                CustomTextView price = new CustomTextView(ShoppingCartActivity.this);
                price.setTextSize(16);
                price.setPadding(10, 0, 10, 10);
                price.setGravity(Gravity.LEFT);
                price.setText(delivery.getName()+" ( "+formatter.format(delivery.getPrice())+" "+getResources().getString(R.string.currency)+" )");
                deliveyLayout.addView(price);

                CustomTextView desc = new CustomTextView(ShoppingCartActivity.this);
                desc.setTextSize(14);
                desc.setPadding(20, 10, 10, 10);
                desc.setGravity(Gravity.LEFT);
                desc.setText(delivery.getDesc());
                deliveyLayout.addView(desc);

                CustomTextView choose = new CustomTextView(ShoppingCartActivity.this);
                choose.setBackgroundResource(R.drawable.textview_round);
                choose.setTextSize(14);
                choose.setText(resources.getString(R.string.choose));
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                parms.gravity = Gravity.RIGHT;
                parms.setMargins(0, 5, 5, 10);
                choose.setLayoutParams(parms);
                deliveyLayout.addView(choose);


                TextView space = new TextView(ShoppingCartActivity.this);
                space.setHeight(6);
                space.setBackgroundResource(R.color.checked_bg);
                deliveyLayout.addView(space);
                mainlayout.addView(deliveyLayout);

                final int finalI = i;
                deliveyLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", String.valueOf(delList.get(finalI).getId()));
                            FlurryAgent.logEvent("Choose Delivery Method", mix);

                        } catch (Exception e) {
                        }
                        prefs.saveIntPerferences(DELIVERY_ID, delList.get(finalI).getId());
                        prefs.saveIntPerferences(DELIVERY_PRICE, delList.get(finalI).getPrice());
                        prefs.saveIntPerferences(DELIVERY_FREE_AMOUNT, delList.get(finalI).getFreeDelivery());
                        updatePayment();
                        dialog.dismiss();
                    }
                });

            }
            dialog.show();
        }

    }





    public void showTotalPrice(){

        Log.e(TAG, "onClick: ********************* 81026 "  );

        if (cartList.size() > 0){
            txtTotal.setText( formatter.format(prefs.getIntPreferences(SP_TEMP_TOTAL))
                    +" "+resources.getString(R.string.currency));
            txtTotal.setVisibility(View.VISIBLE);
            linearTotal.setVisibility(View.VISIBLE);

        }
        else{
            txtTotal.setVisibility(View.GONE);
            linearTotal.setVisibility(View.GONE);
        }




    }


}
