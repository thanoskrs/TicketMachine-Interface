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
        TextView ticketInfoText = (TextView) findViewById(R.id.ticketInfoText);
        TextView cardInfoText = (TextView) findViewById(R.id.cardInfoText);

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
    }


}