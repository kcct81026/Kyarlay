package com.kyarlay.ayesunaing.data;

/**
 * Created by ayesunaing on 9/7/16.
 */
public interface ConstantsDB {

    public  String pId    = "pId";
    public  String id      = "id";

    public  String TABLE_SAVE_CART    = "TABLE_SAVE_CART";
    public  String productID          = "productID";
    public  String count              = "count";
    public  String option             = "option";


    public  String TABLE_CATEGORY_MAIN = "TABLE_CATEGORY_MAIN";
    public  String big_image            = "big_image";
    public  String image                = "image";


    public  String TABLE_CATEGORY = "TABLE_CATEGORY";
    public  String tag            = "tag";
    //public  String status            = "status";
    //public  String name           = "name";

    public  String TABLE_SUPPLIER = "TABLE_SUPPLIER";
    public  String name           = "name";

    public  String TABLE_PRODUCT_LIKE = "TABLE_PRODUCT_LIKE";

    public  String TABLE_POST_SUBSCRIBE   = "TABLE_POST_SUBSCRIBE";

    public  String TABLE_POST_NOTIFICATION = "TABLE_POST_NOTIFICATION";
    public  String TABLE_PRODUCT_NOTI      = "TABLE_PRODUCT_NOTI";
    public  String TABLE_POST_LIKE = "TABLE_POST_LIKE";
    public  String TABLE_VIDEO_LIKE = "TABLE_VIDEO_LIKE";
    public  String postId          = "postId";

    public  String TABLE_ORDER_ITEM_IMAGES   = "TABLE_ORDER_ITEM_IMAGES";
    public  String orderItemId               = "orderItemId";
    public  String TABLE_IMAGES              = "IMAGES";
    public  String url                       = "url";
    public  String dimen                     = "dimen";
    //public  String productID          = "productID";

    public  String TABLE_ORDER   = "TABLE_ORDER";
    public  String orderId        = "orderId";
    public  String address        = "address";
    public  String deliveryPrice  = "deliveryPrice";
    public  String deliveryFee  = "deliveryFee";
    public  String totalPrice     = "totalPrice";
    public  String date           = "date";


    public  String TABLE_ORDER_ITEM   = "TABLE_ORDER_ITEM";
    public  String TABLE_PRODUCT      = "TABLE_PRODUCT";
    public  String TABLE_SEARCHED     = "TABLE_SEARCHED";
    public  String TABLE_BRAND_DETAIL = "TABLE_BRAND_DETAIL";
    public  String title              = "title";
    public  String brand_id           = "brand_id";
    public  String desc               = "desc";
    public  String price              = "price";
    public  String discount           = "discount";
    public  String point_usage        = "point_usage";
    public  String youtubeId          = "youtubeId";
    public  String status             = "status";
    public  String code               = "code";
    public  String categoryId         = "categoryId";
    public  String supplierId         = "supplierId";
    public  String recommended        = "recommended";
    public  String member_discount    = "member_discount";
    public  String final_item_price   = "file_item_price";
    public  String postType           = "postType";
    public  String phoneNo            = "phoneNo";
    public  String preview_img_url    = "preview_img_url";
    public  String productType        = "productType";
    public  String youtubeImageUrl    = "youtubeImageUrl";
    public  String youtubeImageDimen  = "youtubeImageDimen";
    public  String descTitle          = "descTitle";
    public  String discountedPrice    = "discountedPrice";
    public  String productUrl         = "productUrl";
    public  String brandName          = "brandName";
    public  String category_name      = "category_name";

    public  String TABLE_DELIVERY      = "TABLE_DELIVERY";//pId, id, name, desc, price
    public  String freeDelivery        = "freeDelivery";
    public  String wishList            = "wishList";
    public  String timing              = "timing";
    public  String first_time_free     = "first_time_free";
    public  String delivery_type       = "delivery_type";
    public  String express_delivery_days =  "express_delivery_days";
    public  String normal_price       = "normal_price";
    public  String normal_delivery_days       = "normal_delivery_days";



    public  String TABLE_CITY          = "TABLE_CITY";//pid, id, name
    public  String cityID              = "cityID";

    public  String TABLE_BRAND         = "TABLE_BRAND";//pid, cityId, id, name

    public  String TABLE_DISCOUNT_ITEM = "TABLE_DISCOUNT_ITEM";
    public  String TABLE_DISCOUNT      = "TABLE_DISCOUNT";//pid, productId, id, type, percentage, num_extra, gift_img_url, gift_info, min_count, max_count, count_type
    public  String discountID         = "discountID";
    public  String discountType       = "discountType";//discount_percentage && gift
    public  String percentage         = "percentage";
    public  String num_extra          = "num_extra";
    public  String gift_img_url       = "gift_img_url";
    public  String gift_info          = "gift_info";
    public  String campaign_info      = "campaign_info";
    public  String benefit_type       = "benefit_type";
    public  String min_count          = "min_count";
    public  String max_count          = "max_count";
    public  String member_only        = "member_only";
    public  String start_at           = "start_at";
    public  String end_at             = "end_at";
    public  String count_type         = "count_type";//quantity && amount


    public String TABLE_PICKUP_SHOP  = "TABLE_PICKUP_SHOP";
    public String pickup_id          = "pickup_id";
    public String pickup_name        = "pickup_name";
    public String pickup_address     = "pickup_address";
    public String pickup_phone       = "pickup_phone";

    public String TABLE_PRODUCT_STORE_LOCATION = "TABLE_PRODUCT_STORE_LOCATION";
    public String store_id  ="store_id";
    public String store_qty ="store_qty";

    public String TABLE_TOWNSHIP   = "TABLE_TOWNSHIP"  ;

    /*
    "id": 6047,
	"township_id": 3,
	"township_name": "လှိုင်သာယာ",
	"delivery_id": 2,
	"address": "fHlaing Tharyar"
     */

    public String TABLE_CUSTOMER_ADDRESS = "TABLE_CUSTOMER_ADDRESS";
    public String customer_township_id   = "customer_township_id";
    public String customer_delivery_id   = "customer_delivery_id";
    public String customer_address       = "customer_address"   ;
    public String customer_township      = "customer_township";




    public String TABLE_SAVE_NAMES    = "TABLE_SAVE_NAMES";
    public String SM_ID               = "SM_ID";
    public String SM_NAME             = "SM_NAME";

    public String TABLE_SUPER_CATEGORY  = "TABLE_SUPER_CATEGORY";
    public String CAT_ID                = "CAT_ID";
    public String CAT_NAME              = "CAT_NAME";


    public String TABLE_FLASH_SALE              = "TABLE_FLASH_SALE";
    public String flash_id                      = "flash_id";
    public String flash_start_date              = "flash_start_date";
    public String flash_end_date                = "flash_end_date";
    public String flash_available_quantity      = "flash_available_quantity";
    public String flash_reserved_quantity       = "flash_reserved_quantity";
    public String flash_discount                = "flash_discount";




}
