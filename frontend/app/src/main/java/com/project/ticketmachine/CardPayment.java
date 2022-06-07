package com.project.ticketmachine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.ticketmachine.databinding.CardPaymentBinding;

public class CardPayment extends AppCompatActivity {

    private CardPaymentBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = CardPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // previous screen
        binding.paymentBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CardPayment.this, Payment.class);
                CardPayment.this.startActivity(myIntent);
            }
        });

        // cancel transaction
        binding.paymentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CardPayment.this, MainActivity.class);
                CardPayment.this.startActivity(myIntent);
            }
        });

        // pay and update database
        binding.completePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Payment.doInPayment();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}