package com.example.onlinemarket.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.onlinemarket.model.product.Product;

@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_primary_id")
    private long primaryId;

    @ColumnInfo(name = "user_id")
    private long userId;

    @ColumnInfo(name = "product_id")
    private int product_id;

    @ColumnInfo(name = "product_count")
    private int product_count;


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
        return product_id;
    }

    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    public int getProductCount() {
        return product_count;
    }

    public void setProductCount(int product_count) {
        this.product_count = product_count;
    }

    public Cart(int product_id, int product_count) {
        this.product_id = product_id;
        this.product_count = product_count;
    }


}

