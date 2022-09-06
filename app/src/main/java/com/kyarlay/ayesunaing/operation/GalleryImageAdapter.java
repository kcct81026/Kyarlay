package com.kyarlay.ayesunaing.operation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kyarlay.ayesunaing.object.Images;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by kcct on 9/15/16.
 */
public class GalleryImageAdapter extends BaseAdapter {
    private Activity context;
    private List<Images> images;
    Display display;

    public GalleryImageAdapter(Activity context, List<Images> images){
        this.context = context;
        this.images  = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Images imageObj = images.get(position);
        display = context.getWindowManager().getDefaultDisplay();

        ImageView i = new ImageView(context);
        RelativeLayout borderImg = new RelativeLayout(context);
        try{
            Bitmap bitmap = getImageBitmap(imageObj.getUrl());
            i.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }


        int size = (display.getHeight() * 1)/ 10;
        i.setLayoutParams(new Gallery.LayoutParams(size, size));
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            i.setImageTintMode(PorterDuff.Mode.SRC);
        }

        borderImg.setPadding(1, 1, 1, 1);
        borderImg.setBackgroundColor(0xff000000);
        borderImg.addView(i);
        return borderImg;

    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (Exception e) {

        }
        return bm;
    }


}
