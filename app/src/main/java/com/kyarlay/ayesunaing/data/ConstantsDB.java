package com.kyarlay.ayesunaing.data;

/**
 * Created by ayesunaing on 9/7/16.
 */
 public interface ConstantsDB {

      String pId    = "pId";
      String id      = "id";

      String TABLE_SAVE_CART    = "TABLE_SAVE_CART";
      String productID          = "productID";
      String count              = "count";
      String option             = "option";


      String TABLE_CATEGORY_MAIN = "TABLE_CATEGORY_MAIN";
      String big_image            = "big_image";
      String image                = "image";


      String TABLE_CATEGORY = "TABLE_CATEGORY";
      String tag            = "tag";
    //  String status            = "status";
    //  String name           = "name";

      String TABLE_SUPPLIER = "TABLE_SUPPLIER";
      String name           = "name";

      String TABLE_PRODUCT_LIKE = "TABLE_PRODUCT_LIKE";

      String TABLE_POST_SUBSCRIBE   = "TABLE_POST_SUBSCRIBE";

      String TABLE_POST_NOTIFICATION = "TABLE_POST_NOTIFICATION";
      String TABLE_PRODUCT_NOTI      = "TABLE_PRODUCT_NOTI";
      String TABLE_POST_LIKE = "TABLE_POST_LIKE";
      String TABLE_VIDEO_LIKE = "TABLE_VIDEO_LIKE";
      String postId          = "postId";

      String TABLE_ORDER_ITEM_IMAGES   = "TABLE_ORDER_ITEM_IMAGES";
      String orderItemId               = "orderItemId";
      String TABLE_IMAGES              = "IMAGES";
      String url                       = "url";
      String dimen                     = "dimen";
    //  String productID          = "productID";

      String TABLE_ORDER   = "TABLE_ORDER";
      String orderId        = "orderId";
      String address        = "address";
      String deliveryPrice  = "deliveryPrice";
      String totalPrice     = "totalPrice";
      String date           = "date";


      String TABLE_ORDER_ITEM   = "TABLE_ORDER_ITEM";
      String TABLE_PRODUCT      = "TABLE_PRODUCT";
      String TABLE_SEARCHED     = "TABLE_SEARCHED";
      String title              = "title";
      String brand_id           = "brand_id";
      String desc               = "desc";
      String price              = "price";
      String discount           = "discount";
      String point_usage        = "point_usage";
      String youtubeId          = "youtubeId";
      String status             = "status";
      String code               = "code";
      String categoryId         = "categoryId";
      String supplierId         = "supplierId";
      String recommended        = "recommended";
      String member_discount    = "member_discount";
      String final_item_price   = "file_item_price";
      String postType           = "postType";
      String phoneNo            = "phoneNo";
      String preview_img_url    = "preview_img_url";
      String productType        = "productType";
      String youtubeImageUrl    = "youtubeImageUrl";
      String youtubeImageDimen  = "youtubeImageDimen";
      String descTitle          = "descTitle";
      String discountedPrice    = "discountedPrice";
      String productUrl         = "productUrl";
      String brandName          = "brandName";
      String category_name      = "category_name";

      String TABLE_DELIVERY      = "TABLE_DELIVERY";//pId, id, name, desc, price
      String freeDelivery        = "freeDelivery";
      String wishList            = "wishList";
      String timing              = "timing";
      String first_time_free     = "first_time_free";
      String delivery_type       = "delivery_type";
      String express_delivery_days =  "express_delivery_days";
      String normal_price       = "normal_price";
      String normal_delivery_days       = "normal_delivery_days";



      String TABLE_CITY          = "TABLE_CITY";//pid, id, name

      String TABLE_BRAND         = "TABLE_BRAND";//pid, cityId, id, name

      String TABLE_DISCOUNT_ITEM = "TABLE_DISCOUNT_ITEM";
      String TABLE_DISCOUNT      = "TABLE_DISCOUNT";//pid, productId, id, type, percentage, num_extra, gift_img_url, gift_info, min_count, max_count, count_type
      String discountID         = "discountID";
      String discountType       = "discountType";//discount_percentage && gift
      String percentage         = "percentage";
      String num_extra          = "num_extra";
      String gift_img_url       = "gift_img_url";
      String gift_info          = "gift_info";
      String campaign_info      = "campaign_info";
      String benefit_type       = "benefit_type";
      String min_count          = "min_count";
      String max_count          = "max_count";
      String member_only        = "member_only";
      String start_at           = "start_at";
      String end_at             = "end_at";
      String count_type         = "count_type";//quantity && amount


     String TABLE_PICKUP_SHOP  = "TABLE_PICKUP_SHOP";
     String pickup_id          = "pickup_id";
     String pickup_name        = "pickup_name";
     String pickup_address     = "pickup_address";
     String pickup_phone       = "pickup_phone";

     String TABLE_PRODUCT_STORE_LOCATION = "TABLE_PRODUCT_STORE_LOCATION";
     String store_id  ="store_id";
     String store_qty ="store_qty";

     String TABLE_TOWNSHIP   = "TABLE_TOWNSHIP"  ;

    /*
    "id": 6047,
	"township_id": 3,
	"township_name": "လှိုင်သာယာ",
	"delivery_id": 2,
	"address": "fHlaing Tharyar"
     */

     String TABLE_CUSTOMER_ADDRESS = "TABLE_CUSTOMER_ADDRESS";
     String customer_township_id   = "customer_township_id";
     String customer_delivery_id   = "customer_delivery_id";
     String customer_address       = "customer_address"   ;
     String customer_township      = "customer_township";




     String TABLE_SAVE_NAMES    = "TABLE_SAVE_NAMES";
     String SM_ID               = "SM_ID";
     String SM_NAME             = "SM_NAME";

     String TABLE_SUPER_CATEGORY  = "TABLE_SUPER_CATEGORY";
     String CAT_ID                = "CAT_ID";
     String CAT_NAME              = "CAT_NAME";


     String TABLE_FLASH_SALE              = "TABLE_FLASH_SALE";
     String flash_id                      = "flash_id";
     String flash_start_date              = "flash_start_date";
     String flash_end_date                = "flash_end_date";
     String flash_available_quantity      = "flash_available_quantity";
     String flash_reserved_quantity       = "flash_reserved_quantity";
     String flash_discount                = "flash_discount";




}
