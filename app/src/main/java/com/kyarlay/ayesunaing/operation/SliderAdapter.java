package com.kyarlay.ayesunaing.operation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.AndroidLoadImageFromAdsUrl;
import com.kyarlay.ayesunaing.object.SliderData;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    private static final String TAG = "SliderAdapter";

    // list for storing urls of images.
    private final List<SliderData> mSliderItems;
    private AppCompatActivity activity;
    //private List<Images> imagesDetail = new ArrayList<>();


    // Constructor
    public SliderAdapter(AppCompatActivity activity, ArrayList<SliderData> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.activity = activity;
        //this.imagesDetail = imagesDetail;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

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
                Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                Bundle bundle = new Bundle();
                //bundle.putParcelableArrayList("list_image", (ArrayList<? extends Parcelable>) imagesDetail);
                bundle.putString("url", sliderItem.getImgUrl());
                bundle.putInt("index", position);
                intent.putExtras(bundle);
                activity.startActivity(intent);

            }
        });


    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            this.itemView = itemView;


        }
    }
}
