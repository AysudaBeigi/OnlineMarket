package com.example.onlinemarket.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.onlinemarket.model.product.Product;

import java.util.List;
@Dao
public interface ProductDAO {

    @Insert
    void insertProduct(Product productsItem);

    @Delete
    void deleteProduct(Product productsItem);

    @Query("SELECT * FROM productTable WHERE id=:inputId")
    Product getProductItem(int inputId);

    @Query("SELECT * FROM productTable")
    List<Product> getProducts();


}
