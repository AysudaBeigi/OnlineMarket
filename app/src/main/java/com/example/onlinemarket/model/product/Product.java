package com.example.onlinemarket.model.product;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "productTable")
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primaryId")
    private int primaryId;


    @SerializedName("type")
    @ColumnInfo(name = "type")
    private String type;

    @SerializedName("external_url")
    @ColumnInfo(name = "externalUrl")
    private String externalUrl;

    @SerializedName("price")
    @ColumnInfo(name = "price")
    private String price;

    @SerializedName("meta_data")
    @ColumnInfo(name = "metaData")
    private List<Object> metaData;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("sku")
    @ColumnInfo(name = "sku")
    private String sku;

    @SerializedName("slug")
    @ColumnInfo(name = "slug")
    private String slug;

    @SerializedName("date_on_sale_from")
    @ColumnInfo(name = "dateOnSaleFrom")
    private Object dateOnSaleFrom;

    @SerializedName("shipping_required")
    @ColumnInfo(name = "shippingRequired")
    private boolean shippingRequired;

    @SerializedName("date_modified_gmt")
    @ColumnInfo(name = "dateModifiedGmt")
    private String dateModifiedGmt;

    @SerializedName("images")
    @ColumnInfo(name = "images")
    private List<Image> images;


    @SerializedName("price_html")
    @ColumnInfo(name = "priceHtml")
    private String priceHtml;


    @SerializedName("rating_count")
    @ColumnInfo(name = "ratingCount")
    private int ratingCount;


    @SerializedName("date_on_sale_to")
    @ColumnInfo(name = "dateOnSaleTo")
    private Object dateOnSaleTo;

    @SerializedName("sold_individually")
    @ColumnInfo(name = "soldIndividually")
    private boolean soldIndividually;


    @SerializedName("parent_id")
    @ColumnInfo(name = "parentId")
    private int parentId;


    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("shipping_class")
    @ColumnInfo(name = "shippingClass")
    private String shippingClass;

    @SerializedName("short_description")
    @ColumnInfo(name = "shortDescription")
    private String shortDescription;


    @SerializedName("menu_order")
    private int menuOrder;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String description;

    @SerializedName("date_on_sale_to_gmt")
    @ColumnInfo(name = "dateOnSaleToGmt")
    private Object dateOnSaleToGmt;

    @SerializedName("date_on_sale_from_gmt")
    @ColumnInfo(name = "dateOnSaleFromGmt")
    private Object dateOnSaleFromGmt;

    @SerializedName("regular_price")
    @ColumnInfo(name = "regularPrice")
    private String regularPrice;


    @SerializedName("reviews_allowed")
    @ColumnInfo(name = "reviewsAllowed")
    private boolean reviewsAllowed;

    @SerializedName("variations")
    @ColumnInfo(name = "variations")
    private List<Object> variations;

    @SerializedName("categories")
    @ColumnInfo(name = "categories")
    private List<Category> categories;

    @SerializedName("total_sales")
    @ColumnInfo(name = "totalSales")
    private int totalSales;

    @SerializedName("on_sale")
    @ColumnInfo(name = "onSale")
    private boolean onSale;


    @SerializedName("default_attributes")
    @ColumnInfo(name = "defaultAttributes")
    private List<Object> defaultAttributes;

    @SerializedName("purchase_note")
    @ColumnInfo(name = "purchaseNote")
    private String purchaseNote;

    @SerializedName("date_created")
    @ColumnInfo(name = "dateCreated")
    private String dateCreated;

    @SerializedName("tax_class")
    @ColumnInfo(name = "taxClass")
    private String taxClass;

    @SerializedName("date_created_gmt")
    @ColumnInfo(name = "dateCreatedGmt")
    private String dateCreatedGmt;

    @SerializedName("average_rating")
    @ColumnInfo(name = "averageRating")
    private String averageRating;

    @SerializedName("stock_quantity")
    @ColumnInfo(name = "stockQuantity")
    private Object stockQuantity;

    @SerializedName("sale_price")
    @ColumnInfo(name = "salePrice")
    private String salePrice;

    @SerializedName("shipping_class_id")
    @ColumnInfo(name = "shippingClassId")
    private int shippingClassId;

    @SerializedName("date_modified")
    @ColumnInfo(name = "dateModified")
    private String dateModified;


    @SerializedName("attributes")
    @ColumnInfo(name = "attributes")
    private List<Object> attributes;

    @ColumnInfo(name = "allPrice")
    private Double allPrice;

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }


    public String getType() {
        return type;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public String getPrice() {
        return price;
    }

    public List<Object> getMetaData() {
        return metaData;
    }

    public int getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getSlug() {
        return slug;
    }

    public Object getDateOnSaleFrom() {
        return dateOnSaleFrom;
    }

    public boolean isShippingRequired() {
        return shippingRequired;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public List<Image> getImages() {
        return images;
    }


    public String getPriceHtml() {
        return priceHtml;
    }


    public int getRatingCount() {
        return ratingCount;
    }

    /*public List<TagsItem> getTags() {
        return tags;
    }*/

    public Object getDateOnSaleTo() {
        return dateOnSaleTo;
    }

    public boolean isSoldIndividually() {
        return soldIndividually;
    }


    public int getParentId() {
        return parentId;
    }


    public String getName() {
        return name;
    }

    public String getShippingClass() {
        return shippingClass;
    }


    public String getShortDescription() {
        return shortDescription;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public String getDescription() {
        return description;
    }

    public Object getDateOnSaleToGmt() {
        return dateOnSaleToGmt;
    }

    public Object getDateOnSaleFromGmt() {
        return dateOnSaleFromGmt;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public boolean isReviewsAllowed() {
        return reviewsAllowed;
    }

    public List<Object> getVariations() {
        return variations;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public boolean isOnSale() {
        return onSale;
    }


    public int getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setMetaData(List<Object> metaData) {
        this.metaData = metaData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDateOnSaleFrom(Object dateOnSaleFrom) {
        this.dateOnSaleFrom = dateOnSaleFrom;
    }

    public void setShippingRequired(boolean shippingRequired) {
        this.shippingRequired = shippingRequired;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }


    public void setPriceHtml(String priceHtml) {
        this.priceHtml = priceHtml;
    }


    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

   /* public void setTags(List<TagsItem> tags) {
        this.tags = tags;
    }*/

    public void setDateOnSaleTo(Object dateOnSaleTo) {
        this.dateOnSaleTo = dateOnSaleTo;
    }

    public void setSoldIndividually(boolean soldIndividually) {
        this.soldIndividually = soldIndividually;
    }


    public void setParentId(int parentId) {
        this.parentId = parentId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setShippingClass(String shippingClass) {
        this.shippingClass = shippingClass;
    }


    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateOnSaleToGmt(Object dateOnSaleToGmt) {
        this.dateOnSaleToGmt = dateOnSaleToGmt;
    }

    public void setDateOnSaleFromGmt(Object dateOnSaleFromGmt) {
        this.dateOnSaleFromGmt = dateOnSaleFromGmt;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public void setReviewsAllowed(boolean reviewsAllowed) {
        this.reviewsAllowed = reviewsAllowed;
    }

    public void setVariations(List<Object> variations) {
        this.variations = variations;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public void setDefaultAttributes(List<Object> defaultAttributes) {
        this.defaultAttributes = defaultAttributes;
    }

    public void setPurchaseNote(String purchaseNote) {
        this.purchaseNote = purchaseNote;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public void setStockQuantity(Object stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public void setShippingClassId(int shippingClassId) {
        this.shippingClassId = shippingClassId;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    public List<Object> getDefaultAttributes() {
        return defaultAttributes;
    }

    public String getPurchaseNote() {
        return purchaseNote;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public Object getStockQuantity() {
        return stockQuantity;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public int getShippingClassId() {
        return shippingClassId;
    }

    public String getDateModified() {
        return dateModified;
    }

    public List<Object> getAttributes() {
        return attributes;
    }


}
