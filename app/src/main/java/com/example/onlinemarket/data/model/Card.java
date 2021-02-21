package com.example.onlinemarket.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.onlinemarket.data.model.product.Product;

@Entity(tableName = "card")
public class Card {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_primary_id")
    private long primaryId;

    @ColumnInfo(name = "user_id")
    private long userId;

    @ColumnInfo(name = "product_id")
    private int productId;

    @ColumnInfo(name = "product_count")
    private int productCount;


    @ColumnInfo(name = "product")
    private Product product;


    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }


    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public Card(Product product, int productId, int productCount) {
        this.product=product;
        this.productId = productId;
        this.productCount = productCount;
    }


}

