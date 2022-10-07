package com.kyarlay.ayesunaing.operation;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.util.Log;

import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.object.Addresses;
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.Discount;
import com.kyarlay.ayesunaing.object.Flash_Sales;
import com.kyarlay.ayesunaing.object.Images;
import com.kyarlay.ayesunaing.object.MainCategoryObj;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.NameObject;
import com.kyarlay.ayesunaing.object.Order123;
import com.kyarlay.ayesunaing.object.OrderIDs;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.ShopLocation;
import com.kyarlay.ayesunaing.object.StockSummeries;
import com.kyarlay.ayesunaing.object.Supplier;
import com.kyarlay.ayesunaing.object.TownShip;
import com.kyarlay.ayesunaing.object.UniversalPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ayesunaing on 9/6/16.
 */
public class DatabaseAdapter implements ConstantsDB, ConstantVariable {


    private static final String DATABASE_NAME = "KYAR_LAY_DB";
    private static final int DATABASE_VERSION = 96;
    private Context context;
    private DBHelper dBHelper;
    private static SQLiteDatabase db=null ;

    SharedPreferences prefs;
    String SP_NAME = "KyarLay_001";
    private static final String TAG = "DatabaseAdapter";

    public DatabaseAdapter(Context context)
    {
        this.context=context;
        if(prefs == null) {
            prefs =  context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        }
        dBHelper=new DBHelper(context);
        open();
    }

    public DatabaseAdapter open()
    {
        try {
            if (db == null || !db.isOpen()) {
                db = dBHelper.get_database();
            }
        }catch (SQLException e){
        }
        return this;
    }

    // close the database
    public void close() throws SQLException
    {
        if(db==null && db.isOpen())
            dBHelper.close();
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            db = getWritableDatabase();
        }

        public SQLiteDatabase get_database() {
            return db;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                String save_cart = "create table " + TABLE_SAVE_CART +
                        " (" + pId + " integer primary key autoincrement,"+ productID +" integer not null ,"+
                        title +" text not null ," +preview_img_url +" text not null ," +
                        option +" text not null ," +
                        price +" integer not null ," +
                        point_usage +" integer not null ," +
                        member_discount +" integer not null ,"+
                        final_item_price +" integer not null ,"+count +" integer not null );";
                db.execSQL(save_cart);

            } catch(Exception e) {
                e.printStackTrace();
            }
            try {
                String category = "create table " + TABLE_CATEGORY_MAIN +
                        " (" + pId + " integer primary key autoincrement,"+ id +" integer not null ,"
                        +title +" text not null ," +tag +" text not null ," +image +" text not null ,"+
                        big_image +" text not null );";
                db.execSQL(category);

            } catch(Exception e) {
                e.printStackTrace();
            }
            try {
                String category = "create table " + TABLE_CATEGORY +
                        " (" + pId + " integer primary key autoincrement,"+ id +" integer not null ,"
                        +tag +" text not null ," +status +" integer not null ,"+ name +" text not null );";
                db.execSQL(category);

            } catch(Exception e) {
                e.printStackTrace();
            }
            try {
                String supplier = "create table " + TABLE_SUPPLIER +
                        " (" +pId + " integer primary key autoincrement,"+id +" integer ,"+ name +" text not null );";
                db.execSQL(supplier);

            } catch(Exception e) {
                e.printStackTrace();
            }
            try {
                String category = "create table " + TABLE_IMAGES +
                        " (" + pId + " integer primary key autoincrement,"+ dimen +" integer not null ,"
                        + name +" text not null ,"
                        + url +" text not null ,"+ productID +" integer not null );";
                db.execSQL(category);

            } catch(Exception e) {
                e.printStackTrace();
            }
            try {
                String category = "create table " + TABLE_ORDER_ITEM_IMAGES +
                        " (" + pId + " integer primary key autoincrement,"+ dimen +" integer not null ,"
                        + url +" text not null ,"+ orderItemId +" integer not null );";
                db.execSQL(category);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String delivery = "create table " + TABLE_DELIVERY +
                        " (" + pId + " integer primary key autoincrement,"+ id +" integer not null ,"
                        + delivery_type +" integer not null ,"
                        + normal_price +" integer not null ,"
                        + express_delivery_days +" text not null ,"
                        + normal_delivery_days +" text not null ,"
                        + name +" text not null ,"
                        + desc +" text not null ,"+freeDelivery +" integer not null ,"
                        +timing +" integer not null ,"
                        +first_time_free +" integer not null ,"
                        + price +" integer not null );";
                db.execSQL(delivery);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String order = "create table " + TABLE_ORDER +
                        " (" + pId + " integer primary key autoincrement,"+ orderId +" text not null ,"
                        + name +" text not null ,"+ address +" text not null ,"+ phoneNo +" text not null ,"
                        + deliveryPrice +" text not null ,"+ date +" text not null ,"
                        + totalPrice +" integer not null );";
                db.execSQL(order);

            } catch(Exception e) {
                e.printStackTrace();
            }


            try {
                String orderItem = "create table " + TABLE_ORDER_ITEM +
                        " (" + pId + " integer primary key autoincrement,"+ id+" integer not null ,"
                        + orderId+" text not null ,"+ title+" text not null ,"
                        + price+" integer not null ,"+ preview_img_url+" text not null ,"
                        + count +" integer not null );";
                db.execSQL(orderItem);

            } catch(Exception e) {
                e.printStackTrace();
            }


