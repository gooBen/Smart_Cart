package com.example.smart_cart.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.smart_cart.Model.Product;

import java.util.List;
@Dao
public interface productDao {
    @Query("SELECT * FROM product")
    List<Product> getAllProduct();

    @Insert
    void insertAll(Product... products);
}
