package com.example.smart_cart.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.smart_cart.DataBase.productDao;
import com.example.smart_cart.Model.Product;

@Database(entities = {Product.class},version = 1,exportSchema = false)
public abstract class productDoItm extends RoomDatabase{
    public  abstract productDao productDao();
}
