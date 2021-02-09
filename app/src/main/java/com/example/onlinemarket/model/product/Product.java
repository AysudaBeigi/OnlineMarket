package com.example.onlinemarket.model.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {


    @SerializedName("type")
    private String type;

    @SerializedName("external_url")
    private String externalUrl;

    @SerializedName("price")
    private String price;

    @SerializedName("meta_data")
    private List<Object> metaData;

    @SerializedName("id")
    private int id;

    @SerializedName("sku")
    private String sku;

    @SerializedName("slug")
    private String slug;

    @SerializedName("date_on_sale_from")
    private Object dateOnSaleFrom;

    @SerializedName("shipping_required")
    private boolean shippingRequired;

    @SerializedName("date_modified_gmt")
    private String dateModifiedGmt;

    @SerializedName("images")
    private List<Image> images;


    @SerializedName("price_html")
    private String priceHtml;


    @SerializedName("rating_count")
    private int ratingCount;


    @SerializedName("date_on_sale_to")
    private Object dateOnSaleTo;

    @SerializedName("sold_individually")
    private boolean soldIndividually;


    @SerializedName("parent_id")
    private int parentId;


    @SerializedName("name")
    private String name;

    @SerializedName("shipping_class")
    private String shippingClass;

    @SerializedName("short_description")
    private String shortDescription;


    @SerializedName("menu_order")
    private int menuOrder;

    @SerializedName("description")
    private String description;

    @SerializedName("date_on_sale_to_gmt")
    private Object dateOnSaleToGmt;

    @SerializedName("date_on_sale_from_gmt")
    private Object dateOnSaleFromGmt;

    @SerializedName("regular_price")
    private String regularPrice;


    @SerializedName("reviews_allowed")
    private boolean reviewsAllowed;

    @SerializedName("variations")
    private List<Object> variations;

    @SerializedName("categories")
    private List<Category> categories;

    @SerializedName("total_sales")
    private int totalSales;

    @SerializedName("on_sale")
    private boolean onSale;


    @SerializedName("default_attributes")
    private List<Object> defaultAttributes;

    @SerializedName("purchase_note")
    private String purchaseNote;

    @SerializedName("date_created")
    private String dateCreated;

    @SerializedName("tax_class")
    private String taxClass;

    @SerializedName("date_created_gmt")
    private String dateCreatedGmt;

    @SerializedName("average_rating")
    private String averageRating;

    @SerializedName("stock_quantity")
    private Object stockQuantity;

    @SerializedName("sale_price")
    private String salePrice;

    @SerializedName("shipping_class_id")
    private int shippingClassId;

    @SerializedName("date_modified")
    private String dateModified;


    @SerializedName("attributes")
    private List<Object> attributes;

    @SerializedName("allPrice")
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
