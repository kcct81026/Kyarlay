package com.kyarlay.ayesunaing.operation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class AddressAdapter extends ArrayAdapter<String> {

    AppCompatActivity activity;
    String[] objects;
    int productPrice;
    public AddressAdapter(Context context, AppCompatActivity activity, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        this.activity       = activity;
        this.objects        = objects;
        this.productPrice   = productPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.full_screen_spinner, null);
        CustomTextView label = (CustomTextView) convertView.findViewById(R.id.delivey_fee_spinner_layout_text);

        label.setText(objects[position]);

        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.full_screen_spinner, null);
        CustomTextView label = (CustomTextView) convertView.findViewById(R.id.delivey_fee_spinner_layout_text);
        label.setText(objects[position]);
        //label.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        return convertView;
    }
}
