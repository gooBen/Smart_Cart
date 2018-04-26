package com.example.smart_cart.Activities;

import android.app.Activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smart_cart.DataBase.productDoItm;
import com.example.smart_cart.Model.Product;
import com.example.smart_cart.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int ACTION__REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //first create the database
        productDoItm db = Room.databaseBuilder(getApplicationContext(),productDoItm.class,"production").allowMainThreadQueries().build();
        Product product1 = new Product("9300601259373", "Australian natural spring water (1.5L)",8.8,"Spring water in NSW! And healthy recycled plastic, the water is 5 star services");
        Product product2 = new Product("9310112092826","Susan Day Cakes",8.8,"Healthy wheat water, egg powder, humectant, vegetable, emulsifiers, raising agents, salt, wheat starch, lamington");
        db.productDao().insertAll(product1,product2);

        //set the button action
        final Button startBtn = findViewById(R.id.startBtn);
        final Activity activity = this;
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator= new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                        .setPrompt("Please on the QR code")  //note
                        .setCameraId(0)                          //use back camera
                        .setBeepEnabled(false)                   //sound used
                        .setBarcodeImageEnabled(true)
                        .initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this,"Cancelled",Toast.LENGTH_LONG).show();
            }
            else{
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ShppingActivity.class);
                intent.putExtra("str", result.getContents());
                startActivity(intent);
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
