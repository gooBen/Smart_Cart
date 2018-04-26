package com.example.smart_cart.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
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
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String bar = bundle.getString("itemBar");


        productDoItm db = Room.databaseBuilder(getApplicationContext(),productDoItm.class,"production")
                .allowMainThreadQueries()
                .build();
       Product item = db.productDao().findProductWithBar(bar);

        final TextView barTxt = findViewById(R.id.barTxt);
        final TextView name = findViewById(R.id.nameTxt);
        final TextView price = findViewById(R.id.priceTxt);
        final TextView description = findViewById(R.id.descriptionTxt);
        barTxt.setText(item.getBar());
        name.setText(item.getName());
        price.setText(String.valueOf(item.getPrice()));
        description.setText(item.getDescription());

    }
}
