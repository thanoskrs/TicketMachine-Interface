package com.project.ticketmachine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.project.ticketmachine.ui.airport.AirportFragment;
import com.project.ticketmachine.ui.uniform.UniformFragment;

import org.bson.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        MaterialButton backBtn = (MaterialButton) findViewById(R.id.payment_back_button);
        MaterialButton cancelBtn = (MaterialButton) findViewById(R.id.payment_cancel_button);
        ImageButton decreaseQuantityBtn = (ImageButton) findViewById(R.id.decrease_quantity_button);
        ImageButton increaseQuantityBtn = (ImageButton) findViewById(R.id.increase_quantity_button);
        MaterialButton pay_cash = (MaterialButton) findViewById(R.id.pay_cash_button);
        MaterialButton pay_card = (MaterialButton) findViewById(R.id.pay_card_button);

        TextView productText = (TextView) findViewById(R.id.product_chosen_text);
        TextView priceText = (TextView) findViewById(R.id.product_price_chosen_text);
        TextView totalPriceText = (TextView) findViewById(R.id.total_price_text);
        TextView productQuantityText = (TextView) findViewById(R.id.product_quantity);

        String product = getIntent().getStringExtra("duration");
        String price_str = getIntent().getStringExtra("price");
        String price1 = price_str.replace("Τιμή : ", "");
        float price = Float.parseFloat(price1.replace(" €", ""));

        Log.e("get" , getIntent().getStringExtra("duration"));
        Log.e("get" , getIntent().getStringExtra("userID"));
        Log.e("get" , getIntent().getStringExtra("price"));
     //   Log.e("get" , getIntent().getStringExtra("ticketID"));
        Log.e("get" , getIntent().getStringExtra("kind"));

        String total_price = totalPriceText.getText().toString();

        productText.setText(product);
        priceText.setText(priceText.getText().toString() + String.format("%.2f", price)+"€");
        totalPriceText.setText(totalPriceText.getText().toString() + String.format("%.2f", price) +"€");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Payment.this, ProductScreen.class);
                myIntent.putExtra("key", "card");
                myIntent.putExtra("Type", getIntent().getStringExtra("Type"));
                Payment.this.startActivity(myIntent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UniformFragment.inited = false;
                Intent myIntent = new Intent(Payment.this, MainActivity.class);
                Payment.this.startActivity(myIntent);
            }
        });

        decreaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(productQuantityText.getText().toString());
                if (quantity > 1) {
                    productQuantityText.setText(String.valueOf(--quantity));
                    totalPriceText.setText(total_price + String.format("%.2f", price*quantity) + "€");
                } else {
                    Toast.makeText(getApplicationContext(), "Δε μπορείτε να επιλέξετε 0 προϊόντα",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        increaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(productQuantityText.getText().toString());
                Log.e("quantity", String.valueOf(quantity));
                if (quantity < 10) {
                    productQuantityText.setText(String.valueOf(++quantity));
                    totalPriceText.setText(total_price + String.format("%.2f", price*quantity) + "€");
                } else {
                    Toast.makeText(getApplicationContext(), "Δε μπορείτε να επιλέξετε περισσότερα από 10 προϊόντα",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        pay_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.user.get("Type").equals("Ticket") && MainActivity.user.get("userName").equals("") && MainActivity.user.get("Category").equals("")){
                    Document[] paramsDoc = new Document[1];
                    paramsDoc[0] = MainActivity.user;

                    Payment.InsertUser insertUser = new Payment.InsertUser();
                    insertUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paramsDoc);
                }
            }
        });

        pay_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.user.get("Type").equals("Ticket") && MainActivity.user.get("userName").equals("") && MainActivity.user.get("Category").equals("")){
                    Document[] paramsDoc = new Document[1];
                    paramsDoc[0] = MainActivity.user;

                    Payment.InsertUser insertUser = new Payment.InsertUser();
                    insertUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paramsDoc);
                }

            }
        });
    }

    private class InsertUser extends AsyncTask<Document, Document, Document> {

        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;
        private Socket socket = null;


        @Override
        protected Document doInBackground(Document... documents) {

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

                Document doc = documents[0];

                objectOutputStream.writeUTF("Insert User");
                objectOutputStream.flush();

                objectOutputStream.writeObject(doc);
                objectOutputStream.flush();



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
}
