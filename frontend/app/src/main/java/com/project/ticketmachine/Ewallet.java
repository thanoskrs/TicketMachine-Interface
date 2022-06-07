package com.project.ticketmachine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

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
    protected static float final_s = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = EWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        final_s = Float.parseFloat(MainActivity.user.get("Wallet").toString());
        binding.summaryMoneyText.setText("Σύνολο: "+MainActivity.user.get("Wallet").toString() + "€");

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

                String[] params = new String[2];
                params[0] = MainActivity.user.get("userID").toString();
                params[1] = binding.insertMoneyEditText.getText().toString();

                UpdateWallet updateWallet = new UpdateWallet();
                updateWallet.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);


            }
        });
    }


    private static class UpdateWallet extends AsyncTask<String, String, String> {

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
            final_s += Float.parseFloat(new_s);
            binding.summaryMoneyText.setText("Σύνολο: "+final_s + "€");
        }
    }
}
