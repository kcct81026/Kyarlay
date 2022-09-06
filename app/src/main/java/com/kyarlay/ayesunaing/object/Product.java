package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class Product extends UniversalPost {

    int priId;
    @SerializedName("id")
    int id;
    @SerializedName("price")
    int price;
    @SerializedName("discount")
    int discount;

    @SerializedName("point_usage")
    int point_usage;
    @SerializedName("contact")
    String phoneNo;
    @SerializedName("category_id")
    int categoryId;
    @SerializedName("title")
    String title;
    @SerializedName("desc")
    String desc;
    @SerializedName("desc_title")
    String descTitle;
    @SerializedName("youtube_id")
    String youtubeId;
    @SerializedName("status")
    String status;
    @SerializedName("code")
    String code;
    @SerializedName("preview_img_url")
    String previewImage;
    @SerializedName("post_type")
    String postType;
    @SerializedName("youtube_image_url")
    String youtubeImageUrl;
    @SerializedName("product_url")
    String productUrl;
    @SerializedName("youtube_image_dimen")
    int youtubeImageDimen;
    @SerializedName("brand_id")
    int brand_id;
    @SerializedName("recommended")
    String recommended;
    @SerializedName("member_discount")
    int member_discount;
    @SerializedName("brand_name")
    String brand_name;
    @SerializedName("category_name")
    String category_name;

    @SerializedName("options")
    String options;



    @SerializedName("likes")
    int likes;

    @SerializedName("discounted_price")
    int discountedPrice;
    @SerializedName("supplier")
    Supplier supplier;
    @SerializedName("images")
    List<Images> images = new ArrayList<>();
    @SerializedName("discounts")
    List<Discount> discounts = new ArrayList<>();

    @SerializedName("channel")
    String channel;

    @SerializedName("channel_message")
    String channel_message;

    @SerializedName("detail_images")
    List<Images> detailImages = new ArrayList<>();


    @SerializedName("flash_sales")
    List<Flash_Sales> flashSalesArrayList = new ArrayList<>();

    @SerializedName("questions")
    List<QAObject> qaObjectList = new ArrayList<>();

    @SerializedName("stock_summaries")
    List<StockSummeries> stockSummeriesList = new ArrayList<>();

    String productType;
    int count;

    public Product() {
    }

    public List<StockSummeries> getStockSummeriesList() {
        return stockSummeriesList;
    }

    public void setStockSummeriesList(List<StockSummeries> stockSummeriesList) {
        this.stockSummeriesList = stockSummeriesList;
    }

    public List<QAObject> getQaObjectList() {
        return qaObjectList;
    }

    public void setQaObjectList(List<QAObject> qaObjectList) {
        this.qaObjectList = qaObjectList;
    }

    public int getPoint_usage() {
        return point_usage;
    }

    public void setPoint_usage(int point_usage) {
        this.point_usage = point_usage;
    }

    public List<Flash_Sales> getFlashSalesArrayList() {
        return flashSalesArrayList;
    }

    public void setFlashSalesArrayList(List<Flash_Sales> flashSalesArrayList) {
        this.flashSalesArrayList = flashSalesArrayList;
    }

    public String getChannel_message() {
        return channel_message;
    }

    public void setChannel_message(String channel_message) {
        this.channel_message = channel_message;
    }

    public List<Images> getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(List<Images> detailImages) {
        this.detailImages = detailImages;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public int getMember_discount() {
        return member_discount;
    }

    public void setMember_discount(int member_discount) {
        this.member_discount = member_discount;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getDescTitle() {
        return descTitle;
    }

    public void setDescTitle(String descTitle) {
        this.descTitle = descTitle;
    }

    public String getYoutubeImageUrl() {
        return youtubeImageUrl;
    }

    public void setYoutubeImageUrl(String youtubeImageUrl) {
        this.youtubeImageUrl = youtubeImageUrl;
    }

    public int getYoutubeImageDimen() {
        return youtubeImageDimen;
    }

    public void setYoutubeImageDimen(int youtubeImageDimen) {
        this.youtubeImageDimen = youtubeImageDimen;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Override
    public String getPostType() {
        return postType;
    }

    @Override
    public void setPostType(String postType) {
        this.postType = postType;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPriId() {
        return priId;
    }

    public void setPriId(int priId) {
        this.priId = priId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }



    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {

            return new Product[size];
        }

    };

    public Product(Parcel in) {
        super();


        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        priId               = in.readInt();
        id                  = in.readInt();
        price               = in.readInt();
        discount            = in.readInt();
        point_usage         = in.readInt();
        phoneNo             = in.readString();
        title               = in.readString();
        desc                = in.readString();
        descTitle           = in.readString();
        youtubeId           = in.readString();
        youtubeImageUrl     = in.readString();
        youtubeImageDimen   = in.readInt();
        status              = in.readString();
        code                = in.readString();
        previewImage        = in.readString();
        postType            = in.readString();
        productType         = in.readString();
        categoryId          = in.readInt();
        discountedPrice     = in.readInt();
        brand_id            = in.readInt();
        recommended         = in.readString();
        member_discount     = in.readInt();
        brand_name          = in.readString();
        productUrl          = in.readString();
        category_name       = in.readString();
        count               = in.readInt();
        likes               = in.readInt();
        supplier            = in.readParcelable(Supplier.class.getClassLoader());
        in.readTypedList(images, Images.CREATOR);
        in.readTypedList(detailImages, Images.CREATOR);
        in.readTypedList(discounts, Discount.CREATOR);
        in.readTypedList(flashSalesArrayList, Flash_Sales.CREATOR);
        in.readTypedList(qaObjectList, QAObject.CREATOR);
        in.readTypedList(stockSummeriesList, StockSummeries.CREATOR);
        options             = in.readString();
        channel             = in.readString();
        channel_message     = in.readString();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(priId);
        dest.writeInt(id);
        dest.writeInt(price);
        dest.writeInt(discount);
        dest.writeInt(point_usage);
        dest.writeString(phoneNo);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(descTitle);
        dest.writeString(youtubeId);
        dest.writeString(youtubeImageUrl);
        dest.writeInt(youtubeImageDimen);
        dest.writeString(status);
        dest.writeString(code);
        dest.writeString(previewImage);
        dest.writeString(postType);
        dest.writeString(productType);
        dest.writeInt(categoryId);
        dest.writeInt(discountedPrice);
        dest.writeInt(brand_id);
        dest.writeString(recommended);
        dest.writeInt(member_discount);
        dest.writeString(brand_name);
        dest.writeString(productUrl);
        dest.writeString(category_name);
        dest.writeInt(count);
        dest.writeInt(likes);
        dest.writeParcelable(supplier, flags);
        dest.writeTypedList(images);
        dest.writeTypedList(detailImages);
        dest.writeTypedList(discounts);
        dest.writeTypedList(flashSalesArrayList);
        dest.writeTypedList(qaObjectList);
        dest.writeTypedList(stockSummeriesList);
        dest.writeString(options);
        dest.writeString(channel);
        dest.writeString(channel_message);



    }
    @Override
    public int describeContents() {
        return 0;
    }


}
