package com.project.ticketmachine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.project.ticketmachine.databinding.EWalletBinding;

public class Ewallet extends AppCompatActivity {

    EWalletBinding binding;
    private int final_s = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = EWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding.cancelButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(Ewallet.this, MainActivity.class);
            Ewallet.this.startActivity(myIntent);
        });

        binding.backButton.setOnClickListener(view -> {
            if (binding.cashLayout.getVisibility() == View.VISIBLE || binding.cardLayout.getVisibility() == View.VISIBLE){
                binding.cashBox.setVisibility(View.VISIBLE);
                binding.cardBox.setVisibility(View.VISIBLE);
                binding.choose.setVisibility(View.VISIBLE);

                binding.cashLayout.setVisibility(View.INVISIBLE);
                binding.cardLayout.setVisibility(View.INVISIBLE);
            }
            else{
                Intent myIntent = new Intent(Ewallet.this, MoreScreen.class);
                Ewallet.this.startActivity(myIntent);
            }

        });

        binding.cashBox.setOnClickListener(view -> {
            binding.cashBox.setVisibility(View.INVISIBLE);
            binding.cardBox.setVisibility(View.INVISIBLE);
            binding.choose.setVisibility(View.INVISIBLE);

            binding.cardLayout.setVisibility(View.INVISIBLE);
            binding.cashLayout.setVisibility(View.VISIBLE);

        });

        binding.cardBox.setOnClickListener(view -> {
            binding.cashBox.setVisibility(View.INVISIBLE);
            binding.cardBox.setVisibility(View.INVISIBLE);
            binding.choose.setVisibility(View.INVISIBLE);

            binding.cashLayout.setVisibility(View.INVISIBLE);
            binding.cardLayout.setVisibility(View.VISIBLE);

        });

        binding.completePayment.setOnClickListener(view -> {
            if (binding.insertMoneyEditText.length() > 0){
                final_s += Integer.parseInt(binding.insertMoneyEditText.getText().toString());
                binding.summaryMoneyText.setText("Σύνολο: "+final_s + "€");
            }
        });
    }
}
