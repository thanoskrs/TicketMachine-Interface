package com.project.ticketmachine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.ticketmachine.databinding.CashPaymentBinding;

public class CashPayment extends AppCompatActivity {

    private CashPaymentBinding binding;
    private float demandedPrice;
    private float insertedMoney = 0.f;
    private float remainingPrice;
    private float change = 0.f;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = CashPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        demandedPrice = Float.parseFloat(getIntent().getStringExtra("demandedPrice").replace(',', '.'));
        remainingPrice = demandedPrice;
        String remainingMoneyText = binding.remainingMoneyText.getText().toString();

        binding.remainingMoneyText.setText(remainingMoneyText + String.format("%.2f", remainingPrice) +"€");

        // previous screen
        binding.paymentBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // cancel transaction
        binding.paymentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CashPayment.this, MainActivity.class);
                CashPayment.this.startActivity(myIntent);
            }
        });

        // pay if demanded money is gained and update database
        binding.completePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float inserted  = 0.f;
                try {
                    inserted = Float.parseFloat(binding.insertMoneyEditText.getText().toString());

                    if (inserted <= 0.f)
                        inserted = 0.f;
                } catch (NumberFormatException numberFormatException) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG);
                    toast.show();
                }

                insertedMoney = insertedMoney + inserted;
                remainingPrice -= inserted;

                if (remainingPrice >= 0) {
                    binding.remainingMoneyText.setText(remainingMoneyText + String.format("%.2f", remainingPrice) + "€");
                } else {
                    binding.remainingMoneyText.setText(remainingMoneyText +"0€");
                    change = -remainingPrice;
                }

                if (remainingPrice <= 0) {
                    //Payment.doInPayment();

                    Intent myIntent = new Intent(CashPayment.this, OnPostPayment.class);
                    CashPayment.this.startActivity(myIntent);
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}