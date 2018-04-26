package com.example.smart_cart.Bluetooth;


import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;

public class BluetoothConnectedThread extends Thread {
    private final BluetoothSocket mysocket;
    private final InputStream myInStream;

    public BluetoothConnectedThread(BluetoothSocket socket){
        mysocket = socket;
        InputStream tmpIn = null;
         try{
             tmpIn = socket.getInputStream();
         }catch (IOException e){
         }
         myInStream = tmpIn;
    }

    public void getData(String returnData){
        byte[] buffer = new byte[1024];
        String data;

//        while(true){
//            try{
////                data = myInStream.read(buffer);
////                returnData = data;
//              }catch (IOException e){
//                break;
//            }
//        }
    }

    public void cancel(){
        try{
            mysocket.close();
        }catch (IOException e){}
    }
}
