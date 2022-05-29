package com.project.ticketmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public static final String MainServerIp = "10.0.2.2";
    public static final int MainServerPort = 8080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton ticketBtn = (ImageButton) findViewById(R.id.ticketButton);
        ImageButton cardBtn = (ImageButton) findViewById(R.id.cardButton);
        ImageButton englishBtn = (ImageButton) findViewById(R.id.englishButton);
        ImageButton ticketInfoBtn = (ImageButton) findViewById(R.id.ticketInfoButton);
        ImageButton cardInfoBtn = (ImageButton) findViewById(R.id.cardInfoButton);

        TextView languageText = (TextView) findViewById(R.id.language_text);
        TextView ticketRechargeText = (TextView) findViewById(R.id.buy_or_recharge_ticket_text);
        TextView cardRechargeText = (TextView) findViewById(R.id.recharge_card_text);
        TextView ticketInfoText = (TextView) findViewById(R.id.ticketInfoText);
        TextView cardInfoText = (TextView) findViewById(R.id.cardInfoText);
        Button send_barcode = (Button) findViewById(R.id.send_barcode);
        TextInputEditText inputCode = (TextInputEditText) findViewById(R.id.card_barcode);

        ticketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(getApplicationContext(), "Ticket Button", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, ProductScreen.class);
                myIntent.putExtra("key", "ticket"); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, ProductScreen.class);
                myIntent.putExtra("key", "card"); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                //Toast.makeText(getApplicationContext(), "Card Button", Toast.LENGTH_SHORT).show();

            }
        });

        englishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (languageText.getText().toString().equals("EN")) {
                    languageText.setText("GR");
                    englishBtn.setImageResource(R.drawable.greek);
                    ticketInfoText.setText("\nPress here if you want to buy a new ticket or recharge existing ticket");
                    cardInfoText.setText("\nPress here if you want to recharge your card. (Personalized, Anonymous, Unemployed Card)");
                    ticketRechargeText.setText("Buy or Recharge \nTicket");
                    cardRechargeText.setText("Recharge \nCard");
                } else {
                    languageText.setText("EN");
                    englishBtn.setImageResource(R.drawable.english);
                    ticketInfoText.setText("\nΠατήστε εδώ εάν θέλετε να εκδόσετε νέο εισιτήριο ή να επαναφορτίσετε κάποιο υπάρχον εισιτήριο.");
                    cardInfoText.setText("\nΠατήστε εδώ εάν θέλετε να επαναφορτίσετε την κάρτα σας. (Προσωποποιημένη, Ανώνυμη, Κάρτα Ανέργων)");
                    ticketRechargeText.setText("Αγορά ή Επαναφόρτιση \nΕισιτηρίου");
                    cardRechargeText.setText("Επαναφόρτιση \nΚάρτας");
                }
            }
        });

        ticketInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ticketInfoText.getVisibility() == View.INVISIBLE) {
                    ticketInfoText.setVisibility(View.VISIBLE);
                    ticketRechargeText.setVisibility(View.INVISIBLE);
                    ticketInfoBtn.setImageResource(R.drawable.info_pressed_icon);
                }
                else {
                    ticketInfoText.setVisibility(View.INVISIBLE);
                    ticketRechargeText.setVisibility(View.VISIBLE);
                    ticketInfoBtn.setImageResource(R.drawable.info_icon);
                }
            }
        });

        cardInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardInfoText.getVisibility() == View.INVISIBLE) {
                    cardInfoText.setVisibility(View.VISIBLE);
                    cardRechargeText.setVisibility(View.INVISIBLE);
                    cardInfoBtn.setImageResource(R.drawable.info_pressed_icon);
                }
                else {
                    cardInfoText.setVisibility(View.INVISIBLE);
                    cardRechargeText.setVisibility(View.VISIBLE);
                    cardInfoBtn.setImageResource(R.drawable.info_icon);
                }
            }
        });

        send_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //send to the server the barcode code
                String[] params = new String[2];
                params[0] = inputCode.toString();
                CheckCode checkCode = new CheckCode();
                checkCode.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);

            }
        });
    }

    private class CheckCode extends AsyncTask<String, String, String>{
        private Socket socket = null;
        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;


        @Override
        protected String doInBackground(String... strings) {

            try {
                socket = new Socket(MainServerIp , MainServerPort);

                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());

                objectOutputStream.writeUTF("check");
                objectOutputStream.flush();

                int code = Integer.parseInt(strings[0]);

                objectOutputStream.writeInt(code);
                objectOutputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}

