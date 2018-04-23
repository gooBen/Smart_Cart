package com.example.smart_cart.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;


public class BluetoothThread extends Thread {
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final BluetoothSocket mmSocket;

    public BluetoothThread(BluetoothDevice bluetoothDevice){
        BluetoothSocket tmp = null;
        try{
            tmp = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);;
        }catch (IOException e){
        }
        mmSocket = tmp;
    }

        public void run(Context context) {
            try {
                mmSocket.connect();
                Toast.makeText(context,"Connected",Toast.LENGTH_LONG).show();
            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                    Toast.makeText(context,"fuck  fuck fuck fuck",Toast.LENGTH_LONG).show();
                } catch (IOException closeException) {
                }
                return;
            }
        }

        public void cancel(){
                try {
                    mmSocket.close();
                } catch (IOException e) { }
            }


}