            try {
                String product = "create table " + TABLE_PRODUCT +
                        " (" + pId + " integer primary key autoincrement,"+ id+" integer not null ,"
                        + title+" text not null ,"+ desc +" text not null ,"+ price+" integer not null ,"
                        + discountedPrice+" integer not null ,"+ discount+" integer not null ,"  + point_usage +" integer not null ," + youtubeId +" text not null ,"
                        + status+" text not null ,"+ code +" text not null ,"+ descTitle +" text not null ,"
                        + categoryId+" integer not null ,"+brand_id+" integer  ,"+ supplierId +" integer not null ,"
                        + preview_img_url+" text not null ,"+ postType+" text not null ,"+ brandName+" text not null ,"
                        + phoneNo +" text not null ,"+ youtubeImageUrl+" text not null ,"
                        + recommended+" text not null ,"+member_discount+" integer  ,"
                        + youtubeImageDimen +" integer not null ,"+ productUrl+" text not null ,"+category_name+" text not null , "
                        + productType +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String product = "create table " + TABLE_SEARCHED +
                        " (" + pId + " integer primary key autoincrement,"+ id+" integer not null ,"
                        + title+" text not null ,"
                        + preview_img_url+" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }



            try {
                String product = "create table " + TABLE_CITY +
                        " (" + pId + " integer primary key autoincrement,"+ id+" integer not null ,"
                        + name +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }
            try {
                String product = "create table " + TABLE_PICKUP_SHOP +
                        " (" + pId + " integer primary key autoincrement,"+ pickup_id+" integer not null ,"+
                        pickup_name +" text  ," +
                        pickup_address +" text  ,"
                        + pickup_phone +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }




            try {
                String product = "create table " + TABLE_TOWNSHIP +
                        " (" + pId + " integer primary key autoincrement,"+ id +" integer not null ,"
                        + store_id+" integer not null ,"
                        + delivery_type+" integer not null ,"
                        + name +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String product = "create table " + TABLE_CUSTOMER_ADDRESS +
                        " (" + pId + " integer primary key autoincrement,"
                        + id +" integer not null ,"
                        + customer_township_id+" integer not null ,"
                        + customer_delivery_id+" integer not null ,"
                        + customer_township+" text not null ,"
                        + customer_address +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }


            try {
                String product = "create table " + TABLE_BRAND +
                        " (" + pId + " integer primary key autoincrement,"+ id+" integer not null ,"
                        + title+" text not null ,"+ desc+" text not null ,"
                        +tag+" text not null ,"+recommended+" text not null ,"
                        + image +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }
            try {
                String product = "create table " + TABLE_DISCOUNT +
                        " (" + pId + " integer primary key autoincrement,"+ productID+" integer not null ,"
                        + discountID+" integer not null ,"+ discountType+" text   ,"+ percentage+" int not null ,"
                        +num_extra+" integer not null ,"+ gift_img_url+" text ,"+ gift_info+" text  ,"
                        + campaign_info+" text ,"+ benefit_type+" text  ,"+ dimen+" integer not null   ,"
                        + start_at+" text ,"+ end_at+" text  ,"
                        + min_count+" integer not null   ,"+ max_count+" integer not null ,"+member_only+" integer not null , "
                        + count_type +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String product = "create table " + TABLE_PRODUCT_STORE_LOCATION +
                        " (" + pId + " integer primary key autoincrement,"+ productID+" integer not null ,"
                        + store_id +" integer not null ,"
                        + store_qty+" integer not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }


            try {
                String product = "create table " + TABLE_FLASH_SALE +
                        " (" + pId + " integer primary key autoincrement,"+ productID+" integer not null ,"
                        + flash_id+" integer not null ,"
                        +flash_available_quantity+" integer not null ,"
                        +flash_reserved_quantity+" integer not null ,"
                        +flash_discount+" integer not null ,"
                        + flash_start_date+" text ,"
                        + flash_end_date +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }


            try {
                String product = "create table " + TABLE_DISCOUNT_ITEM +
                        " (" + pId + " integer primary key autoincrement,"+ productID+" integer not null ,"
                        + discountID+" integer not null ,"+ discountType+" text   ,"+ percentage+" int not null ,"
                        +num_extra+" integer not null ,"+ gift_img_url+" text ,"+ gift_info+" text  ,"
                        + campaign_info+" text ,"+ benefit_type+" text  ,"+ dimen+" integer not null   ,"
                        + start_at+" text ,"+ end_at+" text  ,"
                        + min_count+" integer not null   ,"+ max_count+" integer not null ,"+member_only+" integer not null , "
                        + count_type +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String product = "create table " + TABLE_PRODUCT_LIKE +
                        " (" + pId + " integer primary key autoincrement,"+ productID+" integer not null ,"
                        + postType +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String product = "create table " + TABLE_POST_LIKE +
                        " (" + pId + " integer primary key autoincrement,"+ postId+" integer not null ,"
                        + postType +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String product = "create table " + TABLE_VIDEO_LIKE +
                        " (" + pId + " integer primary key autoincrement,"+ postId+" integer not null ,"
                        + postType +" text not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String product = "create table " + TABLE_POST_NOTIFICATION +
                        " (" + pId + " integer primary key autoincrement,"+ postId+" integer not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String product = "create table " + TABLE_PRODUCT_NOTI +
                        " (" + pId + " integer primary key autoincrement,"+ postId+" integer not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String product = "create table " + TABLE_POST_SUBSCRIBE +
                        " (" + pId + " integer primary key autoincrement,"+ postId+" integer not null );";
                db.execSQL(product);

            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                String user = "create table " + TABLE_SAVE_NAMES +
                        " (" + pId + " integer primary key autoincrement,"
                        + SM_ID + " int ,"
                        + SM_NAME + " text );";

                db.execSQL(user);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onCreate:TABLE_SAVE_NAMES Exception "  + e.getMessage() );
            }

            try {
                String user = "create table " + TABLE_SUPER_CATEGORY +
                        " (" + pId + " integer primary key autoincrement,"
                        + CAT_ID + " int ,"
                        + CAT_NAME + " text );";

                db.execSQL(user);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onCreate:TABLE_SUPER_CATEGORY Exception "  + e.getMessage() );
            }



        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //super.onDowngrade(db, oldVersion, newVersion);
            try{
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE_CART);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCHED);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY_MAIN);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIER);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOWNSHIP);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_ADDRESS);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRAND);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCOUNT);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STORE_LOCATION);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASH_SALE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCOUNT_ITEM);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST_LIKE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO_LIKE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST_NOTIFICATION);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_NOTI);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST_SUBSCRIBE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE_NAMES);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPER_CATEGORY);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERY);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICKUP_SHOP);

                onCreate(db);
            }catch (Exception e){
                Log.e(TAG, "onDowngrade: Exception :  " + e.getMessage() );
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try{
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE_CART);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCHED);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY_MAIN);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIER);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOWNSHIP);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_ADDRESS);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRAND);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCOUNT);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STORE_LOCATION);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASH_SALE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCOUNT_ITEM);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST_LIKE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO_LIKE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST_NOTIFICATION);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_NOTI);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST_SUBSCRIBE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE_NAMES);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPER_CATEGORY);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERY);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICKUP_SHOP);
                onCreate(db);
            }catch (Exception e){
                Log.e(TAG, "onUpgrade: Exception :  " + e.getMessage() );
            }
        }
    }

    public  void insertBrandDetail(List<Product> products, String type) {
        for (int i = 0; i < products.size(); i++) {
            Product product = new Product();
            product = products.get(i);
            deleteColumn(TABLE_PRODUCT, id, String.valueOf(product.getId()));
            try{
                ContentValues pro = new ContentValues();
                pro.put(id,             product.getId());
                pro.put(title,          product.getTitle());
                pro.put(desc,           product.getDesc());
                pro.put(descTitle,      product.getDescTitle());
                pro.put(price,          product.getPrice());
                pro.put(discount,       product.getDiscount());
                pro.put(point_usage,    product.getPoint_usage());
                pro.put(youtubeId,      product.getYoutubeId());
                pro.put(status,         product.getStatus());
                pro.put(code,           product.getCode());
                pro.put(categoryId,     product.getCategoryId());
                pro.put(supplierId,     product.getSupplier().getId());
                pro.put(preview_img_url,product.getPreviewImage());
                pro.put(brand_id ,      product.getBrand_id());
                pro.put(brandName,      product.getBrand_name());
                pro.put(recommended,    product.getRecommended());
                pro.put(member_discount,product.getMember_discount());
                pro.put(postType,       type);
                pro.put(phoneNo,        product.getPhoneNo());
                pro.put(category_name,  product.getCategory_name());
                pro.put(productType,    type);
                pro.put(productUrl,     product.getProductUrl());
                pro.put(youtubeImageUrl,product.getYoutubeImageUrl());
                pro.put(youtubeImageDimen,product.getYoutubeImageDimen());
                pro.put(discountedPrice, product.getDiscountedPrice());

                for(int j = 0 ; j < product.getImages().size(); j++){
                    Images image = new Images();
                    image = product.getImages().get(j);
                    ContentValues img = new ContentValues();
                    img.put(url,        image.getUrl());
                    img.put(dimen,      image.getDimen());
                    img.put(productID,  product.getId());
                    img.put(name,       image.getName());
                    long imageReturn = db.insert(TABLE_IMAGES, null, img);
                }
                deleteColumn(TABLE_SUPPLIER, id, String.valueOf(product.getSupplier().getId()));
                ContentValues supplier = new ContentValues();
                supplier.put(id,    product.getSupplier().getId());
                supplier.put(name,  product.getSupplier().getName());
                long supplierReturn = db.insert(TABLE_SUPPLIER, null, supplier);

                long productReturn = db.insert(TABLE_PRODUCT, null, pro);
            }catch (Exception e){
            }

        }
    }




    public ArrayList<UniversalPost> getBrandDetail(String proType){
        ArrayList<UniversalPost> posts = new ArrayList<>();

        Cursor cursor  = db.query(TABLE_PRODUCT, null, productType
                +"=?",new String[]{proType}, null, null, id +" DESC");

        if(cursor!= null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Product pro = new Product();
                pro.setId(cursor.getInt(cursor.getColumnIndex(id)));
                pro.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                pro.setDesc(cursor.getString(cursor.getColumnIndex(desc)));
                pro.setPrice(cursor.getInt(cursor.getColumnIndex(price)));
                pro.setDiscount(cursor.getInt(cursor.getColumnIndex(discount)));
                pro.setPoint_usage(cursor.getInt(cursor.getColumnIndex(point_usage)));
                pro.setYoutubeId(cursor.getString(cursor.getColumnIndex(youtubeId)));
                pro.setStatus(cursor.getString(cursor.getColumnIndex(status)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(code)));
                pro.setCategoryId(cursor.getInt(cursor.getColumnIndex(categoryId)));
                pro.setPreviewImage(cursor.getString(cursor.getColumnIndex(preview_img_url)));
                pro.setBrand_id(cursor.getInt(cursor.getColumnIndex(brand_id)));
                pro.setRecommended(cursor.getString(cursor.getColumnIndex(recommended)));
                pro.setMember_discount(cursor.getInt(cursor.getColumnIndex(member_discount)));
                pro.setBrand_name(cursor.getString(cursor.getColumnIndex(brandName)));
                pro.setPhoneNo(cursor.getString(cursor.getColumnIndex(phoneNo)));
                pro.setProductType(cursor.getString(cursor.getColumnIndex(productType)));
                pro.setYoutubeImageUrl(cursor.getString(cursor.getColumnIndex(youtubeImageUrl)));
                pro.setYoutubeImageDimen(cursor.getInt(cursor.getColumnIndex(youtubeImageDimen)));
                pro.setDescTitle(cursor.getString(cursor.getColumnIndex(descTitle)));
                pro.setProductUrl(cursor.getString(cursor.getColumnIndex(productUrl)));
                pro.setDiscountedPrice(cursor.getInt(cursor.getColumnIndex(discountedPrice)));
                pro.setCategory_name(cursor.getString(cursor.getColumnIndex(category_name)));
                pro.setPostType(BRANDED_DETAIL);

                //discount
                List<Discount> discounts = new ArrayList<>();
                Cursor discount  = db.query(TABLE_DISCOUNT, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(id)))}, null, null, null);
                if (discount != null && discount.getCount() > 0) {
                    discount.moveToFirst();
                    do {
                        Discount dis = new Discount();
                        dis.setDiscount_id(discount.getInt(discount.getColumnIndex(discountID)));
                        dis.setDiscountType(discount.getString(discount.getColumnIndex(discountType)));
                        dis.setPercentage(discount.getInt(discount.getColumnIndex(percentage)));
                        dis.setNum_extra(discount.getInt(discount.getColumnIndex(num_extra)));
                        dis.setGift_img_url(discount.getString(discount.getColumnIndex(gift_img_url)));
                        dis.setGift_info(discount.getString(discount.getColumnIndex(gift_info)));
                        dis.setMin_count(discount.getInt(discount.getColumnIndex(min_count)));
                        dis.setMax_count(discount.getInt(discount.getColumnIndex(max_count)));
                        dis.setDimen(discount.getInt(discount.getColumnIndex(dimen)));
                        dis.setCount_type(discount.getString(discount.getColumnIndex(count_type)));
                        dis.setCampaign_info(discount.getString(discount.getColumnIndex(campaign_info)));
                        dis.setBenefit_type(discount.getString(discount.getColumnIndex(benefit_type)));
                        dis.setMember_only(discount.getInt(discount.getColumnIndex(member_only)));
                        dis.setStart_at(discount.getString(discount.getColumnIndex(start_at)));
                        dis.setEnd_at(discount.getString(discount.getColumnIndex(end_at)));
                        discounts.add(dis);
                    }while (discount.moveToNext());
                }
                pro.setDiscounts(discounts);

                List<StockSummeries> summeriesList = new ArrayList<>();
                Cursor storeCursor  = db.query(TABLE_PRODUCT_STORE_LOCATION, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(productID)))}, null, null, null);
                if (storeCursor != null && storeCursor.getCount() > 0) {
                    storeCursor.moveToFirst();
                    do {
                        StockSummeries stockSummeries = new StockSummeries();
                        stockSummeries.setStore_location_id(storeCursor.getInt(storeCursor.getColumnIndex(store_id)));
                        stockSummeries.setQuantity(storeCursor.getInt(storeCursor.getColumnIndex(store_qty)));


                        summeriesList.add(stockSummeries);
                    }while (storeCursor.moveToNext());
                }
                pro.setStockSummeriesList(summeriesList);


                Cursor flashCursor = db.query(TABLE_FLASH_SALE, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(productID)))}, null, null, null );
                if(flashCursor != null && flashCursor.getCount() > 0){
                    List<Flash_Sales> list = new ArrayList<>();
                    flashCursor.moveToFirst();
                    do{
                        Flash_Sales flash_sales = new Flash_Sales();
                        flash_sales.setProductId(flashCursor.getInt(flashCursor.getColumnIndex(productID)));
                        flash_sales.setId(flashCursor.getInt(flashCursor.getColumnIndex(flash_id)));
                        flash_sales.setReserved_quantity(flashCursor.getInt(flashCursor.getColumnIndex(flash_reserved_quantity)));
                        flash_sales.setAvailable_quantity(flashCursor.getInt(flashCursor.getColumnIndex(flash_available_quantity)));
                        flash_sales.setDiscount(flashCursor.getInt(flashCursor.getColumnIndex(flash_discount)));
                        flash_sales.setStart_date(flashCursor.getString(flashCursor.getColumnIndex(flash_start_date)));
                        flash_sales.setEnd_date(flashCursor.getString(flashCursor.getColumnIndex(flash_end_date)));


                        list.add(flash_sales);
                    }while (flashCursor.moveToNext());
                    pro.setFlashSalesArrayList(list);
                }

                Cursor supplier = db.query(TABLE_SUPPLIER, null, id + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(supplierId)))}, null, null, null);
                if (supplier != null && supplier.getCount() > 0) {
                    supplier.moveToFirst();
                    do {
                        Supplier sup = new Supplier();
                        sup.setId(cursor.getInt(cursor.getColumnIndex(supplierId)));
                        sup.setName(supplier.getString(supplier.getColumnIndex(name)));
                        pro.setSupplier(sup);
                    }while (supplier.moveToNext());
                }

                List<Images> imageList = new ArrayList<>();
                Cursor images = db.query(TABLE_IMAGES, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(id)))}, null, null, null);
                if (images != null && images.getCount() > 0) {
                    images.moveToFirst();
                    do {
                        Images img = new Images();
                        img.setName(images.getString(images.getColumnIndex(name)));
                        img.setDimen(images.getInt(images.getColumnIndex(dimen)));
                        img.setUrl(images.getString(images.getColumnIndex(url)));
                        img.setProductId(images.getInt(images.getColumnIndex(productID)));
                        imageList.add(img);
                    }while (supplier.moveToNext());
                }

                pro.setImages(imageList);

                posts.add(pro);
            } while (cursor.moveToNext());
        }

        return posts;
    }

    public int deleteColumn(String table, String condition , String value)
    {
        if(table.equals(TABLE_PRODUCT)){
            int i = db.delete(table,condition+" =? AND "+status+" !=?",new String[]{String.valueOf(value), wishList});
            int j = db.delete(TABLE_IMAGES, productID+"=?",new String[]{String.valueOf(value)});
            int p = db.delete(TABLE_DISCOUNT, productID+"=?",new String[]{String.valueOf(value)});
            int k = db.delete(TABLE_PRODUCT_STORE_LOCATION, productID+"=?",new String[]{String.valueOf(value)});
            int l = db.delete(TABLE_FLASH_SALE, productID+"=?",new String[]{String.valueOf(value)});
            return  i+j +l;
        }else{
            return  db.delete(table,condition+" =?",new String[]{String.valueOf(value)});
        }
    }

    public void deleteAllColumn(String table)
    {
        db.delete(table, null, null);
    }

    public void insertBrand(ArrayList<UniversalPost> brands){
        for(int i = 0 ; i < brands.size(); i++) {
            try {
                Brand brand = new Brand();
                brand = (Brand) brands.get(i);
                ContentValues cat = new ContentValues();
                cat.put(id,          brand.getId());
                cat.put(title,       brand.getTitle());
                cat.put(desc,         brand.getDesc());
                cat.put(image,       brand.getImageUrl());
                cat.put(recommended, brand.getRecommandation());
                cat.put(tag,   brand.getTag());
                deleteColumn(TABLE_BRAND, id, String.valueOf(brand.getId()));
                long bb = db.insert(TABLE_BRAND, null, cat);

            }catch (Exception e){
            }
        }

    }



    public void insertPickUpShop(List<ShopLocation> shopLocationArrayList){



        for(int i = 0 ; i < shopLocationArrayList.size(); i++) {
            try {


                ShopLocation  shopLocation = shopLocationArrayList.get(i);
                //deleteColumn(TABLE_PICKUP_SHOP, pickup_id, String.valueOf(shopLocation.getId()));
                ContentValues cat = new ContentValues();
                cat.put(pickup_id,          shopLocation.getId());
                cat.put(pickup_name,     shopLocation.getName());
                cat.put(pickup_address,         shopLocation.getAddress());
                cat.put(pickup_phone,      shopLocation.getPhone());

                long bb = db.insert(TABLE_PICKUP_SHOP, null, cat);



            }catch (Exception e){
            }
        }

    }

    public ArrayList<ShopLocation> getPickupShops(){
        ArrayList<ShopLocation> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_PICKUP_SHOP, null, null, null, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                ShopLocation order = new ShopLocation();
                order.setId(cursor.getInt(cursor.getColumnIndex(pickup_id)));
                order.setName(cursor.getString(cursor.getColumnIndex(pickup_name)));
                order.setAddress(cursor.getString(cursor.getColumnIndex(pickup_address)));
                order.setPhone(cursor.getString(cursor.getColumnIndex(pickup_phone)));
                //order.setPostType(PICK_UP_SHOP_ITEM);
                result.add(order);
            }while (cursor.moveToNext());

        }
        return result;
    }

    public ArrayList<ShopLocation> getOneShop(int shopId){
        ArrayList<ShopLocation> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_PICKUP_SHOP, null,  pickup_id + "=?", new String[]{String.valueOf(shopId)},null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                ShopLocation order = new ShopLocation();
                order.setId(cursor.getInt(cursor.getColumnIndex(pickup_id)));
                order.setName(cursor.getString(cursor.getColumnIndex(pickup_name)));
                order.setAddress(cursor.getString(cursor.getColumnIndex(pickup_address)));
                order.setPhone(cursor.getString(cursor.getColumnIndex(pickup_phone)));
                //order.setPostType(PICK_UP_SHOP_ITEM);
                result.add(order);
            }while (cursor.moveToNext());

        }
        return result;
    }

    public ArrayList<UniversalPost> getBrand(String tagString){
        ArrayList<UniversalPost> brands = new ArrayList<>();
        try {
            Cursor cat = db.query(TABLE_BRAND, null, tag + "=?", new String[]{String.valueOf(tagString)}, null, null, null);

            if (cat != null && cat.getCount() > 0) {
                cat.moveToFirst();
                do {
                    Brand brand = new Brand();
                    brand.setId(cat.getInt(cat.getColumnIndex(id)));
                    brand.setTitle(cat.getString(cat.getColumnIndex(title)));
                    brand.setDesc(cat.getString(cat.getColumnIndex(desc)));
                    brand.setImageUrl(cat.getString(cat.getColumnIndex(image)));
                    brand.setTag(cat.getString(cat.getColumnIndex(tag)));
                    brand.setRecommandation(cat.getString(cat.getColumnIndex(recommended)));
                    brand.setPostType(BRAND);
                    brands.add(brand);
                } while (cat.moveToNext());
            } else {
            }
        }catch (Exception e){
        }
        return brands;
    }


    public void insertCategoryMain(ArrayList<CategoryMain> categories){
        deleteAllColumn(TABLE_CATEGORY_MAIN);
        for(int i = 0 ; i < categories.size(); i++) {
            try {
                CategoryMain ca = new CategoryMain();
                ca = categories.get(i);
                ContentValues cat = new ContentValues();
                cat.put(id,          ca.getId());
                cat.put(title,       ca.getTitle());
                cat.put(tag,         ca.getTag());
                cat.put(image,       ca.getImage());
                cat.put(big_image,   ca.getImage_big());
                long category = db.insert(TABLE_CATEGORY_MAIN, null, cat);
            }catch (Exception e){
            }
        }

    }

    public ArrayList<UniversalPost> getCategoryMain(){
        ArrayList<UniversalPost> categories = new ArrayList<>();
        Cursor cat = db.query(TABLE_CATEGORY_MAIN, null, null, null, null, null, null);
        if(cat != null && cat.getCount() > 0 ){
            cat.moveToFirst();
            do {
                CategoryMain category = new CategoryMain();
                category.setId(cat.getInt(cat.getColumnIndex(id)));
                category.setTitle(cat.getString(cat.getColumnIndex(title)));
                category.setTag(cat.getString(cat.getColumnIndex(tag)));
                category.setImage(cat.getString(cat.getColumnIndex(image)));
                category.setImage_big(cat.getString(cat.getColumnIndex(big_image)));
                category.setPostType(MAIN_GRIDVIEW_ITEM);
                categories.add(category);
            }while (cat.moveToNext());
        }
        return categories;
    }

    public void insertTownship(List<TownShip> townShipList){
        deleteAllColumn(TABLE_TOWNSHIP);
        for ( int i=0; i<townShipList.size(); i++){
            TownShip townShip = townShipList.get(i);
            try{
                ContentValues contentValues = new ContentValues();
                contentValues.put(id, townShip.getId());
                contentValues.put(store_id, townShip.getStore_location_id());
                contentValues.put(delivery_type, townShip.getDelivery_type());
                contentValues.put(name, townShip.getName());
                long cityReturn = db.insert(TABLE_TOWNSHIP, null, contentValues);
            }catch (Exception e){

            }
        }
    }

    /*
    id": 6042,
	"township_id": 18,
	"township_name": "ကြည့်မြင်တိုင်",
	"delivery_id": 4,
	"address": "Kyimyindine"
     */
    public void insertCustomerAddress(List<Addresses> customerAdList){
        deleteAllColumn(TABLE_CUSTOMER_ADDRESS);
        for ( int i=0; i<customerAdList.size(); i++){
            Addresses townShip = customerAdList.get(i);
            try{
                ContentValues contentValues = new ContentValues();
                contentValues.put(id, townShip.getId());
                contentValues.put(customer_township_id, townShip.getTownShipID());
                contentValues.put(customer_delivery_id, townShip.getDelivery_id());
                contentValues.put(customer_address, townShip.getAddresses());
                contentValues.put(customer_township, townShip.getTownship_name());

                long cityReturn = db.insert(TABLE_CUSTOMER_ADDRESS, null, contentValues);


            }catch (Exception e){

            }
        }
    }

    public void insertCustomerNewAdd(Addresses townShip){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(id, townShip.getId());
            contentValues.put(customer_township_id, townShip.getTownShipID());
            contentValues.put(customer_delivery_id, townShip.getDelivery_id());
            contentValues.put(customer_address, townShip.getAddresses());
            contentValues.put(customer_township, townShip.getTownship_name());

            long cityReturn = db.insert(TABLE_CUSTOMER_ADDRESS, null, contentValues);

            Log.e(TAG, "insertCustomerAddress: ********************* "  + cityReturn );

        }catch (Exception e){

        }
    }

    public ArrayList<Addresses> getCustomerAddressList (){
        ArrayList<Addresses> townShipList = new ArrayList<>();
        Cursor cursor  = db.query(TABLE_CUSTOMER_ADDRESS, null, null, null, null, null, null);

        Log.e(TAG, "getCustomerAddressList: ********************* cursor.getCount()  "  + cursor.getCount()  );

        if(cursor!= null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Addresses town = new Addresses();
                town.setId(cursor.getInt(cursor.getColumnIndex(id)));
                town.setTownShipID(cursor.getInt(cursor.getColumnIndex(customer_township_id)));
                town.setDelivery_id(cursor.getInt(cursor.getColumnIndex(customer_delivery_id)));
                town.setAddresses(cursor.getString(cursor.getColumnIndex(customer_address)));
                town.setTownship_name(cursor.getString(cursor.getColumnIndex(customer_township)));

                townShipList.add(town);
            }while (cursor.moveToNext());
        }

        return townShipList;
    }

    public ArrayList<TownShip> getTownShipByList (){
        ArrayList<TownShip> townShipList = new ArrayList<>();
        Cursor cursor  = db.query(TABLE_TOWNSHIP, null, null, null, null, null, null);

        if(cursor!= null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                TownShip town = new TownShip();
                town.setId(cursor.getInt(cursor.getColumnIndex(id)));
                town.setStore_location_id(cursor.getInt(cursor.getColumnIndex(store_id)));
                town.setName(cursor.getString(cursor.getColumnIndex(name)));
                town.setDelivery_type(cursor.getInt(cursor.getColumnIndex(delivery_type)));
                townShipList.add(town);
            }while (cursor.moveToNext());
        }

        return townShipList;
    }



