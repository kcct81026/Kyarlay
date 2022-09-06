package com.kyarlay.ayesunaing.data;

/**
 * Created by ayesunaing on 8/23/16.
 */
public interface Constant {

    String default_api =  "https://www.kyarlay.com/";
    //String default_api =  "http://159.223.58.13/";

    //https://admin.kyarlay.com/api/customers/point_history?language=uni&type=received

    String constantPointGetReceivedHistory = default_api + "api/customers/point_history?";
    String constantDashBoard              = default_api +  "api/dashboard";
    String constantNewBonusBanner         =  default_api + "api/customers/new_bonus_banner?type=signup";
    String constantMemberInfo             = default_api +  "api/customers/member_info";

    String PREFERENCES_TOOL_BAR_CART      = "PREFERENCES_TOOL_BAR_CART";
    String constantCategoryDetailSublist  = default_api + "api/categories?tag=";
    String constantProductDetailSublist   = default_api + "api/products?category_id=";

    String constantDeliveryList           = default_api + "api/deliveries";
    String constantUploadOrder            = default_api + "api/shopping_carts/checkout";
    //String constantUploadOrder            = default_api + "api/shopping_";

    String constantVersion                = default_api + "api/version";
    //String constantAds                    = default_api + "api/products/ads";
    String constantHelp                   = default_api + "help?mobile=1";
    String constantAboutus                = default_api + "about?mobile=1";
    String constantBrand                  = default_api + "api/brands?tag=";
    String constantBrand_Detail           = default_api + "api/products?category_id=";

    String constatRichText_Detail         = default_api + "api/posts/";
    String constantMemberCheck            = default_api + "api/shopping_carts/verify_member?phone=";
    String constantSearch                 = default_api + "api/products/search?version=";
    //String constantReading                = default_api + "api/posts/categories?";
    String constantRadingDetail           = default_api + "api/posts?";
    String constantVideo                  = default_api + "api/video_programs?page=";

    String constantFeedBack               = default_api + "api/feedback";
    String constantKyarlayPushNoti        = default_api + "a/";
    //String constantCampainList            = default_api + "api/discounts";
    String constantCampainDetail          = default_api + "api/discounts/";
   // String constantDailyPromotion         = default_api + "api/main_promo";
   // String constantDailyPromotionDetail   = default_api + "api/daily_promo";
    //String constantBrandPromotion         = default_api + "api/brands/main";
    String constantBrandPromotionDetail   = default_api + "api/brands/recommended";
    //String constantMainPage               = default_api + "api/main";
    String constantVideoProgramDetail     = default_api + "api/video_programs/";
    String constantVideoProgramLike        = default_api + "api/video_programs/";

    String constantComment                = default_api + "api/comments?post_id=";
    String constantPostLiked              = default_api + "api/postliked?post_id=";
    String constantCommentSend            = default_api + "api/comments";
    //String constantRecommendBrand         = default_api +"api/brands/recommended_brands?customer_id=";
    String constantBrandSponsor           = default_api + "api/brands/sponsors?category_id=";
    String constantKnowledgeSponsor       = default_api + "api/brands/knowledge_sponsors";
    String constantBrandDetailCategory    = default_api + "api/brands/";
    String constantProductLikes           = default_api + "api/customers/liked_products";
    String constantVideoLikes             = default_api + "api/customers/liked_video_programs";
    String constantPostLikes              = default_api + "api/customers/liked_posts";
    String constantTopSearch              = default_api + "api/products/top_searches";
    String constantChatProduct            = default_api + "p/";

    String constantUpdateFreshChat        = default_api + "api/customers/%s/update_freshchat_id";
    String constantUpdateFcmID            = default_api + "api/customers/%s/update_fcm_id";
    String constantUpdateUserInfo         = default_api + "api/customers/%s/update_info";

    String constantProductCount           = default_api + "api/customers/liked_products_count";
    String constantPostCount              = default_api + "api/customers/liked_posts_count";
    String constantDiscounts              = default_api + "api/discounts";
    String constantCollections            = default_api + "api/product_collections/";

    String constantOrderedlist            = default_api + "api/shopping_carts/orders?page=%s&type=%s";
    String constantOrderedDetail          = default_api + "api/shopping_carts/";

    String constantFooterProduct          = default_api + "api/products/related_products?product_id=";
    String constantPopularProduct         = default_api + "api/products/related_products?";
    //String constantFooterPost             = default_api + "api/products/related_products?post_id=";
    String constantNotification           = default_api + "api/customers/%s/notifications?page=%s&post_type=%s";

    String constantProduct                = default_api + "api/products/";
    String constantGetVideoList           = default_api + "api/video_programs/trending?page=";
    String constantGetCustomerDetail      = default_api + "api/customers";
    String constantGetCustomerInfo        = default_api + "api/customers/info";
    //String constantGetGifts               = default_api + "api/gifts?page=";

