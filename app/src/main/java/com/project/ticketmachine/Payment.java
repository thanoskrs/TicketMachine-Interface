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

        MaterialButton backBtn = (MaterialButton) findViewById(R.id.payment_back_button);
        MaterialButton cancelBtn = (MaterialButton) findViewById(R.id.payment_cancel_button);

        TextView productText = (TextView) findViewById(R.id.product_chosen_text);
        TextView priceText = (TextView) findViewById(R.id.product_price_chosen_text);
        TextView totalPriceText = (TextView) findViewById(R.id.total_price_text);

        String product = getIntent().getStringExtra("product");
        String price = getIntent().getStringExtra("price");

        productText.setText(product);
        priceText.setText(priceText.getText().toString() + price);
        totalPriceText.setText(totalPriceText.getText().toString() + price);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Payment.this, ProductScreen.class);
                myIntent.putExtra("key", "card");
                Payment.this.startActivity(myIntent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Payment.this, MainActivity.class);
                Payment.this.startActivity(myIntent);
            }
        });
    }
}
