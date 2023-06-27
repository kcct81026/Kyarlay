package com.kyarlay.ayesunaing.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
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
//import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircleImageView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.operation.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ActivityProfileForm extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "ActivityProfileForm";

    LinearLayout back_layout,callLayout;
    CustomTextView toolTitle, txtTitle,txtContinue,to_contact;
    CircleImageView img;
    ImageView imgCamera;
    //  ProgressBar progressBar;
    ProgressDialog progressDialog;

    MyPreference prefs;
    Resources resources;
    AppCompatActivity activity;

    int PROFILE = 1;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Bitmap bitmap = null;
    int imageDimen = 0;
    long imageSizeKB = 0;
    private String imageName = null;
    static Util util;
    static TransferUtility transferUtility;
    String imageUrl = null;
    String name ="";
    Uri fileUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_picture_form);
        Log.e(TAG, "onCreate: "  );

        activity = ActivityProfileForm.this;
        prefs   = new MyPreference(ActivityProfileForm.this);
        if(prefs.getStringPreferences(LANGUAGE) == ""){
            prefs.saveStringPreferences(LANGUAGE, LANGUAGE_MYANMAR);
        }
        Context context = LocaleHelper.setLocale(ActivityProfileForm.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
       // new MyFlurry(ActivityProfileForm.this);
        util = new Util();
        transferUtility = util.getTransferUtility(this);



        name = getIntent().getStringExtra("name");


        back_layout = findViewById(R.id.back_layout);
        toolTitle = findViewById(R.id.toolTitle);
        txtTitle = findViewById(R.id.txtTitle);


        txtContinue = findViewById(R.id.txtContinue);
        callLayout = findViewById(R.id.callLayout);
        to_contact = findViewById(R.id.to_contact);
        img = findViewById(R.id.img);
        //   progressBar = findViewById(R.id.progressBar);
        imgCamera = findViewById(R.id.imgCamera);
        img.setImageDrawable(activity.getResources().getDrawable(R.mipmap.camera_image_profile));



        toolTitle.setText("Profile Photo");

        txtTitle.setText(resources.getString(R.string.fill_photo));
        txtContinue.setText(resources.getString(R.string.signup));
        to_contact.setText(resources.getString(R.string.to_contact));

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfile();
                // selectImage();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfile();
                //selectImage();
            }
        });

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap != null ){
                    //progressBar.setVisibility(View.VISIBLE);

                    progressDialog = new ProgressDialog(ActivityProfileForm.this);
                    progressDialog.setMessage("Uploading . . . ");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    try {
                        uploadImage(FileUtil.from(activity,fileUri,bitmap,imageSizeKB,true));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    ToastHelper.showToast(activity, resources.getString(R.string.error_photo));
                }


            }
        });

        callLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefs.getStringPreferences(CALL_CENTER_NO).trim().length() > 0) {

                    final ArrayList<String> phoneArray = new ArrayList<String>();
                    StringTokenizer st = new StringTokenizer(prefs.getStringPreferences(CALL_CENTER_NO), ",");
                    while (st.hasMoreTokens()) {
                        phoneArray.add(st.nextToken());
                    }
                    if (phoneArray.size() > 1) {


                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_call);
                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        LinearLayout mainlayout = (LinearLayout) dialog.findViewById(R.id.layout);

                        CustomTextView title1 = (CustomTextView) dialog.findViewById(R.id.title);
                        title1.setText(resources.getString(R.string.product_options));
                        for(int i = 0; i < phoneArray.size(); i ++) {
                            final String phoneString = phoneArray.get(i);
                            LinearLayout phoneLayout = new LinearLayout(activity);
                            phoneLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            phoneLayout.setOrientation(LinearLayout.HORIZONTAL);
                            phoneLayout.setPadding(0, 20, 0, 20);


                            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            childParam1.weight = 0.1f;
                            CustomTextView price = new CustomTextView(activity);
                            price.setTextSize(16);
                            price.setPadding(10, 10, 10, 10);
                            //price.setGravity(Gravity.LEFT);
                            price.setText(phoneString);
                            price.setLayoutParams(childParam1);


                            RadioButton radioButton = new RadioButton(activity);
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            parms.gravity = Gravity.CENTER;
                            parms.weight = 0.9f;
                            radioButton.setLayoutParams(parms);

                            radioButton.setClickable(false);

                            phoneLayout.addView(price);
                            phoneLayout.addView(radioButton);
                            phoneLayout.setWeightSum(1f);


                            TextView space = new TextView(activity);
                            space.setHeight(6);
                            space.setBackgroundResource(R.color.checked_bg);

                            mainlayout.addView(phoneLayout);
                            mainlayout.addView(space);

                            phoneLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {

                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "login");
                                        mix.put("call_id", phoneString);
                                        //FlurryAgent.logEvent("Call Call Center", mix);
                                    } catch (Exception e) {
                                    }
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneString));
                                    startActivity(callIntent);
                                    dialog.dismiss();
                                }
                            });
                        }

                        dialog.show();

                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + prefs.getStringPreferences(CALL_CENTER_NO)));
                        startActivity(callIntent);
                    }
                }
            }
        });



    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityProfileForm.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                        }
                        else
                        {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        }
                    }

                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    changeProfile();
                }

            }
        });
        builder.show();
    }


    private void changeProfile(){
        if (checkPermission()) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PROFILE);

        } else {
            requestPermission();
        }
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

                fileUri = data.getData();
                Cursor returnCursor =
                        getContentResolver().query(fileUri, null, null, null, null);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                imageSizeKB = returnCursor.getLong(sizeIndex) / 1024;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                    if (bitmap != null) {
                        bitmap = rotateImageIfRequired(bitmap, this, fileUri);
                        bitmap = cropToSquare(bitmap);
                        imageDimen = (bitmap.getHeight() * 100) / bitmap.getWidth();
                        //profile.getLayoutParams().height = (imageDimen * display.getWidth()) / 100;
                        // profile.getLayoutParams().width = display.getWidth();
                        // profile.setVisibility(View.GONE);
                        //profile_image.setVisibility(View.VISIBLE);

                        Log.e(TAG, "onActivityResult: *********** image is not null ");

                        img.setImageBitmap(bitmap);
                        imgCamera.setVisibility(View.VISIBLE);
                        txtTitle.setText(name);


                        txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));

                    } else {
                        Log.e(TAG, "onActivityResult: *********** image is  null ");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onActivityResult: " + e.getMessage());
                }


            }
        }

       /* else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

            if (bitmap != null) {
                //bitmap = rotateImageIfRequired(bitmap, this, fileUri);
                bitmap = cropToSquare(bitmap);
                imageDimen = (bitmap.getHeight() * 100) / bitmap.getWidth();
                //profile.getLayoutParams().height = (imageDimen * display.getWidth()) / 100;
                // profile.getLayoutParams().width = display.getWidth();
                // profile.setVisibility(View.GONE);
                //profile_image.setVisibility(View.VISIBLE);

                Log.e(TAG, "onActivityResult: *********** image is not null ");

                img.setImageBitmap(bitmap);
                imgCamera.setVisibility(View.VISIBLE);
                txtTitle.setText(name);


                txtContinue.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));

            } else {
                Log.e(TAG, "onActivityResult: *********** image is  null ");
            }


        }*/
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
        new AlertDialog.Builder(ActivityProfileForm.this)
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

            case MY_CAMERA_PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else
                {
                    ToastHelper.showToast(activity, "Camera Permission Denied!");
                }

                break;
        }

    }
    public String SaveImage1() {
        String root = Environment.getExternalStorageDirectory().toString() + "/kyarlay_image";
        File myDir = new File(root);
        myDir.mkdirs();

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(myDir);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);

        imageName = "kyarlay_" + System.currentTimeMillis() + ".jpg";
        File file = new File(myDir, imageName);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);

            if( imageSizeKB <= 80)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90 , out);
            else if(imageSizeKB > 80 && imageSizeKB < 300)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60 , out);
            else if(imageSizeKB > 300 && imageSizeKB < 900)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20 , out);
            else
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10 , out);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "SaveImage1: " + e.getMessage() );

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "SaveImage1: " + e.getMessage() );
            }
        }


        return file.getAbsolutePath();

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
                    myAccountLogin();
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

    private void myAccountLogin(){

        JSONObject uploadMessage = new JSONObject();
        try {

            uploadMessage.put("image",  imageUrl);
            uploadMessage.put("name",  name);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, String.format(constantUpdateUserInfo, prefs.getIntPreferences(SP_MEMBER_ID)), uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, "onResponse: name "  + name  );
                        Log.e(TAG, "onResponse: name "  + imageUrl  );

                        Log.e(TAG, "onResponse: "  + response.toString() );


                        if (progressDialog != null)
                            progressDialog.dismiss();


                        try{
                            if (response.getInt("status" ) == 1){


                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("user_phone", prefs.getStringPreferences(SP_USER_PHONE));
                                    mix.put("name", prefs.getStringPreferences(SP_USER_NAME));
                                    //FlurryAgent.logEvent("Signup", mix);
                                } catch (Exception e) {
                                }




                                Intent intent = new Intent(ActivityProfileForm.this, ActivityProfileCompleted.class);
                                startActivity(intent);
                                finish();
                            }

                        }catch (Exception e){
                            Log.e(TAG, "onResponse: " + e.getMessage() );

                        }



                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (progressDialog != null)
                    progressDialog.dismiss();
                ToastHelper.showToast(ActivityProfileForm.this, resources.getString(R.string.no_internet_error));


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




}

