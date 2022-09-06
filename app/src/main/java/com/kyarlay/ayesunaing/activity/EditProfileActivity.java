package com.kyarlay.ayesunaing.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.operation.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "EditProfileActivity";

    LinearLayout  back_layout, call_layout, name_layout,  linearParentStatus ;
    LinearLayout boy_layout, girl_layout, other_layout;
    CustomTextView boy, girl, other, txt_parent_status;
    RadioButton boy_radio, girl_radio, other_radio;

    CustomTextView to_contact, txt_title, txt_enter, txt_phone;
    CustomEditText ed_input;
    CustomButton btn_login;
    ProgressBar progress_bar;

    LinearLayout boy_child_layout, girl_child_layout, linearChildStatus;
    CustomTextView boy_child, girl_child,  txt_child_status;
    RadioButton boy_child_radio, girl_child_radio;

    LinearLayout linearCalendar;
    CustomTextView txtBdTitle, kid_birth_date;

    ImageView profile_image;
    NetworkImageView profile;

    Resources resources;

    Display display;
    MyPreference prefs;
    String momordad_result = "";
    String boyorgirl_result = "";
    String imageUrl = null;
    int baby_month = 0, baby_year = 0;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private int PROFILE = 1;
    private Bitmap bitmap = null;
    int imageDimen = 0;
    long imageSizeKB = 0;
    private static String imageName = null;
    static Util util;
    static TransferUtility transferUtility;
    Uri fileUri;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fill_form);

        Log.e(TAG, "onCreate: " );

        new MyFlurry(EditProfileActivity.this);

        prefs   = new MyPreference(EditProfileActivity.this);
        display = getWindowManager().getDefaultDisplay();
        if(prefs.getStringPreferences(LANGUAGE) == ""){
            prefs.saveStringPreferences(LANGUAGE, LANGUAGE_MYANMAR);
        }
        Context context = LocaleHelper.setLocale(EditProfileActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        util = new Util();
        transferUtility = util.getTransferUtility(this);

        back_layout = findViewById(R.id.back_layout);
        call_layout = findViewById(R.id.callLayout);
        name_layout = findViewById(R.id.linearName);
        to_contact = findViewById(R.id.to_contact);
        txt_title = findViewById(R.id.txt_title);
        txt_enter = findViewById(R.id.txt_enter);
        txt_phone = findViewById(R.id.txt_phone);

        ed_input = findViewById(R.id.ed_input);
        btn_login = findViewById(R.id.btn_login);
        progress_bar = findViewById(R.id.progress_bar);
        profile_image = findViewById(R.id.userProfile);
        profile = findViewById(R.id.profile_image);

        linearParentStatus = findViewById(R.id.linearParentStatus);
        boy_layout  =findViewById(R.id.boy_layout);
        girl_layout  =findViewById(R.id.girl_layout);
        other_layout  =findViewById(R.id.other_layout);
        girl  =findViewById(R.id.girl);
        boy  =findViewById(R.id.boy);
        other  =findViewById(R.id.other);
        txt_parent_status  =findViewById(R.id.txt_parent_status);
        boy_radio = findViewById(R.id.radioboy);
        girl_radio = findViewById(R.id.radiogirl);
        other_radio = findViewById(R.id.radioother);

        linearChildStatus = findViewById(R.id.linearChildStatus);
        boy_child_layout  =findViewById(R.id.boy_child_layout);
        girl_child_layout  =findViewById(R.id.girl_child_layout);
        girl_child  =findViewById(R.id.girl_child);
        boy_child  =findViewById(R.id.boy_child);
        txt_child_status  =findViewById(R.id.txt_child_status);
        boy_child_radio = findViewById(R.id.radioboy_child);
        girl_child_radio = findViewById(R.id.radiogirl_child);

        linearCalendar = findViewById(R.id.linearCalendar);
        txtBdTitle = findViewById(R.id.txt_bd_title);
        kid_birth_date = findViewById(R.id.txt_bd);


        linearCalendar.setVisibility(View.VISIBLE);
        linearChildStatus.setVisibility(View.VISIBLE);
        linearParentStatus.setVisibility(View.VISIBLE);
        txt_title.setText(resources.getString(R.string.edit_profile));
        btn_login.setText(resources.getString(R.string.edit_profile));

        back_layout.getLayoutParams().width = (display.getWidth() * 3 ) / 20;
        call_layout.getLayoutParams().width = (display.getWidth() * 17) / 20;
        call_layout.setVisibility(View.GONE);


        txt_enter.setText(resources.getString(R.string.name_hint));
        txt_parent_status.setText(resources.getString(R.string.group_chat_detail_momordad));
        boy.setText(resources.getString(R.string.group_chat_detail_dad));
        girl.setText(resources.getString(R.string.group_chat_detail_mom));
        other.setText(resources.getString(R.string.family_member));

        txt_child_status.setText(resources.getString(R.string.group_chat_detail_boyorgirl));
        boy_child.setText(resources.getString(R.string.group_chat_detail_boy));
        girl_child.setText(resources.getString(R.string.group_chat_detail_girl));


        imageUrl    = prefs.getStringPreferences(SP_USER_PROFILEIMAGE);
        momordad_result = prefs.getStringPreferences(SP_USER_MOMORDAD);
        boyorgirl_result    = prefs.getStringPreferences(SP_USER_BOYORGIRL);
        baby_month  = prefs.getIntPreferences(SP_USER_MONOTH);
        baby_year   = prefs.getIntPreferences(SP_USER_YEAR);




        if(prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals("")){
            profile.setVisibility(View.GONE);
            profile_image.setVisibility(View.VISIBLE);
        }else{
            if (!prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals("null")){
                profile.setImageUrl(prefs.getStringPreferences(SP_USER_PROFILEIMAGE), AppController.getInstance().getImageLoader());
                profile.setVisibility(View.VISIBLE);
                profile_image.setVisibility(View.GONE);
            }
            else{
                profile.setVisibility(View.GONE);
                profile_image.setVisibility(View.VISIBLE);
            }

        }


        if(prefs.getStringPreferences(SP_USER_BOYORGIRL).equals(BOY)){
            boy_child_radio.setChecked(true);
            girl_child_radio.setChecked(false);
        }
        else if(prefs.getStringPreferences(SP_USER_BOYORGIRL).equals(GIRL)){
            girl_child_radio.setChecked(true);
            boy_child_radio.setChecked(false);
        }
        else{
            girl_child_radio.setChecked(false);
            boy_child_radio.setChecked(false);
        }

        if(prefs.getStringPreferences(SP_USER_MOMORDAD).equals(MOTHER)){
            girl_radio.setChecked(true);
            boy_radio.setChecked(false);
            other_radio.setChecked(false);

            linearChildStatus.setVisibility(View.VISIBLE);
            linearCalendar.setVisibility(View.VISIBLE);
        }else if(prefs.getStringPreferences(SP_USER_MOMORDAD).equals(FATHER)){
            girl_radio.setChecked(false);
            boy_radio.setChecked(true);
            other_radio.setChecked(false);

            linearChildStatus.setVisibility(View.VISIBLE);
            linearCalendar.setVisibility(View.VISIBLE);
        }else if(prefs.getStringPreferences(SP_USER_MOMORDAD).equals(OTHER) || prefs.getStringPreferences(SP_USER_MOMORDAD).equals("unknown_parent_status")){
            girl_radio.setChecked(false);
            boy_radio.setChecked(false);
            other_radio.setChecked(true);
            linearChildStatus.setVisibility(View.GONE);
            linearCalendar.setVisibility(View.GONE);
        }
        else {
            girl_radio.setChecked(false);
            boy_radio.setChecked(false);
            other_radio.setChecked(false);
            linearChildStatus.setVisibility(View.GONE);
            linearCalendar.setVisibility(View.GONE);
        }

        txtBdTitle.setText(resources.getString(R.string.child_bd));
        if (baby_year != 0 && baby_month != 0 ){
            kid_birth_date.setText(baby_month+" / "+baby_year);
        }
        else{
            kid_birth_date.setText(resources.getString(R.string.choose_month_hint));
        }


        txt_phone.setText(prefs.getStringPreferences(SP_USER_PHONE));
        ed_input.setText(prefs.getStringPreferences(SP_USER_NAME));


        boy_layout.setOnClickListener(new ParentClickListener());
        girl_layout.setOnClickListener(new ParentClickListener());
        other_layout.setOnClickListener(new ParentClickListener());
        boy_child_layout.setOnClickListener(new ParentClickListener());
        girl_child_layout.setOnClickListener(new ParentClickListener());


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefs.getIntPreferences(SP_CHANGE) == 1){
                    prefs.saveIntPerferences(SP_CHANGE, 0);
                    Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                    finish();
            }
        });



        kid_birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(EditProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.number_picker);
                dialog.setCancelable(true);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);
                dialog.setCanceledOnTouchOutside(true);

                NumberPicker month_picker = (NumberPicker) dialog.findViewById(R.id.month_picker);
                NumberPicker year_picker  = (NumberPicker) dialog.findViewById(R.id.year_picker);
                CustomTextView title      = (CustomTextView) dialog.findViewById(R.id.title);
                CustomButton confirm        = (CustomButton) dialog.findViewById(R.id.confirm);

                title.setText(resources.getString(R.string.baby_birth_date));

                baby_month = Calendar.getInstance().get(Calendar.MONTH) + 1;

                month_picker.setMaxValue(12);
                month_picker.setMinValue(1);
                month_picker.setValue(baby_month);
                month_picker.setWrapSelectorWheel(false);
                month_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                        baby_month = i1;
                    }

                });
                baby_year  = Calendar.getInstance().get(Calendar.YEAR);
                year_picker.setMaxValue(baby_year + 1 )  ;
                year_picker.setMinValue(baby_year - 20);
                year_picker.setWrapSelectorWheel(false);
                year_picker.setValue(baby_year);
                year_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        baby_year = i1;
                    }
                });

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(calculateKidAge(baby_month, baby_year)){

                            kid_birth_date.setText(baby_month+" / "+baby_year);
                            kid_birth_date.setTextColor(resources.getColor(R.color.black));
                            dialog.dismiss();
                        }else{

                            ToastHelper.showToast(EditProfileActivity.this, resources.getString(R.string.kid_age_error));

                        }
                    }
                });

                dialog.show();


            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PROFILE);

                } else {
                    requestPermission();
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PROFILE);
                } else {
                    requestPermission();
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefs.saveIntPerferences(SP_CHANGE, 1);
                if(bitmap != null ){
                    progress_bar.setVisibility(View.VISIBLE);

                    try {
                        uploadImage(FileUtil.from(EditProfileActivity.this,fileUri,bitmap,imageSizeKB,true));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                else
                    checkUserInfomation();
            }
        });

    }

    private void uploadImage(File fileFromUtil) {
       String path = fileFromUtil.getAbsolutePath();


        final File file = new File(path);
        TransferObserver uploadObserver = transferUtility.upload(ConstantVariable.BUCKET_NAME,
                file.getName(),
                file);

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {

                if (TransferState.COMPLETED == state) {
                    GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest( BUCKET_NAME, fileFromUtil.getName() );
                    URL url1 = util.getS3Client(getApplicationContext()).generatePresignedUrl(urlRequest);
                    String [] urlSplit    = url1.toString().split(".jpg");
                    imageUrl             = urlSplit[0]+".jpg";
                    file.delete();
                    checkUserInfomation();
                } else if (TransferState.FAILED == state) {
                    file.delete();

                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {


            }

            @Override
            public void onError(int id, Exception ex) {
                ex.printStackTrace();
            }

        });
    }

    private void checkUserInfomation(){



        if (ed_input.getText().toString().isEmpty()){
            progress_bar.setVisibility(View.GONE);

            ToastHelper.showToast(EditProfileActivity.this, resources.getString(R.string.group_chat_detail_name_error));

        }
        else if (momordad_result.equals(FATHER) || momordad_result.equals(MOTHER)){

            if (baby_year == 0 || baby_month == 0){
                progress_bar.setVisibility(View.GONE);

                ToastHelper.showToast(EditProfileActivity.this, resources.getString(R.string.kid_age_error));

            }
            else{
                progress_bar.setVisibility(View.VISIBLE);
                sendUserInformation();
            }

        }
        else{
            if (momordad_result.equals(OTHER)){
                boyorgirl_result = "";
                baby_month = 0;
                baby_year = 0;
            }
            sendUserInformation();

        }
    }

    private void sendUserInformation(){

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("image",  imageUrl);
            uploadMessage.put("name",  ed_input.getText().toString());
            uploadMessage.put("momordad",  momordad_result);
            uploadMessage.put("boyorgirl",  boyorgirl_result);
            uploadMessage.put("month",  baby_month);
            uploadMessage.put("year",  baby_year);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, String.format(constantUpdateUserInfo, prefs.getIntPreferences(SP_MEMBER_ID)), uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progress_bar.setVisibility(View.GONE);



                        try {

                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "edit_user_profile");
                            FlurryAgent.logEvent("Update User Information", mix);

                        } catch (Exception e) {
                        }

                        try {
                            int status =  response.getInt("status");
                            if(status == 1) {
                                prefs.saveStringPreferences(SP_USER_MOMORDAD, momordad_result);
                                prefs.saveStringPreferences(SP_USER_BOYORGIRL, boyorgirl_result);
                                prefs.saveIntPerferences(SP_USER_MONOTH, baby_month);
                                prefs.saveIntPerferences(SP_USER_YEAR, baby_year);
                                prefs.saveStringPreferences(SP_USER_NAME, ed_input.getText().toString());
                                prefs.saveStringPreferences(SP_USER_PROFILEIMAGE, imageUrl);


                            }

                            if (prefs.getIntPreferences(SP_CHANGE) == 1){
                                prefs.saveIntPerferences(SP_CHANGE, 0);
                                Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                            else
                                finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " + e.getMessage() );
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progress_bar.setVisibility(View.GONE);

                ToastHelper.showToast(EditProfileActivity.this, resources.getString(R.string.no_internet_error));


            }
        }) {

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

        AppController.getInstance().addToRequestQueue(jsonObjReq,"sign_in");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (prefs.getIntPreferences(SP_CHANGE) == 1){
            prefs.saveIntPerferences(SP_CHANGE, 0);
            Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
            this.finish();
        }
        else
            finish();
    }



    private boolean checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }


        return true;
    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{/*Manifest.permission.CAMERA,*/ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PROFILE) {
            if (data != null) {

                fileUri  = data.getData();
                Cursor returnCursor =
                        getContentResolver().query(fileUri, null, null, null, null);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                imageSizeKB = returnCursor.getLong(sizeIndex) / 1024;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);

                    if (bitmap != null){
                        bitmap = rotateImageIfRequired(bitmap, this, fileUri);
                        bitmap = cropToSquare(bitmap);
                        imageDimen = (bitmap.getHeight() *100 ) / bitmap.getWidth();
                        profile.getLayoutParams().height = (imageDimen * display.getWidth()) / 100;
                        profile.getLayoutParams().width = display.getWidth();
                        profile.setVisibility(View.GONE);
                        profile_image.setVisibility(View.VISIBLE);
                        profile_image.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onActivityResult: " + e.getMessage() );
                }



            }
        }
    }



    public Bitmap rotateImageIfRequired(Bitmap img, Context context, Uri selectedImage) throws IOException {

        if (selectedImage.getScheme().equals("content")) {
            String[] projection = { MediaStore.Images.ImageColumns.ORIENTATION };
            Cursor c = context.getContentResolver().query(selectedImage, projection, null, null, null);
            if (c.moveToFirst()) {
                final int rotation = c.getInt(0);
                c.close();
                return rotateImage(img, rotation);
            }
            return img;
        } else {
            ExifInterface ei = new ExifInterface(selectedImage.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);


            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
                default:
                    return img;
            }
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }

    public Bitmap cropToSquare(Bitmap bitmap){
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width)? height - ( height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0)? 0: cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0)? 0: cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(EditProfileActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }

                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }


                    }

                }
                break;
        }

    }


    private boolean calculateKidAge(int month, int year){


        boolean boo = true;
        int kid_year    = 0;
        int cur_month   = Calendar.getInstance().get(Calendar.MONTH);
        int cur_year    = Calendar.getInstance().get(Calendar.YEAR) +1 ;

        int increment = 0;
        if(month > cur_month){
            increment = 1;

        }

        kid_year = cur_year - year - increment;

        if(kid_year < 0)
            boo = false;

        return boo;

    }


    public  class ParentClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.boy_layout :
                    boy_radio.setChecked(true);
                    girl_radio.setChecked(false);
                    other_radio.setChecked(false);
                    linearChildStatus.setVisibility(View.VISIBLE);
                    linearCalendar.setVisibility(View.VISIBLE);
                    momordad_result = FATHER;
                    break;
                case R.id.girl_layout :
                    boy_radio.setChecked(false);
                    girl_radio.setChecked(true);
                    other_radio.setChecked(false);
                    linearChildStatus.setVisibility(View.VISIBLE);
                    linearCalendar.setVisibility(View.VISIBLE);
                    momordad_result = MOTHER;
                    break;

                case R.id.other_layout :
                    boy_radio.setChecked(false);
                    girl_radio.setChecked(false);
                    other_radio.setChecked(true);
                    linearChildStatus.setVisibility(View.GONE);
                    linearCalendar.setVisibility(View.GONE);
                    momordad_result = OTHER;
                    break;

                case R.id.boy_child_layout :
                    boy_child_radio.setChecked(true);
                    girl_child_radio.setChecked(false);
                    boyorgirl_result = BOY;
                    break;

                case R.id.girl_child_layout :
                    boy_child_radio.setChecked(false);
                    girl_child_radio.setChecked(true);
                    boyorgirl_result = GIRL;
                    break;

            }
        }
    }
}