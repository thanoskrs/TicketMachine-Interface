package com.project.ticketmachine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.ticketmachine.databinding.OnPostPaymentBinding;

import java.sql.Time;
import java.util.Timer;

public class OnPostPayment extends AppCompatActivity {

    OnPostPaymentBinding binding;
    private static int TIME_OUT = 4000; //Time to launch the another activity

    InitializeTextToSpeach initializeTextToSpeach;
    String text = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeTextToSpeach = new InitializeTextToSpeach(getApplicationContext());


        binding = OnPostPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getStringExtra("act").equals("pay"))
            Payment.doInPayment();

        binding.loadingPanel.setVisibility(View.INVISIBLE);
        binding.info.setVisibility(View.VISIBLE);



        if (getIntent().getStringExtra("act").equals("recharge")){
            text = "Παρακαλώ παραλάβετε το εισιτήριο και την απόδειξη σας";
            binding.receiveTicketText.setText(text);
        }
        else{
            if (Payment.type == null){
                text = "Παρακαλώ παραλάβετε την κάρτα και την απόδειξη σας";
                binding.receiveTicketText.setText(text);
            }
            else{
                if (Payment.type.equals("Card")) {
                    text = "Παρακαλώ παραλάβετε την κάρτα και την απόδειξη σας";
                    binding.receiveTicketText.setText(text);
                } else {
                    text = "Παρακαλώ παραλάβετε το εισιτήριο και την απόδειξη σας";
                    binding.receiveTicketText.setText(text);
                }
            }

        }

        final Handler handler = new Handler();

        if (MainActivity.TTS) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initializeTextToSpeach.speak(text);
                }
            }, 500);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(OnPostPayment.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }

    @Override
    protected void onDestroy() {
        initializeTextToSpeach.destroy();
        super.onDestroy();
    }
}
