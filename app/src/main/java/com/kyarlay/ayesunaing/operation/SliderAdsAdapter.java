package com.kyarlay.ayesunaing.operation;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityAdsList;
import com.kyarlay.ayesunaing.activity.ActivityWebView;
import com.kyarlay.ayesunaing.activity.AndroidLoadImageFromAdsUrl;
import com.kyarlay.ayesunaing.activity.BrandActivity;
import com.kyarlay.ayesunaing.activity.ProductActivity;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.SliderData;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kyarlay.ayesunaing.data.ConstantVariable.CATEGORY_DETAIL;

public class SliderAdsAdapter extends SliderViewAdapter<SliderAdsAdapter.SliderAdsAdapterViewHolder> {

    private static final String TAG = "SliderAdsAdapter";

    // list for storing urls of images.
    private final List<SliderData> mSliderItems;
    private AppCompatActivity activity;
    List<MainItem> sliderAds = new ArrayList<>();


    // Constructor
    public SliderAdsAdapter(AppCompatActivity activity, ArrayList<SliderData> sliderDataArrayList, List<MainItem> sliderAds) {
        this.mSliderItems = sliderDataArrayList;
        this.activity = activity;
        this.sliderAds = sliderAds;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdsAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdsAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdsAdapterViewHolder viewHolder, final int position) {

        final SliderData sliderItem = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainItem ads = sliderAds.get(position);

                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("ads_id", String.valueOf(ads.getId()));
                    //FlurryAgent.logEvent("Click Ads Banner", mix);
                } catch (Exception e) {
                }

                if(ads.getPost_type().equals("link")){

                    if(ads.getOpen_target() != null){
                        if(ads.getOpen_target() != null && ads.getOpen_target().equals("outside_app")){


                            try {
                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ads.getUrl()));
                                activity.startActivity(myIntent);
                            }catch(ActivityNotFoundException e){
                                Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                            }
                        }else{

                            Intent intent = new Intent(activity, ActivityWebView.class );
                            Bundle bundle = new Bundle();
                            bundle.putString("url",ads.getUrl());
                            bundle.putString("title", "");
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                    }

                }else if(ads.getPost_type().equals("api")){
                    try {

                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_progress);
                        dialog.setCancelable(true);

                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);
                        dialog.setCanceledOnTouchOutside(false);

                        CustomTextView title  = (CustomTextView) dialog.findViewById(R.id.title);
                        CustomTextView text  = (CustomTextView) dialog.findViewById(R.id.text);

                        title.setText(activity.getResources().getString(R.string.progress_dialog_title));
                        text.setText(activity.getResources().getString(R.string.progerss_dialog_text));

                        dialog.show();


                        JsonArrayRequest jsonArrayRequest = adsApi(ads.getUrl(), dialog);
                        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                    } catch (Exception e) {
                        Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                    }
                }else if(ads.getPost_type().equals("image")){
                    try {

                        Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", ads.getPreview_url().toString());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                    }
                }else if(ads.getPost_type().equals("brand_page")){
                    try {

                        Intent intent = new Intent(activity, BrandActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",Integer.parseInt(ads.getUrl()));
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "onSliderClick: "  + e.getMessage() );
                    }
                }else{
                    // ringProgressDialog.dismiss();
                }


            }
        });


    }

    private JsonArrayRequest adsApi(String url, final Dialog dialog)
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() > 0) {

                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            ArrayList<Product> productArrayList = new ArrayList<>();
                            Type listType = new TypeToken<List<Product>>() {}.getType();
                            List<Product> deliveries = mGson.fromJson(response.toString(), listType);

                            for(int i = 0; i < deliveries.size(); i++) {

                                Product pro = new Product();
                                pro = deliveries.get(i);
                                pro.setPostType(CATEGORY_DETAIL);
                                productArrayList.add(pro);
                            }


                            goToNextIntent(productArrayList, dialog);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });
        return jsonObjReq;
    }

    private void goToNextIntent(ArrayList<Product> list, Dialog dialog) {

        if(list.size() == 1){
            Product product = (Product) list.get(0);
            Intent intent = new Intent(activity, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("product", product);
            intent.putExtras(bundle);
            activity.startActivity(intent);
            dialog.dismiss();

        }
        else if (list.size() > 1) {
            Intent intent = new Intent(activity, ActivityAdsList.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("product", list);
            bundle.putString("fromClass", "");
            intent.putExtras(bundle);
            activity.startActivity(intent);
            dialog.dismiss();

        }
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdsAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;

        public SliderAdsAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            this.itemView = itemView;


        }
    }
}
