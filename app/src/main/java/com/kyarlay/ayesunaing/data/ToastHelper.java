package com.kyarlay.ayesunaing.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class ToastHelper implements ConstantVariable {

    public static void showToast(AppCompatActivity activity, String message){
       /* LayoutInflater inflater = activity.getLayoutInflater();
        View toastRoot = inflater.inflate(R.layout.custom_toast_layout, null);
        CustomTextView textView = (CustomTextView) toastRoot.findViewById(R.id.toast_text);
        textView.setText(message);
        Toast toast = new Toast(activity);
        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();*/


        MyPreference prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        Resources resources = context.getResources();
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);


        View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_warning, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.setCancelable(true);


        CustomTextView title = mBottomSheetDialog.findViewById(R.id.title);
        CustomTextView txtWarning = mBottomSheetDialog.findViewById(R.id.txtWarning);
        ImageView imgClose = mBottomSheetDialog.findViewById(R.id.imgClose);



        title.setText(resources.getString(R.string.warning));
        txtWarning.setText(message);
       // title.setText();
        txtWarning.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
        txtWarning.setTypeface(txtWarning.getTypeface(), Typeface.BOLD);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);

        LinearLayout linear = mBottomSheetDialog.findViewById(R.id.linear);
        //linear.setBackgroundColor(activity.getResources().getColor(R.color.white));



        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }


    public static void showTextDialog(AppCompatActivity activity, String message){
        LayoutInflater inflater = activity.getLayoutInflater();
        View toastRoot = inflater.inflate(R.layout.custom_toast_layout, null);
        CustomTextView textView = (CustomTextView) toastRoot.findViewById(R.id.toast_text);
        textView.setText(message);
        Toast toast = new Toast(activity);
        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }


    public static void showToastBlackText(AppCompatActivity activity, String message, String titleText){
        MyPreference prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        Resources resources = context.getResources();
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);


        View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_warning, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.setCancelable(true);


        CustomTextView title = mBottomSheetDialog.findViewById(R.id.title);
        CustomTextView txtWarning = mBottomSheetDialog.findViewById(R.id.txtWarning);
        ImageView imgClose = mBottomSheetDialog.findViewById(R.id.imgClose);



        title.setText(titleText);
        txtWarning.setText(message);
        // title.setText();
        txtWarning.setTextColor(activity.getResources().getColor(R.color.text));
        txtWarning.setTypeface(txtWarning.getTypeface(), Typeface.BOLD);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);

        LinearLayout linear = mBottomSheetDialog.findViewById(R.id.linear);
        //linear.setBackgroundColor(activity.getResources().getColor(R.color.white));



        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }


}
