package com.project.ticketmachine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.ticketmachine.databinding.OnPostPaymentBinding;

import java.sql.Time;
import java.util.Timer;

public class OnPostPayment extends AppCompatActivity {

    OnPostPaymentBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = OnPostPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getStringExtra("act").equals("pay"))
            Payment.doInPayment();

        binding.loadingPanel.setVisibility(View.INVISIBLE);
        binding.info.setVisibility(View.VISIBLE);

        if (getIntent().getStringExtra("act").equals("recharge")){
            binding.receiveTicketText.setText("Παρακαλώ παραλάβετε το εισιτήριο και την απόδειξή σας");
        }
        else{
            if (Payment.type == null){
                binding.receiveTicketText.setText("Παρακαλώ παραλάβετε την κάρτα και την απόδειξή σας");
            }
            else{
                if (Payment.type.equals("Card")) {
                    binding.receiveTicketText.setText("Παρακαλώ παραλάβετε την κάρτα και την απόδειξή σας");
                } else {
                    binding.receiveTicketText.setText("Παρακαλώ παραλάβετε το εισιτήριο και την απόδειξή σας");
                }
            }

        }

    }

}
