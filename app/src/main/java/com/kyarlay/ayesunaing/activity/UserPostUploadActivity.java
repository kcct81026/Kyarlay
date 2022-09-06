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
import android.graphics.Typeface;
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
import android.widget.RelativeLayout;
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
import com.flurry.android.FlurryAgent;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircleBorderImageView;
import com.kyarlay.ayesunaing.custom_widget.CircularNetworkImageView;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AgeConfig;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.operation.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UserPostUploadActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "UserPostUploadActivity";

    CircleBorderImageView img;
    Display display;
    Dialog dialog;

    MyPreference prefs;
    AppCompatActivity activity ;
    Resources resources;
    AgeConfig ageConfig;
    private int GALLERY = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Bitmap bitmap = null;
    int imageDimen = 0;
    long imageSizeKB = 0;
    String imageUrl = null;


    ImageView imgClose;
    CustomTextView txtCreate, txtName, txtAge,txtUpload, txtInfo;
    CustomEditText editPost;
    CustomButton button_create;
    ImageView imgUserPost, imgPhotoClose ;
    LinearLayout linearUpload;
    CircularNetworkImageView profile_image;
    RelativeLayout relativePhoto;


    TransferUtility transferUtility;
    Util util;
    String imageName = null;
    Uri fileUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_user_post);

        prefs  = new MyPreference(UserPostUploadActivity.this);
        activity = UserPostUploadActivity.this;
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        ageConfig = new AgeConfig(activity);

        dialog = new Dialog(activity);
        util = new Util();
        transferUtility = util.getTransferUtility(this);


        imgClose = findViewById(R.id.imgClose);
        txtCreate = findViewById(R.id.txtCreate);
        button_create = findViewById(R.id.button_create);
        profile_image = findViewById(R.id.userNetworkImage);
        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);
        editPost = findViewById(R.id.editPost);
        imgUserPost = findViewById(R.id.imgUserPost);
        linearUpload = findViewById(R.id.linearUpload);
        txtUpload  = findViewById(R.id.txtUpload);
        txtInfo  = findViewById(R.id.txtInfo);
        relativePhoto  = findViewById(R.id.relativePhoto);
        imgPhotoClose  = findViewById(R.id.imgPhotoClose);

        txtUpload.setText(resources.getString(R.string.upload_photo));
        txtInfo.setText(resources.getString(R.string.group_chat_title));

        String age ;
        if (prefs.getStringPreferences(SP_USER_MOMORDAD).equals("unknown_parent_status") || prefs.getStringPreferences(SP_USER_MOMORDAD).equals("other")) {
            age = resources.getString(R.string.family_member);
        } else {
            age = ageConfig.calculateKidAge(prefs.getIntPreferences(SP_USER_MONOTH), prefs.getIntPreferences(SP_USER_YEAR),
                    prefs.getStringPreferences(SP_USER_BOYORGIRL), prefs.getStringPreferences(SP_USER_MOMORDAD));
        }

        txtName.setText(prefs.getStringPreferences(SP_USER_NAME));
        txtAge.setText(age);
        txtName.setTypeface(txtName.getTypeface(), Typeface.BOLD);

        try{
            if (prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals("null")){
                profile_image.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
            }
            else if (!prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals(""))
                profile_image.setImageUrl(prefs.getStringPreferences(SP_USER_PROFILEIMAGE), AppController.getInstance().getImageLoader());
            else
                profile_image.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);


        }catch (Exception e){
            profile_image.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
        }




        linearUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, GALLERY);

                } else {

                    requestPermission();
                }
            }
        });


        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bitmap != null && imageSizeKB != 0 && editPost.getText() != null
                        && editPost.getText().toString().trim().length() > 0 ){


                    showDialog();
                    try {
                        uploadImage(FileUtil.from(activity,fileUri,bitmap,imageSizeKB,false));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if(editPost.getText() != null
                        && editPost.getText().toString().trim().length() > 0 ) {

                    showDialog();
                    sendQuestion();


                }else{

                    ToastHelper.showToast(activity, resources.getString(R.string.post_without_question));

                }

            }
        });


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                prefs.saveIntPerferences(SP_CHANGE, 0);
                finish();
                //overridePendingTransition(R.anim.slide_up, R.anim.slide_bottom);
            }
        });

        imgPhotoClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = null;
                relativePhoto.setVisibility(View.GONE);
            }
        });
    }


    private void sendQuestion()
    {

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("question",  editPost.getText().toString());

            if(bitmap != null) {
                uploadMessage.put("img_url",  imageUrl);
                uploadMessage.put("img_width", bitmap.getWidth());
                uploadMessage.put("img_height", bitmap.getHeight());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,"https://www.kyarlay.com/api/posts", uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();

                        try {

                            FlurryAgent.logEvent("Create User Post");
                        } catch (Exception e) {
                        }

                        try {

                            prefs.saveIntPerferences(SP_USER_POST_ID, response.getInt("id"));
                            prefs.saveStringPreferences(SP_USER_POST_RELEASE_AT, response.getString("release_at"));
                            prefs.saveStringPreferences(SP_USER_POST_BODY, response.getString("body"));
                            prefs.saveIntPerferences(SP_USER_POST_IMAGE_DIMEN, imageDimen);
                            prefs.saveStringPreferences(SP_USER_POST_IMAGE_URL, imageUrl);

                            FirebaseMessaging.getInstance().subscribeToTopic("post_"+response.getInt("id"));



                        }catch (Exception e){
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }


                        finish();
                        //overridePendingTransition(R.anim.slide_up, R.anim.slide_bottom);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );
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
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int) percentDonef;
                // tvFileName.setText("ID:" + id + "|bytesCurrent: " + bytesCurrent + "|bytesTotal: " + bytesTotal + "|" + percentDone + "%");

            }

            @Override
            public void onError(int id, Exception ex) {
                ex.printStackTrace();
            }

        });
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



    public void showDialog(){

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);
        dialog.setCanceledOnTouchOutside(false);

        CustomTextView title  = (CustomTextView) dialog.findViewById(R.id.title);
        CustomTextView text  = (CustomTextView) dialog.findViewById(R.id.text);

        title.setText(resources.getString(R.string.progress_dialog_title));
        text.setText(resources.getString(R.string.upload_post));

        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        prefs.saveIntPerferences(SP_CHANGE, 0);
        finish();
        //overridePendingTransition(R.anim.slide_up, R.anim.slide_bottom);
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                fileUri = data.getData();
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
                // imageDimen = (bitmap.getHeight() *100 ) / bitmap.getWidth();
                //imgUserPost.getLayoutParams().height = (imageDimen * display.getWidth()) / 100;
                // imgUserPost.getLayoutParams().width = display.getWidth();
                imgUserPost.setImageBitmap(bitmap);
                relativePhoto.setVisibility(View.VISIBLE);




            }
        }
    }

    private Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
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


    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{/*Manifest.permission.CAMERA,*/ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



}
