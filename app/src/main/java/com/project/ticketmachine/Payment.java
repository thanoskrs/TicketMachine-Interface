package com.project.ticketmachine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        MaterialButton backBtn = (MaterialButton) findViewById(R.id.payment_back);
        MaterialButton cancelBtn = (MaterialButton) findViewById(R.id.payment_cancel);

        TextView productText = (TextView) findViewById(R.id.product_chosen_text);
        TextView priceText = (TextView) findViewById(R.id.product_price_chosen_text);

        productText.setText(getIntent().getStringExtra("product"));
        priceText.setText(priceText.getText().toString() + getIntent().getStringExtra("price"));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
