package com.project.ticketmachine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.project.ticketmachine.databinding.CodeRechargeBinding;

import org.bson.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class RechargeCode extends AppCompatActivity {
    Button[] arrayOfButtons;
    CodeRechargeBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = CodeRechargeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            String[] params = new String[2];
            params[0] = binding.cardBarcode.getText().toString();
            params[1] = "check";

            if (params[0].length() > 0){

                binding.keyboard.setVisibility(View.INVISIBLE);
                binding.delete.setVisibility(View.INVISIBLE);
                binding.enter.setVisibility(View.INVISIBLE);
                binding.textRecharge.setVisibility(View.INVISIBLE);
                binding.cardBarcode.setVisibility(View.INVISIBLE);
                binding.loadingPanel.setVisibility(View.VISIBLE);
                binding.cancelButton.setVisibility(View.INVISIBLE);
                binding.backButton.setVisibility(View.INVISIBLE);
                binding.cancelText.setVisibility(View.INVISIBLE);
                binding.backText.setVisibility(View.INVISIBLE);

                CheckCode checkCode = new CheckCode();
                checkCode.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
            }

        });

        binding.cancelButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(RechargeCode.this, MainActivity.class);
            RechargeCode.this.startActivity(myIntent);
        });

        binding.backButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(RechargeCode.this, MoreScreen.class);
            RechargeCode.this.startActivity(myIntent);
        });
    }

    private class CheckCode extends AsyncTask<String, String, String> {

        private Socket socket = null;
        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;

        @Override
        protected String doInBackground(String... strings) {

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

            try {
                String ID = strings[0];
                String task = strings[1];

                if (task.equals("check")){
                    Log.e("id" , ID);

                    objectOutputStream.writeUTF("check");
                    objectOutputStream.flush();

                    objectOutputStream.writeUTF(ID);
                    objectOutputStream.flush();

                    String exists = objectInputStream.readUTF();
                    if (exists.equals("Pass")){

                        MainActivity.user = (Document) objectInputStream.readObject();

                        String category = (String) MainActivity.user.get("Category");
                        String type = (String) MainActivity.user.get("Type");
                        String userID = (String) MainActivity.user.get("UserID");

                        Log.e("exists","In db. "+category +" "+type);

                        Intent myIntent = new Intent(RechargeCode.this, ProductScreen.class);
                        myIntent.putExtra("Type", type);
                        RechargeCode.this.startActivity(myIntent);

                    }
                    else{
                        Log.e("exists","Not in db.");
                        publishProgress("Done!");
                    }

                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            binding.loadingPanel.setVisibility(View.GONE);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            binding.keyboard.setVisibility(View.VISIBLE);
            binding.delete.setVisibility(View.VISIBLE);
            binding.enter.setVisibility(View.VISIBLE);
            binding.textRecharge.setVisibility(View.VISIBLE);
            binding.cardBarcode.setVisibility(View.VISIBLE);
            binding.loadingPanel.setVisibility(View.INVISIBLE);
            binding.cancelButton.setVisibility(View.VISIBLE);
            binding.backButton.setVisibility(View.VISIBLE);
            binding.cancelText.setVisibility(View.VISIBLE);
            binding.backText.setVisibility(View.VISIBLE);

            Context context = RechargeCode.this;
            CharSequence message = "Λάθος κωδικός.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        }
    }
}
