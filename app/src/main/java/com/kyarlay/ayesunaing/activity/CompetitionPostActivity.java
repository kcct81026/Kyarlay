/*
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
import android.os.Environment;
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
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AgeConfig;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CompetitionPostActivity extends AppCompatActivity
        implements ConstantVariable, Constant{

    private static final String TAG = "CompetitionPostActivity";

    Display display ;
    Resources resources;
    MyPreference prefs;

    CustomTextView title, question_title, question_name, edit_question_name, gallery_text;
    CustomEditText question;
    CustomButton sent_question;
    LinearLayout button_layout;
    ImageView imageView, gallery;

    LinearLayout title_layout, back_layout;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Bitmap bitmap = null;
    int imageDimen = 0;
    long imageSizeKB = 0;
    String imageUrl = null;
    private int GALLERY = 1;
    private static String imageName = null;
    Dialog dialog;

    static TransferUtility transferUtility;
    static Util util;

    private AgeConfig ageConfig;


    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        dialog = new Dialog(CompetitionPostActivity.this);
        Log.e(TAG, "onCreate: " );

        util = new Util();
        transferUtility = util.getTransferUtility(this);



        display         = getWindowManager().getDefaultDisplay();
        prefs           = new MyPreference(CompetitionPostActivity.this);
        Context context = LocaleHelper.setLocale(CompetitionPostActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();
        ageConfig  = new AgeConfig(CompetitionPostActivity.this);


       // new MyFlurry(CompetitionPostActivity.this);

        setContentView(R.layout.layout_competition_user_post);
        title               = (CustomTextView) findViewById(R.id.title);
        question_title      = (CustomTextView) findViewById(R.id.question_title);
        question_name       = (CustomTextView) findViewById(R.id.question_name);
        edit_question_name  = (CustomTextView) findViewById(R.id.edit_question_name);
        question            = (CustomEditText) findViewById(R.id.question);
        sent_question       = (CustomButton) findViewById(R.id.send_question);
        button_layout       = (LinearLayout) findViewById(R.id.button_layout);
        imageView           = (ImageView) findViewById(R.id.image);
        gallery             = (ImageView) findViewById(R.id.gallery);
        gallery_text        = (CustomTextView) findViewById(R.id.upload_image);


        question.setSelection(0);
        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 17) / 20;

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.saveIntPerferences(SP_CHANGE, 0);
                finish();
            }
        });



        String age ;


        if (prefs.getStringPreferences(SP_USER_MOMORDAD).equals("unknown_parent_status") || prefs.getStringPreferences(SP_USER_MOMORDAD).equals("other")) {
            age = resources.getString(R.string.family_member);
        } else {
            age = ageConfig.calculateKidAge(prefs.getIntPreferences(SP_USER_MONOTH), prefs.getIntPreferences(SP_USER_YEAR),
                    prefs.getStringPreferences(SP_USER_BOYORGIRL), prefs.getStringPreferences(SP_USER_MOMORDAD));
        }


        question_name.setText(prefs.getStringPreferences(SP_USER_NAME)+" - "+age);


        title.setText(prefs.getStringPreferences(COMPETITION_TITLE));
        question_title.setText(prefs.getStringPreferences(COMPETITION_DESC));
        question.setHint(resources.getString(R.string.write_competition));

        gallery_text.setText(resources.getString(R.string.gallery_text));

        question.getLayoutParams().height           = (display.getHeight() * 3 ) / 10;
        button_layout.getLayoutParams().height      = ( display.getHeight() * 2 ) / 10;


        edit_question_name.setText(resources.getString(R.string.group_chat_name_edit));
        edit_question_name.setVisibility(View.GONE);
        sent_question.setText(resources.getString(R.string.send_post));
        sent_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "onClick: "  + imageDimen );

                if(bitmap != null && imageSizeKB != 0 && imageDimen <= 140  ){
                    showDialog();
                    uploadImage();




                }
                else if (imageDimen > 140){

                    ToastHelper.showToast(CompetitionPostActivity.this, resources.getString(R.string.image_long));

                }

                else{

                    ToastHelper.showToast(CompetitionPostActivity.this, resources.getString(R.string.post_without_photo));

                }
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, GALLERY);

                } else {

                    requestPermission();
                }

            }
        });
        gallery_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, GALLERY);

                } else {

                    requestPermission();
                }
            }
        });



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
                new String[]{*/
