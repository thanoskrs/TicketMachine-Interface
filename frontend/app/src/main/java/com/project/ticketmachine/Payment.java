package com.project.ticketmachine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.project.ticketmachine.ui.uniform.UniformFragment;

import org.bson.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    protected static String ticketId = null;
    protected static String userId = null;
    protected static String type;
    ArrayList<String> multipleOrdersTickets;

    private int quantity = 1;
    protected String selected_activity = "";

    InitializeTextToSpeach initializeTextToSpeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        multipleOrdersTickets = new ArrayList<>();
        multipleOrdersTickets.add("90 λεπτών");
        multipleOrdersTickets.add("24 Ωρών");
        multipleOrdersTickets.add("Λεωφοριακές\n" +
                "Γραμμές Express");
        multipleOrdersTickets.add("Λεωφοριακές \n" +
                "Γραμμές Express");
        multipleOrdersTickets.add("90 Λεπτών\n" +
                "Συρμός Μετρό");
        multipleOrdersTickets.add("90 Λεπτών\n" +
                "Μετ'επιστροφής");


        MaterialButton backBtn = (MaterialButton) findViewById(R.id.payment_back_button);
        MaterialButton cancelBtn = (MaterialButton) findViewById(R.id.payment_cancel_button);
        ImageButton decreaseQuantityBtn = (ImageButton) findViewById(R.id.decrease_quantity_button);
        ImageButton increaseQuantityBtn = (ImageButton) findViewById(R.id.increase_quantity_button);
        MaterialButton pay_cash = (MaterialButton) findViewById(R.id.pay_cash_button);
        MaterialButton pay_card = (MaterialButton) findViewById(R.id.pay_card_button);

        TextView kindOfProductText = (TextView) findViewById(R.id.kind_product_chosen_text);
        TextView productText = (TextView) findViewById(R.id.product_chosen_text);
        TextView priceText = (TextView) findViewById(R.id.product_price_chosen_text);
        TextView totalPriceText = (TextView) findViewById(R.id.total_price_text);
        TextView productQuantityText = (TextView) findViewById(R.id.product_quantity);


        initializeTextToSpeach = new InitializeTextToSpeach(getApplicationContext());

        final Handler handler = new Handler();

        if (MainActivity.TTS) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initializeTextToSpeach.speak("Επιλέξτε τον τρόπο πληρωμής");
                }
            }, 500);
        }


        String selected = getIntent().getStringExtra("activity");
        if (selected != null)
            selected_activity = selected;
        else
            selected_activity = "";

        String product = getIntent().getStringExtra("duration");
        String price_str = getIntent().getStringExtra("price");
        String price1 = price_str.replace("Τιμή : ", "");
        String kind = getIntent().getStringExtra("kind");
        float price = Float.parseFloat(price1.replace(" €", ""));

        Log.e("get" , getIntent().getStringExtra("duration"));
        Log.e("get" , getIntent().getStringExtra("userID"));
        Log.e("get" , getIntent().getStringExtra("price"));
        Log.e("get" , getIntent().getStringExtra("ticketID"));
        Log.e("get" , getIntent().getStringExtra("kind"));

        String total_price = totalPriceText.getText().toString();

        ticketId = getIntent().getStringExtra("ticketID");
        userId = getIntent().getStringExtra("userID");
        type = getIntent().getStringExtra("Type");

        if (kind .equals("Airport"))
            kindOfProductText.setText(kindOfProductText.getText().toString() + "Αεροδρόμιο");
        else
            kindOfProductText.setText(kindOfProductText.getText().toString() + "Ενιαίο");

        productText.setText(productText.getText().toString() + product);
        priceText.setText(priceText.getText().toString() + String.format("%.2f", price)+"€");
        totalPriceText.setText(totalPriceText.getText().toString() + String.format("%.2f", price) +"€");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected_activity.equals("e-wallet")){
                    Intent myIntent = new Intent(Payment.this, Ewallet.class);
                    Payment.this.startActivity(myIntent);
                }
                else{
                    finish();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UniformFragment.inited = false;
                Intent myIntent = new Intent(Payment.this, MainActivity.class);
                Payment.this.startActivity(myIntent);
                finish();
            }
        });



        if (multipleOrdersTickets.contains(product)){

            decreaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantity = Integer.parseInt(productQuantityText.getText().toString());
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
                    quantity = Integer.parseInt(productQuantityText.getText().toString());
                    Log.e("quantity", String.valueOf(quantity));
                    if (quantity < 5) {
                        productQuantityText.setText(String.valueOf(++quantity));
                        totalPriceText.setText(total_price + String.format("%.2f", price*quantity) + "€");
                    } else {
                        Toast.makeText(getApplicationContext(), "Δε μπορείτε να επιλέξετε περισσότερα από 5 προϊόντα",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {
            productQuantityText.setVisibility(View.INVISIBLE);
            increaseQuantityBtn.setVisibility(View.INVISIBLE);
            decreaseQuantityBtn.setVisibility(View.INVISIBLE);
        }



        pay_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent myIntent = new Intent(Payment.this, CashPayment.class);
                myIntent.putExtra("Activity", "pay");
                myIntent.putExtra("demandedPrice", String.format("%.2f", price*quantity));
                Payment.this.startActivity(myIntent);

            }
        });

        pay_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Payment.this, CardPayment.class);
                Payment.this.startActivity(myIntent);


            }
        });

    }

    @Override
    protected void onDestroy() {
        initializeTextToSpeach.destroy();
        super.onDestroy();
    }

    protected static void doInPayment() {
        if (MainActivity.user.get("Type").equals("Ticket") && MainActivity.user.get("userName").equals("") && MainActivity.user.get("Category").equals("")){
            Document[] paramsDoc = new Document[1];
            paramsDoc[0] = MainActivity.user;

            Payment.InsertUser insertUser = new Payment.InsertUser();
            insertUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paramsDoc);
        }
        else{
            //update last product
            String[] params = new String[2];
            params[0] = userId;
            params[1] = ticketId;

            UpdateUser updateUser = new UpdateUser();
            updateUser.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        }
    }


    private static class InsertUser extends AsyncTask<Document, Document, Document> {

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

    private static class UpdateUser extends AsyncTask<String, String, String> {

        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;
        private Socket socket = null;


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
                String ticketID = strings[1];

                objectOutputStream.writeUTF("Update User");
                objectOutputStream.flush();

                objectOutputStream.writeUTF(userID);
                objectOutputStream.flush();

                objectOutputStream.writeUTF(ticketID);
                objectOutputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
}