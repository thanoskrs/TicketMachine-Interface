package com.project.ticketmachine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.ticketmachine.databinding.CashPaymentBinding;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CashPayment extends AppCompatActivity {

    public static CashPaymentBinding binding;
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

        String activity = getIntent().getStringExtra("Activity");

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

               // System.out.println(inserted);
                System.out.println(remainingPrice);
                System.out.println(String.format("%.2f", remainingPrice));

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

                    if (activity.equals("e-wallet")){

                        String amount = getIntent().getStringExtra("amount");
                        String[] params = new String[2];
                        params[0] = String.valueOf(MainActivity.user.get("userID"));
                        params[1] = String.valueOf(Float.parseFloat(amount));

                        UpdateWallet updateWallet = new UpdateWallet();
                        updateWallet.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
                    }
                    else{
                        Intent myIntent = new Intent(CashPayment.this, OnPostPayment.class);
                        myIntent.putExtra("act" , "pay");
                        CashPayment.this.startActivity(myIntent);
                    }


                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private  class UpdateWallet extends AsyncTask<String, String, String> {

        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;
        private Socket socket = null;
        private String new_s = "";

        @Override
        protected String doInBackground(String... strings) {

            try {

                if (MainActivity.socket == null){
                    //connect to DB
                    try {
                        socket = new Socket(MainActivity.MainServerIp , MainActivity.MainServerPort);
                        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectInputStream = new ObjectInputStream(socket.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String userID = strings[0];
                new_s = strings[1];

                objectOutputStream.writeUTF("UpdateWallet");
                objectOutputStream.flush();

                objectOutputStream.writeUTF(userID);
                objectOutputStream.flush();

                objectOutputStream.writeUTF(new_s);
                objectOutputStream.flush();




            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent myIntent = new Intent(CashPayment.this, OnPostPayment.class);
            myIntent.putExtra("act" , "e-wallet");
            CashPayment.this.startActivity(myIntent);
        }
    }
}