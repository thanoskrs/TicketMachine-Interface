package com.project.ticketmachine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.ticketmachine.databinding.ActivityProductScreenBinding;
import com.project.ticketmachine.databinding.CheckCardBinding;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class CheckCard extends AppCompatActivity {

    public static Socket socket = null;
    public static ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;
    private CheckCardBinding binding;
    private String selected_box = null;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        System.out.println(extras.get("key"));
        selected_box = (String) extras.get("key");

        binding = CheckCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (selected_box.equals("Card")){
            binding.rechargeTicket.setVisibility(View.INVISIBLE);
            binding.recharge.setVisibility(View.INVISIBLE);
            binding.noRecharge.setVisibility(View.INVISIBLE);

            binding.ticketInfoText.setVisibility(View.VISIBLE);
            binding.cardBarcode.setVisibility(View.VISIBLE);
            binding.sendBarcode.setVisibility(View.VISIBLE);
        }

        binding.recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rechargeTicket.setVisibility(View.INVISIBLE);
                binding.recharge.setVisibility(View.INVISIBLE);
                binding.noRecharge.setVisibility(View.INVISIBLE);

                binding.ticketInfoText.setVisibility(View.VISIBLE);
                binding.cardBarcode.setVisibility(View.VISIBLE);
                binding.sendBarcode.setVisibility(View.VISIBLE);

            }
        });

        binding.noRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] params = new String[2];
                params[0] = "";
                params[1] = "connect";

                CheckCode checkCode = new CheckCode();
                checkCode.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);


                Intent myIntent = new Intent(CheckCard.this, ProductScreen.class);
                myIntent.putExtra("Type", MainActivity.type);
                myIntent.putExtra("Category", MainActivity.category);
                CheckCard.this.startActivity(myIntent);
            }
        });

        binding.sendBarcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                //send to the server the barcode code
                String[] params = new String[2];
                params[0] = binding.cardBarcode.getText().toString();
                params[1] = "check";

                if (params[0].length() > 0){
                    CheckCode checkCode = new CheckCode();
                    checkCode.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence message = "Εισάγετε τον κωδικό της κάρτα σας.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, message, duration);
                    toast.show();
                }
            }
        });

    }

    private class CheckCode extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            if (socket == null){
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
                        MainActivity.category = objectInputStream.readUTF();
                        MainActivity.type = objectInputStream.readUTF();
                        Log.e("exists","In db. "+MainActivity.category +" "+MainActivity.type);

                        if (Objects.equals(MainActivity.type, selected_box)){
                            if (MainActivity.category.equals("Anonymus")){
                                objectOutputStream.writeUTF("Ticket");
                                objectOutputStream.flush();
                            }
                            else{
                                objectOutputStream.writeUTF(MainActivity.type);
                                objectOutputStream.flush();
                            }


                            Intent myIntent = new Intent(CheckCard.this, ProductScreen.class);
                            myIntent.putExtra("Type", MainActivity.type);
                            myIntent.putExtra("Category", MainActivity.category);
                            CheckCard.this.startActivity(myIntent);
                        }
                        else{
                            Log.e("error not match" , MainActivity.type);
                        }
                    }
                    else{
                        Log.e("exists","Not in db.");
//                    Context context = MainActivity.this;
//                    CharSequence message = "Λάθος κωδικός.";
//                    int duration = Toast.LENGTH_SHORT;
//
//                    Toast toast = Toast.makeText(context, message, duration);
//                    toast.show();
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}
