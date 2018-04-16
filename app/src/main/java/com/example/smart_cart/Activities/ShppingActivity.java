package com.example.smart_cart.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smart_cart.R;

public class ShppingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpping);

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
}
