package com.example.smart_cart.Activities;

import android.arch.persistence.room.Room;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.smart_cart.Bluetooth.BluetoothThread;
import com.example.smart_cart.DataBase.productDoItm;
import com.example.smart_cart.Model.Cart;
import com.example.smart_cart.Model.Product;
import com.example.smart_cart.R;
import java.util.ArrayList;


public class ShppingActivity extends AppCompatActivity{
    private final static int  REQUEST_ENABLE_BT = 1;
    private ListView productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpping);
        final Cart cart = new Cart();
        productList = findViewById(R.id.productList);

        //get the parameter for last activity
        final Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        String parameter = bundle.getString("str");


        /* Bluetooth */
        //search phone bluetooth
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            Toast.makeText(this,"Device does not support Bluetooth",Toast.LENGTH_LONG).show();
        }
        else {
            //open bluetooth
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            //find the device by mac address
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(parameter);
            Toast.makeText(this,bluetoothDevice.getName(),Toast.LENGTH_LONG).show();
            BluetoothThread bluetoothThread = new BluetoothThread(bluetoothDevice);
            bluetoothAdapter.cancelDiscovery();
            bluetoothThread.run(this);

            //get product from bar
            String bar;
            bar = bluetoothThread.getBarFromeBluetooth();
            productDoItm db = Room.databaseBuilder(getApplicationContext(),productDoItm.class,"production").allowMainThreadQueries().build();
            Product product = db.productDao().findProductWithBar("9300601259373");
            Product product1 = db.productDao().findProductWithBar("9310112092826");
            //send product to UI
            cart.shoppeOneProduct(product);
            cart.shoppeOneProduct(product1);
            ArrayList<String>cartProduct = cart.getProductsName();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShppingActivity.this,android.R.layout.simple_list_item_1,cartProduct);
            productList.setAdapter(adapter);

        }

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
      if(requestCode == RESULT_CANCELED)
      {
          Toast.makeText(this, "Bluetooth must be opened to continue", Toast.LENGTH_SHORT).show();
          finish();
      }
    }
}