/*
    public  void insertCity(List<City> cities) {

        deleteAllColumn(TABLE_CITY);
        deleteAllColumn(TABLE_TOWNSHIP);

        for (int i = 0; i < cities.size(); i++) {
            City city = new City();
            city = cities.get(i);

            try{
                ContentValues pro = new ContentValues();
                pro.put(id,             city.getId());
                pro.put(name,           city.getName());

                for(int j = 0 ; j < city.getTownships().size(); j++){
                    Township township = new Township();

                    township  = city.getTownships().get(j);

                    ContentValues to = new ContentValues();
                    to.put(cityID,        city.getId());
                    to.put(id,      township.getId());
                    to.put(name,       township.getName());
                    long imageReturn = db.insert(TABLE_TOWNSHIP, null, to);
                }
                long cityReturn = db.insert(TABLE_CITY, null, pro);
            }catch (Exception e){
            }

        }
    }

    public ArrayList<UniversalPost> getCity (){
        ArrayList<UniversalPost> cities = new ArrayList<>();
        Cursor cursor  = db.query(TABLE_CITY, null, null, null, null, null, null);

        if(cursor!= null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex(id)));
                city.setName(cursor.getString(cursor.getColumnIndex(name)));
                List<Township> townships  = new ArrayList<>();

                Cursor township = db.query(TABLE_TOWNSHIP, null, cityID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(id)))}, null, null, null);
                if (township != null && township.getCount() > 0) {
                    township.moveToFirst();
                    do {
                        Township town = new Township();
                        town.setCityId(township.getInt(township.getColumnIndex(cityID)));
                        town.setId(township.getInt(township.getColumnIndex(id)));
                        town.setName(township.getString(township.getColumnIndex(name)));
                        townships.add(town);
                    }while (township.moveToNext());
                }

                city.setTownships(townships);

                cities.add(city);
            } while (cursor.moveToNext());
        }

        return cities;
    }*/

    public void insertSearch(String query, String photo){

        try {
            ContentValues pro = new ContentValues();
            pro.put(id, 1);
            pro.put(title, query);
            pro.put(preview_img_url, photo);

            long productReturn = db.insert(TABLE_SEARCHED, null, pro);
        }catch (Exception e){}
    }

    public List<MainItem> getMianItemSearch(){
        List<MainItem> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_SEARCHED, null, null, null, title, null, pId+" DESC");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int i = 0;
            do {
                if(i < 6) {

                    MainItem item = new MainItem();
                    item.setId(cursor.getInt(cursor.getColumnIndex(id)));
                    item.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                    item.setPreview_url(cursor.getString(cursor.getColumnIndex(preview_img_url)));
                    item.setPostType(SEARCHED_ITEM_NEW);
                    list.add(item);
                    i ++;
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void insertPostSubscribe(int post_Id){

        deleteColumn(TABLE_POST_SUBSCRIBE, postId,String.valueOf(post_Id));
        ContentValues noti = new ContentValues();
        noti.put(postId, post_Id);
        db.insert(TABLE_POST_SUBSCRIBE, null, noti);
    }

    public void deleteSubscribe(int id){

        deleteColumn(TABLE_POST_SUBSCRIBE, postId,String.valueOf(id));
    }

    public void deleteCustomerAddress(int indexId){

        deleteColumn(TABLE_CUSTOMER_ADDRESS, id,String.valueOf(indexId));
    }

    public boolean getSubscribe(int post_id){
        boolean checkNotification = false;
        try {
            Cursor noti = db.query(TABLE_POST_SUBSCRIBE, null, postId + "=?", new String[]{String.valueOf(post_id)}, null, null, null);

            if (noti != null && noti.getCount() > 0) {
                checkNotification = true;
            }

        }catch (Exception e){
        }
        return checkNotification;
    }


    public void insertPostNotification(int post_Id){

        deleteColumn(TABLE_POST_NOTIFICATION, postId,String.valueOf(post_Id));
        ContentValues noti = new ContentValues();
        noti.put(postId, post_Id);
        db.insert(TABLE_POST_NOTIFICATION, null, noti);
    }

    public void insertProductNotification(int post_Id){

        deleteColumn(TABLE_PRODUCT_NOTI, postId,String.valueOf(post_Id));
        ContentValues noti = new ContentValues();
        noti.put(postId, post_Id);
        db.insert(TABLE_PRODUCT_NOTI, null, noti);
    }


    public void deleteNotification(int id){

        deleteColumn(TABLE_POST_NOTIFICATION, postId,String.valueOf(id));
    }

    public void deleteProductNotification(int id){

        deleteColumn(TABLE_PRODUCT_NOTI, postId,String.valueOf(id));
    }


    public boolean getNotification(int post_id){
        boolean checkNotification = false;
        try {
            Cursor noti = db.query(TABLE_POST_NOTIFICATION, null, postId + "=?", new String[]{String.valueOf(post_id)}, null, null, null);

            if (noti != null && noti.getCount() > 0) {
                checkNotification = true;
            }

        }catch (Exception e){
        }
        return checkNotification;
    }

    public boolean getProductNotification(int post_id){
        boolean checkNotification = false;
        try {
            Cursor noti = db.query(TABLE_PRODUCT_NOTI, null, postId + "=?", new String[]{String.valueOf(post_id)}, null, null, null);

            if (noti != null && noti.getCount() > 0) {
                checkNotification = true;
            }

        }catch (Exception e){
        }
        return checkNotification;
    }

    public int getAllProductNotificationSize(){
        int count = 0;
        try {
            Cursor noti = db.query(TABLE_PRODUCT_NOTI, null, null, null,
                    null, null, null);

            if (noti != null && noti.getCount() > 0) {
                count = noti.getCount();
            }

        }catch (Exception e){
        }
        return count;
    }


    public int getAllNotificationSize(){
        int count = 0;
        try {
            Cursor noti = db.query(TABLE_POST_NOTIFICATION, null, null, null,
                    null, null, null);

            if (noti != null && noti.getCount() > 0) {
                count = noti.getCount();
            }

        }catch (Exception e){
        }
        return count;
    }

    public List<MainItem> getAllNotification(){
        List<MainItem> list = new ArrayList<>();
        try {
            Cursor cursor = db.query(TABLE_POST_NOTIFICATION, null, null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    MainItem item = new MainItem();
                    item.setId(cursor.getInt(cursor.getColumnIndex(postId)));
                    list.add(item);

                }while (cursor.moveToNext());

            }

        }catch (Exception e){
        }
        return list;
    }

    public List<MainItem> getAllProductNotification(){
        List<MainItem> list = new ArrayList<>();
        try {
            Cursor cursor = db.query(TABLE_PRODUCT_NOTI, null, null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    MainItem item = new MainItem();
                    item.setId(cursor.getInt(cursor.getColumnIndex(postId)));
                    list.add(item);

                }while (cursor.moveToNext());

            }

        }catch (Exception e){
        }
        return list;
    }

    public void insertVideoLike(int post_Id, String type){
        if(type == "like") {
            ContentValues like = new ContentValues();
            like.put(postId, post_Id);
            like.put(postType, type);
            db.insert(TABLE_VIDEO_LIKE, null, like);
        }else{
            deleteColumn(TABLE_VIDEO_LIKE, postId,String.valueOf(post_Id));
        }

    }

    public boolean checkVideoLiked(int productId){
        Cursor cursor  = db.query(TABLE_VIDEO_LIKE, null, postId+ "=?" ,new String[]{String.valueOf(productId)}, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            return true;
        }
        return false;
    }

    public ArrayList<NameObject> getNameList(){
        Cursor cursor   = null;
        ArrayList<NameObject> order = new ArrayList<>();
        try{
            cursor = db.query(TABLE_SAVE_NAMES, null, null, null, null, null, null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do{
                    NameObject nameObject = new NameObject();
                    nameObject.setId(cursor.getInt(cursor.getColumnIndex(SM_ID)));
                    nameObject.setName(cursor.getString(cursor.getColumnIndex(SM_NAME)));

                    order.add(nameObject);
                }while (cursor.moveToNext());
            }

        }finally {
            if(cursor != null)
                cursor.close();
        }

        return order;
    }

    public ArrayList<MainCategoryObj> getCategorySuperList(){
        Cursor cursor   = null;
        ArrayList<MainCategoryObj> order = new ArrayList<>();
        try{
            cursor = db.query(TABLE_SUPER_CATEGORY, null, null, null, null, null, null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do{
                    MainCategoryObj nameObject = new MainCategoryObj();
                    nameObject.setId(cursor.getInt(cursor.getColumnIndex(CAT_ID)));
                    nameObject.setTitle(cursor.getString(cursor.getColumnIndex(CAT_NAME)));

                    order.add(nameObject);
                }while (cursor.moveToNext());
            }

        }finally {
            if(cursor != null)
                cursor.close();
        }

        return order;
    }



    public void insertNamesLike(int post_Id, String type){
        if(type == "like") {
            ContentValues like = new ContentValues();
            like.put(SM_ID, post_Id);
            like.put(SM_NAME, type);
            db.insert(TABLE_SAVE_NAMES, null, like);
        }else{
            deleteColumn(TABLE_SAVE_NAMES, SM_ID,String.valueOf(post_Id));
        }

    }

    public void insertCategorySuperList(MainCategoryObj obj){

        deleteColumn(TABLE_SUPER_CATEGORY, CAT_ID, String.valueOf( obj.getId()));
        ContentValues like = new ContentValues();
        like.put(CAT_ID, obj.getId());
        like.put(CAT_NAME, obj.getTitle());
        db.insert(TABLE_SUPER_CATEGORY, null, like);

    }

    public boolean checkNamesLiked(int productId){
        Cursor cursor  = db.query(TABLE_SAVE_NAMES, null, SM_ID+ "=?" ,new String[]{String.valueOf(productId)}, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            return true;
        }
        return false;
    }



    public void insertPostLike(int post_Id, String type){
        if(type == "like") {
            ContentValues like = new ContentValues();
            like.put(postId, post_Id);
            like.put(postType, type);
            db.insert(TABLE_POST_LIKE, null, like);
        }else{
            deleteColumn(TABLE_POST_LIKE, postId,String.valueOf(post_Id));
        }

    }

    public boolean checkPostLiked(int productId){
        Cursor cursor  = db.query(TABLE_POST_LIKE, null, postId+ "=?" ,new String[]{String.valueOf(productId)}, null, null, null);
        if(cursor != null && cursor.getCount() > 0){

            return true;
        }
        return false;
    }

    public void insertProductLike(int post_Id, String type){
        if(type == "like") {
            ContentValues like = new ContentValues();
            like.put(productID, post_Id);
            like.put(postType, type);
            long result = db.insert(TABLE_PRODUCT_LIKE, null, like);
        }else{
            deleteColumn(TABLE_PRODUCT_LIKE, productID,String.valueOf(post_Id));
        }

    }

    public boolean checkProductLiked(int productId){
        Cursor cursor  = db.query(TABLE_PRODUCT_LIKE, null, productID+ "=?" ,new String[]{String.valueOf(productId)}, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            return true;
        }
        return false;
    }


    /*public void insertProductLike(int productId, String type){
        ContentValues like = new ContentValues();
        like.put(id, productId);
        like.put(postType, type);
        long supplierReturn = db.insert(TABLE_PRODUCT_LIKE, null, like);

    }

    public boolean checkLiked(int productId, String type){
        boolean boo = false;
        Cursor cursor  = db.query(TABLE_PRODUCT_LIKE, null, id+" =? AND "+ postType +"=? ",new String[]{String.valueOf(id), type}, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            boo = true;
        }
        return boo;
    }*/

    public boolean idContainWishlist(int list_id){
        boolean boo = false;

        Cursor cursor  = db.query(TABLE_PRODUCT, null, id+"=? and "+status+"=?",new String[]{String.valueOf(list_id),wishList}, null, null, null);
        if(cursor!= null && cursor.getCount() > 0){
            boo = true;
        }
        return boo;
    }



    public int updatetWishList(int pid, int update){
        try {
            ContentValues order = new ContentValues();
            if(update == 1)
                order.put(status, wishList);
            else
                order.put(status, "available");
            return db.update(TABLE_PRODUCT, order, id + "=" + pid, null);

        }catch (Exception e){
        }
        return 0;
    }



    /*public  void insertProduct12(List<Product> products, String type) {

        for (int i = 0; i < products.size(); i++) {
            Product product = new Product();
            product = products.get(i);

            deleteColumn(TABLE_PRODUCT, id, String.valueOf(product.getId()));
            try {
                ContentValues pro = new ContentValues();
                pro.put(id,             product.getId());
                pro.put(title,          product.getTitle());
                pro.put(desc,           product.getDesc());
                pro.put(descTitle,      product.getDescTitle());
                pro.put(price,          product.getPrice());
                pro.put(discount,       product.getDiscount());
                pro.put(youtubeId,      product.getYoutubeId());
                pro.put(status,         product.getStatus());
                pro.put(code,           product.getCode());
                pro.put(categoryId,     product.getCategoryId());
                pro.put(supplierId,     product.getSupplier().getId());
                pro.put(preview_img_url, product.getPreviewImage());
                pro.put(brand_id,       product.getBrand_id());
                pro.put(brandName,      product.getBrand_name());
                pro.put(recommended,    product.getRecommended());
                pro.put(member_discount, product.getMember_discount());
                pro.put(postType,       type);
                pro.put(phoneNo,        product.getPhoneNo());
                pro.put(productType,    type);
                pro.put(productUrl,     product.getProductUrl());
                pro.put(youtubeImageUrl, product.getYoutubeImageUrl());
                pro.put(youtubeImageDimen, product.getYoutubeImageDimen());
                pro.put(discountedPrice, product.getDiscountedPrice());
                pro.put(category_name,  product.getCategory_name());

                for (int j = 0; j < product.getImages().size(); j++) {
                    Images image = new Images();
                    image = product.getImages().get(j);
                    ContentValues img = new ContentValues();
                    img.put(url, image.getUrl());
                    img.put(dimen, image.getDimen());
                    img.put(productID, product.getId());
                    img.put(name, image.getName());
                    long imageReturn = db.insert(TABLE_IMAGES, null, img);
                }
                deleteColumn(TABLE_SUPPLIER, id, String.valueOf(product.getSupplier().getId()));
                ContentValues supplier = new ContentValues();
                supplier.put(id, product.getSupplier().getId());
                supplier.put(name, product.getSupplier().getName());
                long supplierReturn = db.insert(TABLE_SUPPLIER, null, supplier);

                deleteColumn(TABLE_DISCOUNT, productID, String.valueOf(product.getId()));
                for (int k = 0; k < product.getDiscounts().size(); k++) {
                    Discount dis = new Discount();
                    dis = product.getDiscounts().get(k);
                    ContentValues d = new ContentValues();
                    d.put(productID,    product.getId());
                    d.put(discountID,   dis.getDiscount_id());
                    d.put(discountType, dis.getDiscountType());
                    d.put(percentage,   dis.getPercentage());
                    d.put(num_extra,    dis.getNum_extra());
                    d.put(gift_img_url, dis.getGift_img_url());
                    d.put(gift_info,    dis.getGift_info());
                    d.put(min_count,    dis.getMin_count());
                    d.put(max_count,    dis.getMax_count());
                    d.put(dimen,        dis.getDimen());
                    d.put(count_type,   dis.getCount_type());
                    d.put(campaign_info, dis.getCampaign_info());
                    d.put(benefit_type, dis.getBenefit_type());
                    d.put(member_only,  dis.getMember_only());
                    d.put(start_at,     dis.getStart_at());
                    d.put(end_at,       dis.getEnd_at());
                    long disreturn = db.insert(TABLE_DISCOUNT, null, d);
                }

                long productReturn = db.insert(TABLE_PRODUCT, null, pro);
            } catch (Exception e) { }

        }

    }

    public ArrayList<UniversalPost> getProductDetail(int type){
        ArrayList<UniversalPost> posts = new ArrayList<>();

        Cursor cursor  = db.query(TABLE_PRODUCT, null, categoryId+"=?",new String[]{String.valueOf(type)}, null, null, id +" DESC");

        if(cursor!= null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Product pro = new Product();
                pro.setId(cursor.getInt(cursor.getColumnIndex(id)));
                pro.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                pro.setDesc(cursor.getString(cursor.getColumnIndex(desc)));
                pro.setPrice(cursor.getInt(cursor.getColumnIndex(price)));
                pro.setDiscount(cursor.getInt(cursor.getColumnIndex(discount)));
                pro.setYoutubeId(cursor.getString(cursor.getColumnIndex(youtubeId)));
                pro.setStatus(cursor.getString(cursor.getColumnIndex(status)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(code)));
                pro.setCategoryId(cursor.getInt(cursor.getColumnIndex(categoryId)));
                pro.setPreviewImage(cursor.getString(cursor.getColumnIndex(preview_img_url)));
                pro.setPostType(CATEGORY_DETAIL);
                pro.setRecommended(cursor.getString(cursor.getColumnIndex(recommended)));
                pro.setMember_discount(cursor.getInt(cursor.getColumnIndex(member_discount)));
                pro.setBrand_id(cursor.getInt(cursor.getColumnIndex(brand_id)));
                pro.setBrand_name(cursor.getString(cursor.getColumnIndex(brandName)));
                pro.setPhoneNo(cursor.getString(cursor.getColumnIndex(phoneNo)));
                pro.setProductType(cursor.getString(cursor.getColumnIndex(productType)));
                pro.setYoutubeImageUrl(cursor.getString(cursor.getColumnIndex(youtubeImageUrl)));
                pro.setYoutubeImageDimen(cursor.getInt(cursor.getColumnIndex(youtubeImageDimen)));
                pro.setDescTitle(cursor.getString(cursor.getColumnIndex(descTitle)));
                pro.setProductUrl(cursor.getString(cursor.getColumnIndex(productUrl)));
                pro.setDiscountedPrice(cursor.getInt(cursor.getColumnIndex(discountedPrice)));
                pro.setCategory_name(cursor.getString(cursor.getColumnIndex(category_name)));
                //discount
                List<Discount> discounts = new ArrayList<>();
                Cursor discount  = db.query(TABLE_DISCOUNT, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(id)))}, null, null, null);
                if (discount != null && discount.getCount() > 0) {
                    discount.moveToFirst();
                    do {
                        Discount dis = new Discount();
                        dis.setDiscount_id(discount.getInt(discount.getColumnIndex(discountID)));
                        dis.setDiscountType(discount.getString(discount.getColumnIndex(discountType)));
                        dis.setPercentage(discount.getInt(discount.getColumnIndex(percentage)));
                        dis.setNum_extra(discount.getInt(discount.getColumnIndex(num_extra)));
                        dis.setGift_img_url(discount.getString(discount.getColumnIndex(gift_img_url)));
                        dis.setGift_info(discount.getString(discount.getColumnIndex(gift_info)));
                        dis.setMin_count(discount.getInt(discount.getColumnIndex(min_count)));
                        dis.setMax_count(discount.getInt(discount.getColumnIndex(max_count)));
                        dis.setDimen(discount.getInt(discount.getColumnIndex(dimen)));
                        dis.setCount_type(discount.getString(discount.getColumnIndex(count_type)));
                        dis.setCampaign_info(discount.getString(discount.getColumnIndex(campaign_info)));
                        dis.setBenefit_type(discount.getString(discount.getColumnIndex(benefit_type)));
                        dis.setMember_only(discount.getInt(discount.getColumnIndex(member_only)));
                        dis.setStart_at(discount.getString(discount.getColumnIndex(start_at)));
                        dis.setEnd_at(discount.getString(discount.getColumnIndex(end_at)));
                        discounts.add(dis);
                    }while (discount.moveToNext());
                }
                pro.setDiscounts(discounts);

                //need to add suppliser
                Cursor supplier = db.query(TABLE_SUPPLIER, null, id + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(supplierId)))}, null, null, null);
                if (supplier != null && supplier.getCount() > 0) {
                    supplier.moveToFirst();
                    do {
                        Supplier sup = new Supplier();
                        sup.setId(cursor.getInt(cursor.getColumnIndex(supplierId)));
                        sup.setName(supplier.getString(supplier.getColumnIndex(name)));
                        pro.setSupplier(sup);
                    }while (supplier.moveToNext());
                }

                List<Images> imageList = new ArrayList<>();
                Cursor images = db.query(TABLE_IMAGES, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(id)))}, null, null, null);
                if (images != null && images.getCount() > 0) {
                    images.moveToFirst();
                    do {
                        Images img = new Images();
                        img.setName(images.getString(images.getColumnIndex(name)));
                        img.setDimen(images.getInt(images.getColumnIndex(dimen)));
                        img.setUrl(images.getString(images.getColumnIndex(url)));
                        img.setProductId(images.getInt(images.getColumnIndex(productID)));
                        imageList.add(img);
                    }while (supplier.moveToNext());
                }

                pro.setImages(imageList);

                posts.add(pro);
            } while (cursor.moveToNext());
        }

        return posts;
    }

    public void insertCategory(ArrayList<Category> categories, int database_control){
        deleteColumn(TABLE_CATEGORY, status, String.valueOf(database_control));
        for(int i = 0 ; i < categories.size(); i++) {
            try {
                Category ca = new Category();
                ca = categories.get(i);
                ContentValues cat = new ContentValues();
                cat.put(id,     ca.getId());
                cat.put(tag,    ca.getTag());
                cat.put(name,   ca.getName());
                cat.put(status, database_control);
                long category = db.insert(TABLE_CATEGORY, null, cat);
            }catch (Exception e){}
        }

    }

    public ArrayList<Category> getCategory(int cat_status){
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cat = db.query(TABLE_CATEGORY, null, status + "=?", new String[]{String.valueOf(cat_status)}, null, null, null);
        if(cat != null && cat.getCount() > 0 ){
            cat.moveToFirst();
            do {
                Category category = new Category();
                category.setId(cat.getInt(cat.getColumnIndex(id)));
                category.setName(cat.getString(cat.getColumnIndex(name)));
                category.setStatus(cat.getInt(cat.getColumnIndex(status)));
                category.setTag(cat.getString(cat.getColumnIndex(tag)));
                categories.add(category);
            }while (cat.moveToNext());
        }
        return categories;
    }
*/
    public int deleteOrder(int pid){
        int toReturn = 0;
        toReturn = deleteColumn(TABLE_SAVE_CART, productID, String.valueOf(pid));
        return toReturn;
    }
    public int insertOrder(Product product, int order_count, int item_price, String option_text){
        //public int insertOrder(Product product, int order_count, int item_price){

        Cursor cursor = db.query(TABLE_SAVE_CART, null, productID + "=?", new String[]{String.valueOf(product.getId())}, null, null, null);
        int cursorCount = cursor.getCount();
        if (cursorCount == 0) {
            ContentValues order = new ContentValues();
            order.put(productID, product.getId());
            order.put(price, product.getPrice());
            order.put(title, product.getTitle());
            order.put(member_discount, product.getMember_discount());
            order.put(point_usage, product.getPoint_usage());
            order.put(preview_img_url, product.getPreviewImage());
            order.put(final_item_price, item_price);

            order.put(option, option_text);
            order.put(count, order_count);

            for (int j = 0; j < product.getImages().size(); j++) {
                Images image = new Images();
                image = product.getImages().get(j);
                ContentValues img = new ContentValues();
                img.put(url, image.getUrl());
                img.put(dimen, image.getDimen());
                img.put(productID, product.getId());
                img.put(name, image.getName());
                long imageReturn = db.insert(TABLE_IMAGES, null, img);
            }
            deleteColumn(TABLE_SUPPLIER, id, String.valueOf(product.getSupplier().getId()));
            ContentValues supplier = new ContentValues();
            supplier.put(id, product.getSupplier().getId());
            supplier.put(name, product.getSupplier().getName());
            long supplierReturn = db.insert(TABLE_SUPPLIER, null, supplier);

            deleteColumn(TABLE_DISCOUNT, productID, String.valueOf(product.getId()));
            for (int k = 0; k < product.getDiscounts().size(); k++) {
                Discount dis = new Discount();
                dis = product.getDiscounts().get(k);
                ContentValues d = new ContentValues();
                d.put(productID,    product.getId());
                d.put(discountID,   dis.getDiscount_id());
                d.put(discountType, dis.getDiscountType());
                d.put(percentage,   dis.getPercentage());
                d.put(num_extra,    dis.getNum_extra());
                d.put(gift_img_url, dis.getGift_img_url());
                d.put(gift_info,    dis.getGift_info());
                d.put(min_count,    dis.getMin_count());
                d.put(max_count,    dis.getMax_count());
                d.put(dimen,        dis.getDimen());
                d.put(count_type,   dis.getCount_type());
                d.put(campaign_info, dis.getCampaign_info());
                d.put(benefit_type, dis.getBenefit_type());
                d.put(member_only,  dis.getMember_only());
                d.put(start_at,     dis.getStart_at());
                d.put(end_at,       dis.getEnd_at());
                long disreturn = db.insert(TABLE_DISCOUNT, null, d);
            }


            deleteColumn(TABLE_PRODUCT_STORE_LOCATION, productID, String.valueOf(product.getId()));
            for (int k = 0; k < product.getStockSummeriesList().size(); k++) {
                StockSummeries stockSummeries = product.getStockSummeriesList().get(k);
                ContentValues contentValues = new ContentValues();
                contentValues.put(productID,    product.getId());
                contentValues.put(store_id,    stockSummeries.getStore_location_id());
                contentValues.put(store_qty,    stockSummeries.getQuantity());

                long disreturn = db.insert(TABLE_PRODUCT_STORE_LOCATION, null, contentValues);

                Log.e(TAG, "insertOrder: ******************  81026 " + disreturn );
            }

            deleteColumn(TABLE_FLASH_SALE, productID, String.valueOf(product.getId()));
            for (int k = 0; k < product.getFlashSalesArrayList().size(); k++) {
                Flash_Sales flash_sales = product.getFlashSalesArrayList().get(k);

                ContentValues d = new ContentValues();
                d.put(productID,    product.getId());
                d.put(flash_id,     flash_sales.getId());
                d.put(flash_start_date, flash_sales.getStart_date());
                d.put(flash_end_date, flash_sales.getEnd_date());
                d.put(flash_available_quantity, flash_sales.getAvailable_quantity());
                d.put(flash_reserved_quantity, flash_sales.getReserved_quantity());
                d.put(flash_discount, flash_sales.getDiscount());

                long disreturn = db.insert(TABLE_FLASH_SALE, null, d);
            }

            long saveCart = db.insert(TABLE_SAVE_CART, null, order);
            Cursor cursor1 = db.query(TABLE_SAVE_CART, null, productID + "=?",
                    new String[]{String.valueOf(product.getId())}, null, null, null);

            int cursorCount1 = cursor1.getCount();
            return cursorCount1;
        }else{
            int old_count = 0;
            String old_option = "";
            cursor.moveToFirst();
            do {
                old_count = cursor.getInt(cursor.getColumnIndex(count));
                old_option  = cursor.getString(cursor.getColumnIndex(option));
            } while (cursor.moveToNext());
            if(old_count+order_count <= SPINNER_COUNT)
                updatetOrder(product.getId(),product.getPrice(), item_price, old_count+order_count, old_option +"  "+option_text);
        }
        return cursorCount;
    }

    public void updatetOrder(int pid, int productPrice, int item_price,  int countNew, String option_text){
        try {
            ContentValues order = new ContentValues();
            order.put(productID, pid);
            order.put(count, countNew);
            order.put(final_item_price, item_price);
            order.put(price, productPrice);
            order.put(option, option_text);
            int i = db.update(TABLE_SAVE_CART, order, productID + "=" + pid, null);
        }catch (Exception e){}

    }

    public void updatetOrderItem(int id, int countNew){
        try {
            ContentValues order = new ContentValues();
            order.put(productID, id);
            order.put(count, countNew);
            int i = db.update(TABLE_SAVE_CART, order, productID + "=" + id, null);
        }catch (Exception e){}

    }

    public int getOrderCount(){
        try {
            Cursor cur = db.rawQuery("SELECT SUM("+count+") FROM "+TABLE_SAVE_CART, null);
            if(cur != null && cur.getCount() > 0){
                cur.moveToFirst();
                return cur.getInt(0);
            }
        }catch (Exception e){}
        return  0;
    }

    public int getOrderItemCount(int id){
        try {
            Cursor cur = db.query(TABLE_SAVE_CART, null, productID + "=?", new String[]{String.valueOf(id)}, null, null,null);
            if(cur != null && cur.getCount() > 0){
                cur.moveToFirst();
                return cur.getInt(cur.getColumnIndex(count));
            }
        }catch (Exception e){}
        return  0;
    }

    public ArrayList<UniversalPost> getOrder(){
        Cursor cursor   = null;
        ArrayList<UniversalPost> order = new ArrayList<>();
        try{
            cursor = db.query(TABLE_SAVE_CART, null, null, null, null, null, null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do{
                    Product product = new Product();
                    product.setId(cursor.getInt(cursor.getColumnIndex(productID)));
                    product.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                    product.setPrice(cursor.getInt(cursor.getColumnIndex(price)));
                    product.setPreviewImage(cursor.getString(cursor.getColumnIndex(preview_img_url)));
                    product.setCount(cursor.getInt(cursor.getColumnIndex(count)));
                    product.setDiscount(cursor.getInt(cursor.getColumnIndex(final_item_price)));
                    product.setMember_discount(cursor.getInt(cursor.getColumnIndex(member_discount)));
                    product.setPoint_usage(cursor.getInt(cursor.getColumnIndex(point_usage)));
                    product.setOptions(cursor.getString(cursor.getColumnIndex(option)));
                    product.setPostType(SHOPPING_CART_ITEM);

                    Cursor flashCursor = db.query(TABLE_FLASH_SALE, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(productID)))}, null, null, null );
                    if(flashCursor != null && flashCursor.getCount() > 0){
                        List<Flash_Sales> list = new ArrayList<>();
                        flashCursor.moveToFirst();
                        do{
                            Flash_Sales flash_sales = new Flash_Sales();
                            flash_sales.setProductId(flashCursor.getInt(flashCursor.getColumnIndex(productID)));
                            flash_sales.setId(flashCursor.getInt(flashCursor.getColumnIndex(flash_id)));
                            flash_sales.setReserved_quantity(flashCursor.getInt(flashCursor.getColumnIndex(flash_reserved_quantity)));
                            flash_sales.setAvailable_quantity(flashCursor.getInt(flashCursor.getColumnIndex(flash_available_quantity)));
                            flash_sales.setDiscount(flashCursor.getInt(flashCursor.getColumnIndex(flash_discount)));
                            flash_sales.setStart_date(flashCursor.getString(flashCursor.getColumnIndex(flash_start_date)));
                            flash_sales.setEnd_date(flashCursor.getString(flashCursor.getColumnIndex(flash_end_date)));


                            list.add(flash_sales);
                        }while (flashCursor.moveToNext());
                        product.setFlashSalesArrayList(list);
                    }

                    List<StockSummeries> summeriesList = new ArrayList<>();
                    Cursor storeCursor  = db.query(TABLE_PRODUCT_STORE_LOCATION, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(productID)))}, null, null, null);
                    if (storeCursor != null && storeCursor.getCount() > 0) {
                        storeCursor.moveToFirst();
                        do {
                            StockSummeries stockSummeries = new StockSummeries();
                            stockSummeries.setStore_location_id(storeCursor.getInt(storeCursor.getColumnIndex(store_id)));
                            stockSummeries.setQuantity(storeCursor.getInt(storeCursor.getColumnIndex(store_qty)));


                            summeriesList.add(stockSummeries);
                        }while (storeCursor.moveToNext());
                    }
                    product.setStockSummeriesList(summeriesList);



                    Cursor dis = db.query(TABLE_DISCOUNT, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(productID)))}, null, null, null );
                    if(dis != null && dis.getCount() > 0){
                        List<Discount> list = new ArrayList<>();
                        dis.moveToFirst();
                        do{
                            Discount discount = new Discount();
                            discount.setProductId(dis.getInt(dis.getColumnIndex(productID)));
                            discount.setDiscount_id(dis.getInt(dis.getColumnIndex(discountID)));
                            discount.setDiscountType(dis.getString(dis.getColumnIndex(discountType)));
                            discount.setPercentage(dis.getInt(dis.getColumnIndex(percentage)));
                            discount.setNum_extra(dis.getInt(dis.getColumnIndex(num_extra)));
                            discount.setGift_img_url(dis.getString(dis.getColumnIndex(gift_img_url)));
                            discount.setGift_info(dis.getString(dis.getColumnIndex(gift_info)));
                            discount.setMin_count(dis.getInt(dis.getColumnIndex(min_count)));
                            discount.setMax_count(dis.getInt(dis.getColumnIndex(max_count)));
                            discount.setDimen(dis.getInt(dis.getColumnIndex(dimen)));
                            discount.setCount_type(dis.getString(dis.getColumnIndex(count_type)));
                            discount.setCampaign_info(dis.getString(dis.getColumnIndex(campaign_info)));
                            discount.setBenefit_type(dis.getString(dis.getColumnIndex(benefit_type)));
                            discount.setMember_only(dis.getInt(dis.getColumnIndex(member_only)));
                            list.add(discount);
                        }while (dis.moveToNext());
                        product.setDiscounts(list);
                    }

                    order.add(product);
                }while (cursor.moveToNext());
            }

        }finally {
            if(cursor != null)
                cursor.close();
        }

        return order;
    }

    public ArrayList<UniversalPost> getOrderFooter(){
        ArrayList<UniversalPost> order = new ArrayList<>();
        Cursor cursor = db.query(TABLE_SAVE_CART, null, null, null, null, null, null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(productID)));
                product.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                product.setPrice(cursor.getInt(cursor.getColumnIndex(price)));
                product.setPreviewImage(cursor.getString(cursor.getColumnIndex(preview_img_url)));
                product.setCount(cursor.getInt(cursor.getColumnIndex(count)));
                product.setDiscount(cursor.getInt(cursor.getColumnIndex(final_item_price)));
                product.setPoint_usage(cursor.getInt(cursor.getColumnIndex(point_usage)));
                product.setMember_discount(cursor.getInt(cursor.getColumnIndex(member_discount)));

                product.setPostType(CART_DETAIL_PRODUCT);

                Cursor dis = db.query(TABLE_DISCOUNT, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(productID)))}, null, null, null );
                if(dis != null && dis.getCount() > 0){
                    List<Discount> list = new ArrayList<>();
                    dis.moveToFirst();
                    do{
                        Discount discount = new Discount();
                        discount.setProductId(dis.getInt(dis.getColumnIndex(productID)));
                        discount.setDiscount_id(dis.getInt(dis.getColumnIndex(discountID)));
                        discount.setDiscountType(dis.getString(dis.getColumnIndex(discountType)));
                        discount.setPercentage(dis.getInt(dis.getColumnIndex(percentage)));
                        discount.setNum_extra(dis.getInt(dis.getColumnIndex(num_extra)));
                        discount.setGift_img_url(dis.getString(dis.getColumnIndex(gift_img_url)));
                        discount.setGift_info(dis.getString(dis.getColumnIndex(gift_info)));
                        discount.setMin_count(dis.getInt(dis.getColumnIndex(min_count)));
                        discount.setMax_count(dis.getInt(dis.getColumnIndex(max_count)));
                        discount.setDimen(dis.getInt(dis.getColumnIndex(dimen)));
                        discount.setCount_type(dis.getString(dis.getColumnIndex(count_type)));
                        discount.setCampaign_info(dis.getString(dis.getColumnIndex(campaign_info)));
                        discount.setBenefit_type(dis.getString(dis.getColumnIndex(benefit_type)));
                        discount.setMember_only(dis.getInt(dis.getColumnIndex(member_only)));
                        list.add(discount);
                    }while (dis.moveToNext());
                    product.setDiscounts(list);
                }

                List<StockSummeries> summeriesList = new ArrayList<>();
                Cursor storeCursor  = db.query(TABLE_PRODUCT_STORE_LOCATION, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(productID)))}, null, null, null);
                if (storeCursor != null && storeCursor.getCount() > 0) {
                    storeCursor.moveToFirst();
                    do {
                        StockSummeries stockSummeries = new StockSummeries();
                        stockSummeries.setStore_location_id(storeCursor.getInt(storeCursor.getColumnIndex(store_id)));
                        stockSummeries.setQuantity(storeCursor.getInt(storeCursor.getColumnIndex(store_qty)));


                        summeriesList.add(stockSummeries);
                    }while (storeCursor.moveToNext());
                }
                product.setStockSummeriesList(summeriesList);



                Cursor flashCursor = db.query(TABLE_FLASH_SALE, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(productID)))}, null, null, null );
                if(flashCursor != null && flashCursor.getCount() > 0){
                    List<Flash_Sales> list = new ArrayList<>();
                    flashCursor.moveToFirst();
                    do{
                        Flash_Sales flash_sales = new Flash_Sales();
                        flash_sales.setProductId(flashCursor.getInt(flashCursor.getColumnIndex(productID)));
                        flash_sales.setId(flashCursor.getInt(flashCursor.getColumnIndex(flash_id)));
                        flash_sales.setReserved_quantity(flashCursor.getInt(flashCursor.getColumnIndex(flash_reserved_quantity)));
                        flash_sales.setAvailable_quantity(flashCursor.getInt(flashCursor.getColumnIndex(flash_available_quantity)));
                        flash_sales.setDiscount(flashCursor.getInt(flashCursor.getColumnIndex(flash_discount)));
                        flash_sales.setStart_date(flashCursor.getString(flashCursor.getColumnIndex(flash_start_date)));
                        flash_sales.setEnd_date(flashCursor.getString(flashCursor.getColumnIndex(flash_end_date)));


                        list.add(flash_sales);
                    }while (flashCursor.moveToNext());
                    product.setFlashSalesArrayList(list);
                }

                order.add(product);
            }while (cursor.moveToNext());
        }


        return order;
    }

    public Product getShoppingCartFooter(){

        // fake product to store data for footer display
        Product fakeProduct = new Product();

        ArrayList<UniversalPost> orders = new ArrayList<>();
        orders = getOrderFooter();

        int total_discount  = 0;
        int total_price     = 0;
        int flash_discountTotal = 0;
        int pointTotal        = 0;
        int pointTotalDiscount        = 0;





        // fake discounts list for footer display
        List<Discount> tempDiscountsList = new ArrayList<>();

        // add product discounts to fake discounts list and cosolidate discounts with same id
        for(int i = 0 ; i < orders.size(); i++ ) {

            Product orderedProduct = (Product) orders.get(i);
            total_price += orderedProduct.getPrice() * orderedProduct.getCount();



            if (orderedProduct.getDiscounts().size() > 0) {
                for (int j = 0; j < orderedProduct.getDiscounts().size(); j++) {
                    Discount discount = (Discount) orderedProduct.getDiscounts().get(j);
                    if (discount.getMember_only() == 0 || (discount.getMember_only() == 1 && prefs.getInt(SP_VIP_ID, 0) == 1)) {
                        checkDiscountAmount(tempDiscountsList, discount, orderedProduct);
                    }
                }
            }

            int flashDiscount = 0;




            if (orderedProduct.getFlashSalesArrayList().size() > 0) {


                final Flash_Sales flash_sales = orderedProduct.getFlashSalesArrayList().get(0);


                Date currentDate1 = new Date();
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                // Please here set your event date//YYYY-MM-DD
                Date futureDate1 = null;
                try {
                    futureDate1 = df1.parse(flash_sales.getEnd_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!currentDate1.after(futureDate1)) {

                    if (flash_sales.getReserved_quantity() < flash_sales.getAvailable_quantity())
                    {
                        flash_discountTotal +=  ( ( orderedProduct.getPrice() * orderedProduct.getFlashSalesArrayList().get(0).getDiscount()) / 100) * orderedProduct.getCount() ;
                        flashDiscount =  ( ( orderedProduct.getPrice() * orderedProduct.getFlashSalesArrayList().get(0).getDiscount()) / 100) ;
                        Flash_Sales flashSales = new Flash_Sales();
                        flashSales.setDiscount(flash_discountTotal);

                        List<Flash_Sales> flashSalesList = new ArrayList<>();
                        flashSalesList.add(flashSales);
                        fakeProduct.setFlashSalesArrayList(flashSalesList);
                    }
                }
            }

            pointTotal = calculatePoint(orderedProduct);

            if (orderedProduct.getPoint_usage() == 0){

                pointTotalDiscount += (orderedProduct.getPrice() - pointTotal - flashDiscount) * orderedProduct.getCount();
            }
            else
                pointTotalDiscount += 0;


        }

        // added member discount to fake discounts list
        Discount memberDiscount = getMemberDiscount(tempDiscountsList, orders);
        tempDiscountsList.add(memberDiscount);


        Discount percentageDiscount = new Discount();
        percentageDiscount.setMax_count(0);
        percentageDiscount.setDiscountType(DISCOUNT_PERCENTAGE);

        List<Discount> fulfilledDiscountsList = new ArrayList<>();

        // filter fulfilled discounts and find total_discount
        for(int i = 0; i < tempDiscountsList.size(); i++) {
            Discount discount = (Discount) tempDiscountsList.get(i);

            if (discount.getNum_extra() >= discount.getMin_count()) {
                if (discount.getDiscountType().equals(DISCOUNT_PERCENTAGE)) {
                    total_discount += discount.getMax_count();
                    percentageDiscount.setMax_count(percentageDiscount.getMax_count() + discount.getMax_count());
                } else {
                    fulfilledDiscountsList.add(discount);
                }
            }
        }

        fulfilledDiscountsList.add(percentageDiscount);

        fakeProduct.setDiscounts(fulfilledDiscountsList);
        fakeProduct.setPrice(total_price - flash_discountTotal -  total_discount - memberDiscount.getMax_count());
        fakeProduct.setPoint_usage(pointTotalDiscount);



        return fakeProduct;
    }



    private int calculatePoint(Product product){

        int price = product.getPrice(), discount = 0;

        if (product.getDiscounts().size() > 0){
            for (int i=0; i< product.getDiscounts().size(); i++){
                Discount dis = product.getDiscounts().get(i);

                if (dis.getMember_only() == 1) {
                    if ( prefs.getInt(SP_VIP_ID, 0) == 1) {
                        String str = dis.getDiscountType();
                        switch (str) {
                            case DISCOUNT_PERCENTAGE:

                                if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {
                                    if (dis.getMin_count() <= product.getCount()) {
                                        discount += discount + ((product.getPrice() * dis.getPercentage()) / 100);


                                    }

                                } else if (dis.getCount_type().equals(DISCOUNT_TYPE_AMOUNT)) {

                                    if (dis.getMin_count() <= (product.getPrice() * product.getCount())) {
                                        discount += discount + ((product.getPrice() * dis.getPercentage()) / 100);


                                    }
                                }
                                break;


                            default:
                                break;
                        }
                    }
                } else {

                    String str = dis.getDiscountType();
                    switch (str) {
                        case DISCOUNT_PERCENTAGE:
                            if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {

                                if (dis.getMin_count() <= product.getCount()) {
                                    discount += discount + ((product.getPrice() * dis.getPercentage()) / 100);


                                }
                            } else if (dis.getCount_type().equals(DISCOUNT_TYPE_AMOUNT)) {

                                if (dis.getMin_count() <= (product.getPrice() * product.getCount())) {
                                    discount += discount + ((product.getPrice() * dis.getPercentage()) / 100);


                                }
                            }
                            break;


                        default:
                            break;
                    }

                }


            }
        }


        int discount1 = 0;
        int tMemberDis = product.getMember_discount();

        if ( tMemberDis != 0 && prefs.getInt(SP_VIP_ID, 0) != 0) {
            discount1 = discount + (price * prefs.getInt(SP_MEMBER_PERCENTAGE, 0) / 100);
            return discount1;
        }


        return discount;



    }


    /*public Product getShoppingCartFooter(){
        Product product     = new Product();
        ArrayList<UniversalPost> order = new ArrayList<>();
        order   = getOrderFooter();
        int total_discount  = 0;
        int total_price     = 0;
        int total_org_price = 0;
        List<Discount> discountList = new ArrayList<>();

        for(int i = 0 ; i < order.size(); i++ ){
            Product pro = (Product) order.get(i);
            total_price += (pro.getDiscount() * pro.getCount() );
            total_org_price += (pro.getPrice() * pro.getCount());
            pro.setPrice(pro.getPrice() * pro.getCount());
            int product_price = pro.getPrice() * pro.getCount();
            if(pro.getDiscounts().size() > 0){
                for (int k = 0; k < pro.getDiscounts().size(); k++) {
                    Discount dis = (Discount) pro.getDiscounts().get(k);

                    String str = dis.getDiscountType();
                    if (dis.getMember_only() == 1) {
                        if (prefs.getInt(SP_VIP_ID, 0) == 1) {
                            switch (str) {
                                case DISCOUNT_PERCENTAGE:
                                    if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {
                                        total_discount += (pro.getPrice() * dis.getPercentage()) / 100;
                                        product_price -= (pro.getPrice() * dis.getPercentage()) / 100;
                                    } else if (dis.getCount_type().equals(DISCOUNT_TYPE_AMOUNT)) {
                                        Discount dis1 = checkDiscountAmount( discountList,dis, pro);
                                        dis1.setDiscountType(DISCOUNT_GIFT);
                                        if(dis1.getDimen() == 0 && discountList.size() > 0 ){
                                            discountList.remove(k);
                                        }
                                        discountList.add(dis1);
                                    }
                                    break;
                                case DISCOUNT_GIFT:
                                    Discount dis1 = checkDiscountAmount( discountList, dis, pro);
                                    dis1.setDiscountType(DISCOUNT_GIFT);
                                    if(dis1.getDimen() == 0  && discountList.size() > 0){
                                        discountList.remove(k);
                                    }
                                    discountList.add(dis1);

                                default:
                                    break;
                            }
                        }
                    } else {

                        switch (str) {
                            case DISCOUNT_PERCENTAGE:
                                if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {
                                    total_discount = (pro.getPrice() * dis.getPercentage()) / 100;
                                    product_price -= (pro.getPrice() * dis.getPercentage()) / 100;
                                } else if (dis.getCount_type().equals(DISCOUNT_TYPE_AMOUNT)) {
                                    Discount dis1 = checkDiscountAmount(discountList, dis, pro);
                                    dis1.setDiscountType(DISCOUNT_GIFT);
                                    if(dis1.getDimen() == 0  && discountList.size() > 0){
                                        discountList.remove(k);
                                    }
                                    discountList.add(dis1);

                                }
                                break;
                            case DISCOUNT_GIFT:
                                Discount dis1 = checkDiscountAmount(discountList, dis, pro);
                                dis1.setDiscountType(DISCOUNT_GIFT);
                                if(dis1.getDimen() == 0  && discountList.size() > 0){
                                    discountList.remove(k);
                                }
                                discountList.add(dis1);

                            default:
                                break;
                        }
                    }
                }
            }


            //need to update here
            if(pro.getMember_discount() > 0 && prefs.getInt(SP_VIP_ID, 0) != 0){
                Discount memDis = checkMemberDiscountAmount(discountList, product_price, DISCOUNT_TYPE_AMOUNT);
                memDis.setDiscountType(DISCOUNT_TYPE_AMOUNT);
                discountList.add(memDis);
            }

        }

        if(total_discount > 0){
            Discount dis    = new Discount();
            dis.setMax_count(total_discount);
            dis.setDiscountType(DISCOUNT_PERCENTAGE);
            discountList.add(dis);

        }

       *//*
        if(pro.getMember_discount() != 0 && prefs.getInt(SP_VIP_ID, 0) != 0){
            Discount dis    = new Discount();
            dis.setMax_count(((total_org_price - total_discount) * prefs.getInt(SP_MEMBER_PERCENTAGE, 0)/100));
            dis.setDiscountType(DISCOUNT_TYPE_AMOUNT);
            discountList.add(dis);

        }
*//*


        product.setDiscounts(discountList);
        product.setPrice(total_price);

        return product;
    }*/

    public Discount getMemberDiscount(List<Discount> discounts, List<UniversalPost> products){
        Discount memberDiscount   = new Discount();
        memberDiscount.setNum_extra(1);
        memberDiscount.setMin_count(0);
        memberDiscount.setMax_count(0);
        memberDiscount.setDiscountType(DISCOUNT_TYPE_AMOUNT);
        memberDiscount.setCount_type(DISCOUNT_TYPE_AMOUNT);

        if (prefs.getInt(SP_VIP_ID, 0) == 1) {
            for (int i = 0; i < products.size(); i++) {
                Product tempProduct = (Product) products.get(i);

                if (tempProduct.getMember_discount() == 1) {
                    int discountedPrice = tempProduct.getPrice() * tempProduct.getCount();

                    for (int j = 0; j < tempProduct.getDiscounts().size(); j++) {
                        Discount tempDiscount = (Discount) tempProduct.getDiscounts().get(j);

                        if (tempDiscount.getDiscountType().equals(DISCOUNT_PERCENTAGE)) {
                            for (int k = 0; k < discounts.size(); k++) {
                                if (tempDiscount.getDiscount_id() == discounts.get(k).getDiscount_id() &&
                                        discounts.get(k).getNum_extra() >= discounts.get(k).getMin_count()) {

                                    int discountedAmount = discountedPrice * tempDiscount.getPercentage() / 100;
                                    discountedPrice = discountedPrice - discountedAmount;
                                }
                            }
                        }

                    }

                    memberDiscount.setMax_count(memberDiscount.getMax_count() + (discountedPrice * 5 / 100));
                }
            }
        }

        return memberDiscount;
    }

    public void checkDiscountAmount( List<Discount> discountsList, Discount discount, Product product){

        boolean found = false;
        Discount tempDiscount = discount;
        tempDiscount.setMax_count(0);

        for(int i = 0; i < discountsList.size(); i ++) {
            if(tempDiscount.getDiscount_id() == discountsList.get(i).getDiscount_id()) {
                tempDiscount = discountsList.get(i);

                found = true;
                i = discountsList.size();
            }
        }

        if (tempDiscount.getCount_type().equals(DISCOUNT_TYPE_AMOUNT)) {
            tempDiscount.setNum_extra(tempDiscount.getNum_extra() + (product.getCount() * product.getPrice()));
        } else {
            tempDiscount.setNum_extra(tempDiscount.getNum_extra() + product.getCount());
        }

        if (tempDiscount.getDiscountType().equals(DISCOUNT_PERCENTAGE)) {
            tempDiscount.setMax_count(tempDiscount.getMax_count() + (tempDiscount.getPercentage() * product.getPrice() * product.getCount() / 100));
        } else {
            tempDiscount.setMax_count(1);
        }

        if(!found)  {
            discountsList.add(tempDiscount);
        }
    }


    public int getOrderPrice(){
        int totalresult= 0;

        try {
            Cursor cursor = db.query(TABLE_SAVE_CART, null, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int eachPrice = cursor.getInt(cursor.getColumnIndex(final_item_price)) * cursor.getInt(cursor.getColumnIndex(count));
                    totalresult += eachPrice;

                } while (cursor.moveToNext());
            }
            return totalresult;
        }catch (Exception e){}


        return totalresult;
    }

    public int getTotalBasicPrice() {

        int totalBasicPrice= 0;
        try {
            Cursor cursor = db.query(TABLE_SAVE_CART, null, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int eachPrice = cursor.getInt(cursor.getColumnIndex(price)) * cursor.getInt(cursor.getColumnIndex(count));
                    totalBasicPrice += eachPrice;

                } while (cursor.moveToNext());
            }
        }catch (Exception e){}

        return totalBasicPrice;
    }

    public ArrayList<Delivery> getDelivery(){
        ArrayList<Delivery> deliveries = new ArrayList<>();
        try {
            Cursor cursor = db.query(TABLE_DELIVERY, null, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Delivery del = new Delivery();
                    del.setId(cursor.getInt(cursor.getColumnIndex(id)));
                    del.setName(cursor.getString(cursor.getColumnIndex(name)));
                    del.setDesc(cursor.getString(cursor.getColumnIndex(desc)));
                    del.setPrice(cursor.getInt(cursor.getColumnIndex(price)));
                    del.setFreeDelivery(cursor.getInt(cursor.getColumnIndex(freeDelivery)));
                    del.setChoose_timing(cursor.getInt(cursor.getColumnIndex(timing)));
                    del.setFirst_time_free(cursor.getInt(cursor.getColumnIndex(first_time_free)));
                    del.setDelivery_type(cursor.getInt(cursor.getColumnIndex(delivery_type)));
                    del.setNormal_price(cursor.getInt(cursor.getColumnIndex(normal_price)));
                    del.setNormal_delivery_days(cursor.getString(cursor.getColumnIndex(normal_delivery_days)));
                    del.setExpress_delivery_days(cursor.getString(cursor.getColumnIndex(express_delivery_days)));
                    deliveries.add(del);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){}
        return deliveries;
    }


    public ArrayList<OrderIDs> getOrderList(){
        ArrayList<OrderIDs> orderIDses = new ArrayList<>();
        Cursor cursor = db.query(TABLE_SAVE_CART, null, null, null, null, null, pId +" DESC");
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                OrderIDs orderID = new OrderIDs();

                orderID.setId(cursor.getInt(cursor.getColumnIndex(productID)));
                orderID.setQuantity(cursor.getInt(cursor.getColumnIndex(count)));
                orderID.setPrice(cursor.getInt(cursor.getColumnIndex(final_item_price)));
                orderID.setOption(cursor.getString(cursor.getColumnIndex(option)));
                orderID.setTitle(cursor.getString(cursor.getColumnIndex(title)));

                orderIDses.add(orderID);
            }while (cursor.moveToNext());
        }

        return orderIDses;
    }

    public String getOrderToServer(){
        ArrayList<OrderIDs> orderIDses = new ArrayList<>();
        Cursor cursor = db.query(TABLE_SAVE_CART, null, null, null, null, null, pId +" DESC");
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                OrderIDs orderID = new OrderIDs();
                orderID.setId(cursor.getInt(cursor.getColumnIndex(productID)));
                orderID.setQuantity(cursor.getInt(cursor.getColumnIndex(count)));
                orderID.setPrice(cursor.getInt(cursor.getColumnIndex(final_item_price)));
                orderID.setOption(cursor.getString(cursor.getColumnIndex(option)));

                orderIDses.add(orderID);
            }while (cursor.moveToNext());
        }
        JSONArray toReturn = new JSONArray();
        for(int i = 0 ; i < orderIDses.size() ; i ++){
            JSONObject object = new JSONObject();
            try {
                object.put("id", orderIDses.get(i).getId());
                object.put("quantity", orderIDses.get(i).getQuantity());
                object.put("price", orderIDses.get(i).getPrice());
                object.put("option", orderIDses.get(i).getOption());
                toReturn.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toReturn.toString();
    }


    public String getCheckOrderToServer(){
        ArrayList<OrderIDs> orderIDses = new ArrayList<>();
        Cursor cursor = db.query(TABLE_SAVE_CART, null, null, null, null, null, pId +" DESC");
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                OrderIDs orderID = new OrderIDs();
                orderID.setId(cursor.getInt(cursor.getColumnIndex(productID)));
                orderID.setQuantity(cursor.getInt(cursor.getColumnIndex(count)));
                orderID.setPrice(cursor.getInt(cursor.getColumnIndex(final_item_price)));
                orderID.setOption(cursor.getString(cursor.getColumnIndex(option)));

                orderIDses.add(orderID);
            }while (cursor.moveToNext());
        }
        JSONArray toReturn = new JSONArray();
        for(int i = 0 ; i < orderIDses.size() ; i ++){
            JSONObject object = new JSONObject();
            try {
                object.put("product_id", orderIDses.get(i).getId());
                object.put("quantity", orderIDses.get(i).getQuantity());
                toReturn.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toReturn.toString();
    }

    public ArrayList<OrderIDs> stringToArrayList(String string){
        ArrayList<OrderIDs> arrayList = new ArrayList<>();

        try {
            JSONArray idArray = new JSONArray(string);
            for(int i = 0; i < idArray.length(); i++){
                JSONObject object = idArray.getJSONObject(i);
                OrderIDs orderIDs = new OrderIDs();
                orderIDs.setId(object.getInt("id"));
                orderIDs.setQuantity(object.getInt("quantity"));
                orderIDs.setPrice(object.getInt("price"));
                arrayList.add(orderIDs);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    public int insertOrderTable(String order_id, Order123 ord){

        long ordertable = 0;
        SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String myDate = format.format(new Date());

        try {

            ContentValues order = new ContentValues();
            order.put(orderId, order_id);
            order.put(totalPrice, ord.getTotalPrice());
            order.put(name, ord.getName());
            order.put(address, ord.getAddress());
            order.put(phoneNo, ord.getPhoneNo());
            order.put(deliveryPrice, ord.getDeliveryPrice());
            order.put(date, myDate);
            ordertable = db.insert(TABLE_ORDER, null, order);
        }catch (Exception e){ }
        return (int)ordertable;

    }
    public ArrayList<UniversalPost> getOrderTable(){
        ArrayList<UniversalPost> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_ORDER, null, null, null, null, null, orderId + " DESC");
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Order123 order = new Order123();
                order.setOrderId(cursor.getString(cursor.getColumnIndex(orderId)));
                order.setTotalPrice(cursor.getInt(cursor.getColumnIndex(totalPrice)));
                order.setName(cursor.getString(cursor.getColumnIndex(name)));
                order.setAddress(cursor.getString(cursor.getColumnIndex(address)));
                order.setPhoneNo(cursor.getString(cursor.getColumnIndex(phoneNo)));
                order.setDeliveryPrice(cursor.getInt(cursor.getColumnIndex(deliveryPrice)));
                order.setDate(cursor.getString(cursor.getColumnIndex(date)));
                order.setPostType(TOTAL_ORDER);
                result.add(order);
            }while (cursor.moveToNext());

        }
        return result;
    }

    public  void insertOrderItem(Order123 order, String order_id) {
        ArrayList<OrderIDs> orderIDses = new ArrayList<>();
        orderIDses = stringToArrayList(order.getOrders());

        for (int i = 0; i < orderIDses.size(); i++) {
            OrderIDs orderIDs = new OrderIDs();
            orderIDs = orderIDses.get(i);
            Cursor cursor = db.query(TABLE_PRODUCT, null, id + "=?", new String[]{String.valueOf(orderIDs.getId())}, null, null,null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    try{
                        ContentValues pro = new ContentValues();
                        pro.put(id,             cursor.getInt(cursor.getColumnIndex(id)));
                        pro.put(title,          cursor.getString(cursor.getColumnIndex(title)));
                        pro.put(price,          orderIDs.getPrice());
                        pro.put(preview_img_url,cursor.getString(cursor.getColumnIndex(preview_img_url)));
                        pro.put(count,          orderIDs.getQuantity());
                        pro.put(orderId,        order_id);
                        long productReturn = db.insert(TABLE_ORDER_ITEM, null, pro);



                    }catch (Exception e){}
                }while (cursor.moveToNext());
            }
        }
    }

