package com.project.ticketmachine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.project.ticketmachine.databinding.EWalletBinding;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.MissingFormatArgumentException;

public class Ewallet extends AppCompatActivity {

    protected static EWalletBinding binding;
    Button[] arrayOfButtons;
    public static String amount = "";
    InitializeTextToSpeach initializeTextToSpeach;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = EWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeTextToSpeach = new InitializeTextToSpeach(getApplicationContext());
        final Handler handler = new Handler();

        if (MainActivity.TTS) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initializeTextToSpeach.speak("Εισάγετε επιθυμητό ποσό");
                }
            }, 100);
        }

        arrayOfButtons = new Button[10];
        arrayOfButtons[0] = binding.zero;
        arrayOfButtons[1] = binding.one;
        arrayOfButtons[2] = binding.two;
        arrayOfButtons[3] = binding.three;
        arrayOfButtons[4] = binding.four;
        arrayOfButtons[5] = binding.five;
        arrayOfButtons[6] = binding.six;
        arrayOfButtons[7] = binding.seven;
        arrayOfButtons[8] = binding.eight;
        arrayOfButtons[9] = binding.nine;




        for (int i = 0; i < arrayOfButtons.length; i++){
            final int num = i;
            arrayOfButtons[i].setOnClickListener(view -> {
                binding.amount.setText(binding.amount.getText().toString().replace("€","") + String.valueOf(num) + "€");

            });
        }

        binding.delete.setOnClickListener(view -> {
            if (binding.amount.getText().toString().length() > 1){
                StringBuilder updatedCode;
                if (binding.amount.getText().toString().length() == 2){
                    updatedCode = new StringBuilder("");;
                    binding.amount.setText(updatedCode);
                }
                else{
                    updatedCode = new StringBuilder(binding.amount.getText().toString());
                    updatedCode.deleteCharAt(updatedCode.length()-2);
                }
                binding.amount.setText(updatedCode);
            }

        });

        binding.cancelButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(Ewallet.this, MainActivity.class);
            Ewallet.this.startActivity(myIntent);
            finish();
        });

        binding.backButton.setOnClickListener(view -> {
            if (binding.cashBox.getVisibility() == View.VISIBLE){
                binding.mainLayout.setVisibility(View.VISIBLE);
                binding.choose.setVisibility(View.INVISIBLE);

                binding.cashBox.setVisibility(View.INVISIBLE);
                binding.cardBox.setVisibility(View.INVISIBLE);
            }
            else{
                Intent myIntent = new Intent(Ewallet.this, MoreScreen.class);
                Ewallet.this.startActivity(myIntent);
            }

        });

        binding.cashBox.setOnClickListener(view -> {
            Intent myIntent = new Intent(Ewallet.this, CashPayment.class);
            myIntent.putExtra("Activity", "e-wallet");
            myIntent.putExtra("amount", binding.amount.getText().toString().replace("€",""));

            myIntent.putExtra("demandedPrice",  binding.amount.getText().toString().replace("€",""));
            Ewallet.this.startActivity(myIntent);



        });

        binding.cardBox.setOnClickListener(view -> {
            Intent myIntent = new Intent(Ewallet.this, CardPayment.class);
            myIntent.putExtra("Activity", "e-wallet");
            myIntent.putExtra("amount", binding.amount.getText().toString().replace("€",""));
            Ewallet.this.startActivity(myIntent);

        });

        binding.enter.setOnClickListener(view -> {
            if (binding.amount.length() > 0){

                binding.mainLayout.setVisibility(View.INVISIBLE);
                binding.choose.setVisibility(View.VISIBLE);

                binding.cashBox.setVisibility(View.VISIBLE);
                binding.cardBox.setVisibility(View.VISIBLE);

                if (MainActivity.TTS) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initializeTextToSpeach.speak("Επιλέξτε τον τρόπο πληρωμής");
                        }
                    }, 100);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        binding = null;
        initializeTextToSpeach.destroy();
        super.onDestroy();
    }
}
