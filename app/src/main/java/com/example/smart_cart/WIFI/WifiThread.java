package com.example.smart_cart.WIFI;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smart_cart.DataBase.productDoItm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;


public class WifiThread extends Thread{
    private Handler handler;
    private String piIPAddress;

    public WifiThread(Handler handler, String piIPAddress)
    {
        this.handler = handler;
        this.piIPAddress = piIPAddress;
    }

    @Override
    public void run()
    {
        String content = null;
        Socket socket = null;

        try{
            socket = new Socket(piIPAddress, 1234);
            BufferedReader bff = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            while((content = bff.readLine())!= null)
           {
               Message m = new Message();
               m.what = 1;
               m.obj = content;
               handler.sendMessage(m);
           }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
