package com.example.smart_cart.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smart_cart.R;

public class Check_out extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        double cost = bundle.getDouble("totalCost");


        final TextView costTxt = findViewById(R.id.totalCostTxt);
        costTxt.setText("AUD$ "+ String.valueOf(cost));

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
}
