package com.project.ticketmachine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.project.ticketmachine.databinding.CodeRechargeBinding;
import com.project.ticketmachine.ui.uniform.UniformFragment;

import org.bson.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class RechargeCode extends AppCompatActivity {
    Button[] arrayOfButtons;
    public static CodeRechargeBinding binding;

    InitializeTextToSpeach initializeTextToSpeach;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = CodeRechargeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeTextToSpeach = new InitializeTextToSpeach(getApplicationContext());

        final Handler handler = new Handler();

        if (MainActivity.TTS) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initializeTextToSpeach.speak("Εισάγετε τον κωδικό της κράτησης");
                }
            }, 300);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
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
                binding.cardBarcode.setText(binding.cardBarcode.getText() + String.valueOf(num));
            });
        }

        binding.delete.setOnClickListener(view -> {
            if (binding.cardBarcode.getText().toString().length() > 0){
                StringBuilder updatedCode = new StringBuilder(binding.cardBarcode.getText().toString());
                updatedCode.deleteCharAt(updatedCode.length()-1);
                binding.cardBarcode.setText(updatedCode);
            }
        });

        binding.enter.setOnClickListener(view -> {
            //send to the server the barcode code


            if (binding.cardBarcode.getText().toString().length() > 0){

                if (MainActivity.ProductCodes.containsKey(binding.cardBarcode.getText().toString())){

                    binding.keyboard.setVisibility(View.INVISIBLE);
                    binding.textRecharge.setVisibility(View.INVISIBLE);
                    binding.cardBarcode.setVisibility(View.INVISIBLE);
                    binding.loadingPanel.setVisibility(View.VISIBLE);
                    binding.cancelButton.setVisibility(View.INVISIBLE);
                    binding.backButton.setVisibility(View.INVISIBLE);
                    binding.cancelText.setVisibility(View.INVISIBLE);
                    binding.backText.setVisibility(View.INVISIBLE);

                    Intent myIntent = new Intent(RechargeCode.this, OnPostPayment.class);
                    myIntent.putExtra("act" , "recharge");
                    RechargeCode.this.startActivity(myIntent);


                }
                else{
                    Context context = RechargeCode.this;
                    CharSequence message = "Λάθος κωδικός.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, message, duration);
                    toast.show();
                }

            }

        });

        binding.cancelButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(RechargeCode.this, MainActivity.class);
            RechargeCode.this.startActivity(myIntent);
            finish();
        });

        binding.backButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(RechargeCode.this, MoreScreen.class);
            RechargeCode.this.startActivity(myIntent);
        });
    }

    @Override
    protected void onDestroy() {
        binding = null;
        initializeTextToSpeach.destroy();
        super.onDestroy();
    }
}
