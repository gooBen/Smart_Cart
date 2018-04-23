package com.example.smart_cart.Activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smart_cart.Bluetooth.BluetoothThread;
import com.example.smart_cart.R;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;
import java.util.jar.Attributes;

public class ShppingActivity extends AppCompatActivity {
    private final static int  REQUEST_ENABLE_BT = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpping);

        //get the parameter for last activity
        Intent intent=getIntent();
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
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                BluetoothDevice bluetoothDevice = findDeviceByAddress(pairedDevices, parameter);
                Toast.makeText(this, bluetoothDevice.getName(), Toast.LENGTH_LONG).show();
//                BluetoothThread bluetoothThread = new BluetoothThread(bluetoothDevice);
//                bluetoothAdapter.cancelDiscovery();
//                bluetoothThread.run(this);
            }
            //find the device by mac address
//            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(parameter);
//            Toast.makeText(this,bluetoothDevice.getName(),Toast.LENGTH_LONG).show();
//            BluetoothThread bluetoothThread = new BluetoothThread(bluetoothDevice);
//            bluetoothAdapter.cancelDiscovery();
//            bluetoothThread.run(this);

        }


        //get the button by id
        final Button checket_outBtn = findViewById(R.id.checkOutBtn);
        //set the button listener
        checket_outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShppingActivity.this,Check_out.class);
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

    public BluetoothDevice findDeviceByAddress(Set<BluetoothDevice> bluetoothDeviceset,String addresss){
        for(BluetoothDevice device:bluetoothDeviceset){
            if( device.getAddress() == addresss.toLowerCase())
            {
                return device;
            }
        }
        return null;
    }
}
