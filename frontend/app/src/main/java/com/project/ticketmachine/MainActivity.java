package com.project.ticketmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*import com.google.android.material.textfield.TextInputEditText;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;*/


public class MainActivity extends AppCompatActivity {


    public static final String MainServerIp = "10.0.2.2";
    public static final int MainServerPort = 8080;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton ticketBtn = (ImageButton) findViewById(R.id.ticketButton);
        ImageButton cardBtn = (ImageButton) findViewById(R.id.cardButton);

        ImageButton ticketInfoBtn = (ImageButton) findViewById(R.id.ticketInfoButton);
        ImageButton cardInfoBtn = (ImageButton) findViewById(R.id.cardInfoButton);

        ImageButton greeceBtn = (ImageButton) findViewById(R.id.greeceButton);
        ImageButton ukBtn = (ImageButton) findViewById(R.id.ukButton);
        ImageButton italyBtn = (ImageButton) findViewById(R.id.italyButton);
        ImageButton germanBtn = (ImageButton) findViewById(R.id.germanButton);
        ImageButton franceBtn = (ImageButton) findViewById(R.id.franceButton);
        ImageButton saudiArabiaBtn = (ImageButton) findViewById(R.id.saudiArabiaButton);
        ImageButton russiaBtn = (ImageButton) findViewById(R.id.russiaButton);

        ImageButton imageButtons[] = {greeceBtn, ukBtn, italyBtn, germanBtn, franceBtn, saudiArabiaBtn, russiaBtn};

        TextView ticketRechargeText = (TextView) findViewById(R.id.buy_or_recharge_ticket_text);
        TextView cardRechargeText = (TextView) findViewById(R.id.recharge_card_text);
        TextView ticketInfoText = (TextView) findViewById(R.id.ticketInfoText);
        TextView cardInfoText = (TextView) findViewById(R.id.cardInfoText);

        Button send_barcode = (Button) findViewById(R.id.send_barcode);
        TextInputEditText inputCode = (TextInputEditText) findViewById(R.id.card_barcode);


        ticketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, ProductScreen.class);
                myIntent.putExtra("key", "ticket");
                MainActivity.this.startActivity(myIntent);
            }
        });

        cardBtn.setId(45);

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ID1", String.valueOf(cardBtn.getId()));
                Intent myIntent = new Intent(MainActivity.this, ProductScreen.class);
                myIntent.putExtra("key", "card");
                MainActivity.this.startActivity(myIntent);
            }
        });

        ticketInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ID", String.valueOf(cardBtn.getId()));
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

        ukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setAlphaForLanguageButtons(imageButtons);
                ukBtn.setAlpha(1.0f);
                ticketInfoText.setText("\nPress here if you want to buy a new ticket or recharge existing ticket");
                cardInfoText.setText("\nPress here if you want to recharge your card. (Personalized, Anonymous, Unemployed Card)");
                ticketRechargeText.setText("Buy or Recharge \nTicket");
                cardRechargeText.setText("Recharge \nCard");

            }
        });

        greeceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setAlphaForLanguageButtons(imageButtons);
                greeceBtn.setAlpha(1.0f);
                ticketInfoText.setText("\nΠατήστε εδώ εάν θέλετε να εκδόσετε νέο εισιτήριο ή να επαναφορτίσετε κάποιο υπάρχον εισιτήριο.");
                cardInfoText.setText("\nΠατήστε εδώ εάν θέλετε να επαναφορτίσετε την κάρτα σας. (Προσωποποιημένη, Ανώνυμη, Κάρτα Ανέργων)");
                ticketRechargeText.setText("Αγορά ή Επαναφόρτιση \nΕισιτηρίου");
                cardRechargeText.setText("Επαναφόρτιση \nΚάρτας");


            }
        });

        italyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlphaForLanguageButtons(imageButtons);
                italyBtn.setAlpha(1.0f);
            }
        });

        germanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlphaForLanguageButtons(imageButtons);
                germanBtn.setAlpha(1.0f);
            }
        });

        franceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlphaForLanguageButtons(imageButtons);
                franceBtn.setAlpha(1.0f);
            }
        });

        saudiArabiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlphaForLanguageButtons(imageButtons);
                saudiArabiaBtn.setAlpha(1.0f);
            }
        });

        russiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlphaForLanguageButtons(imageButtons);
                russiaBtn.setAlpha(1.0f);
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

    private void setAlphaForLanguageButtons(ImageButton[] imageButtons) {
        for (int i = 0; i < imageButtons.length; i++)
            imageButtons[i].setAlpha(0.5f);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
