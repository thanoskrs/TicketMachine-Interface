package com.project.ticketmachine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.project.ticketmachine.databinding.ActivityMoreScreenBinding;
import com.project.ticketmachine.databinding.CheckCardBinding;

public class MoreScreen extends AppCompatActivity {

    ActivityMoreScreenBinding binding;
    InitializeTextToSpeach initializeTextToSpeach;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMoreScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeTextToSpeach = new InitializeTextToSpeach(getApplicationContext());

        final Handler handler = new Handler();

        if (MainActivity.TTS) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initializeTextToSpeach.speak("Επιλέξτε επιθυμητή ενέργεια");
                }
            }, 100);
        }

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
            Intent myIntent = new Intent(MoreScreen.this, CheckCard.class);
            myIntent.putExtra("key" , "e-wallet");
            MoreScreen.this.startActivity(myIntent);
        });

        binding.backButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(MoreScreen.this, MainActivity.class);
            MoreScreen.this.startActivity(myIntent);
        });

//        binding.studentBox.setOnClickListener(view -> {
//
//        });

    }

    @Override
    protected void onDestroy() {
        binding = null;
        initializeTextToSpeach.destroy();
        super.onDestroy();
    }
}
