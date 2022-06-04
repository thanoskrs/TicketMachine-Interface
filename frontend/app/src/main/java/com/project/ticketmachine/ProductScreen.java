package com.project.ticketmachine;

import static com.project.ticketmachine.MainActivity.MainServerIp;
import static com.project.ticketmachine.MainActivity.MainServerPort;
import static com.project.ticketmachine.MainActivity.category;
import static com.project.ticketmachine.MainActivity.type;

import android.annotation.SuppressLint;
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

import com.google.android.material.internal.NavigationMenuItemView;
import com.project.ticketmachine.databinding.ActivityProductScreenBinding;
import com.project.ticketmachine.ui.airport.AirportFragment;
import com.project.ticketmachine.ui.uniform.UniformFragment;

import org.bson.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProductScreen extends AppCompatActivity {

    private ActivityProductScreenBinding binding;
    Document ticket = null;
    String price = null;
    String duration = null;
    String kind = null;
    public volatile boolean notify = false;
    public static ArrayList<Document> list = null;
    public static final AtomicBoolean processed = new AtomicBoolean(true) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        binding = ActivityProductScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String task = getIntent().getStringExtra("Type");
        if (task.equals("Simple Ticket")){

            binding.softBackground.setVisibility(View.INVISIBLE);
            binding.repeatOrderLayout.setVisibility(View.INVISIBLE);



            Random rand = new Random();
            int id = rand.nextInt(900) + 100;

            MainActivity.user = new Document();
            MainActivity.user.append("userID",String.valueOf(id));
            MainActivity.user.append("userName","");
            MainActivity.user.append("Category","");
            MainActivity.user.append("LastProductId","");
            MainActivity.user.append("LastProductScreen",false);
            MainActivity.user.append("Type","Ticket");


            String[] params = new String[3];
            params[0] = (String) MainActivity.user.get("Category");
            params[1] = (String) MainActivity.user.get("Type");
            params[2] = (String) MainActivity.user.get("userID");

            synchronized (processed){
                GetTickets getTickets = new GetTickets();
                getTickets.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
                try {
                    processed.wait();

                    UniformFragment.fill_cards_uniform();
                    UniformFragment.fill_tickets_uniform();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        else{
            boolean showLastProductScreen = (boolean) MainActivity.user.get("LastProductScreen");
            String lastProductId = (String) MainActivity.user.get("LastProductId");

            if (!showLastProductScreen || lastProductId.equals("")){
                binding.softBackground.setVisibility(View.INVISIBLE);
                binding.repeatOrderLayout.setVisibility(View.INVISIBLE);

                String[] params = new String[3];
                params[0] = (String) MainActivity.user.get("Category");
                params[1] = (String) MainActivity.user.get("Type");
                params[2] = (String) MainActivity.user.get("userID");


                synchronized (processed){
                    GetTickets getTickets = new GetTickets();
                    getTickets.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
                    try {
                        processed.wait();

                        UniformFragment.fill_cards_uniform();
                        UniformFragment.fill_tickets_uniform();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                Log.e("notify",String.valueOf(processed));

            } else {

                String[] params = new String[1];
                params[0] = String.valueOf(MainActivity.user.get("LastProductId"));

                LoadInfoForLastProductScreen loadLastProductScreen = new LoadInfoForLastProductScreen();
                loadLastProductScreen.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);


            }
        }

        binding.cancelRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.softBackground.setVisibility(View.INVISIBLE);

                String[] params = new String[3];
                params[0] = (String) MainActivity.user.get("Category");
                params[1] = (String) MainActivity.user.get("Type");
                params[2] = (String) MainActivity.user.get("userID");

                synchronized (processed){
                    GetTickets getTickets = new GetTickets();
                    getTickets.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
                    try {
                        processed.wait();

                        if (Objects.equals(MainActivity.user.get("Category"), "Anonymus") || Objects.equals(MainActivity.user.get("Category"), "")){
                            UniformFragment.fill_cards_uniform();
                            UniformFragment.fill_tickets_uniform();
                        }
                        else{
                            UniformFragment.fill_tickets_uniform();
                            UniformFragment.fill_cards_uniform();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        binding.repeatOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(ProductScreen.this, Payment.class);
                paymentScreen.putExtra("userID", MainActivity.user.get("userID").toString());
                paymentScreen.putExtra("ticketID", ticket.get("TicketID").toString());
                paymentScreen.putExtra("kind", kind);
                paymentScreen.putExtra("duration", duration);
                paymentScreen.putExtra("price", price);
                paymentScreen.putExtra("Type", MainActivity.user.get("Type").toString());

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
        navView.getMenu().getItem(0).setChecked(true);

    }

    private class GetTickets extends AsyncTask<String, String, String> {

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

                String category = strings[0];
                String type = strings[1];
                String userid = strings[2];

                Log.e("category" , String.valueOf(category));
                Log.e("type" , String.valueOf(type));
                Log.e("userID" , String.valueOf(userid));


                if (category.equals("Anonymus")){
                    objectOutputStream.writeUTF("Ticket");
                    objectOutputStream.flush();
                }
                else{
                    objectOutputStream.writeUTF(type);
                    objectOutputStream.flush();
                }

                int list_len = objectInputStream.readInt();
                ProductScreen.list = new ArrayList<>();

                for (int i =0; i < list_len; i++){
                    ProductScreen.list.add((Document) objectInputStream.readObject());
                }
                for (int i =0; i < list_len; i++){
                    System.out.println(ProductScreen.list.get(i));
                }

                synchronized (processed){
                    Log.e("proc" , String.valueOf(processed));
                    processed.notifyAll();
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

        @SuppressLint("SetTextI18n")
        @Override
        protected String doInBackground(String... strings) {

            String ticketId = strings[0];

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

                objectOutputStream.writeUTF("LastProductScreen");
                objectOutputStream.flush();

                objectOutputStream.writeUTF(ticketId);
                objectOutputStream.flush();

                ticket = (Document) objectInputStream.readObject();
                kind = (String) ticket.get("Kind");
                duration = (String) ticket.get("Name");

                if (Objects.equals(MainActivity.user.get("Category"), "Student")) {
                    price = (String) ticket.get("Student Price");
                }
                else {
                    price = (String) ticket.get("Standard Price");
                }

                publishProgress("Done!");


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            CheckCard.loading.setVisibility(View.GONE);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            if (kind.equals("Uniform"))
                binding.kindProductChosenText.setText(binding.kindProductChosenText.getText().toString() + "Ενιαίο");
            else
                binding.kindProductChosenText.setText(binding.kindProductChosenText.getText().toString() + "Αεροδρόμιο");

            binding.durationProductChosenText.setText(binding.durationProductChosenText.getText().toString() + duration);

            binding.productPriceChosenText.setText(binding.productPriceChosenText.getText().toString() + price + "€");
        }
    }

}

