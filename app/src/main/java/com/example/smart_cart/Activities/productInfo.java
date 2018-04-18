package com.example.smart_cart.Activities;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.smart_cart.Model.Product;
import com.example.smart_cart.R;

import com.example.smart_cart.DataBase.productDoItm;

import java.util.List;

public class productInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        productDoItm db = Room.databaseBuilder(getApplicationContext(),productDoItm.class,"production")
                .allowMainThreadQueries()
                .build();

        List<Product> products = db.productDao().getAllProduct();

        final TextView bar = findViewById(R.id.barTxt);
        final TextView name = findViewById(R.id.nameTxt);
        final TextView price = findViewById(R.id.priceTxt);

        bar.setText(String.valueOf(products.get(1).getBar()));
        name.setText(products.get(1).getName());
        price.setText(String.valueOf(products.get(1).getPrice()));
    }
}
