package com.project.ticketmachine;

import static com.project.ticketmachine.MainActivity.MainServerIp;
import static com.project.ticketmachine.MainActivity.MainServerPort;
import static com.project.ticketmachine.MainActivity.objectInputStream;

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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Payment extends AppCompatActivity {

    MaterialButton backBtn = (MaterialButton) findViewById(R.id.payment_back_button);
    MaterialButton cancelBtn = (MaterialButton) findViewById(R.id.payment_cancel_button);
    MaterialButton payCashBtn = (MaterialButton) findViewById(R.id.pay_cash_button);
    MaterialButton payCardBtn = (MaterialButton) findViewById(R.id.pay_card_button);
    ImageButton decreaseQuantityBtn = (ImageButton) findViewById(R.id.decrease_quantity_button);
    ImageButton increaseQuantityBtn = (ImageButton) findViewById(R.id.increase_quantity_button);


    TextView productText = (TextView) findViewById(R.id.product_chosen_text);
    TextView priceText = (TextView) findViewById(R.id.product_price_chosen_text);
    TextView totalPriceText = (TextView) findViewById(R.id.total_price_text);
    TextView productQuantityText = (TextView) findViewById(R.id.product_quantity);

    String product = getIntent().getStringExtra("product");
    String price_str = getIntent().getStringExtra("price");
    float price = Float.parseFloat(price_str.replace("$", ""));

    String userId = null;
    String ticketId = null;

    String total_price = totalPriceText.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        productText.setText(product);
        priceText.setText(priceText.getText().toString() + price);
        totalPriceText.setText(totalPriceText.getText().toString() + price);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Payment.this, ProductScreen.class);
                myIntent.putExtra("key", "card");
                Payment.this.startActivity(myIntent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        payCashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] params = new String[2];
                params[0] = userId;
                params[1] = ticketId;
                UpdateUserInfoOnPayment updateUserInfo = new UpdateUserInfoOnPayment();
                updateUserInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
            }
        });

        payCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] params = new String[2];
                params[0] = userId;
                params[1] = ticketId;
                UpdateUserInfoOnPayment updateUserInfo = new UpdateUserInfoOnPayment();
                updateUserInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
            }
        });

    }



    private class UpdateUserInfoOnPayment extends AsyncTask<String, String, String> {

        private Socket socket = null;
        private ObjectOutputStream objectOutputStream;

        @Override
        protected String doInBackground(String... strings) {

            String userId = strings[0];
            String ticketId = strings[1];
            try {
                socket = new Socket(MainServerIp , MainServerPort);

                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                objectOutputStream.writeUTF("Payment");
                objectOutputStream.flush();

                objectOutputStream.writeUTF(userId);
                objectOutputStream.writeUTF(ticketId);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    objectOutputStream.close();
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }
    }
}
