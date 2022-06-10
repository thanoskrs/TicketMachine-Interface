package com.project.ticketmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.project.ticketmachine.databinding.ActivityMainBinding;

import org.bson.Document;

import org.bson.Document;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    public static final String MainServerIp = "192.168.1.9";
    public static final int MainServerPort = 8080;
    public static Socket socket = null;
    private String student = "";
    private boolean checked = false;
    public static String category = "";
    public static String type = "";

    ActivityMainBinding binding;

    public static ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;
    public static Document user = null;

    public static HashMap<String , String> ProductCodes;

    InitializeTextToSpeach initializeTextToSpeach;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeTextToSpeach = new InitializeTextToSpeach(getApplicationContext());

        ProductCodes = new HashMap<>();
        ProductCodes.put("12345678" , "airport_box2_card");
        ProductCodes.put("87654321" , "uniform_box4_card");

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


        ticketBtn.setOnClickListener(view ->{

            Intent myIntent = new Intent(MainActivity.this, CheckCard.class);
                myIntent.putExtra("key", "Ticket");
                MainActivity.this.startActivity(myIntent);
        });


        cardBtn.setOnClickListener(view -> {

            Intent myIntent = new Intent(MainActivity.this, CheckCard.class);
            myIntent.putExtra("key", "Card");
            MainActivity.this.startActivity(myIntent);

        });

        ticketInfoBtn.setOnClickListener(view -> {
            Log.e("ID", String.valueOf(cardBtn.getId()));
            if (ticketInfoText.getVisibility() == View.INVISIBLE) {
                ticketInfoText.setVisibility(View.VISIBLE);
                ticketRechargeText.setVisibility(View.INVISIBLE);
                ticketInfoBtn.setImageResource(R.drawable.info_pressed_icon);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initializeTextToSpeach.speak(ticketInfoText.getText().toString());
                    }
                }, 300);
            }
            else {
                ticketInfoText.setVisibility(View.INVISIBLE);
                ticketRechargeText.setVisibility(View.VISIBLE);
                ticketInfoBtn.setImageResource(R.drawable.info_icon);
            }
        });

        cardInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardInfoText.getVisibility() == View.INVISIBLE) {
                    cardInfoText.setVisibility(View.VISIBLE);
                    cardRechargeText.setVisibility(View.INVISIBLE);
                    cardInfoBtn.setImageResource(R.drawable.info_pressed_icon);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initializeTextToSpeach.speak(cardInfoText.getText().toString());
                        }
                    }, 300);
                }
                else {
                    cardInfoText.setVisibility(View.INVISIBLE);
                    cardRechargeText.setVisibility(View.VISIBLE);
                    cardInfoBtn.setImageResource(R.drawable.info_icon);
                }
            }
        });

        binding.moreBox.setOnClickListener(view -> {
            Log.i("click" , "more button");

            Intent myIntent = new Intent(MainActivity.this, MoreScreen.class);
         //   myIntent.putExtra("key", "Card");
            MainActivity.this.startActivity(myIntent);
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




    }

    private void setAlphaForLanguageButtons(ImageButton[] imageButtons) {
        for (int i = 0; i < imageButtons.length; i++)
            imageButtons[i].setAlpha(0.5f);
    }


    @Override
    protected void onDestroy() {
        initializeTextToSpeach.destroy();
        super.onDestroy();
    }
}
