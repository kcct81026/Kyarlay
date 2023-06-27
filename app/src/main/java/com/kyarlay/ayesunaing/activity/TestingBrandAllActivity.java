package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
//import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.Category;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.MainCategoryObj;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.ClickeListener;
import com.kyarlay.ayesunaing.operation.MainGridItemClickListener;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.myatminsoe.mdetect.MDetect;

public class TestingBrandAllActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "TestingBrand";

    private LinearLayout back_layout,title_layout, search_layout;
    private RelativeLayout like_layot, cart_layout;
    private CustomTextView title,txtSearch;
    private CircularTextView  wishlist, cart_text;
    private RecyclerView recyclerView, recyclerHeader;
    private RecyclerViewHeader header;


    private Display display;
    private Resources resources;
    private AppCompatActivity activity;
    private MyPreference prefs;
    private boolean loading = false;
    private ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    private UniversalAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private String filterString = "";
    private List<Category> categoryList1 = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private int selectedPosition = 0;
    private MyListAdapter myListAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_brand_all);

        activity = this;
        display = getWindowManager().getDefaultDisplay();
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        //new MyFlurry(activity);

        prefs.saveIntPerferences(SP_PAGE_CAT_NEWS,  SP_DEFAULT);

        back_layout = findViewById(R.id.back_layout);
        title_layout = findViewById(R.id.title_layout);
        like_layot = findViewById(R.id.like_layot);
        cart_layout = findViewById(R.id.cart_layout);
        title = findViewById(R.id.title);
        wishlist = findViewById(R.id.wishlist_count);
        cart_text = findViewById(R.id.menu_cart_idenfier);
        recyclerView = findViewById(R.id.recycler_view);
        header = findViewById(R.id.header);
        txtSearch = findViewById(R.id.txtSearch);
        search_layout = findViewById(R.id.search_layout);
        recyclerHeader = findViewById(R.id.recyclerHeader);

        like_layot.getLayoutParams().width     = ( display.getWidth() * 2) / 20;
        back_layout.getLayoutParams().width     = ( display.getWidth() * 2) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 14) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 2) / 20;


        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        adapter = new UniversalAdapter(activity, universalPosts);
        recyclerView.setAdapter(adapter);

        myListAdapter = new MyListAdapter( categoryList1);
        recyclerHeader.setLayoutManager(new GridLayoutManager(activity, 1, RecyclerView.HORIZONTAL, false));
        recyclerHeader.setAdapter(myListAdapter);

        mainCategoriesFromServer();




        if (filterString.equals("")){
            title.setText("All Categories");

            JsonArrayRequest request = mainCategory("");
            AppController.getInstance().addToRequestQueue(request);

        }

        title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();

            }
        });

        recyclerView.addOnItemTouchListener(new MainGridItemClickListener(TestingBrandAllActivity.this.getApplicationContext(), recyclerView, universalPosts, new ClickeListener() {
            @Override
            public void onClick(View view, int position) {
                Brand brand = (Brand) universalPosts.get(position);
                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source","brand");
                    mix.put("source_id", brand.getTag());
                    //FlurryAgent.logEvent("View Brand Detail", mix);
                } catch (Exception e) {
                }
                if(! brand.getPostType().equals(CART_DETAIL_NO_ITEM)) {

                    Brand bbitem = (Brand) universalPosts.get(position);
                    Intent intent = new Intent(TestingBrandAllActivity.this, BrandActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("fromBrandAll", true);
                    bundle.putInt("id", bbitem.getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

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
                int totalItemCount = linearLayoutManager.getItemCount();
                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {
                    loading = true;

                    JsonArrayRequest request = mainCategory("");
                    AppController.getInstance().addToRequestQueue(request);
                }

            }
        });



    }

    private void showFilterDialog() {

        final int[] chooseFilter = {-1};
        final String[] tagArr = {""};

        Dialog mBottomSheetDialog = new Dialog(activity);

        mBottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBottomSheetDialog.setContentView(R.layout.filter_dynamic_dialog);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);

        Window window = mBottomSheetDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
        wlp.height   = activity.getWindowManager().getDefaultDisplay().getHeight();
        window.setAttributes(wlp);

        ImageView imgClose = mBottomSheetDialog.findViewById(R.id.imgClose);
        CustomTextView title = mBottomSheetDialog.findViewById(R.id.title);
        CustomTextView txtCancel = mBottomSheetDialog.findViewById(R.id.txtCancel);
        CustomTextView txtApply = mBottomSheetDialog.findViewById(R.id.txtApply);
        LinearLayout linearAdd = mBottomSheetDialog.findViewById(R.id.linearAdd);


        for (int i=0; i < categoryList.size();i++){
            View addView1 = LayoutInflater.from(activity).inflate(R.layout.dialog_filter_item,null);
            LinearLayout layout = addView1.findViewById(R.id.layout);
            LinearLayout linearChild = addView1.findViewById(R.id.linearChild);
            CustomTextView txt = addView1.findViewById(R.id.txt);
            ImageView img = addView1.findViewById(R.id.img);
            txt.setText(categoryList.get(i).getName());

            linearAdd.addView(addView1);


            int finalI = i;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    chooseFilter[0] = categoryList.get(finalI).getId();
                    tagArr[0] = categoryList.get(finalI).getTag();

                    Log.e(TAG, "onClick: **********  "  + chooseFilter[0] );
                    mBottomSheetDialog.cancel();


                    universalPosts.clear();
                    categoryList1.clear();



                    Product pro2 = new Product();
                    pro2.setPostType(CART_DETAIL_FOOTER);
                    universalPosts.add(pro2);
                    adapter.notifyDataSetChanged();



                    mainSubCategories(chooseFilter[0], tagArr[0]);


                }
            });
        }



        title.setTypeface(title.getTypeface(), Typeface.BOLD);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.cancel();
            }
        });


        mBottomSheetDialog.show();

    }

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        List<Category> gridList = new ArrayList<>();

        public MyListAdapter(List<Category> gridList) {
            this.gridList = gridList;
        }
        @Override
        public MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.category_tab_sticky, parent, false);
            MyListAdapter.ViewHolder viewHolder = new MyListAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyListAdapter.ViewHolder holder, int position) {
            Category obj =( Category) gridList.get(position);
            holder.textView.setText(gridList.get(position).getName());
            Typeface myTypeface;
            if (MDetect.INSTANCE.isUnicode()){
                myTypeface = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
            }
            else{
                myTypeface = AppController.typefaceManager.getCompanion().getZAWGYITWO();
            }

            holder.textView.setTypeface(myTypeface);
            holder.textView.setText(obj.getName());


            if (selectedPosition == position)
                holder.textView.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
            else
                holder.textView.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_grey));


            //holder.textView.setTypeface(holder.textView.getTypeface(), Typeface.BOLD);

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    selectedPosition = position;

                    if (selectedPosition == position)
                        holder.textView.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
                    else
                        holder.textView.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_grey));

                    myListAdapter.notifyDataSetChanged();
                    // recyclerHeader.scrollToPosition(position);

                    prefs.saveIntPerferences(SP_PAGE_CAT_NEWS, SP_DEFAULT);


                    Log.e(TAG, "onClick: **************** "  + obj.getTag() );

                    universalPosts.clear();



                    Product pro2 = new Product();
                    pro2.setPostType(CART_DETAIL_FOOTER);
                    universalPosts.add(pro2);
                    adapter.notifyDataSetChanged();


                    JsonArrayRequest request = mainCategory(obj.getTag());
                    AppController.getInstance().addToRequestQueue(request);


                }
            });

        }


        @Override
        public int getItemCount() {
            return gridList.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.textView = (TextView) itemView.findViewById(R.id.txt_tab_sticky);

            }
        }
    }



    private void mainCategoriesFromServer()
    {



        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantMainSuperCategory + "?language=" + prefs.getStringPreferences(SP_LANGUAGE)  ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {



                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }



                        if(response.length() > 0) {



                            ArrayList<MainCategoryObj> catList = new ArrayList<>();

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<MainCategoryObj>>() {}.getType();
                                catList = mGson.fromJson(response.toString(), listType);

                                if (catList.size() > 0){

                                    for (int i=0; i < catList.size(); i++){
                                        MainCategoryObj mainCategoryObj = catList.get(i);
                                        Category category = new Category();
                                        category.setId(mainCategoryObj.getId());
                                        category.setName(mainCategoryObj.getTitle());
                                        category.setTag(mainCategoryObj.getTag());
                                        categoryList.add(category);

                                    }



                                  /*  selectedPosition = 0 ;



                                    myListAdapter.notifyDataSetChanged();
                                    //header.attachTo(recyclerView);

                                    header.setVisibility(View.VISIBLE);
                                    //cardViewHidden.setVisibility(View.VISIBLE);

                                    recyclerHeader.setVisibility(View.VISIBLE);*/


                                }




                            }catch (Exception e){

                                Log.e(TAG, "onResponse: mainCategoriesFromServer Exception : "  + e.getMessage() );
                            }


                        }




                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onResponse: mainCategoriesFromServer Exception : "  + error.getMessage() );

                Product pro = new Product();
                pro.setPriId(1);
                pro.setPostType(POPULAR_BANNER);
                pro.setTitle(resources.getString(R.string.populars));
                universalPosts.add(pro);

                Product pro1 = new Product();
                pro1.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro1);
                adapter.notifyItemInserted(universalPosts.size());

            }
        }){

            /**
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


    private JsonArrayRequest mainCategory(String tagStr)
    {

        Log.e(TAG, "mainCategory: ---------------------  " + constantBrand+ tagStr +"&"+LANG+"="+prefs.getStringPreferences(LANGUAGE)+ "&page=" + prefs.getIntPreferences(SP_PAGE_CAT_NEWS) );

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantBrand+ tagStr +"&"+LANG+"="+prefs.getStringPreferences(LANGUAGE)+ "&page=" + prefs.getIntPreferences(SP_PAGE_CAT_NEWS),

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(response.length() > 0) {

                            if (tagStr == "")
                                loading = false;
                            else
                                loading = true;


                            prefs.saveIntPerferences(SP_PAGE_CAT_NEWS, prefs.getIntPreferences(SP_PAGE_CAT_NEWS) + SP_DEFAULT);
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Brand>>() {
                                }.getType();
                                List<Brand> categoryList = mGson.fromJson(response.toString(), listType);
                                for (int i = 0; i < categoryList.size(); i++) {
                                    Brand bb = new Brand();
                                    bb = categoryList.get(i);
                                    bb.setPostType(BRAND);
                                    bb.setTag(prefs.getStringPreferences(SP_BRAND_TAG));
                                    universalPosts.add(bb);
                                }
                                adapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }


                        }else{
                            loading = true;
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);

                            adapter.notifyDataSetChanged();

                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = false;
            }
        });
        return jsonObjReq;
    }

    private void mainSubCategories(int superID, String tagStr)
    {
        Log.e(TAG, "mainSubCategories: " + constantSuperCategories + "/" + superID +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) );
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantSuperCategories + "/" + superID +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e(TAG, " mainSubCategories onResponse: ***********---------      "  + response.toString() );





                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }


                        if(response.length() > 0){


                            ArrayList<CategoryMain> categoryMainArrayList = new ArrayList<>();
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<CategoryMain>>() {}.getType();
                                categoryMainArrayList = mGson.fromJson(response.toString(), listType);



                                if (categoryMainArrayList.size() > 0){


                                    for (int i=0; i < categoryMainArrayList.size(); i++){

                                        Category category = new Category();
                                        category.setId(categoryMainArrayList.get(i).getId());
                                        category.setName(categoryMainArrayList.get(i).getTitle());
                                        category.setTag(categoryMainArrayList.get(i).getTag());
                                        categoryList1.add(category);



                                    }

                                    selectedPosition = 0;
                                    myListAdapter.notifyDataSetChanged();

                                    header.setVisibility(View.VISIBLE);

                                    recyclerHeader.setVisibility(View.VISIBLE);


                                    prefs.saveIntPerferences(SP_PAGE_CAT_NEWS, SP_DEFAULT);



                                    JsonArrayRequest request = mainCategory(categoryMainArrayList.get(0).getTag());
                                    AppController.getInstance().addToRequestQueue(request);
                                }



                                loading = false;


                            }catch (Exception e){

                                Log.e(TAG, "onResponse: "   + e.getMessage() );
                            }


                        }
                        else{
                            Product pro1 = new Product();
                            pro1.setPostType(CART_DETAIL_FOOTER);
                            universalPosts.add(pro1);
                            adapter.notifyItemInserted(universalPosts.size());
                        }





                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                // mainPageItem();

            }
        }){

            /**
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



}