    String constantLuckyDay               = default_api + "api/lucky_days";
    //String constantSetNetScore            = default_api + "api/customers/%s/set_net_promoter_score";
    String constantEarnPoint              = default_api + "api/customers/%s/earn_points";
    String constantPointHistory           = default_api + "api/customers/points";
    String constantInviteFriend           = default_api + "api/customers/invite";
    String constantGetPoint               = default_api + "api/customers/bonus_banner?version=%s&phone=%s&type=%s";
    String constantSendFriendFeedback     = default_api + "api/customers/%s/set_net_promoter_score";

    String constantActivateMember         = default_api + "api/customers/activate_membership";

    String constantRequestOtp              = default_api + "api/customers/request_otp";
    String constantCheckOtp                = default_api + "api/customers/check_otp";
    String constantPointInfo               = default_api + "api/customers/point_info?customer_id=";
    String constantInviteList              = default_api + "api/customers/invited_list?page=";
    String constantGetGiftExchangeList     = default_api + "api/gifts?version=%s&page=";
    String constantClaimGift               = default_api + "api/customers/%s/claim_gift";

    String constantPostCategory            = default_api + "api/posts/categories";
    String constantPostClickNews           = default_api + "api/posts?tag=%s&page=";
    String constantVideoAds                = default_api + "api/video_programs/%s/info";
    String constantToolCategories          = default_api + "api/tool_categories?version=";
    String constantToolList                = default_api + "api/tool_categories/list?version=";
    String constantclinic                  = default_api + "api/clinics";
    String constantschools                 = default_api + "api/schools";
    String constantCounts                  = default_api + "api/count?type=%s&id=%s&customer_id=%s";
    String constantMomolay                 = "http://www.momolay.com/api/kyarlay_index?page=";
    String constantMomolayDetails          = "http://www.momolay.com/api/posts/%s/kyarlay";
    String constantCommentUpdateDelete     = default_api + "api/comments/";
    String constantFortuneData             = default_api + "api/lucky_days/by_day?year=%s&month=%s&day=%s&fbclid=IwAR10C75TzYbepiiKNaLi_YqsCwev0rtLkEwypLMGq5IUGIQzL3iFDAxHIWE";
    String constantFortuneAllData          = default_api + "api/lucky_days/star?year=%s&month=%s&fbclid=IwAR10C75TzYbepiiKNaLi_YqsCwev0rtLkEwypLMGq5IUGIQzL3iFDAxHIWE";
    String constantNameListSearch          = default_api + "api/baby_names?length=%s&first_index=%s&second_index=%s&third_index=%s&forth_index=%s&gender=%s";
    String constantLikedNameList           = default_api + "api/baby_names/liked_baby_names?page=";
    String constantFlashSalesList          = default_api + "api/flash_sales?page=";
    String constantSuperCategories         = default_api + "api/super_categories";
    String constantMainBanner              = default_api + "api/main_banner";
    String constantSafeCategory            = default_api + "api/safe_categories/";
    String constantProductQuestions        = default_api + "api/product_questions";
    String constantProductQuesSearchID     = default_api + "api/product_questions/by_product?product_id=";
    String constantMainSuperCategory       = default_api + "api/super_categories/main";
    //String constantToolSuperCategory       = default_api + "api/super_tool_categories/main";
    String constantMainFlashSale           = default_api + "api/flash_sales/main";
    String constantMainDiscountList        = default_api + "api/discounts/main";
    String constantMainCollectionsList     = default_api + "api/product_collections/main";
    String constantMainPopularBrand        = default_api + "api/brands/main";
    String constantMainBrandPartner        = default_api + "api/brands/shops";
    //String constantCompetition             = default_api + "api/competitions";
    String constantCompetitionDelete       = default_api + "api/competitions/delete_entry" ;
   // String constantCompetitionVote         = default_api + "api/competitions/vote" ;
    String constantCompetitionSearch       = default_api + "api/competitions/search?photo_id=" ;
   // String constantCompetitionHasPhoto     = default_api + "api/competitions/has_photo" ;
    String constantCompetitionTerms        = default_api + "competitions/%s?" ;
    String constantShopLocations           = default_api + "api/store_locations?" ;
    String constantTownShipList            = default_api + "api/cities/master_list?";
    String constantAddressList             = default_api + "api/addresses";
    String constantPaymentList             = default_api + "api/payment_methods";
    String constantCheckDelivery           = default_api + "api/shopping_carts/check_delivery";
    String constantKyarlayAds              = default_api + "api/ads/?placement=";
    String constantAdsClick                = default_api + "api/ads/%s/click";
    String constantHotCategory             =default_api + "api/categories/top?";
    String constantProductNewList          = default_api + "api/products/new?";
    String constantProductTopList          = default_api + "api/products/top?";
    String constantMainNavigation          = default_api + "api/main_nav?";

    String constantCuponCode =  default_api + "api/coupon?code=";


}
