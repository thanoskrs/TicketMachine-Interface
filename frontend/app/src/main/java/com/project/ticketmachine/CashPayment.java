package com.project.ticketmachine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.ticketmachine.databinding.CashPaymentBinding;

public class CashPayment extends AppCompatActivity {

    private CashPaymentBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = CashPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.paymentBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CashPayment.this, Payment.class);
                CashPayment.this.startActivity(myIntent);
            }
        });

        binding.paymentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CashPayment.this, MainActivity.class);
                CashPayment.this.startActivity(myIntent);
            }
        });

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