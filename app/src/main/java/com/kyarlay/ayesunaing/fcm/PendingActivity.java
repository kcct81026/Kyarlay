package com.kyarlay.ayesunaing.fcm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.BrandActivity;
import com.kyarlay.ayesunaing.activity.CampainDetailActivity;
import com.kyarlay.ayesunaing.activity.CompetitionActivity;
import com.kyarlay.ayesunaing.activity.FlashSalesActivity;
import com.kyarlay.ayesunaing.activity.ProductActivity;
import com.kyarlay.ayesunaing.activity.ReadingCommentDetailsActivity;
import com.kyarlay.ayesunaing.activity.SearchPhotoActivity;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.ConstantVariable;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*
 * Created by ayesunaing on 10/25/16.

*/


public class PendingActivity extends AppCompatActivity implements ConstantVariable {
    private static final String TAG = "PendingActivity";


    String maintype ="";
    DatabaseAdapter databaseAdapter;

    MyPreference prefs;

    String[] finalTrim;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.pendinglayout);
        databaseAdapter = new DatabaseAdapter(PendingActivity.this);
        prefs  = new MyPreference(PendingActivity.this);
       // new MyFlurry(PendingActivity.this);


        try{
            Intent intent = getIntent();

            Uri targetUrl = intent.getData();
            String data = targetUrl.toString();


            Log.e(TAG, "onCreate: "  + data );

            String[] outer_trim = data.split("//");
            String newString = outer_trim[outer_trim.length - 1];
            String[] mytrim = newString.split("\\?");
            finalTrim = mytrim[0].split("/");
            maintype = finalTrim[0];
        }catch (Exception e){
            Log.e(TAG, "onCreate: "  +  e.getMessage());
            finish();
        }





        if(maintype.equals("c")){
            String url = "https://www.kyarlay.com/api/products?category_id="+finalTrim[1]+"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE);


            try {

                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "category");
                //FlurryAgent.logEvent("DeepLinking", mix);
            } catch (Exception e) {
            }

            JsonArrayRequest jsonArrayRequest = productList(url);
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);


        }else if(maintype.equals("p")){

            String url = "https://www.kyarlay.com/api/products/"+finalTrim[1]+"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE);


            try {
                 Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "product list");
                //FlurryAgent.logEvent("DeepLinking", mix);
            } catch (Exception e) {
            }
            JsonArrayRequest jsonArrayRequest = product(url);
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        }
        else if (maintype.equals("a")){
            try {
                 Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "article");
                //FlurryAgent.logEvent("DeepLinking", mix);
            } catch (Exception e) {
            }
            Intent intent1 = new Intent(PendingActivity.this, ReadingCommentDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("fromdeep", 1);
            bundle.putInt("id", Integer.parseInt(finalTrim[1]));
            intent1.putExtras(bundle);
            startActivity(intent1);
            finish();
        }
        else if (maintype.equals("d")){
            try {
                 Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "discount");
                //FlurryAgent.logEvent("DeepLinking", mix);
            } catch (Exception e) {
            }
            Intent intent5 = new Intent(PendingActivity.this, CampainDetailActivity.class);
            intent5.putExtra("fromdeep", 1);
            intent5.putExtra("id",Integer.parseInt(finalTrim[1]));
            startActivity(intent5);
            finish();
        }
        else if (maintype.equals("b")){
            try {
                 Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "brand");
                //FlurryAgent.logEvent("DeepLinking", mix);
            } catch (Exception e) {
            }
            Intent intentBrand = new Intent(PendingActivity.this, BrandActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", Integer.parseInt(finalTrim[1]));
            bundle.putInt("fromdeep", 1);
            intentBrand.putExtras(bundle);
            startActivity(intentBrand);
            finish();
        }
        else if (maintype.equals("flash_sale")){
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "flash_sale");
                //FlurryAgent.logEvent("DeepLinking", mix);
            } catch (Exception e) {
            }
            Intent intentBrand = new Intent(PendingActivity.this, FlashSalesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("fromClass", FROM_FIREBASE);
            intentBrand.putExtras(bundle);
            startActivity(intentBrand);
            finish();
        }
        else if (maintype.equals("cphoto")){
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "article");
                //FlurryAgent.logEvent("DeepLinking", mix);
            } catch (Exception e) {
            }
            Intent intent1 = new Intent(PendingActivity.this, SearchPhotoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("fromdeep", 1);
            bundle.putInt("id", Integer.parseInt(finalTrim[1]));
            intent1.putExtras(bundle);
            startActivity(intent1);
            finish();
        }

        else if (maintype.equals("competition")){
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "article");
                //FlurryAgent.logEvent("DeepLinking", mix);
            } catch (Exception e) {
            }

            if (prefs.getBooleanPreference(HAS_COMPETITION) && Integer.parseInt(finalTrim[1]) == 1){
                Intent intent1 = new Intent(PendingActivity.this, CompetitionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fromdeep", 1);
                intent1.putExtras(bundle);
                startActivity(intent1);
                finish();
            }
            else{
                finish();
            }


        }

        else{
            finish();
        }

    }

    private JsonArrayRequest product(String url)
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
            new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    if(response.length() > 0) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        ArrayList<Product> arrayList = new ArrayList<>();
                        Type listType = new TypeToken<List<Product>>() {}.getType();
                        List<Product> deliveries = mGson.fromJson(response.toString(), listType);

                        for(int i = 0; i < deliveries.size(); i++) {
                            Product pro = new Product();
                            pro = deliveries.get(i);
                            pro.setPostType(OTHER);
                            arrayList.add(pro);
                        }
                        try {
                             Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", "product" );
                            mix.put("product_id", String.valueOf(arrayList.get(0).getId()));
                            //FlurryAgent.logEvent("Incoming from Deep Linking", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(PendingActivity.this, ProductActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("product", arrayList.get(0));
                        bundle.putString("status", "pending");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    }
                }

            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            try {
                 Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "product" );
                mix.put("product_id", "-1" );

                //FlurryAgent.logEvent("Incoming from Deep Linking", mix);
            } catch (Exception e) {
            }


            }
        });
        return jsonObjReq;
    }

    private JsonArrayRequest productList(String url)
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                             Map<String, String> mix = new HashMap<String, String>();

                            mix.put("type", "product_list" );
                            mix.put("status", "success" );

                            //FlurryAgent.logEvent("Incoming from Deep Linking", mix);
                        } catch (Exception e) {
                        }
                        if(response.length() > 0) {
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            ArrayList<Product> arrayList = new ArrayList<>();
                            Type listType = new TypeToken<List<Product>>() {}.getType();
                            List<Product> deliveries = mGson.fromJson(response.toString(), listType);

                            for(int i = 0; i < deliveries.size(); i++) {
                                Product pro = new Product();
                                pro = deliveries.get(i);
                                pro.setPostType(CATEGORY_DETAIL);
                                arrayList.add(pro);

                            }
                            Intent intent = new Intent(PendingActivity.this, DeeplinkingListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("fromclass", "pending");
                            bundle.putParcelableArrayList("product", arrayList);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                     Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "product_list" );
                    mix.put("status", "error" );

                    //FlurryAgent.logEvent("Incoming from Deep Linking", mix);
                } catch (Exception e) {
                }

            }
        });
        return jsonObjReq;
    }

}
