package com.kyarlay.ayesunaing.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.EditTextBackPressed;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Comment;
import com.kyarlay.ayesunaing.object.KyarlayAds;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.Reading_Post;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.FileUtil;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayesu on 12/22/17.
 */

public class ReadingCommentDetailsActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "ReadingCommentDetails";

    // vars
    Reading reading;
    String status = "";
    String pageUrl = "";
    MediaAdapter mediaAdapter;
    ArrayList<UniversalPost> universalListPost = new ArrayList<>();
    MyPreference prefs;
    Resources resources;
    Display display;
    FirebaseAnalytics firebaseAnalytics;
    String isComment = "";
    DatabaseAdapter databaseAdapter;

    Boolean loading = true;
    RecyclerView.LayoutManager manager;
    String post_type="";
    int fromdeep = 0;
    ProgressDialog progressDialog;




    // widgets
    RecyclerView recyclerView;
    LinearLayout  back_layout;
    EditTextBackPressed message_text;
    ImageView send, imgComment, imgRemove, imgCamera;
    RelativeLayout relativeComment;
    ProgressBar progressBar;

    CardView cardView;
    LinearLayout edit_layout, linearSend, linearCamera;

    boolean backpressed ;

    LinearLayout header;
    CustomTextView header_title, view_more;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private int PROFILE = 1;

    private Bitmap bitmap = null;
    int imageDimen = 0;
    long imageSizeKB = 0;
    private static String imageName = null;
    static Util util;
    static TransferUtility transferUtility;

    String imageUrl = null;
    int imageWidth, imageHeight = 0;
    int   postId = 0;
    String productId = "" , commentType = "";

    Uri fileUri;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment_details_activity);


        firebaseAnalytics   = FirebaseAnalytics.getInstance(ReadingCommentDetailsActivity.this);
        prefs   = new MyPreference(ReadingCommentDetailsActivity.this);
        Context context = LocaleHelper.setLocale(ReadingCommentDetailsActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();
        display          = getWindowManager().getDefaultDisplay();


        util = new Util();
        transferUtility = util.getTransferUtility(this);

        Log.e(TAG, "onCreate: " );


        progressBar         = (ProgressBar) findViewById(R.id.progressBar1);

        back_layout      = (LinearLayout) findViewById(R.id.back_layout);

        recyclerView    = (RecyclerView) findViewById(R.id.recycler_view);
        message_text = findViewById(R.id.message_text);
        send = findViewById(R.id.send);
        cardView = findViewById(R.id.product_detail_adapter_footer_layout);
        edit_layout = findViewById(R.id.edit_layout);
        header              = (LinearLayout) findViewById(R.id.header);
        header_title        = (CustomTextView) findViewById(R.id.header_title);
        view_more           = (CustomTextView) findViewById(R.id.view_more);

        relativeComment = findViewById(R.id.relativeComment);
        imgComment = findViewById(R.id.imgComment);
        imgRemove = findViewById(R.id.imgCommentRemove);
        imgCamera = findViewById(R.id.imgCamera);
        linearCamera = findViewById(R.id.linearCamera);
        linearSend = findViewById(R.id.linearSend);

        linearCamera.setVisibility(View.GONE);
        relativeComment.setVisibility(View.GONE);


        new MyFlurry(ReadingCommentDetailsActivity.this);
        databaseAdapter = new DatabaseAdapter(ReadingCommentDetailsActivity.this);

        backpressed = false;

        reading = new Reading();
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();

        try{
            reading.setId(bundle.getInt("id"));
            fromdeep = bundle.getInt("fromdeep");
            reading.setTitle(bundle.getString("title"));
            reading.setLikes(bundle.getInt("like_count"));
            reading.setComment_coount(bundle.getInt("comment_count"));
            reading.setSort_by(bundle.getString("from_class"));
            status   = bundle.getString("status");
            pageUrl = bundle.getString("page_url");
            post_type = bundle.getString("post_type");
            //noti_post_type = bundle.getString("noti_post_type");
            reading.setSort_by(status);
            reading.setPostType(READING_DETAIL);
            isComment   = bundle.getString("comment");
        }catch (Exception e){
            Log.e(TAG, "onCreate: Exception "  + e.getMessage() );
        }

        prefs.saveIntPerferences(SP_PAGE_READ_COMMENT, SP_DEFAULT);


        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        mediaAdapter        = new MediaAdapter(ReadingCommentDetailsActivity.this, universalListPost);
        recyclerView.setAdapter(mediaAdapter);

        try {

            Map<String, String> mix = new HashMap<String, String>();
            mix.put("type", "read_post");
            mix.put("source", "post_detail");
            mix.put("post_id", String.valueOf(reading.getId()));
            mix.put("status", status);
            FlurryAgent.logEvent("View Post", mix);
        } catch (Exception e) {
        }

        if (prefs.isNetworkAvailable()){
            checkCount(reading.getId());
        }

        linearSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        linearCamera.setOnClickListener(new View.OnClickListener() {
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

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(reading.getSort_by()!= null && reading.getSort_by().equals(FROM_FIREBASE)){
                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "Post_Detail");
                        FlurryAgent.logEvent("Incoming Pushnotification Click", mix);

                    } catch (Exception e) {}
                    Intent backIntent = new Intent(ReadingCommentDetailsActivity.this, MainActivity.class);
                    startActivity(backIntent);
                    finish();
                }
                else if (fromdeep == 1){
                    Intent backIntent = new Intent(ReadingCommentDetailsActivity.this, MainActivity.class);
                    startActivity(backIntent);
                    finish();
                }
                else
                    finish();

            }
        });

        progressBar.setVisibility(View.VISIBLE);

        if(post_type != null && post_type.trim().length() > 0){
            header.setVisibility(View.VISIBLE);

            header_title.setText(reading.getTitle());
            view_more.setText(resources.getString(R.string.view_more));
            callAds();
        }else{
            header.setVisibility(View.GONE);
            if(prefs.isNetworkAvailable()){


                richTextDetail(constatRichText_Detail + reading.getId() +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&version=" + prefs.getIntPreferences(SP_CURRENT_VERSION));


            }else{

                ToastHelper.showToast(ReadingCommentDetailsActivity.this, resources.getString(R.string.no_internet_error));

            }

        }





        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadingCommentDetailsActivity.this, VideoProgramDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("comment", "read");
                bundle.putInt("id", reading.getId());
                bundle.putString("title", reading.getTitle());
                bundle.putString("url", reading.getUrl());
                bundle.putInt("like_count", reading.getLikes());
                bundle.putInt("comment_count", reading.getComment_coount());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                try{

                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    if ((lastVisibleItem + 1) == totalItemCount && loading == false) {
                        loading = true;
                        prefs.saveIntPerferences(SP_PAGE_READ_COMMENT, prefs.getIntPreferences(SP_PAGE_READ_COMMENT) + SP_DEFAULT);
                        callAds();
                    }


                }catch (Exception e){
                    Log.e(TAG, "onScrolled: Exception "  + e.getMessage() );
                }

            }
        });

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeComment.setVisibility(View.GONE);
                bitmap = null;
            }
        });

        message_text.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                // message_text.setLines(7);
                InputMethodManager inputMethod = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                // message_text.setLines(7);
                if (inputMethod!= null) {





                    if (bitmap != null)
                        linearCamera.setVisibility(View.GONE);
                    else
                        linearCamera.setVisibility(View.VISIBLE);


                    message_text.requestFocus();
                    getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethod.showSoftInput(message_text, InputMethodManager.SHOW_IMPLICIT);

                    backpressed = true;


                }

                return false;
            }
        });



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: cardView Click" );
            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backpressed = false;


                if(prefs.getStringPreferences(SP_USER_TOKEN) != ""  && prefs.getStringPreferences(SP_USER_TOKEN).length() > 0){

                    if (bitmap != null){

                        if (prefs.isNetworkAvailable()){

                            progressDialog = new ProgressDialog(ReadingCommentDetailsActivity.this);
                            progressDialog.setMessage("Loading . . . ");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();



                            try {
                                uploadImage(FileUtil.from(ReadingCommentDetailsActivity.this,fileUri,bitmap,imageSizeKB,false));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{

                            ToastHelper.showToast(ReadingCommentDetailsActivity.this, resources.getString(R.string.no_internet_error));


                        }

                    }
                    else if(message_text.getText()!= null  && message_text.getText().toString().trim().length() > 0){
                        progressDialog = new ProgressDialog(ReadingCommentDetailsActivity.this);
                        progressDialog.setMessage("Loading . . . ");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        sendComment(message_text.getText().toString(), reading.getId(), post_type);
                        message_text.setText("");


                        InputMethodManager imm = (InputMethodManager)getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(message_text.getWindowToken(), 0);




                    }else{

                        ToastHelper.showToast(ReadingCommentDetailsActivity.this, resources.getString(R.string.comment_without_text));

                    }




                }else{

                    Intent intent   = new Intent(ReadingCommentDetailsActivity.this, ActivityLogin.class);
                    startActivity(intent);

                }

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
                    sendComment(message_text.getText().toString(), reading.getId(), post_type);

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
            Log.e(TAG, "SaveImage1:  "  + e.getMessage() );

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "SaveImage1:  "  + e.getMessage() );
            }
        }


        return file.getAbsolutePath();

    }


    private void sendComment(String comment, int post_id, final String post_type)
    {

        String mixType = "normal";
        String photo = "no";

        String commentText  = comment;

        if (commentText.trim().length() > 0){

            if (commentText.contains("\n")){
                String[] commentSplit = commentText.split("\n");
                commentText = commentSplit[0];
            }


            String[] trims = commentText.split(" ");
            String goIntent = "";
            for (int i=0; i < trims.length; i++){
                if (trims[i].contains("https://www.kyarlay.com/") || trims[i].contains("http://www.kyarlay.com/"))
                    goIntent = trims[i];
            }

            if (!goIntent.equals(""))
            {
                checkCommentUrl(goIntent);
            }
        }

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("comment",  comment);
            uploadMessage.put("post_id",  post_id);
            uploadMessage.put("comment_type",  commentType);
            if(post_type != null && post_type.trim().length() > 0)
                uploadMessage.put("post_type", post_type);

            if (imageUrl != null && imageUrl.trim().length() > 0){
                uploadMessage.put("img_url",  imageUrl);
                uploadMessage.put("img_width",  imageWidth);
                uploadMessage.put("img_height",  imageHeight);
                photo = "yes";
            }

            if (postId != 0){
                uploadMessage.put("comment_post_id",   postId);
                mixType = "post";
            }

            if (productId != null && productId.trim().length() > 0){
                uploadMessage.put("product_id",   productId);
                mixType = "product";
            }



        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "sendComment:  "  + e.getMessage() );
        }




        final String finalMixType = mixType;
        final String finalPhoto = photo;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantCommentSend +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE), uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();


                        if(post_type != null && post_type.trim().length() > 0) {
                            FirebaseMessaging.getInstance().subscribeToTopic("video_program_" + reading.getId());
                        }else {
                            FirebaseMessaging.getInstance().subscribeToTopic("post_" + reading.getId());
                        }

                        message_text.setText("");
                        bitmap = null;
                        postId = 0;
                        imageUrl = "";
                        productId = "";
                        relativeComment.setVisibility(View.GONE);




                        InputMethodManager imm = (InputMethodManager)getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(message_text.getWindowToken(), 0);

                        //message_text.setLines(2);

                        Comment my_comment = new Comment();
                        try {
                            my_comment.setId(response.getInt("id"));
                            my_comment.setBody(response.getString("body"));
                            my_comment.setCommentor_id(response.getInt("customer_id"));
                            my_comment.setParent_status(prefs.getStringPreferences(SP_USER_MOMORDAD));
                            my_comment.setKid_gender(prefs.getStringPreferences(SP_USER_BOYORGIRL));
                            my_comment.setBirth_month(prefs.getIntPreferences(SP_USER_MONOTH));
                            my_comment.setBirth_year(prefs.getIntPreferences(SP_USER_YEAR));
                            my_comment.setCommentor(prefs.getStringPreferences(SP_USER_NAME));
                            my_comment.setPostType(READING_COMMENT);
                            my_comment.setCommentor_profile(prefs.getStringPreferences(ConstantVariable.SP_USER_PROFILEIMAGE));
                            my_comment.setCreated_at("now");

                            my_comment.setComment_type(response.getString("comment_type"));
                            my_comment.setCommentor_type(response.getString("commentor_type"));

                            my_comment.setProduct_id(response.getInt("product_id"));
                            my_comment.setPost_id(response.getInt("post_id"));
                            my_comment.setSubtitle(response.getString("subtitle"));
                            my_comment.setPreview_img_url(response.getString("preview_img_url"));
                            my_comment.setComment_photo_url(response.getString("img_url"));
                            my_comment.setPrice(response.getInt("price"));
                            my_comment.setComment_dimen(response.getInt("img_dimen"));


                            universalListPost.add(my_comment);
                            manager.scrollToPosition(universalListPost.size() - 1);

                            try {


                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("post_id", String.valueOf(reading.getId()));
                                mix.put("type", finalMixType);
                                mix.put("photo_included", finalPhoto);
                                FlurryAgent.logEvent("Add New Comment", mix);
                            } catch (Exception e) {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: myComment " +  e.getMessage() );
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: comment Exception :   "  + error.getMessage()  );

                progressDialog.dismiss();
            }
        }) {

            /* *
             * Passing some request headers
             **/
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

    private void  checkCount(int countID)
    {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,String.format(constantCounts, "post", countID + "" , prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID)) , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {




                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error "  + error.getMessage() );

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"Order123");
    }


    @Override
    public void onBackPressed() {
        linearCamera.setVisibility(View.GONE);


        if (backpressed){
            if (message_text.getText().toString().isEmpty()){


                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(message_text.getWindowToken(), 0);
            }

            backpressed = false;
        }
        else{
            if(reading.getSort_by()!= null && reading.getSort_by().equals(FROM_FIREBASE)){
                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "Post_Detail");
                    FlurryAgent.logEvent("Incoming Pushnotification Click", mix);
                } catch (Exception e) {}
                Intent backIntent = new Intent(ReadingCommentDetailsActivity.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
            else if (fromdeep == 1){
                Intent backIntent = new Intent(ReadingCommentDetailsActivity.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
            else
                finish();
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

                        imageWidth = bitmap.getWidth();
                        imageHeight = bitmap.getHeight();



                        imageDimen = (bitmap.getHeight() * 100) / bitmap.getWidth();


                        linearCamera.setVisibility(View.GONE);
                        relativeComment.setVisibility(View.VISIBLE);
                        imgComment.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onActivityResult:  "  + e.getMessage() );
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

    public void showKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams)
                message_text.getLayoutParams();
        linearLayoutParams.height = (int) (display.getHeight() * 0.2);
        message_text.setLayoutParams(linearLayoutParams);


        message_text.requestFocus();
        getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(message_text, InputMethodManager.SHOW_IMPLICIT);

        backpressed = true;
    }

    public void richTextDetail(String richtext_url) {


        Log.e(TAG, "richTextDetail: "  +richtext_url );

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                richtext_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                if(response.length() > 0) {
                    universalListPost.clear();
                    progressBar.setVisibility(View.GONE);

                    try {
                        Gson gson = new Gson();
                        Reading main = gson.fromJson(response.toString(), Reading.class);


                        if (main.getPostType().equals(USER_POST)) {
                            main.setPostType(USER_POST_READING_DETAILS);
                        } else {
                            main.setPostType(READING_DETAIL);

                        }


                        try{
                            if (main.getPage_img_url().trim().length() <= 0){
                                main.setPage_img_url(pageUrl);
                            }


                        }catch (Exception e){
                            Log.e(TAG, "onResponse: reading page profile exception "  + e.getMessage() );
                        }

                        universalListPost.add(main);

                        for(int i = 0; i < main.getPosts().size(); i ++){
                            Reading_Post post = new Reading_Post();
                            post = main.getPosts().get(i);




                            if(post.getPost_type().equals(READING_PRODUCT)) {
                                Product product = post.getProduct();
                                product.setPostType(CATEGORY_DETAIL);
                                universalListPost.add(product);



                            }

                            else  if(post.getPost_type().equals(READING_TOOL)) {

                                if (post.getYoutube_id().equals("all")
                                        || (post.getYoutube_id().equals("is_it_safe")
                                        || (post.getYoutube_id().equals("news")))
                                        || (post.getYoutube_id().equals("video"))
                                        || (post.getYoutube_id().equals("maharbote"))
                                        || (post.getYoutube_id().equals("momolay"))
                                        || (post.getYoutube_id().equals("baby_names"))
                                        || (post.getYoutube_id().equals("birthday_calendar"))
                                        || (post.getYoutube_id().equals("ovulation_calculator"))
                                        || (post.getYoutube_id().equals("due_date"))
                                        || (post.getYoutube_id().equals("clinic_directory"))
                                        || (post.getYoutube_id().equals("schools"))

                                ){
                                    post.setPostType(TOOL_ARTICLE);
                                    universalListPost.add(post);
                                }



                            }

                            else if (post.getPost_type().equals(READING_ARTICLE)){

                                post.setPostType(TOOL_ARTICLE);
                                universalListPost.add(post);


                            }

                            else if (post.getPost_type().equals(READING_PLAYLIST)){
                                post.setPostType(TOOL_ARTICLE);
                                universalListPost.add(post);


                            }

                            else if(post.getPost_type().equals(READING_TEXT)) {
                                post.setPostType(post.getPost_type());
                                universalListPost.add(post);



                            }
                            else if(post.getPost_type().equals(READING_YOUTUBE)) {

                                post.setPostType(post.getPost_type());
                                universalListPost.add(post);

                            }

                        }

                        Reading readingLikeComment = new Reading();
                        readingLikeComment.setUrl(main.getUrl());
                        int likes = 0;
                        likes = reading.getLikes();


                        if(reading.getLikes() > 0 || databaseAdapter.checkPostLiked(reading.getId())) {
                            if(databaseAdapter.checkPostLiked(reading.getId())){
                                if(reading.getLikes() == 0) {
                                    likes = reading.getLikes() + 1;

                                }
                            }
                        }


                        readingLikeComment.setId(reading.getId());
                        readingLikeComment.setLikes(likes);
                        readingLikeComment.setPostType(LIKECOMMNET);
                        universalListPost.add(readingLikeComment);


                        mediaAdapter.notifyDataSetChanged();

                        try {

                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("post_id", String.valueOf(reading.getId()));
                            FlurryAgent.logEvent("View Comment Page", mix);

                        } catch (Exception e) {
                        }


                        callAds();
                        mediaAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: Exception :  "  + e.getMessage() );



                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });

        AppController.getInstance().addToRequestQueue(request);

    }

    private void callAds(){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantKyarlayAds + "rectangular" , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if(universalListPost.size() != 0 && universalListPost.get(universalListPost.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalListPost.remove(universalListPost.size() - 1);
                }
                Log.e(TAG, "onResponse: ************ "  + response.toString() );
                KyarlayAds main = new KyarlayAds();

                if (response.length() > 0){
                    Gson gson = new Gson();
                    main = gson.fromJson(response.toString(), KyarlayAds.class);
                }
                else{
                    main.setId(-810);
                }

                getComment(main);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                KyarlayAds main = new KyarlayAds();
                main.setId(-810);
                getComment(main);

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    private void getComment(KyarlayAds kyarlayAds)
    {

        if(prefs.getIntPreferences(SP_PAGE_READ_COMMENT) == 1){
            if (kyarlayAds.getId() != -810){

                kyarlayAds.setPostType(ADS);
                universalListPost.add(kyarlayAds);

            }
        }

        String url1 = "";
        if(post_type != null && post_type.trim().length() > 0){
            url1 = constantComment+reading.getId()+"&page="+prefs.getIntPreferences(SP_PAGE_READ_COMMENT)+"&post_type=video_program";
        }else
            url1 = constantComment+reading.getId()+"&page="+prefs.getIntPreferences(SP_PAGE_READ_COMMENT);

        url1 = url1  +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE);

        Log.e(TAG, "getComment: "  + url1 );



        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url1,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {



                        progressBar.setVisibility(View.GONE);

                        if(response.length() > 0){

                            loading = false;
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Comment>>() {
                                }.getType();
                                List<Comment> categoryList = mGson.fromJson(response.toString(), listType);

                                if (categoryList.size() > 0){

                                    for (int i=0; i < categoryList.size(); i++){
                                        Comment comment = categoryList.get(i);
                                        comment.setCreated_at(getDateInMillis(comment.getCreated_at()));
                                        comment.setPostType(READING_COMMENT);
                                        if (comment.getComment_dimen() >= 85)
                                            comment.setComment_dimen(80);
                                        else if (comment.getComment_dimen() < 85)
                                            comment.setComment_dimen(65);

                                        universalListPost.add( comment);

                                    }
                                }

                                mediaAdapter.notifyDataSetChanged();


                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }else {
                            loading = true;



                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                ToastHelper.showToast(ReadingCommentDetailsActivity.this,  resources.getString(R.string.search_error));



            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    public String getDateInMillis(String srcDate) throws ParseException {
        Calendar cal =  Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            cal.setTime(sdf.parse(srcDate));
            Calendar calendar = Calendar.getInstance();
            long now = calendar.getTimeInMillis();
            long time = cal.getTimeInMillis();

            long diff = now - time;

            int seconds = (int) (diff / 1000) % 60 ;
            int minutes = (int) ((diff / (1000*60)) % 60);
            int hours   = (int) ((diff / (1000*60*60)) % 24);
            int days = (int) (diff / (1000*60*60*24));

            if(days == 0 && hours ==0 && minutes ==0 && seconds > 0){
                return  seconds +" sec ago";
            }else if(days == 0 && hours ==0 && minutes > 0){
                return  minutes+" min ago";
            }else if(days == 0 && hours > 0){
                return hours+" hour ago";
            }else if(days > 0 ){
                return days+" day ago";
            }else if(days > 7){
                return srcDate;
            }else {
                return "";
            }


        } catch (ParseException e1) {
            e1.printStackTrace();
            Log.e(TAG, "getDateInMillis:  "  + e1.getMessage() );
            return "exception";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkCommentUrl(String data1){


        String maintype = "";

        String[] outer_trim = new String[2];
        if (data1.contains("http://www.kyarlay.com"))
            outer_trim = data1.split("http://www.kyarlay.com/");
        else if (data1.contains("https://www.kyarlay.com"))
            outer_trim = data1.split("https://www.kyarlay.com/");


        if (outer_trim.length >= 2 ){
            {

                String[] finalTrim = outer_trim[1].split("/");
                if (finalTrim.length < 2 ) {
                    Log.e(TAG, "onCreate: / finish" );

                }
                else{
                    maintype = finalTrim[0];



                    if(maintype.equals("p")){

                        try{
                            productId = finalTrim[1];
                            commentType = "product";
                        }catch (Exception e){
                            Log.e(TAG, "checkCommentUrl product Exception :  "   + e.getMessage()  );
                        }
                    }
                    else if (maintype.equals("a")){

                        try{
                            postId =  Integer.parseInt(finalTrim[1]);
                            commentType = "post";
                        }catch (Exception e){
                            Log.e(TAG, "checkCommentUrl post Exception  :  "   + e.getMessage()  );
                        }
                    }


                }
            }
        }
    }






}