/*Manifest.permission.CAMERA,*//*
 Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);

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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CompetitionPostActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    public void showDialog(){

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = CompetitionPostActivity.this.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);
        dialog.setCanceledOnTouchOutside(false);

        CustomTextView title  = (CustomTextView) dialog.findViewById(R.id.title);
        CustomTextView text  = (CustomTextView) dialog.findViewById(R.id.text);

        title.setText(resources.getString(R.string.progress_dialog_title));
        text.setText(resources.getString(R.string.upload_post));

        dialog.show();

    }
    private void uploadImage() {
        String path = SaveImage1();

        final File file = new File(path);
        TransferObserver uploadObserver = transferUtility.upload(ConstantVariable.BUCKET_NAME,
                file.getName(),
                file);

            uploadObserver.setTransferListener(new TransferListener() {

                @Override
                public void onStateChanged(int id, TransferState state) {

                    if (TransferState.COMPLETED == state) {
                        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest( BUCKET_NAME, imageName );
                        //GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest( BUCKET_NAME, imageName );
                        URL url1 = util.getS3Client(getApplicationContext()).generatePresignedUrl(urlRequest);
                        String [] urlSplit    = url1.toString().split(".jpg");
                        imageUrl             = urlSplit[0]+".jpg";
                        file.delete();
                        sendQuestion();
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                Uri fileUri = data.getData();
                Cursor returnCursor =
                        getContentResolver().query(fileUri, null, null, null, null);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                imageSizeKB = returnCursor.getLong(sizeIndex) / 1024;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                    bitmap = rotateImageIfRequired(bitmap, this, fileUri);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onActivityResult: "  + e.getMessage() );
                }
                imageDimen = (bitmap.getHeight() *100 ) / bitmap.getWidth();
                imageView.getLayoutParams().height = (imageDimen * display.getWidth()) / 100;
                imageView.getLayoutParams().width = display.getWidth();
                imageView.setImageBitmap(bitmap);


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
            Log.e(TAG, "SaveImage1:  "   + e.getMessage() );

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "SaveImage1:  "   + e.getMessage() );
            }
        }


        return file.getAbsolutePath();

    }


    @Override
    public void onResume() {
        super.onResume();
        String age ;



        if (prefs.getStringPreferences(SP_USER_MOMORDAD).equals("unknown_parent_status") || prefs.getStringPreferences(SP_USER_MOMORDAD).equals("other")) {
            age = resources.getString(R.string.family_member);
        } else {
            age = ageConfig.calculateKidAge(prefs.getIntPreferences(SP_USER_MONOTH), prefs.getIntPreferences(SP_USER_YEAR),
                    prefs.getStringPreferences(SP_USER_BOYORGIRL), prefs.getStringPreferences(SP_USER_MOMORDAD));
        }

        question_name.setText(prefs.getStringPreferences(SP_USER_NAME)+" - "+age);

    }


    private void sendQuestion()
    {


        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("title",  "");
            uploadMessage.put("competition_id",  prefs.getIntPreferences(ConstantVariable.COMPETITION_ID));

            if(bitmap != null) {
                uploadMessage.put("img_url",  imageUrl);
                uploadMessage.put("img_dimen",imageDimen);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,"https://www.kyarlay.com/api/competitions/new_entry", uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        dialog.dismiss();

                        try {

                            //FlurryAgent.logEvent("Create User Post");
                        } catch (Exception e) {
                        }

                        try{
                            int status = response.getInt("status");

                            final Dialog dialog1 = new Dialog(CompetitionPostActivity.this);
                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog1.setContentView(R.layout.dialog_done);
                            dialog1.setCancelable(true);

                            Window window = dialog1.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.CENTER;
                            wlp.width   = CompetitionPostActivity.this.getWindowManager().getDefaultDisplay().getWidth();
                            window.setAttributes(wlp);
                            dialog1.setCanceledOnTouchOutside(false);

                            CustomTextView title  = (CustomTextView) dialog1.findViewById(R.id.txtResult);
                            CustomTextView text  = (CustomTextView) dialog1.findViewById(R.id.txtClose);
                            title.setText(response.getString("message"));
                            text.setText(resources.getString(R.string.close_button));
                            dialog1.show();
                            if (status == 1) {

                                JSONObject jsonObject = response.getJSONObject("post");
                                prefs.saveIntPerferences(SP_COMPETITION_POST_ID, jsonObject.getInt("id"));
                                prefs.saveStringPreferences(SP_COMPETITION_POST_BODY, jsonObject.getString("title"));
                                prefs.saveIntPerferences(SP_COMPETITION_IMAGE_DIMEN, imageDimen);
                                prefs.saveStringPreferences(SP_COMPETITION_IMAGE_URL, imageUrl);

                            }


                            text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                    finish();
                                }
                            });

                        }catch (Exception e){
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }





                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );
            }
        }) {

            */
/**
             * Passing some request headers
             * *//*

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
        prefs.saveIntPerferences(SP_CHANGE, 0);
        finish();
    }


}
*/
