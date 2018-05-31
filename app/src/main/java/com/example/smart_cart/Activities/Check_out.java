package com.example.smart_cart.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smart_cart.R;

import java.util.Calendar;

public class Check_out extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        double cost = bundle.getDouble("totalCost");
        int amount = bundle.getInt("amount");

        final TextView costTxt = findViewById(R.id.totalCostTxt);
        costTxt.setText("AUD$ "+ String.format("%.2f",cost));

        final TextView tatalAmount = findViewById(R.id.shoppingAmount);
        tatalAmount.setText(String.valueOf(amount));

        final TextView systemTime = findViewById(R.id.shoppingTime);
        //get system time
        Calendar calendar = Calendar.getInstance();
        String time = calendar.get(Calendar.YEAR)+"-"+TimeFormat(calendar.get(Calendar.MONTH)+1)+"-"+ TimeFormat(calendar.get(Calendar.DAY_OF_MONTH))+" "+TimeFormat(calendar.get(Calendar.HOUR_OF_DAY))+":"+TimeFormat(calendar.get(Calendar.MINUTE))+":"+TimeFormat(calendar.get(Calendar.SECOND));
        systemTime.setText(time);


        final Button finishBtn = findViewById(R.id.finishBtn);
        //set the button listener
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Check_out.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private  String TimeFormat(int time)
    {
        return time>=10?""+time:"0"+time;
    }
}