/*    public ArrayList<UniversalPost> getOrderItem(String order_id){
        ArrayList<UniversalPost> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_ORDER_ITEM, null, orderId  + "=?", new String[]{String.valueOf(order_id)}, null, null,null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Product pro = new Product();
                pro.setId(cursor.getInt(cursor.getColumnIndex(id)));
                pro.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                pro.setCount(cursor.getInt(cursor.getColumnIndex(count)));
                pro.setPreviewImage(cursor.getString(cursor.getColumnIndex(preview_img_url)));
                pro.setPrice(cursor.getInt(cursor.getColumnIndex(price)));

                pro.setPostType(ORDER_DETAIL);

                result.add(pro);
            }while (cursor.moveToNext());

        }
        return result;
    }*/


    public void insertDelivery(ArrayList<Delivery> deliveryArraylist){
        for(int i = 0 ; i < deliveryArraylist.size(); i++) {

            Delivery delivery = new Delivery();
            delivery = deliveryArraylist.get(i);
            deleteColumn(TABLE_DELIVERY, id, String.valueOf(delivery.getId()));
            try {
                ContentValues del = new ContentValues();
                del.put(id,             delivery.getId());
                del.put(name,           delivery.getName());
                del.put(desc,           delivery.getDesc());
                del.put(price,          delivery.getPrice());
                del.put(freeDelivery,   delivery.getFreeDelivery());
                del.put(timing      ,   delivery.getChoose_timing());
                del.put(first_time_free,delivery.getFirst_time_free());
                del.put(delivery_type, delivery.getDelivery_type());
                del.put(normal_price, delivery.getNormal_price());
                del.put(normal_delivery_days, delivery.getNormal_delivery_days());
                del.put(express_delivery_days, delivery.getExpress_delivery_days());

                long de = db.insert(TABLE_DELIVERY, null, del);
            }catch (Exception e){}
        }

        /*
        	"": 3,
	"delivery_type": 0,
	"": 1000,
	"express_delivery_days": "1 ~ 3 days",
	"normal_price": 0,
	"normal_delivery_days": "4 ~ 7 days",

         */
    }

    /*public ArrayList<UniversalPost> getProducts(String type){
        ArrayList<UniversalPost> posts = new ArrayList<>();
        Cursor cursor  = db.query(TABLE_PRODUCT, null, productType+"=?",new String[]{String.valueOf(type)}, null, null, id +" DESC");

        if(cursor!= null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Product pro = new Product();
                pro.setId(cursor.getInt(cursor.getColumnIndex(id)));
                pro.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                pro.setDesc(cursor.getString(cursor.getColumnIndex(desc)));
                pro.setPrice(cursor.getInt(cursor.getColumnIndex(price)));
                pro.setDiscount(cursor.getInt(cursor.getColumnIndex(discount)));
                pro.setYoutubeId(cursor.getString(cursor.getColumnIndex(youtubeId)));
                pro.setStatus(cursor.getString(cursor.getColumnIndex(status)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(code)));
                pro.setCategoryId(cursor.getInt(cursor.getColumnIndex(categoryId)));
                pro.setPreviewImage(cursor.getString(cursor.getColumnIndex(preview_img_url)));
                pro.setPostType(CATEGORY_DETAIL);
                pro.setRecommended(cursor.getString(cursor.getColumnIndex(recommended)));
                pro.setMember_discount(cursor.getInt(cursor.getColumnIndex(member_discount)));
                pro.setBrand_id(cursor.getInt(cursor.getColumnIndex(brand_id)));
                pro.setBrand_name(cursor.getString(cursor.getColumnIndex(brandName)));
                pro.setPhoneNo(cursor.getString(cursor.getColumnIndex(phoneNo)));
                pro.setProductType(cursor.getString(cursor.getColumnIndex(productType)));
                pro.setYoutubeImageUrl(cursor.getString(cursor.getColumnIndex(youtubeImageUrl)));
                pro.setYoutubeImageDimen(cursor.getInt(cursor.getColumnIndex(youtubeImageDimen)));
                pro.setDescTitle(cursor.getString(cursor.getColumnIndex(descTitle)));
                pro.setProductUrl(cursor.getString(cursor.getColumnIndex(productUrl)));
                pro.setDiscountedPrice(cursor.getInt(cursor.getColumnIndex(discountedPrice)));
                pro.setCategory_name(cursor.getString(cursor.getColumnIndex(category_name)));
                //discount
                List<Discount> discounts = new ArrayList<>();
                Cursor discount  = db.query(TABLE_DISCOUNT, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(id)))}, null, null, null);
                if (discount != null && discount.getCount() > 0) {
                    discount.moveToFirst();
                    do {
                        Discount dis = new Discount();
                        dis.setDiscount_id(discount.getInt(discount.getColumnIndex(discountID)));
                        dis.setDiscountType(discount.getString(discount.getColumnIndex(discountType)));
                        dis.setPercentage(discount.getInt(discount.getColumnIndex(percentage)));
                        dis.setNum_extra(discount.getInt(discount.getColumnIndex(num_extra)));
                        dis.setGift_img_url(discount.getString(discount.getColumnIndex(gift_img_url)));
                        dis.setGift_info(discount.getString(discount.getColumnIndex(gift_info)));
                        dis.setMin_count(discount.getInt(discount.getColumnIndex(min_count)));
                        dis.setMax_count(discount.getInt(discount.getColumnIndex(max_count)));
                        dis.setDimen(discount.getInt(discount.getColumnIndex(dimen)));
                        dis.setCount_type(discount.getString(discount.getColumnIndex(count_type)));
                        dis.setCampaign_info(discount.getString(discount.getColumnIndex(campaign_info)));
                        dis.setBenefit_type(discount.getString(discount.getColumnIndex(benefit_type)));
                        dis.setMember_only(discount.getInt(discount.getColumnIndex(member_only)));
                        dis.setStart_at(discount.getString(discount.getColumnIndex(start_at)));
                        dis.setEnd_at(discount.getString(discount.getColumnIndex(end_at)));
                        discounts.add(dis);
                    }while (discount.moveToNext());
                }
                pro.setDiscounts(discounts);

                //need to add suppliser
                Cursor supplier = db.query(TABLE_SUPPLIER, null, id + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(supplierId)))}, null, null, null);
                if (supplier != null && supplier.getCount() > 0) {
                    supplier.moveToFirst();
                    do {
                        Supplier sup = new Supplier();
                        sup.setId(cursor.getInt(cursor.getColumnIndex(supplierId)));
                        sup.setName(supplier.getString(supplier.getColumnIndex(name)));
                        pro.setSupplier(sup);
                    }while (supplier.moveToNext());
                }

                List<Images> imageList = new ArrayList<>();
                Cursor images = db.query(TABLE_IMAGES, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(id)))}, null, null, null);
                if (images != null && images.getCount() > 0) {
                    images.moveToFirst();
                    do {
                        Images img = new Images();
                        img.setName(images.getString(images.getColumnIndex(name)));
                        img.setDimen(images.getInt(images.getColumnIndex(dimen)));
                        img.setUrl(images.getString(images.getColumnIndex(url)));
                        img.setProductId(images.getInt(images.getColumnIndex(productID)));
                        imageList.add(img);
                    }while (supplier.moveToNext());
                }

                pro.setImages(imageList);

                posts.add(pro);
            } while (cursor.moveToNext());
        }

        return posts;
    }

    public ArrayList<UniversalPost> getWishList(){
        ArrayList<UniversalPost> posts = new ArrayList<>();

        Cursor cursor  = db.query(TABLE_PRODUCT, null, status+"=?",new String[]{String.valueOf(wishList)}, null, null, id +" DESC");
        if(cursor!= null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Product pro = new Product();
                pro.setId(cursor.getInt(cursor.getColumnIndex(id)));
                pro.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                pro.setDesc(cursor.getString(cursor.getColumnIndex(desc)));
                pro.setPrice(cursor.getInt(cursor.getColumnIndex(price)));
                pro.setDiscount(cursor.getInt(cursor.getColumnIndex(discount)));
                pro.setYoutubeId(cursor.getString(cursor.getColumnIndex(youtubeId)));
                pro.setStatus(cursor.getString(cursor.getColumnIndex(status)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(code)));
                pro.setCategoryId(cursor.getInt(cursor.getColumnIndex(categoryId)));
                pro.setPreviewImage(cursor.getString(cursor.getColumnIndex(preview_img_url)));
                pro.setBrand_name(cursor.getString(cursor.getColumnIndex(brandName)));
                pro.setBrand_id(cursor.getInt(cursor.getColumnIndex(brand_id)));
                pro.setRecommended(cursor.getString(cursor.getColumnIndex(recommended)));
                pro.setMember_discount(cursor.getInt(cursor.getColumnIndex(member_discount)));
                pro.setPostType(CATEGORY_DETAIL_WISHLIST);
                pro.setPhoneNo(cursor.getString(cursor.getColumnIndex(phoneNo)));
                pro.setProductType(cursor.getString(cursor.getColumnIndex(productType)));
                pro.setYoutubeImageUrl(cursor.getString(cursor.getColumnIndex(youtubeImageUrl)));
                pro.setYoutubeImageDimen(cursor.getInt(cursor.getColumnIndex(youtubeImageDimen)));
                pro.setDescTitle(cursor.getString(cursor.getColumnIndex(descTitle)));
                pro.setProductUrl(cursor.getString(cursor.getColumnIndex(productUrl)));
                pro.setDiscountedPrice(cursor.getInt(cursor.getColumnIndex(discountedPrice)));
                pro.setCategory_name(cursor.getString(cursor.getColumnIndex(category_name)));
                //need to add suppliser

                //discount
                List<Discount> discounts = new ArrayList<>();
                Cursor discount  = db.query(TABLE_DISCOUNT, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(id)))}, null, null, null);
                if (discount != null && discount.getCount() > 0) {
                    discount.moveToFirst();
                    do {
                        Discount dis = new Discount();
                        dis.setDiscount_id(discount.getInt(discount.getColumnIndex(discountID)));
                        dis.setDiscountType(discount.getString(discount.getColumnIndex(discountType)));
                        dis.setPercentage(discount.getInt(discount.getColumnIndex(percentage)));
                        dis.setNum_extra(discount.getInt(discount.getColumnIndex(num_extra)));
                        dis.setGift_img_url(discount.getString(discount.getColumnIndex(gift_img_url)));
                        dis.setGift_info(discount.getString(discount.getColumnIndex(gift_info)));
                        dis.setMin_count(discount.getInt(discount.getColumnIndex(min_count)));
                        dis.setMax_count(discount.getInt(discount.getColumnIndex(max_count)));
                        dis.setDimen(discount.getInt(discount.getColumnIndex(dimen)));
                        dis.setCount_type(discount.getString(discount.getColumnIndex(count_type)));
                        dis.setCampaign_info(discount.getString(discount.getColumnIndex(campaign_info)));
                        dis.setBenefit_type(discount.getString(discount.getColumnIndex(benefit_type)));
                        dis.setMember_only(discount.getInt(discount.getColumnIndex(member_only)));
                        discounts.add(dis);
                    }while (discount.moveToNext());
                }
                pro.setDiscounts(discounts);

                Cursor supplier = db.query(TABLE_SUPPLIER, null, id + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(supplierId)))}, null, null, null);
                if (supplier != null && supplier.getCount() > 0) {
                    supplier.moveToFirst();
                    do {
                        Supplier sup = new Supplier();
                        sup.setId(cursor.getInt(cursor.getColumnIndex(supplierId)));
                        sup.setName(supplier.getString(supplier.getColumnIndex(name)));
                        pro.setSupplier(sup);
                    }while (supplier.moveToNext());
                }


                List<Images> imageList = new ArrayList<>();
                Cursor images = db.query(TABLE_IMAGES, null, productID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(id)))}, null, null, null);
                if (images != null && images.getCount() > 0) {
                    images.moveToFirst();
                    do {
                        Images img = new Images();
                        img.setName(images.getString(images.getColumnIndex(name)));
                        img.setDimen(images.getInt(images.getColumnIndex(dimen)));
                        img.setUrl(images.getString(images.getColumnIndex(url)));
                        img.setProductId(images.getInt(images.getColumnIndex(productID)));
                        imageList.add(img);
                    }while (supplier.moveToNext());
                }

                pro.setImages(imageList);
                posts.add(pro);
            } while (cursor.moveToNext());
        }
        return posts;
    }

    public boolean idContainWishlist(int list_id){
        boolean boo = false;

        Cursor cursor  = db.query(TABLE_PRODUCT, null, id+"=? and "+status+"=?",new String[]{String.valueOf(list_id),wishList}, null, null, null);
        if(cursor!= null && cursor.getCount() > 0){
            boo = true;
        }
        return boo;
    }



    public int updatetWishList(int pid, int update){
        try {
            ContentValues order = new ContentValues();
            if(update == 1)
                order.put(status, wishList);
            else
                order.put(status, "available");
            return db.update(TABLE_PRODUCT, order, id + "=" + pid, null);

        }catch (Exception e){
        }
        return 0;
    }







*/






}
