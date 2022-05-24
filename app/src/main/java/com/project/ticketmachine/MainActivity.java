package com.project.ticketmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
        TextView tickeInfoText = (TextView) findViewById(R.id.ticketInfoText);

        ticketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Ticket Button", Toast.LENGTH_SHORT).show();
            }
        });

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Card Button", Toast.LENGTH_SHORT).show();
            }
        });

        englishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Lan0", languageText.getText().toString());

                if (languageText.getText().toString().equals("EN")) {
                    Log.e("Lan1", "ok");
                    languageText.setText("GR");
                    englishBtn.setImageResource(R.drawable.greek);
                    ticketRechargeText.setText("Buy or Recharge \nTicket");
                    cardRechargeText.setText("Recharge \nCard");
                } else {
                    Log.e("Lan2", "ok");
                    languageText.setText("EN");
                    englishBtn.setImageResource(R.drawable.english);
                    ticketRechargeText.setText("Αγορά ή Επαναφόρτιση \nΕισιτηρίου");
                    cardRechargeText.setText("Επαναφόρτιση \nΚάρτας");
                }
            }
        });

        ticketInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ticketInfoBtn.getVisibility() == View.INVISIBLE) {
                    tickeInfoText.setVisibility(View.VISIBLE);
                }
                else {
                    tickeInfoText.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


}