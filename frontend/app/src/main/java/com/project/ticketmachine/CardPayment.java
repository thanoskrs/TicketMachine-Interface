package com.project.ticketmachine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.ticketmachine.databinding.CardPaymentBinding;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CardPayment extends AppCompatActivity {

    private CardPaymentBinding binding;
    private String activity = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = CardPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String act = getIntent().getStringExtra("Activity");
        if (act != null)
            activity = act;

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
                Intent myIntent = new Intent(CardPayment.this, MainActivity.class);
                CardPayment.this.startActivity(myIntent);
                finish();
            }
        });

        // pay and update database
        binding.completePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.equals("e-wallet")){

                    String amount = getIntent().getStringExtra("amount");
                    String[] params = new String[2];
                    params[0] = String.valueOf(MainActivity.user.get("userID"));
                    params[1] = String.valueOf(Float.parseFloat(amount));

                    UpdateWallet updateWallet = new UpdateWallet();
                    updateWallet.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
                }
                else{
                    Intent myIntent = new Intent(CardPayment.this, OnPostPayment.class);
                    myIntent.putExtra("act" , "pay");
                    CardPayment.this.startActivity(myIntent);
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

            Intent myIntent = new Intent(CardPayment.this, OnPostPayment.class);
            myIntent.putExtra("act" , "e-wallet");
            CardPayment.this.startActivity(myIntent);
        }
    }
}