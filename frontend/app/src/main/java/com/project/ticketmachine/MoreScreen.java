package com.project.ticketmachine;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.project.ticketmachine.databinding.ActivityMoreScreenBinding;
import com.project.ticketmachine.databinding.CheckCardBinding;

public class MoreScreen extends AppCompatActivity {

    ActivityMoreScreenBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMoreScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding.cardInfoBox.setOnClickListener(view -> {
            Intent myIntent = new Intent(MoreScreen.this, CheckCard.class);
            myIntent.putExtra("key" , "info");
            MoreScreen.this.startActivity(myIntent);
        });

        binding.codeRechargeBox.setOnClickListener(view -> {
            Intent myIntent = new Intent(MoreScreen.this, RechargeCode.class);
            MoreScreen.this.startActivity(myIntent);
        });

        binding.eWalletBox.setOnClickListener(view -> {

        });

        binding.backButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(MoreScreen.this, MainActivity.class);
            MoreScreen.this.startActivity(myIntent);
        });

    }
}
