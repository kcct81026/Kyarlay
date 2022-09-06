package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.ToolObject;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ToolsClickActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "ToolsClickActivity";
    
    // widgets
    LinearLayout back_layout;
    Boolean loading=true;
    RecyclerView.LayoutManager manager;

    MyPreference prefs;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    MediaAdapter mediaAdapter;
    DatabaseAdapter databaseAdapter;
    RecyclerView recyclerView ;

    Display display ;
    Resources resources;
    AppCompatActivity activity;
    String tag = "";
    ProgressBar progressBar;

    int index = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_click);

        Log.e(TAG, "onCreate:  "  );

        tag = getIntent().getStringExtra("tag");

        activity =  ToolsClickActivity.this;
        display = activity.getWindowManager().getDefaultDisplay();

        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        databaseAdapter = new DatabaseAdapter(activity);
        mediaAdapter = new MediaAdapter((AppCompatActivity) activity, mainCatDetails);

        back_layout = findViewById(R.id.back_layout);
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar        = (ProgressBar) findViewById(R.id.progress_bar);


        prefs.saveIntPerferences(ConstantVariable.SP_PAGE_CAT_NEWS, SP_DEFAULT);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mediaAdapter);


        if (prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);

            mainPageItem();

        }else{
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_NO_ITEM);
            mainCatDetails.add(noitem);

        }

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mainPageItem()
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantToolCategories + prefs.getIntPreferences(SP_CURRENT_VERSION),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);

                        if(response.length() > 0) {
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }



                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<ToolObject>>() {
                                }.getType();
                                List<ToolObject> lists = mGson.fromJson(response.toString(), listType);

                                if (lists.size() > 0){
                                    for (int i=0; i < lists.size(); i++) {

                                        if (lists.get(i).getToolChildObjects().size() > 0){

                                            ToolObject toolObject = lists.get(i);
                                            toolObject.setPostType(TOOL_SUB);
                                            mainCatDetails.add(toolObject);

                                            if (lists.get(i).getTag().equals(tag)){

                                                index = mainCatDetails.size() - 1;
                                            }
                                        }

                                    }

                                }

                                Reading pro2 = new Reading();
                                pro2.setPostType(CART_DETAIL_NO_ITEM);
                                mainCatDetails.add(pro2);

                                mediaAdapter.notifyItemInserted(mainCatDetails.size());

                                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView
                                        .getLayoutManager();

                                layoutManager.scrollToPosition(index);

                            }catch (Exception e){
                                Log.e(TAG, "onResponse:   mainPageItem  " + e.getMessage() );
                            }


                        }



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

}
