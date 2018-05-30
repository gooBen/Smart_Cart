package com.example.smart_cart.Activities;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import com.example.smart_cart.DataBase.productDoItm;
import com.example.smart_cart.DeleteClickListener;
import com.example.smart_cart.Model.Cart;
import com.example.smart_cart.Model.Product;
import com.example.smart_cart.R;
import com.example.smart_cart.ShoppingListView;
import com.example.smart_cart.WIFI.WifiThread;

import java.util.ArrayList;

import static android.widget.Toast.makeText;


public class ShppingActivity extends AppCompatActivity{
    private final static int  REQUEST_ENABLE_BT = 1;
    private ShoppingListView productList;
    final Cart cart = new Cart();
    productDoItm db;
    private Handler handler;
    //product list
    ArrayAdapter<String> adapter; //listView adapter


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpping);
        productList = findViewById(R.id.productList);

        //get the parameter for last activity
        final Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        String IP = bundle.getString("IP");



        //test only//////////////////////////////////////////////////////////////////////////////
        db = Room.databaseBuilder(getApplicationContext(),productDoItm.class,"production").allowMainThreadQueries().build();
        Product product = db.productDao().findProductWithBar("9300601259373");//m.obj.toString()
        Product product1 = db.productDao().findProductWithBar("9300601259373");//m.obj.toString()
        Product product2 = db.productDao().findProductWithBar("9300601259373");//m.obj.toString()
        Product product3 = db.productDao().findProductWithBar("9300601259373");//m.obj.toString()
        //send product to UI
        cart.shoppeOneProduct(product);
        cart.shoppeOneProduct(product1);
        cart.shoppeOneProduct(product2);
        cart.shoppeOneProduct(product3);
        ArrayList<String> cartProduct  = cart.getProductsName();
        adapter = new ArrayAdapter<String>(ShppingActivity.this,android.R.layout.simple_list_item_1,cartProduct);
        productList.setAdapter(adapter);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //get the product bar from socket
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message m) {
//                switch (m.what) {
//                    case 1:
//                        db = Room.databaseBuilder(getApplicationContext(),productDoItm.class,"production").allowMainThreadQueries().build();
//                        Product product = db.productDao().findProductWithBar("9300601259373");//m.obj.toString()
//                        //send product to UI
//                        cart.shoppeOneProduct(product);
//                        cartProduct = cart.getProductsName();
//                        adapter = new ArrayAdapter<String>(ShppingActivity.this,android.R.layout.simple_list_item_1,cartProduct);
//                        productList.setAdapter(adapter);
//                        break;
//                }
//            }
//        };

        //WIFI
//        WifiManager mWifiManager = (WifiManager)this.getApplicationContext().getSystemService(WIFI_SERVICE);
//        if(mWifiManager == null){
//            makeText(this, "this diver not support wifi", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            WifiThread wifiThread = new WifiThread(handler, IP);
//            wifiThread.start();
//        }


        //set item click event
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemBar = null;
                itemBar = cart.getBarByIndex(position);
                Intent inforIntent = new Intent();
                inforIntent.setClass(ShppingActivity.this,productInfo.class);
                inforIntent.putExtra("itemBar",itemBar);
                startActivity(inforIntent);
            }
        });
      //set item delete event
        productList.setDelButtonClickListener(new DeleteClickListener() {
            @Override
            public void onClickDelete(int position) {
                cart.deleteFromCart(cart.getProductByIndex(position));
                ArrayList<String> cartProduct  = cart.getProductsName();
                adapter = new ArrayAdapter<String>(ShppingActivity.this,android.R.layout.simple_list_item_1,cartProduct);
                productList.setAdapter(adapter);
            }
        });

        //get the button by id
        final Button checket_outBtn = findViewById(R.id.checkOutBtn);
        //set the button listener
        checket_outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShppingActivity.this,Check_out.class);
                intent.putExtra("totalCost",cart.totalCost());
                startActivity(intent);
            }
        });


        final Button mapBtn = findViewById(R.id.mapBtn);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShppingActivity.this,Check_out.class);
            }
        });
    }
}
