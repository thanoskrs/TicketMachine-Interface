package com.project.ticketmachine.ui.airport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.project.ticketmachine.MainActivity;
import com.project.ticketmachine.Payment;
import com.project.ticketmachine.ProductScreen;
import com.project.ticketmachine.databinding.FragmentAirportBinding;
import com.project.ticketmachine.ui.uniform.UniformFragment;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AirportFragment extends Fragment {

    private FragmentAirportBinding binding;
    public static boolean inited = false;
    private static MaterialButton[] airport_ticket_arrayBox;
    private static MaterialButton[] airport_card_arrayBox;
    private static TextView[] airport_ticket_arrayDuration;
    private static TextView[] airport_ticket_arrayCost;
    private static TextView[] airport_card_arrayDuration;
    private static TextView[] airport_card_arrayCost;

    String product_kind = null;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AirportViewModel airportViewModel =
                new ViewModelProvider(this).get(AirportViewModel.class);

        binding = FragmentAirportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //boxes
        airport_ticket_arrayBox = new MaterialButton[4];
        airport_ticket_arrayBox[0] = binding.airportBox1Ticket;
        airport_ticket_arrayBox[1] = binding.airportBox2Ticket;
        airport_ticket_arrayBox[2] = binding.airportBox3Ticket;
        airport_ticket_arrayBox[3] = binding.airportBox4Ticket;

        airport_card_arrayBox = new MaterialButton[6];
        airport_card_arrayBox[0] = binding.airportBox1Card;
        airport_card_arrayBox[1] = binding.airportBox2Card;
        airport_card_arrayBox[2] = binding.airportBox3Card;
        airport_card_arrayBox[3] = binding.airportBox4Card;
        airport_card_arrayBox[4] = binding.airportBox5Card;
        airport_card_arrayBox[5] = binding.airportBox6Card;

        //tickets

        airport_ticket_arrayDuration = new TextView[4];
        airport_ticket_arrayDuration[0] = binding.airportDurationBox1Ticket;
        airport_ticket_arrayDuration[1] = binding.airportDurationBox2Ticket;
        airport_ticket_arrayDuration[2] = binding.airportDurationBox3Ticket;
        airport_ticket_arrayDuration[3] = binding.airportDurationBox4Ticket;


        airport_ticket_arrayCost = new TextView[4];
        airport_ticket_arrayCost[0] = binding.airportCostBox1Ticket;
        airport_ticket_arrayCost[1] = binding.airportCostBox2Ticket;
        airport_ticket_arrayCost[2] = binding.airportCostBox3Ticket;
        airport_ticket_arrayCost[3] = binding.airportCostBox4Ticket;


        //cards

        airport_card_arrayDuration = new TextView[6];
        airport_card_arrayDuration[0] = binding.airportDurationBox1Card;
        airport_card_arrayDuration[1] = binding.airportDurationBox2Card;
        airport_card_arrayDuration[2] = binding.airportDurationBox3Card;
        airport_card_arrayDuration[3] = binding.airportDurationBox4Card;
        airport_card_arrayDuration[4] = binding.airportDurationBox5Card;
        airport_card_arrayDuration[5] = binding.airportDurationBox6Card;


        airport_card_arrayCost = new TextView[6];
        airport_card_arrayCost[0] = binding.airportCostBox1Card;
        airport_card_arrayCost[1] = binding.airportCostBox2Card;
        airport_card_arrayCost[2] = binding.airportCostBox3Card;
        airport_card_arrayCost[3] = binding.airportCostBox4Card;
        airport_card_arrayCost[4] = binding.airportCostBox5Card;
        airport_card_arrayCost[5] = binding.airportCostBox6Card;


        ProductScreen activity = (ProductScreen) getActivity();

        if (MainActivity.user == null){
            product_kind = "Ticket";
        }
        else{
            if (MainActivity.user.get("Category").equals("Anonymus"))
                product_kind = "Ticket";
            else
                product_kind = (String) MainActivity.user.get("Type");
        }

        if (product_kind.equals("Ticket")){
            binding.cardView.setVisibility(View.INVISIBLE);
            binding.ticketView.setVisibility(View.VISIBLE);

            // ticket - airport

            fill_tickets_airport();


        }
        else{
            binding.ticketView.setVisibility(View.INVISIBLE);
            binding.cardView.setVisibility(View.VISIBLE);


            // card - airport

            fill_cards_airport();

        }



        for (int i = 0; i < airport_card_arrayBox.length; i++){

            int finalI = i;
            MaterialButton box = airport_card_arrayBox[i];
            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent paymentScreen = new Intent(getActivity(), Payment.class);
                    paymentScreen.putExtra("userID", MainActivity.user.get("userID").toString());
                    paymentScreen.putExtra("duration", airport_card_arrayDuration[finalI].getText().toString());
                    paymentScreen.putExtra("price", airport_card_arrayCost[finalI].getText().toString());
                    paymentScreen.putExtra("ticketID", airport_card_arrayBox[finalI].getResources().getResourceEntryName(airport_card_arrayBox[finalI].getId()));
                    paymentScreen.putExtra("kind", "Airport");
                    paymentScreen.putExtra("Type", "Card");

                    //  Log.e("send_card_ticket" , String.valueOf(airport_card_arrayBox[finalI].getAccessibilityClassName()));
                    AirportFragment.this.startActivity(paymentScreen);
                }
            });
        }


        for (int i = 0; i < airport_ticket_arrayBox.length; i++){
            int finalI = i;
            MaterialButton box = airport_ticket_arrayBox[i];
            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent paymentScreen = new Intent(getActivity(), Payment.class);
                    paymentScreen.putExtra("userID", MainActivity.user.get("userID").toString());
                    paymentScreen.putExtra("duration", airport_ticket_arrayDuration[finalI].getText().toString());
                    paymentScreen.putExtra("price", airport_ticket_arrayCost[finalI].getText().toString());
                    paymentScreen.putExtra("ticketID", airport_ticket_arrayBox[finalI].getResources().getResourceEntryName(airport_ticket_arrayBox[finalI].getId()));
                    paymentScreen.putExtra("kind", "Airport");
                    paymentScreen.putExtra("Type", "Ticket");

                    Log.e("send_ticket" , airport_ticket_arrayBox[finalI].getResources().getResourceEntryName(airport_ticket_arrayBox[finalI].getId()));
                    AirportFragment.this.startActivity(paymentScreen);
                }
            });
        }


        binding.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.TicketsInfo.class);

                List<String> products = new ArrayList<String>();
                intent.putExtra("kind", "Airport");
                startActivity(intent);
            }
        });

        // on cancel button
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UniformFragment.inited = false;
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });



//        final TextView textView = binding.textNotifications;
//        airportViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @SuppressLint("SetTextI18n")
    public static void fill_tickets_airport(){

        int i = 1;
        for (Document document : ProductScreen.list) {
            if (Objects.equals(document.get("TicketID"), "airport_box" + i+"_ticket")) {
                airport_ticket_arrayDuration[i - 1].setText((String) document.get("Name"));
                airport_ticket_arrayCost[i - 1].setText("Τιμή : " + (String) document.get("Standard Price") + " €");
                i++;
            }
        }
        inited = true;


    }

    @SuppressLint("SetTextI18n")
    public static void fill_cards_airport(){


        // card - uniform


        int i = 1;
        for (Document document: ProductScreen.list){
            if (Objects.equals(document.get("TicketID"), "airport_box" + i + "_card")){
                airport_card_arrayDuration[i-1].setText((String)document.get("Name"));

                if (Objects.equals(MainActivity.user.get("Category"), "Student"))
                    airport_card_arrayCost[i-1].setText("Τιμή : "+(String)document.get("Student Price")+" €");
                else
                    airport_card_arrayCost[i-1].setText("Τιμή : "+(String)document.get("Standard Price")+" €");

                i++;
            }

        }

        inited = true;




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}