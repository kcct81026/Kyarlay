package com.kyarlay.ayesunaing.operation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.object.Supplier;

import java.util.ArrayList;

/**
 * Created by ayesu on 9/11/17.
 */

public class MinMaxAdapter extends ArrayAdapter<Supplier> {

    AppCompatActivity activity;
    ArrayList<Supplier> objects;
    int productPrice;
    public MinMaxAdapter(Context context, AppCompatActivity activity, int textViewResourceId, ArrayList<Supplier> objects) {
        super(context, textViewResourceId, objects);
        this.activity       = activity;
        this.objects        = objects;
        this.productPrice   = productPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.filter_spinner_layout, null);
        CustomTextView label = (CustomTextView) convertView.findViewById(R.id.delivey_fee_spinner_layout_text);

        label.setText(objects.get(position).getName());
        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.filter_spinner_layout, null);
        CustomTextView label = (CustomTextView) convertView.findViewById(R.id.delivey_fee_spinner_layout_text);
        label.setText(objects.get(position).getName());
        return convertView;
    }
}

