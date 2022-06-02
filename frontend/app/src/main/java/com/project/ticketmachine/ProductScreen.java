package com.project.ticketmachine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.project.ticketmachine.databinding.ActivityProductScreenBinding;

import org.bson.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ProductScreen extends AppCompatActivity {

    private ActivityProductScreenBinding binding;
    private String product_kind;
    public static String category = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Bundle extras = getIntent().getExtras();

        System.out.println(extras.get("Type"));
        System.out.println(extras.get("Category"));


        if (extras.get("Type").equals("Ticket")){
            product_kind = "ticket";
            category = "undefined";
        }
        else{
            product_kind = "card";
            if (extras.get("Category").equals("Student")){
                category = "Student";
            }
            else if(extras.get("Category").equals("Anonymus")){
                category = "Anonymus";
            }
        }

        binding = ActivityProductScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (product_kind.equals("ticket")){
            binding.softBackground.setVisibility(View.INVISIBLE);
            binding.repeatOrderLayout.setVisibility(View.INVISIBLE);

            String[] params = new String[2];
            //params[0] = String.valueOf(extras.get("Student"));
            params[0] = category;
            params[1] = "Ticket";

            GetTickets getTickets = new GetTickets();
            getTickets.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        }
        else{
            String[] params = new String[2];
            if (Objects.equals(category, "Anonymus")){
                params[0] = String.valueOf(extras.get("Category"));
                params[1] = "Ticket";
            }
            else if (Objects.equals(category, "Student")){
                params[0] = String.valueOf(extras.get("Category"));
                params[1] = "Card";
            }
            GetTickets getTickets = new GetTickets();
            getTickets.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        }

        binding.cancelRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.softBackground.setVisibility(View.INVISIBLE);
            }
        });

        binding.repeatOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(ProductScreen.this, Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                ProductScreen.this.startActivity(paymentScreen);
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_uniform, R.id.navigation_airport)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_product_screen);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    public String getProduct_kind() {
        return product_kind;
    }


    private class GetTickets extends AsyncTask<String, String, String> {

        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;
        private Socket socket = null;


        @Override
        protected String doInBackground(String... strings) {

            try {

                objectOutputStream = CheckCard.objectOutputStream;
                objectInputStream = CheckCard.objectInputStream;

                String category = strings[0];
                String task = strings[1];
                Log.e("category" , category);
                Log.e("task" , task);



                int list_len = objectInputStream.readInt();
                System.out.println(list_len);
                ArrayList<Document> list = new ArrayList<>();

                for (int i =0; i < list_len; i++){
                    list.add((Document) objectInputStream.readObject());
                }
                for (int i =0; i < list_len; i++){
                    System.out.println(list.get(i));
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            return null;
        }



    }

}