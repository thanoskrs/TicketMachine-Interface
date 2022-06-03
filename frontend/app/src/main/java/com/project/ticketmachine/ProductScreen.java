package com.project.ticketmachine;

import static com.project.ticketmachine.MainActivity.MainServerIp;
import static com.project.ticketmachine.MainActivity.MainServerPort;

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
import com.project.ticketmachine.ui.uniform.UniformFragment;

import org.bson.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ProductScreen extends AppCompatActivity {

    private ActivityProductScreenBinding binding;
    private String product_kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityProductScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean showLastProductScreen = false;

        if (MainActivity.user != null){
            showLastProductScreen = (Boolean) MainActivity.user.get("LastProductScreen");
        }
        else{
            Random random = new Random();
            int id = random.nextInt(900) + 100;

            MainActivity.user = new Document();
            MainActivity.user.append("userID", id);
            MainActivity.user.append("userName", "");
            MainActivity.user.append("Student", false);
            MainActivity.user.append("Unemployed", false);
            MainActivity.user.append("LastProductScreen", false);


           //
        }


        if (!showLastProductScreen){
            binding.softBackground.setVisibility(View.INVISIBLE);
            binding.repeatOrderLayout.setVisibility(View.INVISIBLE);

            String[] params = new String[3];

            String category = (String) MainActivity.user.get("Category");
            String type = (String) MainActivity.user.get("Type");
            String userID = (String) MainActivity.user.get("userID");

            params[0] = category;
            params[1] = type;
            params[2] = userID;

            GetTickets getTickets = new GetTickets();
            getTickets.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {

            String[] params = new String[1];
            params[0] = String.valueOf(MainActivity.user.get("LastProductId"));

            LoadInfoForLastProductScreen loadLastProductScreen = new LoadInfoForLastProductScreen();
            loadLastProductScreen.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        }


        binding.cancelRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.softBackground.setVisibility(View.INVISIBLE);

                String[] params = new String[3];

                String category = (String) MainActivity.user.get("Category");
                String type = (String) MainActivity.user.get("Type");
                String userID = (String) MainActivity.user.get("UserID");

                params[0] = category;
                params[1] = type;
                params[2] = userID;

                GetTickets getTickets = new GetTickets();
                getTickets.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);


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

    private class GetTickets extends AsyncTask<String, String, String> {

        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;
        private Socket socket = null;


        @Override
        protected String doInBackground(String... strings) {

            try {

                if (socket == null){
                    //connect to DB
                    try {
                        socket = new Socket(MainActivity.MainServerIp , MainActivity.MainServerPort);
                        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectInputStream = new ObjectInputStream(socket.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String category = strings[0];
                String type = strings[1];
                String userid = strings[2];

                Log.e("category" , category);
                Log.e("type" , type);
                Log.e("userID" , userid);


                if (category.equals("Anonymus")){
                    objectOutputStream.writeUTF("Ticket");
                    objectOutputStream.flush();
                }
                else{
                    objectOutputStream.writeUTF(type);
                    objectOutputStream.flush();
                }



                int list_len = objectInputStream.readInt();
                System.out.println(list_len);
                MainActivity.list = new ArrayList<>();

                for (int i =0; i < list_len; i++){
                    MainActivity.list.add((Document) objectInputStream.readObject());
                }
                for (int i =0; i < list_len; i++){
                    System.out.println(MainActivity.list.get(i));
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            return null;
        }



    }


    private class LoadInfoForLastProductScreen extends AsyncTask<String, String, String> {

        private Socket socket = null;
        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;

        @Override
        protected String doInBackground(String... strings) {

            String ticketId = strings[0];

            try {
                socket = new Socket(MainServerIp , MainServerPort);

                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                objectOutputStream.writeUTF("LastProductScreen");
                objectOutputStream.flush();

                objectOutputStream.writeUTF(ticketId);
                objectOutputStream.flush();

                Document ticket = (Document) objectInputStream.readObject();
                String kind = (String) ticket.get("Kind");
                String duration = (String) ticket.get("Name");
                String price;

                if (MainActivity.user.get("Category").equals("Student")) {
                    price = (String) ticket.get("Student Price");
                } else {
                    price = (String) ticket.get("Standard Price");
                }

                if (kind.equals("Uniform"))
                    binding.kindProductChosenText.setText(binding.kindProductChosenText.getText().toString() + "Ενιαίο");
                else
                    binding.kindProductChosenText.setText(binding.kindProductChosenText.getText().toString() + "Αεροδρόμιο");

                binding.durationProductChosenText.setText(binding.durationProductChosenText.getText().toString() + duration);

                binding.productPriceChosenText.setText(binding.productPriceChosenText.getText().toString() + price + "€");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}