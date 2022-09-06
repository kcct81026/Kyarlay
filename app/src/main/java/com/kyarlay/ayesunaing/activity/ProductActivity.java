package com.kyarlay.ayesunaing.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.FlowLayout;
import com.kyarlay.ayesunaing.custom_widget.SelectableRoundedImageView;
import com.kyarlay.ayesunaing.custom_widget.TagAdapter;
import com.kyarlay.ayesunaing.custom_widget.TagFlowLayout;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Category;
import com.kyarlay.ayesunaing.object.Discount;
import com.kyarlay.ayesunaing.object.Flash_Sales;
import com.kyarlay.ayesunaing.object.Images;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Supplier;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import me.myatminsoe.mdetect.MDetect;

/**
 * Created by ayesunaing on 8/30/16.
 */
public class ProductActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "ProductActivity";


    ArrayList<UniversalPost> universalList = new ArrayList<>();
    Product product ;

    RecyclerView recyclerView;
    UniversalAdapter adapter;
    ImageView cart;
    LinearLayout linearOrder;
    CircularTextView  cart_text, wishlist_count;
    RelativeLayout layout_like;
    Animation bounce;
    AnimatorSet animationSet;
    DatabaseAdapter databaseAdapter;
    MyPreference prefs;

    Display display;
    Resources resources;
    CustomTextView title;
    LinearLayout title_layout, back_layout;
    RelativeLayout cart_layout;


    String fromClass = "";
    ImageLoader imageLoader;

    CustomButton txtOrder;
    FirebaseAnalytics firebaseAnalytics;
    AppCompatActivity activity;

    Map<Integer, CountDownTimer> countDownMap = new HashMap<Integer, CountDownTimer>();

    int qtyProduct = 1;
    String optionTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs   = new MyPreference(ProductActivity.this);


        Context context = LocaleHelper.setLocale(ProductActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        imageLoader         = AppController.getInstance().getImageLoader();
        display = getWindowManager().getDefaultDisplay();
        setContentView(R.layout.product_detail_adapter);
        activity = ProductActivity.this;
        databaseAdapter = new DatabaseAdapter(ProductActivity.this);
        Intent intent = getIntent();
        final Bundle bundle=intent.getExtras();
        product = bundle.getParcelable("product");

        fromClass   = bundle.getString("fromClass");

        new MyFlurry(ProductActivity.this);

        Log.e(TAG, "onCreate: "  + product.getId());

        linearOrder      = findViewById(R.id.linearOrder);
        linearOrder.setVisibility(View.GONE);

        txtOrder         = findViewById(R.id.order);
        firebaseAnalytics   = FirebaseAnalytics.getInstance(ProductActivity.this);

        recyclerView     = (RecyclerView) findViewById(R.id.recycler_view);

        cart             = (ImageView)    findViewById(R.id.menu_cart_imageview);
        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);

        layout_like     = (RelativeLayout) findViewById(R.id.like_layot);
        wishlist_count  = (CircularTextView) findViewById(R.id.wishlist_count);
        layout_like.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 11) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;



        if (prefs.isNetworkAvailable()){
            checkCount(product.getId());
        }

        try {

            Map<String, String> mix = new HashMap<String, String>();
            mix.put("product_id",String.valueOf(product.getId()));
            mix.put("category_id",String.valueOf(product.getCategoryId()));
            mix.put("status", fromClass);
            FlurryAgent.logEvent("View Product", mix);
        } catch (Exception e) {
        }


        product.setPostType(PRODUCT_DETAIL);
        universalList.add(product);




        List<Images> items = new ArrayList<>();
        items = product.getDetailImages();

        if (items.size() > 0){
            for(int i = 0 ; i < items.size() ; i++){
                Images uni = items.get(i);
                if (i == 0)
                    uni.setImgVisible(1);
                else
                    uni.setImgVisible(0);
                uni.setPostType(DETAIL_IMAGE);
            }
        }



        final Product productDetail1 = new Product();
        productDetail1.setBrand_id(product.getBrand_id());
        productDetail1.setDetailImages(product.getDetailImages());
        productDetail1.setBrand_name(product.getBrand_name());
        productDetail1.setId(product.getId());
        productDetail1.setCategoryId(product.getCategoryId());
        productDetail1.setOptions(product.getOptions());
        productDetail1.setDesc(product.getDesc());
        productDetail1.setQaObjectList(product.getQaObjectList());
        productDetail1.setPostType(DETAIL_GRID);
        universalList.add(productDetail1);



        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "product_detail");
                    FlurryAgent.logEvent("Click Product Wishlist Icon", mix);
                } catch (Exception e) {}
                Intent intent = new Intent(ProductActivity.this, WishListActivity.class);
                startActivity(intent);
            }
        });


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countDownMap == null) {
                    return;
                }else
                {

                    for(CountDownTimer timer : countDownMap.values()){
                        timer.cancel();
                    }

                }
                if(fromClass != null && fromClass.equals(FROM_FIREBASE)){
                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "Product");
                        FlurryAgent.logEvent("Incoming Pushnotification Click", mix);

                    } catch (Exception e) {}

                    Intent proIntent = new Intent(ProductActivity.this, MainActivity.class);
                    startActivity(proIntent);
                }
                ProductActivity.this.finish();
            }
        });
        title.setText(product.getTitle()+"");



        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "product_detail");
                        FlurryAgent.logEvent("Click Shopping Cart", mix);


                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(ProductActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(ProductActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }

            }
        });


        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        adapter = new UniversalAdapter(ProductActivity.this, universalList, countDownMap);
        recyclerView.setAdapter(adapter);



        if (prefs.isNetworkAvailable()){
            Product pro1 = new Product();
            pro1.setPostType(CART_DETAIL_FOOTER);
            universalList.add(pro1);
            getPromoteProduct();
        }




        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scrollUp = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if(newState == RecyclerView.SCROLL_STATE_IDLE && scrollUp) {


                }



                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();


                if (universalList.get(lastVisibleItem).getPostType().equals(CATEGORY_DETAIL) ){
                    linearOrder.setVisibility(View.GONE);
                }
                else{
                    if (dx == 0 && dy == 0){
                        linearOrder.setVisibility(View.GONE);
                    }
                    else {
                        if(product.getRecommended() != null &&  product.getRecommended().trim().length() > 0){
                            linearOrder.setVisibility(View.GONE);
                        }
                        else
                            linearOrder.setVisibility(View.VISIBLE);

                    }
                }




            }
        });

        // for spinner
        final ArrayList<Supplier> coutslist1 = new ArrayList<>();

        for (int i = 1; i <= SPINNER_COUNT; i++) {
            Supplier supplier = new Supplier();
            supplier.setId(i);
            supplier.setName((i) + "");
            coutslist1.add(supplier);
        }








        txtOrder.setText(resources.getString(R.string.save_to_cart));
        final int original_price = product.getPrice();
        boolean flashSaleCheck = false;

        if (product.getFlashSalesArrayList().size() > 0) {
            final Flash_Sales flash_sales = product.getFlashSalesArrayList().get(0);


            Date currentDate1 = new Date();
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            // Please here set your event date//YYYY-MM-DD
            Date futureDate1 = null;
            try {
                futureDate1 = df1.parse(flash_sales.getEnd_date());
            } catch (ParseException e) {
                e.printStackTrace();
                Log.e(TAG, "onCreate: "  + e.getMessage() );
            }

            if (!currentDate1.after(futureDate1)) {

                if (flash_sales.getReserved_quantity() < flash_sales.getAvailable_quantity())
                    flashSaleCheck = true;
            }
        }

        int total_discount = 0;
        if (!flashSaleCheck){
            if(product.getDiscounts().size() > 0) {
                for (int k = 0; k < product.getDiscounts().size(); k++) {
                    Discount dis = (Discount) product.getDiscounts().get(k);
                    String str = dis.getDiscountType();


                    if (dis.getMember_only() == 1) {
                        if (prefs.getIntPreferences(SP_VIP_ID) == 1) {
                            switch (str) {
                                case DISCOUNT_PERCENTAGE:
                                    if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {
                                        total_discount = (product.getPrice() * dis.getPercentage()) / 100;
                                    }
                                    break;

                                default:
                                    break;
                            }
                        }
                    } else {

                        switch (str) {
                            case DISCOUNT_PERCENTAGE:
                                if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {
                                    total_discount = (product.getPrice() * dis.getPercentage()) / 100;
                                }
                                break;

                            default:
                                break;
                        }
                    }

                }


            }


        }
        else{

            total_discount =( original_price * product.getFlashSalesArrayList().get(0).getDiscount()) / 100;
        }

        if(product.getMember_discount() != 0 && prefs.getIntPreferences(SP_VIP_ID) != 0 ) {

            total_discount += ((product.getPrice() - total_discount)* prefs.getIntPreferences(SP_MEMBER_PERCENTAGE) / 100);
        }








        final int finalTotal_discount = total_discount;




        txtOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(ProductActivity.this);

                final int[] selectedPosition = {0};
                View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottom_shopping_cart_option, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.setCancelable(true);

                SelectableRoundedImageView img = sheetView.findViewById(R.id.img);
                CustomTextView title = sheetView.findViewById(R.id.title);
                CustomTextView titleCategory = sheetView.findViewById(R.id.titleCategory);
                CustomTextView txtOptionTitle = sheetView.findViewById(R.id.txtOptionTitle);
                ImageView imgClose = sheetView.findViewById(R.id.imgClose);
                ImageView imgMinus = sheetView.findViewById(R.id.imgMinus);
                ImageView imgPlus = sheetView.findViewById(R.id.imgPlus);
                TagFlowLayout gridView  = sheetView.findViewById(R.id.category_detail_header);
                TextView quantity = sheetView.findViewById(R.id.quantity);
                LinearLayout linearHeader = sheetView.findViewById(R.id.linearHeader);
                CustomButton order = sheetView.findViewById(R.id.order);
                gridView.setMaxSelectCount(1);

                img.setImageUrl(product.getPreviewImage(), imageLoader);
                title.setText(product.getTitle());
                titleCategory.setText(product.getCategory_name());
                order.setText(resources.getString(R.string.save_to_cart));

                title.setTypeface(title.getTypeface(), Typeface.BOLD);
                txtOptionTitle.setTypeface(txtOptionTitle.getTypeface(), Typeface.BOLD);

                qtyProduct = 1;
                optionTitle = "";

                quantity.setText(qtyProduct + "");

                Log.e(TAG, "onClick:  ***********************   "  + product.getCategory_name() );


                imgMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (qtyProduct >  1){
                            qtyProduct = qtyProduct - 1;
                        }
                        quantity.setText(qtyProduct + "");
                    }
                });

                imgPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qtyProduct = qtyProduct + 1;
                        quantity.setText(qtyProduct + "");
                    }
                });

                order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        mBottomSheetDialog.dismiss();

                        dialogOrder((original_price - finalTotal_discount) );



                    }
                });



                if(product.getOptions() != null && product.getOptions().trim().length() > 0) {

                    linearHeader.setVisibility(View.VISIBLE);


                    final ArrayList<Category> phoneArray = new ArrayList<>();
                    StringTokenizer st = new StringTokenizer(product.getOptions(), ",");
                    while (st.hasMoreTokens()) {
                        Category category = new Category();
                        category.setName(st.nextToken());
                        phoneArray.add(category);
                    }

                    TagAdapter tagAdapter = new TagAdapter(phoneArray) {
                        @Override
                        public View getView(FlowLayout parent, int position, Object o) {
                            Category obj =( Category) o;
                            final LayoutInflater inflater = LayoutInflater.from(ProductActivity.this);
                            TextView tv = (TextView) inflater.inflate(R.layout.category_tab_view,
                                    gridView, false);

                           // tv.setBackground(getResources().getDrawable(R.drawable.tab_color_selector));

                            if (selectedPosition[0] == position)
                                tv.setBackground(getResources().getDrawable(R.drawable.checked_bg_yellow));
                            else
                                tv.setBackground(getResources().getDrawable(R.drawable.checked_bg_grey));

                            Typeface myTypeface;
                            if (MDetect.INSTANCE.isUnicode()){
                                myTypeface = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
                            }
                            else{
                                myTypeface = AppController.typefaceManager.getCompanion().getZAWGYITWO();
                            }
                            tv.setTypeface(myTypeface);
                            tv.setText(obj.getName());
                            return tv;
                        }
                    };
                    gridView.setAdapter(tagAdapter);


                    tagAdapter.setSelectedList(0);


                    gridView.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            try {




                                AppController.getInstance().onStop();

                                tagAdapter.setSelectedList(position);

                                selectedPosition[0] = position;


                                tagAdapter.notifyDataChanged();


                                optionTitle = phoneArray.get(position).getName();

                            } catch (Exception e) {
                            }
                            return false;
                        }
                    });

                }
                else{
                    linearHeader.setVisibility(View.GONE);
                }

                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });


                mBottomSheetDialog.show();
            }
        });




    }


    private void dialogOrder( int total){
        databaseAdapter.insertOrder(product, qtyProduct, total, optionTitle);

        bounceCount();

        if(prefs.getIntPreferences(SP_MEMBER_ID) != 0){

            prefs.saveBooleanPreference(LOGIN_SAVECART, false);
            try {


                Map<String, String> mix = new HashMap<String, String>();
                mix.put("product_id",String.valueOf(product.getId()));
                FlurryAgent.logEvent("Add To Cart", mix);
            } catch (Exception e) {}

            Bundle fbundle = new Bundle();
            fbundle.putString("item_id",product.getId()+"");
            fbundle.putString("item_name",product.getTitle()+"");
            fbundle.putString("item_category",product.getCategory_name()+"");
            fbundle.putString("price",product.getPrice()+"");
            fbundle.putString("quantity",qtyProduct+"");
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, fbundle);


            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setContentView(R.layout.dialog_add_to_cart);

            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
            window.setAttributes(wlp);

            CustomButton cancel = (CustomButton) dialog.findViewById(R.id.dialog_delete_cancel);
            CustomButton confirm = (CustomButton) dialog.findViewById(R.id.dialog_delete_confirm);
            // CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
            CustomTextView text = (CustomTextView) dialog.findViewById(R.id.text);

            //title.setText(resources.getString(R.string.added_to_cart_title));
            text.setText(product.getTitle()+"\t "+resources.getString(R.string.save_to_cart_error));
            cancel.setText(resources.getString(R.string.added_to_cart_cancel));
            confirm.setText(resources.getString(R.string.added_to_cart_confirm));

            int countBefore  = databaseAdapter.getOrderCount();
            CircularTextView circularTextView = (CircularTextView) dialog.findViewById(R.id.menu_cart_idenfier);
            circularTextView.setText(String.valueOf( countBefore));

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "product_detail_dialog");
                        FlurryAgent.logEvent("Click Shopping Cart", mix);

                    } catch (Exception e) {
                    }

                    dialog.dismiss();
                    Intent intent = new Intent(activity, ShoppingCartActivity.class);
                    activity.startActivity(intent);
                }

            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else{
            prefs.saveBooleanPreference(LOGIN_SAVECART, true);
            Intent intent   = new Intent(activity, ActivityLogin.class);
            startActivity(intent);
        }

    }



    public void bounceCount (){
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce_animation);
        animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext()
                , R.animator.flip_animation);
        int count = databaseAdapter.getOrderCount();
        if (count == 0) {
            cart_text.setVisibility(View.GONE);
        } else {
            cart_text.setVisibility(View.VISIBLE);
            cart_text.setText(count + "");
        }

        animationSet.setTarget(cart_text);
        cart_text.startAnimation(bounce);
        animationSet.start();
    }


    public void update_wishlist(){
        wishlist_count.setStrokeWidth(1);
        wishlist_count.setStrokeColor("#000000");
        wishlist_count.setSolidColor("#ffffff");

        int count_wish  = prefs.getIntPreferences(SP_PRODUCT_LIKED_COUNT);
        if(count_wish == 0){
            wishlist_count.setVisibility(View.GONE);
        }else{
            wishlist_count.setVisibility(View.VISIBLE);
            wishlist_count.setText(count_wish + "");
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (countDownMap == null) {

            return;
        }else
        {

            for(CountDownTimer timer : this.countDownMap.values()){
                timer.cancel();
            }

        }
        if(fromClass != null && fromClass.equals(FROM_FIREBASE)){
            try {


                Map<String, String> mix = new HashMap<String, String>();
                mix.put("source", "Product");
                FlurryAgent.logEvent("Incoming Pushnotification Click", mix);
            } catch (Exception e) {}
            Intent proIntent = new Intent(ProductActivity.this, MainActivity.class);
            startActivity(proIntent);
        }
        super.onBackPressed();



    }


    @Override
    protected void onResume() {
        super.onResume();
        cart_text.setStrokeWidth(1);
        cart_text.setStrokeColor("#000000");
        cart_text.setSolidColor("#ffffff");
        int count = databaseAdapter.getOrderCount();

        if (count == 0) {
            cart_text.setVisibility(View.GONE);
        } else {
            cart_text.setVisibility(View.VISIBLE);
            cart_text.setText(count + "");
        }

        wishlist_count.setStrokeWidth(1);
        wishlist_count.setStrokeColor("#000000");
        wishlist_count.setSolidColor("#ffffff");

        int count_wish  = prefs.getIntPreferences(SP_PRODUCT_LIKED_COUNT);
        if(count_wish == 0){
            wishlist_count.setVisibility(View.GONE);
        }else{
            wishlist_count.setVisibility(View.VISIBLE);
            wishlist_count.setText(count_wish + "");
        }

    }


    private void getPromoteProduct()
    {


        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(constantFooterProduct+product.getId()+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(response.length() > 0) {

                            if(universalList.size() != 0 && universalList.get(universalList.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalList.remove(universalList.size() - 1);
                            }


                            Product pro = new Product();
                            pro.setPriId(0);
                            pro.setPostType(POPULAR_BANNER);
                            pro.setTitle(resources.getString(R.string.populars));
                            universalList.add(pro);
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> products = mGson.fromJson(response.toString(), listType);
                                for (int i = 0; i < products.size(); i++) {

                                    Product product = new Product();
                                    product = products.get(i);
                                    if(!product.getPostType().equals(DISCOUNT_TITLE))
                                        product.setPostType(CATEGORY_DETAIL);
                                    universalList.add(product);


                                }
                                Product pro1 = new Product();
                                pro1.setPostType(CART_DETAIL_NO_ITEM);
                                universalList.add(pro1);
                                adapter.notifyItemInserted(universalList.size());

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }
                        else{
                            if(universalList.size() != 0 && universalList.get(universalList.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalList.remove(universalList.size() - 1);
                            }

                            if(universalList.size() != 0 && universalList.get(universalList.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                                universalList.remove(universalList.size() - 1);
                            }

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalList.add(noitem);

                            adapter.notifyItemInserted(universalList.size());
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }


    private void  checkCount(int countID)
    {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,String.format(constantCounts, "product", countID + "" , prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID)) , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {




                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error "  + error.getMessage() );

            }
        }) {

            /**
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
